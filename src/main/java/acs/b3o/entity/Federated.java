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

    @ManyToOne
    @JoinColumn(name = "userinput1", referencedColumnName = "title")
    private FLData userinput1; // FLData 엔티티와의 외래 키 관계

    @ManyToOne
    @JoinColumn(name = "userinput2", referencedColumnName = "title")
    private FLData userinput2; // 동일

    @ManyToOne
    @JoinColumn(name = "userinput3", referencedColumnName = "title")
    private FLData userinput3; // 동일

    @ManyToOne
    @JoinColumn(name = "userinput4", referencedColumnName = "title")
    private FLData userinput4; // 동일

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date; // 학습 날짜

    @Column(length = 25)
    private String model; // 학습 결과

    @Column(length = 20)
    private String status; // 진행 상태

    @ManyToOne
    @JoinColumn(name = "groupcode", referencedColumnName = "groupcode")
    private UserGroup groupcode; // UserGroup 엔티티와의 외래 키 관
}
