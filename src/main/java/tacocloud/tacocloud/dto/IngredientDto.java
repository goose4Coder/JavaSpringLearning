package tacocloud.tacocloud.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class IngredientDto {
    private Long id;
    private String name;
    private IngredientCategory category;
}
