package com.transferp.file_transfer.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.transferp.file_transfer.service.LocalFilesService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FileUploadController {
    final LocalFilesService localFiles = new LocalFilesService(); //Autowired
    
    @GetMapping("/test")
    public ResponseEntity<byte[]> getTest() {
        return localFiles.downloadByName("tesdasdlsa.jpg");
    }
    

    @GetMapping("/download") //get every file name
    public ResponseEntity<Object[][]> getAllFileName() {
        return localFiles.getFileListDetail();
    }
    
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName) {
        return localFiles.downloadByName(fileName);
    }
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("upFile") MultipartFile upFile) {
        return localFiles.saveLocal(upFile);
    }

    @DeleteMapping("/delete/{fileName}") //delete by name
    public ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName) {
        return localFiles.deleteByName(fileName);
    }
}
