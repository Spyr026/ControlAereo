package es.cic.curso._5.final_individual_15.Control.Aereo.Controller;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.DTOs.AvionDetalleDTO;
import es.cic.curso._5.final_individual_15.Control.Aereo.Service.AvionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aviones")
public class AvionController {
    @Autowired
    private AvionService avionService;

    @GetMapping
    public ResponseEntity<List<Avion>> getAll() {
        return ResponseEntity.ok(avionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avion> getById(@PathVariable Long id) {
        return avionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Get para el DTO
    @GetMapping("/{id}/detalle")
    public ResponseEntity<AvionDetalleDTO> getAvionDetalle(@PathVariable Long id) {
        return avionService.getAvionDetalle(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Avion> create(@RequestBody Avion avion) {
        return avionService.save(avion)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avion> actualizarAvion(@PathVariable Long id, @RequestBody Avion avion) {
        // Le damos el id al avión que queremos actualizar
        avion.setId(id);
        return avionService.save(avion)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (avionService.findById(id).isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    avionService.deleteById(id);
    return ResponseEntity.ok().build();
    }
}
