package tacocloud.tacocloud.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.entity.IngredientEntity;
import tacocloud.tacocloud.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientCrudController {
    private final IngredientService service;
    @GetMapping
    public List<IngredientDto> getAllIngredients() {
        return service.listAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable Long id) {
        return service.byId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
