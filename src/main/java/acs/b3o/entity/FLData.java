package acs.b3o.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fldata")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FLData {
    @Id
    @Column(length = 48)
    private String title; // 주요 키로 사용되는 제목

    @Column(length = 512, nullable = false)
    private String protein; // 단백질 설명

    @Column(length = 512, nullable = false)
    private String medicine; // 약 성분 설명

    @Column(nullable = false)
    private double result; // 친화도 결과
}
