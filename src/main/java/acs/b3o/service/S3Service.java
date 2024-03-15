package acs.b3o.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class S3Service {

  @Value("${aws.s3.bucket}")
  private String bucket;

  private final AmazonS3 amazonS3;

  public List<String> uploadFile(List<MultipartFile> multipartFiles, String subFolderPath,
      String taskTitle) {
    List<String> fileNameList = new ArrayList<>();
    String newIntermediateFolder = "inference/";
    String fullFolderPath =
        newIntermediateFolder + (subFolderPath.endsWith("/") ? subFolderPath : subFolderPath + "/");

    // forEach 구문을 통해 multipartFiles 리스트로 넘어온 파일들을 순차적으로 처리
    multipartFiles.forEach(file -> {
      // 파일명을 taskTitle로 직접 설정
      String fileName = fullFolderPath + taskTitle;
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(file.getSize());
      objectMetadata.setContentType(file.getContentType());

      try (InputStream inputStream = file.getInputStream()) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
      } catch (IOException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
      }
      fileNameList.add(fileName);
    });

    return fileNameList;
  }


  public ResponseEntity<byte[]> getObject(String intermediateFolderPath, String subFolderPath,
      String storedFileName) throws IOException {
    // 경로 결합
    String path =
        intermediateFolderPath + "/" + (subFolderPath != null && !subFolderPath.trim().isEmpty() ?
            subFolderPath + "/" : "") + storedFileName;

    S3Object o = amazonS3.getObject(new GetObjectRequest(bucket, path));
    S3ObjectInputStream objectInputStream = o.getObjectContent();
    byte[] bytes = IOUtils.toByteArray(objectInputStream);

    String fileName = URLEncoder.encode(storedFileName, "UTF-8").replaceAll("\\+", "%20");
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    httpHeaders.setContentLength(bytes.length);
    httpHeaders.setContentDispositionFormData("attachment", fileName);

    return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
  }
}
