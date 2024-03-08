package acs.b3o.dto.request;

import lombok.Getter;
import lombok.Setter;
import acs.b3o.entity.User;
import java.util.Date;

@Getter
@Setter
public class InferenceRequest {
  private String model;
  private String title;
  private String input;
  private String result;
  private String stats;
  private Date date;
  private User user;
}
