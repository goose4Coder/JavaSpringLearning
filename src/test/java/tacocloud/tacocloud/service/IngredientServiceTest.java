package tacocloud.tacocloud.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tacocloud.tacocloud.dto.IngredientCategory;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.entity.IngredientEntity;
import tacocloud.tacocloud.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {
    @Mock
    private IngredientRepository repository;
    @InjectMocks
    private IngredientService service;

    @Test
    public void testById_givesCorrectIngredient(){
        List<IngredientEntity> toTestWith= new ArrayList<IngredientEntity>();
        toTestWith.add(IngredientEntity.builder().name("Secret sauce").id(0L).category("SAUCE").build());
        toTestWith.add(IngredientEntity.builder().name("Wheat tortilla").id(1L).category("BREAD").build());
        when(repository.findById(1L)).thenReturn(Optional.of(toTestWith.get(1)));
        Optional<IngredientDto> toCheck = service.byId(1);
        Assertions.assertTrue(toCheck.isPresent());
        Assertions.assertEquals(1, toCheck.get().getId());
        Assertions.assertEquals(IngredientCategory.BREAD, toCheck.get().getCategory());
        Assertions.assertEquals("Wheat tortilla", toCheck.get().getName());

    }
    @Test
    public void testByIdOfCategory_givesCorrectIngredient(){
        List<IngredientEntity> toTestWith= new ArrayList<IngredientEntity>();
        IngredientEntity sauce=IngredientEntity.builder().name("Secret sauce").id(0L).category("SAUCE").build();
        when(repository.ofCategoryById(0,"SAUCE")).thenReturn(Optional.of(sauce));

        Optional<IngredientDto> toCheck = service.byIdOfCategory(0,IngredientCategory.SAUCE);
        Assertions.assertTrue(toCheck.isPresent());
        Assertions.assertEquals(0, toCheck.get().getId());
        Assertions.assertEquals(IngredientCategory.SAUCE, toCheck.get().getCategory());
        Assertions.assertEquals("Secret sauce", toCheck.get().getName());

    }

    @Test
    public void testByIdOfCategory_notFoundWhenWrongCategory(){
        List<IngredientEntity> toTestWith= new ArrayList<IngredientEntity>();
        IngredientEntity sauce=IngredientEntity.builder().name("Secret sauce").id(0L).category("SAUCE").build();
        Mockito.lenient().when(repository.ofCategoryById(anyLong(),anyString())).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.ofCategoryById(0,"SAUCE")).thenReturn(Optional.of(sauce));
        Optional<IngredientDto> toCheck = service.byIdOfCategory(0,IngredientCategory.BREAD);
        Assertions.assertTrue(toCheck.isEmpty());


    }

    @Test
    public void testById_nullOnInexistentId(){
        List<IngredientEntity> toTestWith= new ArrayList<IngredientEntity>();
        toTestWith.add(IngredientEntity.builder().name("Secret sauce").id(0L).category("SAUCE").build());
        toTestWith.add(IngredientEntity.builder().name("Wheat tortilla").id(1L).category("BREAD").build());
        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.lenient().when(repository.findById(1L)).thenReturn(Optional.of(toTestWith.get(1)));
        Mockito.lenient().when(repository.findById(0L)).thenReturn(Optional.of(toTestWith.get(0)));
        Optional<IngredientDto> toCheck = service.byId(7);
        Assertions.assertTrue(toCheck.isEmpty());

    }

    @Test
    public void createIngredient_createsCorrectly(){
        IngredientEntity entity=IngredientEntity.builder().name("Secret sauce").id(1L).category("SAUCE").build();
        IngredientDto dto= IngredientDto.builder().name("Secret sauce").category(IngredientCategory.SAUCE).id(2L).build();
        when(repository.save(any(IngredientEntity.class))).thenReturn(entity);
        IngredientDto toCheck = service.createIngredient(dto);
        Assertions.assertEquals(1, toCheck.getId());
        Assertions.assertEquals(IngredientCategory.SAUCE, toCheck.getCategory());
        Assertions.assertEquals("Secret sauce", toCheck.getName());

    }
    @Test
    public void updateIngredient_updatesCorrectly(){
        IngredientEntity entity=IngredientEntity.builder().name("Secret sauce").id(1L).category("SAUCE").build();
        IngredientDto dto= IngredientDto.builder().name("Secret sauce").category(IngredientCategory.SAUCE).id(2L).build();
        when(repository.save(any(IngredientEntity.class))).thenReturn(entity);
        IngredientDto toCheck = service.updateIngredient(1,dto);
        Assertions.assertEquals(1, toCheck.getId());
        Assertions.assertEquals(IngredientCategory.SAUCE, toCheck.getCategory());
        Assertions.assertEquals("Secret sauce", toCheck.getName());

    }


}
