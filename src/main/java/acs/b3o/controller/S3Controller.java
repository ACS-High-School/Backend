package acs.b3o.controller;


import acs.b3o.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = " S3 컨트롤러", description = "s3 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

  private final S3Service awsS3Service;

  @Operation(summary = "파일 업로드", description = "s3에 파일을 업로드 합니다.")
  @PostMapping("/upload")
  public ResponseEntity<List<String>> uploadFile(
      @RequestParam("multipartFiles") List<MultipartFile> multipartFiles,
      @RequestParam("subFolderPath") String subFolderPath,
      @RequestParam("taskTitle") String taskTitle) {
    return ResponseEntity.ok(awsS3Service.uploadFile(multipartFiles, subFolderPath, taskTitle));
  }

  @GetMapping("/csv_download/{subFolderPath}/{fileName}")
  public ResponseEntity<byte[]> download(
      @PathVariable("subFolderPath") String subFolderPath,
      @PathVariable("fileName") String fileName) throws IOException {
    return awsS3Service.getObject(subFolderPath, fileName);
  }
}