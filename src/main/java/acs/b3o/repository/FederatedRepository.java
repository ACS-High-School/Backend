package acs.b3o.repository;

import acs.b3o.entity.Federated;
import acs.b3o.entity.UserGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FederatedRepository extends JpaRepository<Federated, Long> {
    Federated findByGroupCode(UserGroup userGroup);

    @Query("SELECT f FROM Federated f WHERE f.groupCode.groupCode IN :groupCodes")
    List<Federated> findAllByGroupCodes(@Param("groupCodes") List<Integer> groupCodes);
}
