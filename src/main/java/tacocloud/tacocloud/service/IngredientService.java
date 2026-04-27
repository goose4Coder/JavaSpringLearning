package tacocloud.tacocloud.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.entity.IngredientEntity;
import tacocloud.tacocloud.mapper.IngredientMapper;
import tacocloud.tacocloud.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;

    public List<IngredientDto> listAll() {
        return repository.findAll().stream()
                .map(IngredientMapper::toDto)
                .toList();
    }

    public Optional<IngredientDto> byId(long id){
        return repository.findById(id).map(IngredientMapper::toDto);
    }

    public IngredientDto createIngredient(IngredientDto ingredient){
        IngredientEntity savedEntity = repository.save(IngredientMapper.toEntity(ingredient));
        return IngredientMapper.toDto(savedEntity);
    }
}
