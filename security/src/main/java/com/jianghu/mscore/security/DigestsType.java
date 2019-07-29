package com.jianghu.mscore.security;

public enum DigestsType {
    MD5("MD5"),
    SHA1("SHA-1");

    private String name;

    private DigestsType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

