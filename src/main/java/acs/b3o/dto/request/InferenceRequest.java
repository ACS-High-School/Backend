package acs.b3o.dto.request;

import acs.b3o.entity.User;
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
  private String input;
  private String result;
  private String stats;
  private Date date;
  private User user;
}
