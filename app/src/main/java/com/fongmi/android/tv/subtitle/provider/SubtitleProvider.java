package com.silent.android.mxboxs.subtitle.provider;

import com.silent.android.mxboxs.subtitle.model.SubtitleAsset;
import com.silent.android.mxboxs.subtitle.model.SubtitleCandidate;
import com.silent.android.mxboxs.subtitle.model.SubtitleContext;
import com.silent.android.mxboxs.subtitle.model.SubtitleQuery;

import java.util.List;

public interface SubtitleProvider {

    String getName();

    default String getKey() {
        return getName();
    }

    boolean isEnabled();

    default boolean isQueryIndependent() {
        return false;
    }

    List<SubtitleCandidate> search(SubtitleQuery query, SubtitleContext context) throws Exception;

    SubtitleAsset resolve(SubtitleCandidate candidate, SubtitleContext context) throws Exception;
}
