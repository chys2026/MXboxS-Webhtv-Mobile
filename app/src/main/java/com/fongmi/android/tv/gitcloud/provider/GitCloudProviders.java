package com.silent.android.mxboxs.gitcloud.provider;

import com.silent.android.mxboxs.gitcloud.GitProviderType;

public final class GitCloudProviders {

    private static final GitHubProvider GITHUB = new GitHubProvider();
    private static final CnbProvider CNB = new CnbProvider();

    private GitCloudProviders() {
    }

    public static GitCloudProvider get(GitProviderType type) {
        if (type == GitProviderType.CNB) return CNB;
        return GITHUB;
    }
}
