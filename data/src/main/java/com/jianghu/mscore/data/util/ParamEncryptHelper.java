package com.jianghu.mscore.data.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

import com.jianghu.mscore.data.annotation.Encrypt;
import com.jianghu.mscore.util.EncryptUtil;
import com.jianghu.mscore.util.ObjectUtil;
import com.jianghu.mscore.util.ReflectUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamEncryptHelper {
    private static final Logger logger = LoggerFactory.getLogger(ParamEncryptHelper.class);

    public ParamEncryptHelper() {
    }

    public static void encrypt(Object object) {
        if (!ObjectUtil.isNullObj(object)) {
            Field[] fields = object.getClass().getDeclaredFields();
            if (!ArrayUtils.isEmpty(fields)) {
                Stream.of(fields).filter((field) -> {
                    return field.getAnnotation(Encrypt.class) != null;
                }).forEach((field) -> {
                    encryptField(object, field);
                });
            }
        }
    }

    private static void encryptField(Object object, Field field) {
        try {
            Object value = ReflectUtil.getValueByProperty(object, field.getName());
            field.setAccessible(true);
            field.set(object, EncryptUtil.encrypt(value + ""));
        } catch (Exception var3) {
            var3.printStackTrace();
            logger.debug("字段加密失败!" + var3.getMessage());
        }

    }

    public static void decrypt(List<Object> objects) {
        objects.stream().filter((object) -> {
            return object != null;
        }).forEach((object) -> {
            Field[] fields = object.getClass().getDeclaredFields();
            Stream.of(fields).filter((field) -> {
                return field.getAnnotation(Encrypt.class) != null;
            }).forEach((field) -> {
                decryptField(object, field);
            });
        });
    }

    private static void decryptField(Object object, Field field) {
        try {
            Object value = ReflectUtil.getValueByProperty(object, field.getName());
            field.setAccessible(true);
            String decrypt = EncryptUtil.decrypt(value + "");
            field.set(object, decrypt);
        } catch (Exception var4) {
            logger.debug("字段解密失败!" + var4.getMessage());
        }

    }
}

