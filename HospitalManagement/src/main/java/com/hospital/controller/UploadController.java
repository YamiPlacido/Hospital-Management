package com.hospital.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.hospital.model.Examination;
import com.hospital.dto.UploadForm;
import com.hospital.repository.ExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "admin")
public class UploadController {
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "upload" + File.separator + "employee"+ File.separator;
    private static String EXAMINATION_UPLOADED_FOLDER = "upload"+ File.separator +"exam"+ File.separator;


    @Autowired
    ExaminationRepository examinationRepository;
    Examination examination = new Examination();
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
    		@RequestParam("successPage") String successPage) {

        if (file.isEmpty()) {
            return "redirect:error";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:"+successPage;
    }

    @PostMapping("api/uploadMultiFiles/examination")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadForm form) {
        System.out.println("Description:" + form.getEx_id());
        examination = examinationRepository.findById(form.getEx_id()).get();
        String result = null;
        try {
            result = this.saveUploadedFiles(form.getFiles());
        }
        // Here Catch IOException only.
        // Other Exceptions catch by RestGlobalExceptionHandler class.
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Uploaded to: <br/>" + result, HttpStatus.OK);
    }

    // Save Files
    private String saveUploadedFiles(MultipartFile[] files) throws IOException {
        int quantity = files.length;
        int index = 1;

        // Make sure directory exists!
        File uploadDir = new File(EXAMINATION_UPLOADED_FOLDER);
        uploadDir.mkdirs();

        StringBuilder sb = new StringBuilder();
        examination.setImage_path_1("");
        examination.setImage_path_2("");
        examination.setImage_path_3("");
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }
            String fileName = file.getOriginalFilename();
            String uploadFilePath = EXAMINATION_UPLOADED_FOLDER + fileName;

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            switch(index) {
                case 1:
                    examination.setImage_path_1(fileName);
                    break;
                case 2:
                    examination.setImage_path_2(fileName);
                    break;
                case 3:
                    examination.setImage_path_3(fileName);
                default:
                    // code block
            }
            examinationRepository.save(examination);
            index++;

            sb.append(uploadFilePath).append("<br/>");
        }
//        index = 1;
        return sb.toString();
    }
}
