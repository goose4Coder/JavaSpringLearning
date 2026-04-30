package tacocloud.tacocloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tacocloud.tacocloud.entity.TacoEntity;

public interface TacoRepository extends JpaRepository<TacoEntity, Long> {
}
