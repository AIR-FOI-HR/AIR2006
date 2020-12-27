package com.example.drinkup.employee.ui.activation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.drinkup.R;
import com.example.drinkup.employee.EmployeeMainActivity;

public class TokenScanning extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_token_scanning, container, false);
        ((EmployeeMainActivity) getActivity()).customizeActionBar(getString(R.string.token_scanning_fragment_title));
        return root;
    }
}