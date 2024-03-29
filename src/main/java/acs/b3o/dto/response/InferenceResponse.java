package acs.b3o.dto.response;

import lombok.Builder;
import lombok.Getter;
import acs.b3o.entity.User;
import java.util.Date;

@Getter
@Builder
public class InferenceResponse {

  private Integer _id;
  private String model;
  private String title;
  private String input;
  private String result;
  private String stats;
  private Date date;
  private User user;
  private String message;
}
