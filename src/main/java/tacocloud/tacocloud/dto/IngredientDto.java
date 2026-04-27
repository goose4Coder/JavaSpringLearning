package tacocloud.tacocloud.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class IngredientDto {
    private Long id;
    private String name;
    private IngredientCategory category;
}
