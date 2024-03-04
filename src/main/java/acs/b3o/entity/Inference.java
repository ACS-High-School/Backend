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

  @Column(nullable = false, length = 25)
  private String model;

  @Column(nullable = false, length = 25)
  private String title;

  @Column(nullable = false, length = 512)
  private String input1;

  @Column(nullable = false, length = 512)
  private String input2;

  @Column(length = 512)
  private String result;

  @Column(length = 20)
  private String stats;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  @ManyToOne
  @JoinColumn(name = "nickname", referencedColumnName = "nickname")
  private User nickname; // 닉네임, 기본 키로 사용

}
