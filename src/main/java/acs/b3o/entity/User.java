package acs.b3o.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(length = 20)
    private String nickname; // 닉네임, 기본 키로 사용

    @Column(length = 12, nullable = false)
    private String name; // 이름

    @Column(length = 40, nullable = false, unique = true)
    private String email; // ID (이메일), 유니크 제약 조건 추가

    @Column(length = 20)
    private String company; // 소속, null 허용
}