package tacocloud.tacocloud.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tacocloud.tacocloud.dto.TacoDto;
import tacocloud.tacocloud.dto.TacoSize;
import tacocloud.tacocloud.entity.IngredientEntity;
import tacocloud.tacocloud.entity.TacoEntity;
import tacocloud.tacocloud.mapper.IngredientMapper;
import tacocloud.tacocloud.repository.TacoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TacoServiceTest {
    @Mock
    private TacoRepository repository;
    @InjectMocks
    private TacoService service;

    @Test
    public void testById_givesTacoCorrectly(){
        List<IngredientEntity> ingredients = new ArrayList<IngredientEntity>();
        ingredients.add(IngredientEntity.builder().name("Secret sauce").id(0L).category("SAUCE").build());
        ingredients.add(IngredientEntity.builder().name("Wheat tortilla").id(1L).category("BREAD").build());
        TacoEntity taco = TacoEntity.builder().name("Empty sauced taco").size("LARGE").id(1L).ingredients(ingredients).build();
        TacoEntity taco2 = TacoEntity.builder().name("Empty sauced taco2").size("BIG").id(1L).ingredients(ingredients).build();
        when(repository.findById(1L)).thenReturn(Optional.of(taco));
        when(repository.findById(2L)).thenReturn(Optional.of(taco2));
        Optional<TacoDto> toCheck = service.byId(1L);
        Optional<TacoDto> toCheck2 = service.byId(2L);
        Assertions.assertTrue(toCheck.isPresent());
        Assertions.assertEquals(1, toCheck.get().getId());
        Assertions.assertEquals("Empty sauced taco", toCheck.get().getName());
        Assertions.assertEquals(TacoSize.LARGE, toCheck.get().getSize());
        Assertions.assertEquals(ingredients.stream().map(IngredientMapper::toDto).toList(), toCheck.get().getIngredients());

        Assertions.assertTrue(toCheck2.isPresent());
        Assertions.assertEquals(1, toCheck2.get().getId());
        Assertions.assertEquals("Empty sauced taco2", toCheck2.get().getName());
        Assertions.assertEquals(TacoSize.LARGE, toCheck2.get().getSize());
        Assertions.assertEquals(ingredients.stream().map(IngredientMapper::toDto).toList(), toCheck2.get().getIngredients());

    }

    @Test
    public void createTaco_createsTacoCorrectly(){
        List<IngredientEntity> ingredients = new ArrayList<IngredientEntity>();
        ingredients.add(IngredientEntity.builder().name("Secret sauce").id(0L).category("SAUCE").build());
        ingredients.add(IngredientEntity.builder().name("Wheat tortilla").id(1L).category("BREAD").build());
        TacoEntity taco = TacoEntity.builder().name("Empty sauced taco").size("MEDIUM").id(1L).ingredients(ingredients).build();
        TacoDto dto = TacoDto.builder()
                .name("Empty sauced taco").size(TacoSize.MEDIUM).id(0L).ingredients(ingredients.stream().map(IngredientMapper::toDto).toList()).build();
        when(repository.save(any(TacoEntity.class))).thenReturn(taco);
        TacoDto toCheck = service.createTaco(dto);
        Assertions.assertEquals(1, toCheck.getId());
        Assertions.assertEquals("Empty sauced taco", toCheck.getName());
        Assertions.assertEquals(TacoSize.MEDIUM, toCheck.getSize());
        Assertions.assertEquals(ingredients.stream().map(IngredientMapper::toDto).toList(), toCheck.getIngredients());

    }

    @Test
    public void updateTaco_updatesTacoCorrectly(){
        List<IngredientEntity> ingredients = new ArrayList<IngredientEntity>();
        ingredients.add(IngredientEntity.builder().name("Secret sauce").id(0L).category("SAUCE").build());
        ingredients.add(IngredientEntity.builder().name("Wheat tortilla").id(1L).category("BREAD").build());
        TacoEntity taco = TacoEntity.builder().name("Empty sauced taco").size("MEDIUM").id(1L).ingredients(ingredients).build();
        TacoDto dto = TacoDto.builder()
                .name("Empty sauced taco").size(TacoSize.MEDIUM).id(4L).ingredients(ingredients.stream().map(IngredientMapper::toDto).toList()).build();
        when(repository.save(any(TacoEntity.class))).thenReturn(taco);
        TacoDto toCheck = service.updateTaco(1L,dto);
        Assertions.assertEquals(1, toCheck.getId());
        Assertions.assertEquals("Empty sauced taco", toCheck.getName());
        Assertions.assertEquals(TacoSize.MEDIUM, toCheck.getSize());
        Assertions.assertEquals(ingredients.stream().map(IngredientMapper::toDto).toList(), toCheck.getIngredients());

    }
    @Test
    public void testById_nullOnInexistentId(){
        List<IngredientEntity> ingredients = new ArrayList<IngredientEntity>();
        ingredients.add(IngredientEntity.builder().name("Secret sauce").id(0L).category("SAUCE").build());
        ingredients.add(IngredientEntity.builder().name("Wheat tortilla").id(1L).category("BREAD").build());
        TacoEntity taco = TacoEntity.builder().name("Empty sauced taco").size("LARGE").id(1L).ingredients(ingredients).build();
        TacoEntity taco2 = TacoEntity.builder().name("Empty sauced taco2").size("BIG").id(5L).ingredients(ingredients).build();
        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.findById(1L)).thenReturn(Optional.of(taco));
        Mockito.lenient().when(repository.findById(5L)).thenReturn(Optional.of(taco2));
        Optional<TacoDto> toCheck = service.byId(3L);
        Assertions.assertTrue(toCheck.isEmpty());


    }


}
