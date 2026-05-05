package tacocloud.tacocloud.mapper;


import lombok.experimental.UtilityClass;
import org.springframework.web.bind.annotation.Mapping;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.dto.TacoDto;
import tacocloud.tacocloud.dto.TacoSize;
import tacocloud.tacocloud.entity.IngredientEntity;
import tacocloud.tacocloud.entity.TacoEntity;
import tacocloud.tacocloud.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class TacoMapper {
    static public TacoDto toDto(TacoEntity entity){
        if (entity==null){
            return null;
        }
        List<IngredientDto> ingredients = new ArrayList<IngredientDto>();
        for (int i = 0; i < entity.getIngredients().size(); i++) {
            IngredientDto ingredient = IngredientMapper.toDto(entity.getIngredients().get(i));
            ingredients.add(ingredient);
        }
        return TacoDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .size(TacoSize.fromString(entity.getSize()))
                .ingredients(ingredients)
                .build();
    }

    static public TacoEntity toEntity(TacoDto dto){
        if (dto==null){
            return null;
        }
        List<IngredientEntity> ingredients=dto.getIngredients().stream().map(IngredientMapper::toEntityWithId).toList();
        TacoEntity a = TacoEntity.builder()
                .name(dto.getName())
                .size(dto.getSize().toString())
                .ingredients(ingredients)
                .build();


        return a;
    }
}
