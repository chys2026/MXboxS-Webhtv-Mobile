package com.silent.android.mxboxs.subtitle;

import com.silent.android.mxboxs.bean.Sub;
import com.silent.android.mxboxs.subtitle.model.SubtitleAsset;

public final class SubtitleInjector {

    public Sub toSub(SubtitleAsset asset) {
        if (asset == null) return null;
        String url = isEmpty(asset.getLocalPath()) ? asset.getUri() : asset.getLocalPath();
        if (isEmpty(url)) return null;
        Sub sub = Sub.create(asset.getDisplayName(), url, asset.getLanguage(), asset.getMimeType());
        if (asset.getSelectionFlag() != 0) sub.setFlag(asset.getSelectionFlag());
        return sub;
    }

    private boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }
}
