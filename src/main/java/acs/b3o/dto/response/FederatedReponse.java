package acs.b3o.dto.response;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FederatedReponse {
    private String userinput1;
    private String userinput2;
    private String userinput3;
    private String userinput4;
    private Date date;
    private String model;
    private String status;
    private int groupcode;
}
