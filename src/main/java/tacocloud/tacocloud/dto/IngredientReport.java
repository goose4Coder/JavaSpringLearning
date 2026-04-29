package tacocloud.tacocloud.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class IngredientReport {
    @NonNull
    IngredientDto value;
    boolean failed=false;
    String message="";
    public IngredientReport(String error){
        value=new IngredientDto(0L,"",IngredientCategory.EXTRA);
        message=error;
        failed=true;
    }
}
