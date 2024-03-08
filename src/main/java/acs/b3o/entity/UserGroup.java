package acs.b3o.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usergroup")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGroup {

    @Id
    @Column(name = "groupcode")
    private int groupCode; // 그룹 코드, 기본 키로 사용

    @ManyToOne
    @JoinColumn(name = "user1", referencedColumnName = "username")
    private User user1; // user1 username, User 엔티티와의 외래 키 관계

    @ManyToOne
    @JoinColumn(name = "user2", referencedColumnName = "username")
    private User user2; // user2 username, User 엔티티와의 외래 키 관계

    @ManyToOne
    @JoinColumn(name = "user3", referencedColumnName = "username")
    private User user3; // user3 username, User 엔티티와의 외래 키 관계

    @ManyToOne
    @JoinColumn(name = "user4", referencedColumnName = "username")
    private User user4; // user4 username, User 엔티티와의 외래 키 관계

    @Column(length = 20)
    private String status; // 그룹 진행 상태

}
