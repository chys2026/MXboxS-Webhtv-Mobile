package com.silent.android.mxboxs.gitcloud.provider;

import com.silent.android.mxboxs.gitcloud.AccountInfo;
import com.silent.android.mxboxs.gitcloud.CreateRepoRequest;
import com.silent.android.mxboxs.gitcloud.DownloadRef;
import com.silent.android.mxboxs.gitcloud.GitAccount;
import com.silent.android.mxboxs.gitcloud.GitBranch;
import com.silent.android.mxboxs.gitcloud.GitCloudException;
import com.silent.android.mxboxs.gitcloud.GitFile;
import com.silent.android.mxboxs.gitcloud.GitFileContent;
import com.silent.android.mxboxs.gitcloud.GitProviderType;
import com.silent.android.mxboxs.gitcloud.GitRepo;
import com.silent.android.mxboxs.gitcloud.ProviderCapabilities;
import com.silent.android.mxboxs.gitcloud.SaveOptions;
import com.silent.android.mxboxs.gitcloud.SaveResult;

import java.util.List;

public interface GitCloudProvider {

    GitProviderType type();

    ProviderCapabilities capabilities();

    AccountInfo validateToken(GitAccount account, String token) throws GitCloudException;

    List<GitRepo> listRepos(GitAccount account, String token) throws GitCloudException;

    default List<GitRepo> searchRepos(GitAccount account, String token, String keyword) throws GitCloudException {
        throw new GitCloudException("当前平台不支持全局搜索仓库");
    }

    default GitRepo getRepo(GitAccount account, String token, String fullName) throws GitCloudException {
        throw new GitCloudException("当前平台不支持读取指定仓库");
    }

    default List<GitRepo> listUserRepos(GitAccount account, String token, String owner) throws GitCloudException {
        throw new GitCloudException("当前平台不支持查看用户仓库");
    }

    default GitRepo forkRepo(GitAccount account, String token, GitRepo repo) throws GitCloudException {
        throw new GitCloudException("当前平台不支持 Fork 仓库");
    }

    GitRepo createRepo(GitAccount account, String token, CreateRepoRequest request) throws GitCloudException;

    void deleteRepo(GitAccount account, String token, GitRepo repo) throws GitCloudException;

    List<GitBranch> listBranches(GitAccount account, String token, GitRepo repo) throws GitCloudException;

    List<GitFile> listFiles(GitAccount account, String token, GitRepo repo, String ref, String path) throws GitCloudException;

    GitFileContent readFile(GitAccount account, String token, GitRepo repo, String ref, String path) throws GitCloudException;

    SaveResult saveSmallFile(GitAccount account, String token, GitRepo repo, String branch, String path, byte[] data, SaveOptions options) throws GitCloudException;

    default void deleteFile(GitAccount account, String token, GitRepo repo, String branch, GitFile file, String message) throws GitCloudException {
        throw new GitCloudException("当前平台不支持直接删除文件");
    }

    String rawUrl(GitAccount account, GitRepo repo, String ref, String path);

    DownloadRef archiveUrl(GitAccount account, String token, GitRepo repo, String ref, String path);
}
