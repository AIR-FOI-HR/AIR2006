package com.example.drinkup.employee.ui.activation;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.example.drinkup.R;
import com.example.drinkup.employee.EmployeeMainActivity;
import com.example.drinkup.services.RequestService;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

// reference: https://github.com/learntodroid/AndroidQRCodeScanner
public class TokenScanningFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private EmployeeMainActivity activity;

    private PreviewView cameraView;
    private FrameLayout progressBarHolder;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private final Timer timer = new Timer();
    private boolean toastMessageShown;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_token_scanning, container, false);
        activity = (EmployeeMainActivity) getActivity();
        activity.customizeActionBar(getString(R.string.token_scanning_fragment_title));
        cameraView = root.findViewById(R.id.cameraView);
        progressBarHolder = root.findViewById(R.id.loading);
        activity.runOnUiThread(() -> progressBarHolder.setVisibility(View.GONE));
        toastMessageShown = false;

        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
        requestCamera();
        return root;
    }

    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(activity, R.string.camera_permission_required, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(activity, getString(R.string.error_loading_camera) + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, ContextCompat.getMainExecutor(activity));
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        cameraView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(cameraView.createSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(activity), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
            @Override
            public void onQRCodeFound(String decodedQrCode) {
                if (progressBarHolder.getVisibility() == View.GONE) {
                    if (!toastMessageShown) {
                        progressBarHolder.setVisibility(View.VISIBLE);
                        String tokenId = decodedQrCode.substring(decodedQrCode.lastIndexOf('/') + 1);
                        new RequestService(getContext()).retrieveOfferDetails(tokenId,
                                new Consumer<JSONObject>() {
                                    @Override
                                    public void accept(JSONObject jsonObject) {
                                        if (jsonObject == null) {
                                            showAToast(R.string.token_no_longer_exists);
                                        } else {
                                            try {
                                                boolean alreadyUsed = jsonObject.getBoolean("iskoristen");
                                                String tokenId = jsonObject.getString("id");
                                                if (alreadyUsed) {
                                                    showAToast(R.string.token_already_redeemed);
                                                } else {
                                                    int barIdOfToken = jsonObject.getJSONObject("ponuda").getInt("objektId");
                                                    String offerTitle = jsonObject.getJSONObject("ponuda").getString("naslov");
                                                    String offerDescription = jsonObject.getJSONObject("ponuda").getString("opis");
                                                    if (barIdOfToken != activity.getWorkingBarId()) {
                                                        showAToast(R.string.token_not_applicable_here);
                                                    } else {
                                                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int option) {
                                                                switch (option) {
                                                                    case DialogInterface.BUTTON_POSITIVE:
                                                                        new RequestService(getContext()).redeemToken(tokenId,
                                                                                new Consumer<JSONObject>() {
                                                                                    @Override
                                                                                    public void accept(JSONObject jsonObject) {
                                                                                        showAToast(R.string.token_successfully_activated);
                                                                                    }
                                                                                }, new Consumer<VolleyError>() {
                                                                                    @Override
                                                                                    public void accept(VolleyError volleyError) {
                                                                                        showAToast(R.string.token_activation_failed);
                                                                                    }
                                                                                });
                                                                        break;
                                                                    case DialogInterface.BUTTON_NEGATIVE:
                                                                        progressBarHolder.setVisibility(View.GONE);
                                                                        break;
                                                                }
                                                            }
                                                        };

                                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                        final String messageContent;
                                                        if (offerDescription.isEmpty()) {
                                                            messageContent = MessageFormat.format(getString(R.string.redeem_token_confirmation_message_without_description), offerTitle);
                                                        } else {
                                                            messageContent = MessageFormat.format(getString(R.string.redeem_token_confirmation_message), offerTitle, offerDescription);
                                                        }
                                                        builder.setMessage(messageContent)
                                                                .setPositiveButton(R.string.redeem_option, dialogClickListener)
                                                                .setNegativeButton(R.string.reject_option, dialogClickListener)
                                                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                                    @Override
                                                                    public void onCancel(DialogInterface dialog) {
                                                                        progressBarHolder.setVisibility(View.GONE);
                                                                    }
                                                                })
                                                                .show();
                                                    }
                                                }
                                            } catch (JSONException e) {
                                                showAToast(R.string.token_info_retrieval_failed);
                                            }
                                        }
                                    }
                                }, new Consumer<VolleyError>() {
                                    @Override
                                    public void accept(VolleyError volleyError) {
                                        showAToast(R.string.token_info_retrieval_failed);
                                    }
                                });
                    }
                }
            }
        }));

        cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }

    private void showAToast(int textCode) {
        toastMessageShown = true;
        progressBarHolder.setVisibility(View.GONE);
        Toast.makeText(getContext(), textCode, Toast.LENGTH_LONG).show();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                toastMessageShown = false;
            }
        }, 5000);
    }
}