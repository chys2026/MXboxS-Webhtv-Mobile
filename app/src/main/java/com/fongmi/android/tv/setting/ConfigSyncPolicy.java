package com.silent.android.mxboxs.setting;

import android.text.TextUtils;

public final class ConfigSyncPolicy {

    private ConfigSyncPolicy() {}

    public static boolean shouldSyncLive(String previousVodUrl, String currentLiveUrl) {
        return TextUtils.equals(currentLiveUrl, previousVodUrl);
    }
}
