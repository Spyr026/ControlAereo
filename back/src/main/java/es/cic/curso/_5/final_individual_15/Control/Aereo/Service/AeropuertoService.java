package es.cic.curso._5.final_individual_15.Control.Aereo.Service;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoIsFullException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AeropuertoException.AeropuertoNotValidException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.AvionException.AvionNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.DTOs.AeropuertoDetalleDto;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AeropuertoRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AeropuertoService {
    
    private AeropuertoRepository aeropuertoRepository;
    private AvionRepository avionRepository;
    private VueloRepository vueloRepository;

    public AeropuertoService(AeropuertoRepository aeropuertoRepository, AvionRepository avionRepository, VueloRepository vueloRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
        this.avionRepository = avionRepository;
        this.vueloRepository = vueloRepository;
    }

    //CRUD

    //CREATE and UPDATE - SAVE
    public Optional<Aeropuerto> save(Aeropuerto aeropuerto) {

        if (aeropuerto.getId() != null) {
            Optional<Aeropuerto> optExisting = aeropuertoRepository.findById(aeropuerto.getId());
            if (optExisting.isEmpty()) {
                throw new AeropuertoNotFoundException(aeropuerto.getId());
            }
            Aeropuerto existing = optExisting.get();
            existing.setNombre(aeropuerto.getNombre());
            existing.setCodigoIATA(aeropuerto.getCodigoIATA());
            existing.setCiudad(aeropuerto.getCiudad());
            existing.setMaximoAviones(aeropuerto.getMaximoAviones());
            return Optional.of(aeropuertoRepository.save(existing));
        }
        //Validamos que el nombre, codigoIATA, ciudad y maximoAviones no sean nulos o vacíos
        if (aeropuerto.getNombre() == null || aeropuerto.getNombre().isEmpty()) {
            throw new AeropuertoNotValidException("El nombre del aeropuerto no puede ser nulo o vacío");
        }
        if (aeropuerto.getCodigoIATA() == null || aeropuerto.getCodigoIATA().isEmpty()) {
            throw new AeropuertoNotValidException("El código IATA del aeropuerto no puede ser nulo o vacío");
        }
        if (aeropuerto.getCiudad() == null || aeropuerto.getCiudad().isEmpty()) {
            throw new AeropuertoNotValidException("La ciudad del aeropuerto no puede ser nula o vacía");
        }
        return Optional.of(aeropuertoRepository.save(aeropuerto));
    }

    //READ - FIND
    public List<Aeropuerto> findAll() {
        return aeropuertoRepository.findAll();
    }

    public Optional<Aeropuerto> findById(Long id) {
        //Si no existe el aeropuerto, lanza excepción
        if (aeropuertoRepository.findById(id).isEmpty()) {
            throw new AeropuertoNotFoundException(id);
        }
        return aeropuertoRepository.findById(id);
    }


    //DELETE - DELETE
    public void deleteById(Long id) {
        //Si no existe el aeropuerto, lanza excepción
        if (aeropuertoRepository.findById(id).isEmpty()) {
            throw new AeropuertoNotFoundException(id);
        }

        //Si el aeropuerto tiene aviones asignados, los aviones se quedan sin aeropuerto
        List<Avion> avionesEnAeropuerto = avionRepository.findByAeropuertoId(id);
        for (Avion avion : avionesEnAeropuerto) {
            avion.setAeropuerto(null);
            avionRepository.save(avion);
        }

        //Si el aeropuerto tiene vuelos de origen o destino, los vuelos se eliminan
        List<Vuelo> vuelosOrigen = vueloRepository.findByAeropuertoOrigenId(id);
        for (Vuelo vuelo : vuelosOrigen) {
            vueloRepository.deleteById(vuelo.getId());
        }
        List<Vuelo> vuelosDestino = vueloRepository.findByAeropuertoDestinoId(id);
        for (Vuelo vuelo : vuelosDestino) {
            vueloRepository.deleteById(vuelo.getId());
        }
        aeropuertoRepository.deleteById(id);
    }

    //OTROS MÉTODOS
    public List<Aeropuerto> getAeropuertosConEspacio() {
        
        List<Aeropuerto> aeropuertos = aeropuertoRepository.findAll();
        List<Aeropuerto> aeropuertosConEspacio = new ArrayList<>();
        for (Aeropuerto a : aeropuertos) {
            if (avionRepository.findByAeropuertoId(a.getId()).size() < a.getMaximoAviones()) {
                aeropuertosConEspacio.add(a);
            }
        }
        return aeropuertosConEspacio;
    }

    public Optional<AeropuertoDetalleDto> getAeropuertoDetalle(Long aeropuertoId) {

        //Si no existe el aeropuerto, lanza excepción
        if (aeropuertoRepository.findById(aeropuertoId).isEmpty()) {
            throw new AeropuertoNotFoundException(aeropuertoId);
        }

        Aeropuerto aeropuerto = aeropuertoRepository.findById(aeropuertoId).orElseThrow();
        AeropuertoDetalleDto dto = new AeropuertoDetalleDto();
        dto.setId(aeropuerto.getId());
        dto.setNombre(aeropuerto.getNombre());
        dto.setCodigoIATA(aeropuerto.getCodigoIATA());
        dto.setCiudad(aeropuerto.getCiudad());
        dto.setAviones(avionRepository.findByAeropuertoId(aeropuertoId));
        dto.setVuelosOrigen(vueloRepository.findByAeropuertoOrigenId(aeropuertoId));
        dto.setVuelosDestino(vueloRepository.findByAeropuertoDestinoId(aeropuertoId));
        return Optional.of(dto);
    }

    public Optional<Aeropuerto> asignarAvion(Long id, Long avionId) {

        //Si no existe el aeropuerto, lanza excepción
        if (aeropuertoRepository.findById(id).isEmpty()) {
            throw new AeropuertoNotFoundException(id);
        }
        //Si no existe el avión, lanza excepción
        Aeropuerto aeropuerto = aeropuertoRepository.findById(id).orElseThrow(
            () -> new AeropuertoNotFoundException(id)
        );
        //Si el aeropuerto está lleno, lanza excepción
        if (avionRepository.findByAeropuertoId(aeropuerto.getId()).size() >= aeropuerto.getMaximoAviones()) {
            throw new AeropuertoIsFullException(aeropuerto.getId());
        }
        //Si no existe el avión, lanza excepción
        Avion avion = avionRepository.findById(avionId).orElseThrow(
            () -> new AvionNotFoundException(avionId)
        );
        //SI esta todo correcto, asigna el avión al aeropuerto
        avion.setAeropuerto(aeropuerto);
        avionRepository.save(avion);
        return Optional.of(aeropuerto);
    }

    public Optional<Aeropuerto> removerAvion(Long id, Long avionId) {

        //Si no existe el aeropuerto, lanza excepción
        Aeropuerto aeropuerto = aeropuertoRepository.findById(id).orElseThrow(
            () -> new AeropuertoNotFoundException(id)
        );
        //Si no existe el avión, lanza excepción
        Avion avion = avionRepository.findById(avionId).orElseThrow(
            () -> new AvionNotFoundException(avionId)
        );
        avion.setAeropuerto(null);
        avionRepository.save(avion);
        return Optional.of(aeropuerto);
    }

    public List<Avion> getAvionesDisponibles() {
        List<Avion> aviones = avionRepository.findAll();
        List<Avion> disponibles = new ArrayList<>();
        for (Avion avion : aviones) {
            if (avion.getAeropuerto() == null) {
                disponibles.add(avion);
            }
        }
    return disponibles;
    }
}
