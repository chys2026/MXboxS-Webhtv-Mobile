package com.silent.android.mxboxs.setting;

public final class FlagSwitchPlaybackPolicy {

    private FlagSwitchPlaybackPolicy() {
    }

    public static boolean shouldDeferPlayback(boolean deferFlagSwitchPlayback, boolean programmaticSwitch) {
        return deferFlagSwitchPlayback && !programmaticSwitch;
    }
}
