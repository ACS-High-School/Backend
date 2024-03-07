package acs.b3o.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group_status")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupStatus {
    @Id
    private Integer groupCode;

    private Boolean user1Confirmed;
    private Boolean user2Confirmed;
    private Boolean user3Confirmed;
    private Boolean user4Confirmed;

    // Getters and Setters
}
