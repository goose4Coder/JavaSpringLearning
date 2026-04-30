package tacocloud.tacocloud.controller;


import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacocloud.tacocloud.dto.TacoCreationRequest;
import tacocloud.tacocloud.dto.TacoDto;
import tacocloud.tacocloud.dto.TacoReport;
import tacocloud.tacocloud.service.TacoRequestParsingService;
import tacocloud.tacocloud.service.TacoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/taco/")
@RequiredArgsConstructor
public class TacoCrudController {
    private final TacoService service;
    private final TacoRequestParsingService tacoCreator;
    @GetMapping
    public List<TacoDto> getAllTacos(){
        return service.listAll();
    }
    @GetMapping("/{id}/")
    public Optional<TacoDto> getById(@PathVariable Long id){
        return service.byId(id);
    }
    @PostMapping
    public ResponseEntity<TacoReport> createTaco(@RequestBody TacoCreationRequest toCreate){
        if (!tacoCreator.isTacoValid(toCreate).equals("Valid")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TacoReport(tacoCreator.isTacoValid(toCreate)));
        }
        TacoDto created = service.createTaco(tacoCreator.parseTaco(toCreate));
        return ResponseEntity.status(HttpStatus.OK).body(new TacoReport(created));
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<TacoReport> updateTaco(@PathVariable Long id, @RequestBody TacoCreationRequest toUpdateWith){
        if (service.byId(id).isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TacoReport("Taco with the requested id does not exist"));
        }
        if (!tacoCreator.isTacoValid(toUpdateWith).equals("Valid")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TacoReport(tacoCreator.isTacoValid(toUpdateWith)));
        }
        TacoDto updated = service.updateTaco(id,tacoCreator.parseTaco(toUpdateWith));
        return ResponseEntity.status(HttpStatus.OK).body(new TacoReport(updated));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<TacoReport> deleteTaco(@PathVariable Long id){
        Optional<TacoDto> deleted = service.byId(id);
        if (deleted.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TacoReport("Taco with the requested id does not exist"));
        }
        service.deleteTaco(id);
        return ResponseEntity.status(HttpStatus.OK).body(new TacoReport(deleted.get()));
    }

}
