package acs.b3o.controller;


import acs.b3o.service.S3Service;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inference")
public class S3Controller {

  private final S3Service awsS3Service;

  @PostMapping("/uploadFile")
  public ResponseEntity<List<String>> uploadFile(
      @RequestParam("multipartFiles") List<MultipartFile> multipartFiles,
      @RequestParam("subFolderPath") String subFolderPath,
      @RequestParam("taskTitle") String taskTitle){
    return ResponseEntity.ok(awsS3Service.uploadFile(multipartFiles, subFolderPath, taskTitle));
  }

  @GetMapping ("/getFile")
  public ResponseEntity<byte[]> download() throws IOException {
    return awsS3Service.getFile("data/raw-data/test_data.csv");
  }
}