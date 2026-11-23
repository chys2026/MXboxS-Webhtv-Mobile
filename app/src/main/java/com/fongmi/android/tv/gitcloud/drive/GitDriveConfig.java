package com.silent.android.mxboxs.gitcloud.drive;

import com.silent.android.mxboxs.gitcloud.GitAccount;
import com.silent.android.mxboxs.gitcloud.GitRepo;

import java.io.File;

public class GitDriveConfig {

    public GitAccount account;
    public GitRepo repo;
    public String token;
    public String branch;
    public File worktreeDir;
    public String purpose;
    public String defaultRemotePath;

    public String branch() {
        return branch == null || branch.isEmpty() ? repo.defaultBranch : branch;
    }
}
