package tacocloud.tacocloud.repository;

import tacocloud.tacocloud.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long>{

}
