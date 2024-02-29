package acs.b3o.dto.request;

import java.util.Date;
import lombok.Getter;

@Getter
public class FederatedRequest {
    private String userinput1;
    private String userinput2;
    private String userinput3;
    private String userinput4;
    private Date date;
    private String model;
    private String status;
    private int groupcode;
}
