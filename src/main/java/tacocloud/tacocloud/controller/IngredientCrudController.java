package tacocloud.tacocloud.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tacocloud.tacocloud.dto.IngredientDto;
import tacocloud.tacocloud.dto.IngredientReport;

import tacocloud.tacocloud.service.IngredientService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredient/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class IngredientCrudController {
    private final IngredientService service;
    @GetMapping
    public List<IngredientDto> getAllIngredients() {
        return service.listAll();
    }
    @GetMapping("/{id}/")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable Long id) {
        return service.byId(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IngredientReport> createIngredient(@RequestBody IngredientDto dto) {
        IngredientDto result = service.createIngredient(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new IngredientReport(result));
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<IngredientReport> updateIngredient(@PathVariable Long id, @RequestBody IngredientDto dto) {
        if (service.byId(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new IngredientReport("Ingredient with the requested id does not exist"));
        }
        IngredientDto result = service.updateIngredient(id,dto);

        return ResponseEntity.status(HttpStatus.OK).body(new IngredientReport(result));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<IngredientReport> deleteIngredient(@PathVariable Long id) {
        Optional<IngredientDto> deleted=service.byId(id);
        if (service.byId(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new IngredientReport("Ingredient with the requested id does not exist"));
        }
        service.deleteIngredient(id);
        return ResponseEntity.status(HttpStatus.OK).body(new IngredientReport(deleted.get()));
    }




}
