package com.silent.android.mxboxs.gitcloud;

import java.util.Map;

public class DownloadRef {

    public String url;
    public Map<String, String> headers;

    public DownloadRef(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
    }
}
