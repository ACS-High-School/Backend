package acs.b3o.dto.response;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InferenceResponse {

  private String model;
  private String title;
  private String input1;
  private String input2;
  private String result;
  private String stats;
  private Date date;
  private String nickname; // 닉네임, 기본 키로 사용

  private String message;
}
