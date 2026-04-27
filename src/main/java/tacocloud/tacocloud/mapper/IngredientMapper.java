package tacocloud.tacocloud.mapper;

import lombok.experimental.UtilityClass;
import tacocloud.tacocloud.dto.IngredientCategory;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.entity.IngredientEntity;

@UtilityClass
public class IngredientMapper {

    static public IngredientDto toDto(IngredientEntity entity){
        if (entity == null) {
            return null;
        }
        return IngredientDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(IngredientCategory.fromString(entity.getCategory())).build();
    }

    static public IngredientEntity toEntity(IngredientDto dto){
        if (dto == null) {
            return null;
        }
        return IngredientEntity.builder().name(dto.getName()).category(dto.getCategory().toString()).build();
    }
}
