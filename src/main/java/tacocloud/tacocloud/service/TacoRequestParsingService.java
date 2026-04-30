package tacocloud.tacocloud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tacocloud.tacocloud.dto.IngredientCategory;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.dto.TacoCreationRequest;
import tacocloud.tacocloud.dto.TacoDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TacoRequestParsingService {
    private final IngredientService service;

    public String isTacoValid(TacoCreationRequest taco){
        if (service.byIdOfCategory(taco.getBread(), IngredientCategory.BREAD).isEmpty()){
            return "BREAD with requested id not found";
        }
        for (int i = 0; i < taco.getMeat().size(); i++) {
            if (service.byIdOfCategory(taco.getMeat().get(i), IngredientCategory.MEAT).isEmpty()){
                return "MEAT number "+ i + " with requested id "+taco.getMeat().get(i) +" not found";
            }
        }
        for (int i = 0; i < taco.getSauce().size(); i++) {
            if (service.byIdOfCategory(taco.getSauce().get(i), IngredientCategory.SAUCE).isEmpty()){
                return "SAUCE number "+ i + " with requested id "+taco.getSauce().get(i) +" not found";
            }
        }
        for (int i = 0; i < taco.getSauce().size(); i++) {
            if (service.byIdOfCategory(taco.getVegetables().get(i), IngredientCategory.VEGETABLE).isEmpty()){
                return "VEGETABLES number "+ i + " with requested id "+taco.getVegetables().get(i) +" not found";
            }
        }
        for (int i = 0; i < taco.getOther().size(); i++) {
            if (service.byId(taco.getOther().get(i)).isEmpty()){
                return "Additional ingredient number "+ i + " with requested id "+taco.getOther().get(i) +" not found";
            }
        }
        return "Valid";
    }

    public TacoDto parseTaco(TacoCreationRequest taco){
        List<IngredientDto> ingredients = taco.getMeat().stream().map(id -> service.byId(id).get()).collect(Collectors.toList());
        ingredients.addAll(taco.getSauce().stream().map(id -> service.byId(id).get()).toList());
        ingredients.addAll(taco.getVegetables().stream().map(id -> service.byId(id).get()).toList());
        ingredients.addAll(taco.getOther().stream().map(id -> service.byId(id).get()).toList());
        ingredients.add(service.byId(taco.getBread()).get());
        return TacoDto.builder()
                .name(taco.getName())
                .size(taco.getSize())
                .ingredients(ingredients)
                .build();
    }
}
