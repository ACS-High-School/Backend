package acs.b3o.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "inference", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"title", "username"})
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inference {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer _id;

  @Column(length = 48, nullable = false)
  private String title; // 제목

  @Column(length = 25, nullable = false)
  private String model; // 학습 모델

  @Column(length = 512, nullable = false)
  private String input; // 학습 데이터

  @Column(length = 512)
  private String result; // 학습 결과

  @Column(length = 20)
  private String stats; // 학습 진행 상태

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date date; // 학습 날짜

  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private User user; // 학습 진행자
}
