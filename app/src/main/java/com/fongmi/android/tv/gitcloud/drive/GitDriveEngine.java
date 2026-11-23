package com.silent.android.mxboxs.gitcloud.drive;

import com.silent.android.mxboxs.gitcloud.GitCloudException;

import java.util.List;

public interface GitDriveEngine {

    GitDriveState bind(GitDriveConfig config) throws GitCloudException;

    GitDriveState pull(GitDriveConfig config) throws GitCloudException;

    CommitResult commitAndPush(GitDriveConfig config, List<FileChange> changes) throws GitCloudException;

    GitDriveState status(GitDriveConfig config) throws GitCloudException;

    void cancel(String taskId);
}
