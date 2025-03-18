package com.transferp.file_transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.transferp.file_transfer.service.LocalFilesService;

@SpringBootTest
class FileTransferApplicationTests {
	LocalFilesService localFiles = new LocalFilesService(); //Autowired
	@Test
	void deleteNonExistFile() {
		ResponseEntity<String> response = localFiles.deleteByName("xxx.xxx");
		HttpStatusCode statusCode = HttpStatusCode.valueOf(400);
		String body = response.getBody();
		assertEquals(statusCode, response.getStatusCode());
		assertEquals("File not found", body);
	}

}
