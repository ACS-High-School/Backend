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
@Table(name = "inference")
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
  private String input1; // 학습 데이터 1

  @Column(length = 512, nullable = false)
  private String input2; // 학습 데이터 2

  @Column(length = 512)
  private String result; // 학습 결과

  @Column(length = 20)
  private String stats; // 진행 상태

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date date; // 학습 날짜

  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private User user; // 학습 진행자
}
