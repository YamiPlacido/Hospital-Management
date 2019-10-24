package com.hospital.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {
    private MultipartFile[] files;
    private long ex_id;

    public long getEx_id() {
        return ex_id;
    }

    public void setEx_id(long ex_id) {
        this.ex_id = ex_id;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

}
