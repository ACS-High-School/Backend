package acs.b3o.dto.request;

import java.util.Date;
import lombok.Getter;

@Getter
public class FederatedRequest {
    private String userInput1;
    private String userInput2;
    private String userInput3;
    private String userInput4;
    private Date date;
    private String model;
    private String status;
    private int groupCode;
}
