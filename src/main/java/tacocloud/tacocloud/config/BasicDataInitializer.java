package tacocloud.tacocloud.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tacocloud.tacocloud.dto.IngredientCategory;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.service.IngredientService;

@Component
@RequiredArgsConstructor
public class BasicDataInitializer implements CommandLineRunner {
    private final IngredientService ingredientService;
    public void run(String... args) {
        // Заполняем таблицу примерами только один раз.
        if (!ingredientService.listAll().isEmpty()) {
            System.out.println("DBBBBBB: DB not empty");
            return;

        }
        System.out.println("DBBBBBB: DB empty, filling");
        ingredientService.createIngredient(IngredientDto.builder().name("Secret sauce").category(IngredientCategory.SAUCE).build());
        ingredientService.createIngredient(IngredientDto.builder().name("Tortilla").category(IngredientCategory.BREAD).build());

    }
}
