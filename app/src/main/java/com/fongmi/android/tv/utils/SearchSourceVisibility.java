package com.silent.android.mxboxs.utils;

public final class SearchSourceVisibility {

    private SearchSourceVisibility() {
    }

    public static boolean shouldShow(boolean hasVisibleResults) {
        return hasVisibleResults;
    }
}
