package uk.co.typesystems.monotoggle;

import android.content.ContentResolver;
import android.provider.Settings;
import android.util.Log;

import java.util.Optional;
import java.util.function.Function;

class MonochomeSetting {
    private ContentResolver cr = null;
    private final String SETTING_DALTONIZER_ENABLED = "accessibility_display_daltonizer_enabled";
    private final String SETTING_DALTONIZER = "accessibility_display_daltonizer";

    MonochomeSetting(ContentResolver cr) {
        this.cr = cr;
    }

    private Optional<Integer> secureSettingGetInt(String name) {
        try {
            return Optional.of(Settings.Secure.getInt(cr, name));
        } catch (Settings.SettingNotFoundException e) {
            Log.e("MonoToggle", "read " + name, e);
            return Optional.empty();
        }
    }

    private Optional<Boolean> isSet() {
        final Optional<Integer> daltonizerEnabledOpt = secureSettingGetInt(SETTING_DALTONIZER_ENABLED);
        final Optional<Integer> daltonizerOpt = secureSettingGetInt(SETTING_DALTONIZER);

        return daltonizerEnabledOpt.flatMap(new Function<Integer, Optional<Boolean>>() {
            @Override
            public Optional<Boolean> apply(final Integer daltonizerEnabled) {
                return daltonizerOpt.map(new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(final Integer daltonizer) {
                        return daltonizerEnabled != 0 && daltonizer == 0;
                    }
                });
            }
        });
    }

    void toggle() {
        final Optional<Boolean> set = isSet();
        if (set.isPresent()) {
            if (set.orElse(true)) {
                Settings.Secure.putInt(cr, SETTING_DALTONIZER_ENABLED, 0);
                Settings.Secure.putInt(cr, SETTING_DALTONIZER, -1);
            }
            else {
                Settings.Secure.putInt(cr, SETTING_DALTONIZER_ENABLED, 1);
                Settings.Secure.putInt(cr, SETTING_DALTONIZER, 0);
            }
        }
    }
}
