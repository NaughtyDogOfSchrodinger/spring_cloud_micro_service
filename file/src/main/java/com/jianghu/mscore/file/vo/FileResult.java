package com.jianghu.mscore.file.vo;

public class FileResult {

    private String rand;

    private String key;

    private String url;

    public FileResult(String rand) {
        this.rand = rand;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
