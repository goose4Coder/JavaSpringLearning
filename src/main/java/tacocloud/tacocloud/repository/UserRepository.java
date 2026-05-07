package tacocloud.tacocloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tacocloud.tacocloud.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.username = :uname")
    Optional<UserEntity> getByUsername(@Param("uname") String uname);

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.username = :uname")
    boolean usernameExists(@Param("uname") String uname);
}
