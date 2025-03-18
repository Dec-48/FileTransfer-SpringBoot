package com.transferp.file_transfer.service;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardOpenOption;
import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class LocalFilesService {
    File dir = new File("upload/");
    
    public ResponseEntity<Object[][]> getFileListDetail(){
        try {
            File directory = new File("upload/");
            File[] files = directory.listFiles();
            Object[][] ret = new Object[files.length][2];
            double x;
            for (int i = 0; i < files.length; i++){
                ret[i][0] = files[i].getName();
                x = files[i].length() / 1024.0;
                x /= 1024.0;
                ret[i][1] = String.format("%.2f", x);
            }   
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ret);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    public ResponseEntity<String> saveLocal(MultipartFile upFile){
        try {
            File saveFile = new File(dir.getAbsolutePath() + "/" + upFile.getOriginalFilename());
            upFile.transferTo(saveFile);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Can't uploaded");
        } 
    }

    
    public ResponseEntity<String> deleteByName(String name){
        try {
            File deleteFile = new File(dir.getAbsolutePath() + "/" + name);
            FileChannel channel = FileChannel.open(deleteFile.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE);
            FileLock locker = channel.tryLock(0L, Long.MAX_VALUE, true); // Lock the file
            deleteFile.delete();
            channel.close();
            locker.release();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("deleted successfully");
        } catch (Exception e) {
            if (e instanceof NoSuchFileException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File not found");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    public ResponseEntity<byte[]> downloadByName(String name) {
        try {
            File downloadFile = new File(dir.getAbsolutePath() + "/" + name);
            FileChannel channel = FileChannel.open(downloadFile.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE);
            FileLock locker = channel.tryLock(0L, Long.MAX_VALUE, true); // Lock the file
            byte[] fileContent = Files.readAllBytes(downloadFile.toPath());
            ResponseEntity<byte[]> ret = ResponseEntity.accepted()
                .header("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"")
                .body(fileContent);
            locker.release();
            channel.close();
            return ret;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
