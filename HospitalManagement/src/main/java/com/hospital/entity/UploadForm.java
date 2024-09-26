package com.hospital.entity;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {
    private MultipartFile[] files;
    private int ex_id;

    public int getEx_id() {
        return ex_id;
    }

    public void setEx_id(int ex_id) {
        this.ex_id = ex_id;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

}
