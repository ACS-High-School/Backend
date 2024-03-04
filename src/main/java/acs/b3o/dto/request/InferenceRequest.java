package acs.b3o.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InferenceRequest {

  private String model;
  private String title;
  private String input1;
  private String input2;
  private String result;
  private String stats;
  private Date date;
  private String nickname; // 닉네임, 기본 키로 사용
}
