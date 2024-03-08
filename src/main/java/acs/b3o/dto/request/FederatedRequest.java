package acs.b3o.dto.request;

import lombok.Getter;
import java.util.Date;

@Getter
public class FederatedRequest {
    private Date date;
    private String description;
    private String model;
    private String status;
    private int groupCode;
    private String user1Status;
    private String user2Status;
    private String user3Status;
    private String user4Status;
}
