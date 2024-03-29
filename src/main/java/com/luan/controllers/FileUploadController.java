package com.luan.controllers;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.luan.models.ResponseObject;
import com.luan.services.IStorageService;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
	// This controller receive file/image from client
	//Inject Storage Service here
	@Autowired
	private IStorageService storageService;
	@PostMapping("")
	public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file) {
		try {
			//save files to a folder => use a service
			String generatedFileName = storageService.storeFile(file);
			return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("ok", "upload file successfully", generatedFileName)
				
			);
			//32ede0f7bc0c4ac0b49b0478894130bf.jpg => how to open this file in Web Browser ? 
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
				new ResponseObject("ok", exception.getMessage(), "")
			);
		}
	}
	// get image's url
	@GetMapping("/files/{fileName:.+}")
	//files/32ede0f7bc0c4ac0b49b0478894130bf.jpg
	public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
		try {
			byte[] bytes = storageService.readFileContent(fileName);
			return ResponseEntity
				.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(bytes);
		} catch (Exception exception) {
			return ResponseEntity.noContent().build();
		}
	}
	// How to load all uploaded files ?
	@GetMapping("")
	public ResponseEntity<ResponseObject> getUploadedFiles() {
		try {
			List<String> urls = storageService.loadAll()
				.map(path -> {
					// convert fileName to url(send request "readDetailFile")
					String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"readDetailFile", path.getFileName().toString()).build().toUri().toString();
					return urlPath;
				})
				.collect(Collectors.toList());
			return ResponseEntity.ok(new ResponseObject("ok", "List files successfully", urls));
		} catch (Exception exception) {
			return ResponseEntity.ok(new ResponseObject("failed", "List files failed", new String[] {}));
		}
	}
}
