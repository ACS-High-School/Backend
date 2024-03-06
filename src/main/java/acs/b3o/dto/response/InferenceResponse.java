package acs.b3o.dto.response;

import acs.b3o.entity.User;
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
  private User user;

  private String message;
}
