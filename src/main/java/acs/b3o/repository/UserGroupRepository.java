package acs.b3o.repository;

import acs.b3o.entity.User;
import acs.b3o.entity.UserGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    UserGroup findByGroupCode(int groupCode);


    @Query("SELECT ug FROM UserGroup ug WHERE ug.user1 = :user OR ug.user2 = :user OR ug.user3 = :user OR ug.user4 = :user")
    List<UserGroup> findByUser(@Param("user") User user);

}
