package com.silent.android.mxboxs.gitcloud.drive;

public class FileChange {

    public String path;
    public byte[] data;
    public boolean delete;

    public FileChange(String path, byte[] data) {
        this.path = path;
        this.data = data;
    }
}
