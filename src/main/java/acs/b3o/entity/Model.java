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
@Table(name = "model")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    @Id
    @Column(length = 20, nullable = false)
    private String name; // 사용할 모델

}
