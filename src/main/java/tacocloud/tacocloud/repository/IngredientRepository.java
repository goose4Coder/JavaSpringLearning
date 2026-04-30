package tacocloud.tacocloud.repository;

import tacocloud.tacocloud.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long>{
    @Query("select i from IngredientEntity i where i.id = :ofId AND i.category= :ofCategory")
    Optional<IngredientEntity> ofCategoryById(@Param("ofId") long id,@Param("ofCategory") String category);
}
