package hr.foi.air.core;

public class GenerousJudge implements TokenJudge {
    @Override
    public boolean canGetToken() {
        return true;
    }

    @Override
    public String getDenialMessage() {
        return Core.getAppResources().getString(R.string.generous_denial_message);
    }
}
