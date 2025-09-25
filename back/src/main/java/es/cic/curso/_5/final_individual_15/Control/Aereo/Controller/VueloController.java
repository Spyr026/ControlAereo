package es.cic.curso._5.final_individual_15.Control.Aereo.Controller;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloFechaHoraException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Service.VueloService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
public class VueloController {

    private VueloService vueloService;

    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping
    public ResponseEntity<List<Vuelo>> getAll() {
        List<Vuelo> vuelos = vueloService.findAll();
        return ResponseEntity.ok(vuelos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> getById(@PathVariable Long id) {
        Vuelo vuelo = vueloService.findById(id).orElseThrow(
            () -> new VueloNotFoundException(id)
        );
        return ResponseEntity.ok(vuelo);
    }

    @GetMapping("/avion/{id}")
    public ResponseEntity<List<Vuelo>> getByAvionId(@PathVariable Long id) {
        List<Vuelo> vuelos = vueloService.findByAvionId(id);
        return ResponseEntity.ok(vuelos);
    }

    @GetMapping("/avionesDisponibles")
    public ResponseEntity<List<Avion>> getAvionesDisponibles(
        @RequestParam Long origenId,
        @RequestParam Long destinoId,
        @RequestParam String fechaSalida,
        @RequestParam String fechaLlegada
    ) {
        List<Avion> aviones = vueloService.getAvionesDisponibles(origenId, destinoId, fechaSalida, fechaLlegada);
        return ResponseEntity.ok(aviones);
    }

    @PostMapping
    public ResponseEntity<Vuelo> create(@RequestBody Vuelo vuelo) {
        Vuelo nuevoVuelo = vueloService.save(vuelo).orElseThrow(
            () -> new VueloFechaHoraException()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVuelo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vuelo> actualizarVuelo(@PathVariable Long id, @RequestBody Vuelo vuelo) {
        // Le damos el id al vuelo que queremos actualizar
        vuelo.setId(id);
        Vuelo vueloActualizado = vueloService.save(vuelo).orElseThrow(
            () -> new RuntimeException("Error al actualizar vuelo")
        );
        return ResponseEntity.ok(vueloActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vueloService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
