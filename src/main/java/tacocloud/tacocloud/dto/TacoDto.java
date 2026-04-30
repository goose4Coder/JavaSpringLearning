package tacocloud.tacocloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class TacoDto {
    private Long id;
    private String name;
    private List<IngredientDto> ingredients;
    private TacoSize size;
}
