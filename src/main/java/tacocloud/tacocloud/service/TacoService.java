package tacocloud.tacocloud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tacocloud.tacocloud.dto.TacoDto;
import tacocloud.tacocloud.entity.TacoEntity;
import tacocloud.tacocloud.mapper.TacoMapper;
import tacocloud.tacocloud.repository.TacoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TacoService {
    private final TacoRepository repository;

    public List<TacoDto> listAll(){
        return repository.findAll().stream().map(TacoMapper::toDto).toList();
    }

    public Optional<TacoDto> byId(Long id){
        return repository.findById(id).map(TacoMapper::toDto);
    }

    public TacoDto createTaco(TacoDto taco){
        TacoEntity saved = repository.save(TacoMapper.toEntity(taco));
        return TacoMapper.toDto(saved);
    }

    public TacoDto updateTaco(Long id, TacoDto toUpdateTo){
        TacoEntity saved=TacoMapper.toEntity(toUpdateTo);
        saved.setId(id);
        saved=repository.save(saved);
        return TacoMapper.toDto(saved);
    }

    public void deleteTaco(Long id){
        repository.deleteById(id);
    }
}
