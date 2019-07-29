package com.jianghu.mscore.web.constant;

/**
 * 设备终端枚举
 *
 * @author hujiang.
 * @version 1.0
 * @since 2018.12.19
 */
public enum PlatformEnum {

    /**
     * IOS
     */
    IOS(1, "IOS"),

    /**
     * Android
     */
    Android(2, "Android"),

    /**
     * PC
     */
    PC(3, "PC");

    private int number;

    private String name;

    PlatformEnum(int number, String name) {
        this.number = number;
        this.name = name;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }}
