package com.transferp.file_transfer.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transferp.file_transfer.service.LocalDataService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FileUploadController {
    final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    final LocalDataService localData = new LocalDataService();

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("upFile") MultipartFile upFile) {
        logger.info("Post uploadFile");
        return localData.saveLocal(upFile);
    }

    @GetMapping("/download")
    public ResponseEntity<Object[][]> getAll() {
        logger.info("Get getAll");
        return localData.getFileListDetail();
    }
    
    @GetMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName) {
        return localData.deleteByName(fileName);
    }

    // @GetMapping("/lock/{fileName}")
    // public ResponseEntity<String> lockFile(@PathVariable("fileName") String fileName) {
    //     localData.ldTryLock(fileName);
    //     return ResponseEntity.ok("locking : " + fileName);
    // }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName) {
        return localData.downloadByName(fileName);
    }
    
    
}
