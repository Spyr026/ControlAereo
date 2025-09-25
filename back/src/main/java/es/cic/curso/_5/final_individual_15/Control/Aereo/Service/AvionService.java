package es.cic.curso._5.final_individual_15.Control.Aereo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionIncompletoException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionIsInVueloException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.DTOs.AvionDetalleDTO;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;

@Service
public class AvionService {

    private AvionRepository avionRepository;
    private VueloService vueloRepository;

    public AvionService(AvionRepository avionRepository, VueloService vueloRepository) {
        this.avionRepository = avionRepository;
        this.vueloRepository = vueloRepository;
    }

    /**
     * Busca en la BBDD todas las entradas de Avion
     * 
     * @return Lista de Aviones
     */
    public List<Avion> findAll() {
        return avionRepository.findAll();
    }

    /**
     * Busca un avion en base a un ID en la BBDD
     * 
     * @param id del avion
     * @return EL avion que encuentre con ese id
     * @throws AvionNotFoundException en caso de que no encuentre ningun avión
     */
    public Optional<Avion> findById(Long id) {
        // Si no existe el avión, lanza excepción
        if (avionRepository.findById(id).isEmpty()) {
            throw new AvionNotFoundException(id);
        }
        return avionRepository.findById(id);
    }

    /**
     * Actualiza el avión en caso de que se pase con un ID
     * En caso de que se pase sin el, lo crea.
     * 
     * @param avion
     * @return
     */
    public Optional<Avion> save(Avion avion) {
        // Si el ID no es nulo, es un update
        if (avion.getId() != null) {
            // Si no existe el avión, lanza excepción
            Optional<Avion> optExisting = avionRepository.findById(avion.getId());
            if (optExisting.isEmpty()) {
                throw new AvionNotFoundException(avion.getId());
            }
            // Sacamos el avion del optional
            Avion existing = optExisting.get();
            // Actualizamos los campos
            existing.setMatricula(avion.getMatricula());
            existing.setModelo(avion.getModelo());
            existing.setAerolinea(avion.getAerolinea());
            existing.setCapacidad(avion.getCapacidad());
            existing.setEstado(avion.getEstado());
            // Guarda y devuelve el OPTIONAL de avión actualizado
            return Optional.of(avionRepository.save(existing));
        }
        // Si es un create, guarda y devuelve el OPTIONAL de avión creado

        // PERO primero verificamos los campos
        if (avion.getMatricula() == null ||
                avion.getModelo() == null ||
                avion.getAerolinea() == null ||
                avion.getCapacidad() <= 0) {
            // Si alguno no esta completo, lanzamos una excepción
            throw new AvionIncompletoException();
        }
        return Optional.of(avionRepository.save(avion));
    }

    public void deleteById(Long id) {

        Optional<Avion> avionBorrar = avionRepository.findById(id);
        // Si el avion no existe
        if (avionBorrar.isEmpty()) {
            throw new AvionNotFoundException(id);

        } // Si el vuelo está en proceso, no se puede borrar
        List<Vuelo> vuelosAsociados = vueloRepository.findByAvionId(id);

        if (vuelosAsociados.isEmpty()) {
            // No pasa nada, se puede eliminar el avión
        } else {
            for (Vuelo vuelo : vuelosAsociados) {
                if ("EN_VUELO".equals(vuelo.getEstado())) {
                    throw new AvionIsInVueloException(id);
                }
            }
            // Si ningún vuelo está en progreso, desasocia el avión de todos los vuelos
            for (Vuelo vuelo : vuelosAsociados) {
                vuelo.setAvion(null);
                vueloRepository.save(vuelo);
            }
        }
        // Cogemos el Avion a borrar del optional para poder trabajar con el
        Avion avion = avionBorrar.get();

        // Si el avion tiene un aeropuerto, le sacamos del aeropuerto porloquesea
        if (avion.getAeropuerto() != null) {
            avion.setAeropuerto(null);
            avionRepository.save(avion);
        }

        avionRepository.deleteById(id);
    }

    public Optional<AvionDetalleDTO> getAvionDetalle(Long id) {

        Optional<Avion> optAvion = avionRepository.findById(id);

        // Si no encontramos el Avion en la BBDD
        if (optAvion.isEmpty()) {
            throw new AvionNotFoundException(id);
        }
        // Sacamos a avion para usarlo bien
        Avion avion = optAvion.get();
        AvionDetalleDTO dto = new AvionDetalleDTO();
        dto.setId(avion.getId());
        dto.setMatricula(avion.getMatricula());
        dto.setModelo(avion.getModelo());
        dto.setAerolinea(avion.getAerolinea());
        dto.setCapacidad(avion.getCapacidad());
        dto.setEstado(avion.getEstado());
        if (avion.getAeropuerto() != null) {
            dto.setAeropuertoId(avion.getAeropuerto().getId());
            dto.setAeropuertoNombre(avion.getAeropuerto().getNombre());
        }
        return Optional.of(dto);
    }

}