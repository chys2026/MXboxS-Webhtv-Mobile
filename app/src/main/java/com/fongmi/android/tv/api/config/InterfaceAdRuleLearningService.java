package com.silent.android.mxboxs.api.config;

import com.silent.android.mxboxs.bean.Rule;
import com.silent.android.mxboxs.utils.Task;

import java.util.ArrayList;
import java.util.List;

public final class InterfaceAdRuleLearningService {

    private InterfaceAdRuleLearningService() {
    }

    public static void schedule(String sourceName, String sourceUrl, List<String> ads, List<Rule> rules) {
        List<String> adsSnapshot = ads == null ? List.of() : new ArrayList<>(ads);
        List<Rule> rulesSnapshot = rules == null ? List.of() : new ArrayList<>(rules);
        Task.submit(() -> ImportedAdRuleCandidateStore.merge(
                InterfaceAdRuleAnalyzer.analyze(sourceName, sourceUrl, adsSnapshot, rulesSnapshot)));
    }
}
