package acs.b3o.dto.response;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FederatedReponse {
    private String userInput1;
    private String userInput2;
    private String userInput3;
    private String userInput4;
    private Date date;
    private String model;
    private String status;
    private int groupCode;
}
