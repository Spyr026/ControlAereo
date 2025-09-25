package es.cic.curso._5.final_individual_15.Control.Aereo.Controller;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.DTOs.AeropuertoDetalleDto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Service.AeropuertoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/aeropuertos")
public class AeropuertoController {

    
    private AeropuertoService aeropuertoService;

    public AeropuertoController(AeropuertoService aeropuertoService) {
        this.aeropuertoService = aeropuertoService;
    }

    @GetMapping
    public ResponseEntity<List<Aeropuerto>> getAll() {
        List<Aeropuerto> aeropuertos = aeropuertoService.findAll();
        return ResponseEntity.ok(aeropuertos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aeropuerto> getById(@PathVariable Long id) {
        Aeropuerto aeropuerto = aeropuertoService.findById(id).orElseThrow(
            () -> new AeropuertoNotFoundException(id)
        );
        return ResponseEntity.ok(aeropuerto);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<AeropuertoDetalleDto> getAeropuertoDetalle(@PathVariable Long id) {
        AeropuertoDetalleDto dto = aeropuertoService.getAeropuertoDetalle(id).orElseThrow(
            () -> new AeropuertoNotFoundException(id)
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/disponibles")
    public List<Aeropuerto> getAeropuertosConEspacio() {
        return aeropuertoService.getAeropuertosConEspacio();
    }

    @GetMapping("/aviones/disponibles")
    public ResponseEntity<List<Avion>> getAvionesDisponibles() {
        List<Avion> aviones = aeropuertoService.getAvionesDisponibles();
        return ResponseEntity.ok(aviones);
    }

    @PostMapping
    public ResponseEntity<Aeropuerto> create(@RequestBody Aeropuerto aeropuerto) {
        Aeropuerto created = aeropuertoService.save(aeropuerto).orElseThrow(
            () -> new RuntimeException("Error al crear aeropuerto")
        );
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aeropuerto> actualizarAeropuerto(@PathVariable Long id, @RequestBody Aeropuerto aeropuerto) {
        // lógica de actualización
        aeropuerto.setId(id);
        Aeropuerto updated = aeropuertoService.save(aeropuerto).orElseThrow(
            () -> new RuntimeException("Error al actualizar aeropuerto")
        );
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/asignarAvion/{avionId}")
    public ResponseEntity<Aeropuerto> asignarAvion(@PathVariable Long id, @PathVariable Long avionId) {
        Aeropuerto aeropuerto = aeropuertoService.asignarAvion(id, avionId).orElseThrow(
            () -> new RuntimeException("Error al asignar avión")
        );
        return ResponseEntity.ok(aeropuerto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aeropuertoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * ELimina Un avion de un aeropuerto - TAnto de la lista como del propio avion
     */
    @DeleteMapping("/{id}/eliminarAvion/{avionId}")
    public ResponseEntity<Aeropuerto> removerAvion(@PathVariable Long id, @PathVariable Long avionId) {
        Aeropuerto aeropuerto = aeropuertoService.removerAvion(id, avionId).orElseThrow(
            () -> new RuntimeException("Error al eliminar avión")
        );
        return ResponseEntity.ok(aeropuerto);
    }
}
