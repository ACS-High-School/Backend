package acs.b3o.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FLDataResponse {
    private String title;
    private String protein;
    private String medicine;
}
