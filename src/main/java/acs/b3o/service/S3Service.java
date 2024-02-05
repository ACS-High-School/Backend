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

  private static final String BASE_FOLDER_PATH = "data/processed-data/";

  public List<String> uploadFile(List<MultipartFile> multipartFiles, String subFolderPath, String taskTitle) {
    List<String> fileNameList = new ArrayList<>();
    String fullFolderPath = BASE_FOLDER_PATH + (subFolderPath.endsWith("/") ? subFolderPath : subFolderPath + "/");

    // forEach 구문을 통해 multipartFiles 리스트로 넘어온 파일들을 순차적으로 fileNameList 에 추가
    multipartFiles.forEach(file -> {
      String fileName = fullFolderPath + createFileName(file.getOriginalFilename(), taskTitle);
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(file.getSize());
      objectMetadata.setContentType(file.getContentType());

      try (InputStream inputStream = file.getInputStream()) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));
      } catch (IOException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
      }
      fileNameList.add(fileName);

    });

    return fileNameList;
  }

  // 먼저 파일 업로드시, 파일명을 난수화하기 위해 UUID 를 활용하여 난수를 돌린다.
  public String createFileName(String fileName, String taskTitle) {
    String extension = getFileExtension(fileName);
    return UUID.randomUUID().toString() + "_" + taskTitle + extension;
  }

  // 파일 확장자가 CSV가 아닐 경우 오류를 반환하는 로직
  private String getFileExtension(String fileName) {
    if (fileName != null && !fileName.isEmpty()) {
      int dotIndex = fileName.lastIndexOf(".");
      if (dotIndex > 0) {
        String extension = fileName.substring(dotIndex + 1);
        if ("csv".equalsIgnoreCase(extension)) {
          return fileName.substring(dotIndex);
        }
      }
    }
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 (" + fileName + "). CSV 파일만 허용됩니다.");
  }

  public ResponseEntity<byte[]> getFile(String storedFileName) throws IOException{
    S3Object o = amazonS3.getObject(new GetObjectRequest(bucket, storedFileName));
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
