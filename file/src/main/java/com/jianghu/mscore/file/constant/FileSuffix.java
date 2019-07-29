package com.jianghu.mscore.file.constant;

public enum FileSuffix {

    IMAGE(FileType.JPEG_JPG, FileType.PNG, FileType.GIF),

    OFFICE(FileType.XLS_DOC, FileType.XLSX_DOCX),

    RAR(FileType.ZIP, FileType.RAR),

    PDF(FileType.PDF);

    private FileType[] types;

    FileSuffix(FileType... types) {
        this.types = types;
    }

    public FileType[] getTypes() {
        return types;
    }
}
