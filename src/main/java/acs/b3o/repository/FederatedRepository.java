package acs.b3o.repository;

import acs.b3o.entity.Federated;
import acs.b3o.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FederatedRepository extends JpaRepository<Federated, Long> {
    Federated findByGroupCode(UserGroup userGroup);
}
