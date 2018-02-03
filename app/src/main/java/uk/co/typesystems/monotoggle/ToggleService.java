package uk.co.typesystems.monotoggle;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class ToggleService extends IntentService {
    private final String TAG = "ToggleService";

    public ToggleService() {
        super("ToggleService");
        Log.i(TAG, "Creating ToggleService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final MonochomeSetting monochomeSetting = new MonochomeSetting(this.getContentResolver());
        monochomeSetting.toggle();
        Log.i(TAG, "MonoToggle has run");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Destroy called");
        super.onDestroy();
    }
}
