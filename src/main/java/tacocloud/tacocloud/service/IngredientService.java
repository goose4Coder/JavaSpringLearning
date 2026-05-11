package tacocloud.tacocloud.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tacocloud.tacocloud.dto.IngredientCategory;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.entity.IngredientEntity;
import tacocloud.tacocloud.mapper.IngredientMapper;
import tacocloud.tacocloud.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    @Autowired
    private final IngredientRepository repository;

    public List<IngredientDto> listAll() {
        return repository.findAll().stream()
                .map(IngredientMapper::toDto)
                .toList();
    }

    public Optional<IngredientDto> byId(long id){
        return repository.findById(id).map(IngredientMapper::toDto);
    }

    public Optional<IngredientDto> byIdOfCategory(long id, IngredientCategory category){
        return repository.ofCategoryById(id,category.toString()).map(IngredientMapper::toDto);
    }

    public IngredientDto createIngredient(IngredientDto ingredient){
        IngredientEntity savedEntity = repository.save(IngredientMapper.toEntity(ingredient));
        return IngredientMapper.toDto(savedEntity);
    }

    public IngredientDto updateIngredient(long id, IngredientDto ingredient){
        IngredientEntity entity = IngredientMapper.toEntity(ingredient);
        entity.setId(id);
        IngredientEntity savedEntity = repository.save(entity);
        return IngredientMapper.toDto(savedEntity);
    }

    public void deleteIngredient(long id){
        repository.deleteById(id);

    }
}
