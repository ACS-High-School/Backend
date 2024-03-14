package acs.b3o.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "federated")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Federated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int _id; // 자동 증가되는 기본 키

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date; // 학습 날짜

    @Column(name = "description", length = 128, nullable = false) // "description" 컬럼 추가
    private String description; // FL 그룹 설명

    @Column(length = 20)
    private String model; // FL 결과

    @Column(length = 20)
    private String status; // 진행 상태

    @ManyToOne
    @JoinColumn(name = "groupcode", referencedColumnName = "groupcode")
    private UserGroup groupCode; // UserGroup 엔티티와의 외래 키 관계

    // 아래는 추가된 사용자 상태 컬럼에 대한 선언
    @Column(name = "user1status", length = 20, nullable = false)
    private String user1Status; // 사용자1 상태

    @Column(name = "user2status", length = 20, nullable = false)
    private String user2Status; // 사용자2 상태

    @Column(name = "user3status", length = 20, nullable = false)
    private String user3Status; // 사용자3 상태

    @Column(name = "user4status", length = 20, nullable = false)
    private String user4Status; // 사용자4 상태

    @Column(name = "taskname", length = 20)
    private String taskName; // 사용자4 상태
}
