package com.jianghu.mscore.file.util;

import com.jianghu.mscore.file.constant.FileType;
import com.jianghu.mscore.file.exception.FileException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * The type File type util.
 * Description
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.05.20
 */
public final class FileTypeUtil {

    public static final String POINT = ".";

    public static final String SEPARATOR = "/";

    private static String getFileSuffix(String fileName) {
        if (StringUtils.isEmpty(fileName)) return StringUtils.EMPTY;
        int index;
        if ((index = fileName.lastIndexOf(POINT)) == -1) return StringUtils.EMPTY;
        return fileName.substring(index + 1);
    }

    public static String getFileSuffix(File file) {
        if (file == null) throw new FileException("文件不存在");
        return getFileSuffix(file.getName());
    }

    public static String getFileSuffix(MultipartFile file) {
        if (file == null) throw new FileException("文件不存在");
        return getFileSuffix(file.getOriginalFilename());
    }

    /**
     * 将文件头转换成16进制字符串
     *
     * @param src
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     *
     * @param filePath 文件路径
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(String filePath) throws IOException {

        byte[] b = new byte[4];

        try (InputStream inputStream = new FileInputStream(filePath)) {
            inputStream.read(b, 0, b.length);
        }
        return bytesToHexString(b);
    }

    private static String getFileContent(File file) throws IOException {

        byte[] b = new byte[4];

        try (InputStream inputStream = new FileInputStream(file)) {
            inputStream.read(b, 0, b.length);
        }
        return bytesToHexString(b);
    }

    /**
     * 判断文件类型
     *
     * @param filePath 文件路径
     * @return 文件类型 type
     * @throws IOException the io exception
     */
    public static FileType getType(String filePath) throws IOException {

        return getFileType(getFileContent(filePath));
    }

    /**
     * Gets type.
     *
     * @param file the file
     * @return the type
     * @throws IOException the io exception
     */
    public static FileType getType(File file) throws IOException {
        return getFileType(getFileContent(file));
    }

    public static FileType getType(InputStream inputStream) throws IOException {
        byte[] b = new byte[4];
        inputStream.read(b, 0, b.length);
        return getFileType(bytesToHexString(b));
    }

    private static FileType getFileType(String fileContent) {
        String fileHead = fileContent;

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }

}
