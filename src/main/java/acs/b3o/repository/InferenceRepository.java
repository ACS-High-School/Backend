package acs.b3o.repository;

import acs.b3o.entity.Inference;
import acs.b3o.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InferenceRepository extends JpaRepository<Inference, Integer> {

  List<Inference> findAllByUser(User user);
}
