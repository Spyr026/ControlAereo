package es.cic.curso._5.final_individual_15.Control.Aereo.Service;

import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloFechaHoraException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloInvalidoException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Exception.VueloException.VueloNotFoundException;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.VueloRepository;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.AvionRepository;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VueloService {

    private VueloRepository vueloRepository;
    private AvionRepository avionRepository;

    public VueloService(VueloRepository vueloRepository, AvionRepository avionRepository) {
        this.vueloRepository = vueloRepository;
        this.avionRepository = avionRepository;
    }

    public List<Vuelo> findAll() {
        return vueloRepository.findAll();
    }

    public Optional<Vuelo> findById(Long id) {
        //Si no existe el vuelo, lanza excepción
        if (vueloRepository.findById(id).isEmpty()) {
            throw new VueloNotFoundException(id);
        }
        return vueloRepository.findById(id);
    }

    public Optional<Vuelo> save(Vuelo vuelo) {
        //Si intentamos ACTUALIZAR un vuelo que no existe, lanzamos excepción
        if (vuelo.getId() != null && !vueloRepository.existsById(vuelo.getId())) {
            throw new VueloNotFoundException(vuelo.getId());
        }
        // Si no existe el avión asignado, lanzamos excepción
        if (vuelo.getAvion() != null && !avionRepository.existsById(vuelo.getAvion().getId())) {
            throw new VueloNotFoundException(vuelo.getAvion().getId());
        }

        //Si EL VUELO TIENE ID, ES UN UPDATE
        if (vuelo.getId() != null) {
            Optional<Vuelo> optExisting = vueloRepository.findById(vuelo.getId());
            //Si no existe el vuelo, lanzamos excepción
            if (optExisting.isEmpty()) {
                throw new VueloNotFoundException(vuelo.getId());
            }
            //Si existe, actualizamos los campos
            //Sacamos el Optional para poder usar los metodos
            Vuelo existing = optExisting.get();
            existing.setNumeroVuelo(vuelo.getNumeroVuelo());
            existing.setFechaSalida(vuelo.getFechaSalida());
            existing.setFechaLlegada(vuelo.getFechaLlegada());
            existing.setAeropuertoOrigen(vuelo.getAeropuertoOrigen());
            existing.setAeropuertoDestino(vuelo.getAeropuertoDestino());
            existing.setAvion(vuelo.getAvion());
            return Optional.of(vueloRepository.save(existing));
        }

        //RESTO DE VALIDACIONES DE Vuelo:

        //Si el número de vuelo es nulo o vacío
        if (vuelo.getNumeroVuelo() == null || vuelo.getNumeroVuelo().isEmpty()) {
            throw new VueloInvalidoException("El número de vuelo no puede ser nulo o vacío.");
        }        

        //SI el avión no tiene un origen ni un destino
        if (vuelo.getAeropuertoOrigen() == null || vuelo.getAeropuertoDestino() == null) {
            throw new VueloInvalidoException("El vuelo debe tener un aeropuerto de origen y destino.");
        }

        //JUntamos la fechaylahora en un LocalDateTime para poder calcular la duración
        //y comprobar que la llegada no es anterior a la salida
        LocalDateTime salida = LocalDateTime.of(vuelo.getFechaSalida(), vuelo.getHoraSalida());
        LocalDateTime llegada = LocalDateTime.of(vuelo.getFechaLlegada(), vuelo.getHoraLlegada());
        Duration duracion = Duration.between(salida, llegada);

        //Y ahora Verificamos las horas:
        //Si la fecha y hora del vuelo son anteriores a la fecha actual:
        if (vuelo.getFechaSalida().isBefore(LocalDate.now())){
            throw new VueloFechaHoraException(vuelo.getId(), "La fecha de salida del vuelo no puede ser anterior a la fecha actual.");
        }
        if (vuelo.getHoraSalida().isBefore(LocalTime.now()) && vuelo.getFechaSalida().isEqual(LocalDate.now())) {
            throw new VueloFechaHoraException(vuelo.getId(), "La hora de salida del vuelo no puede ser anterior a la hora actual.");
        }

        //Si la FEcha y hora de LLEGADA son ANTERIORES a la hora de SALIDA
        if (vuelo.getFechaLlegada().isBefore(vuelo.getFechaSalida())) {
            throw new VueloFechaHoraException(vuelo.getId(), "La fecha de llegada no puede ser anterior a la fecha de salida.");
        }
        if (vuelo.getHoraLlegada().isBefore(vuelo.getHoraSalida()) && vuelo.getFechaLlegada().isEqual(vuelo.getFechaSalida())) {
            throw new VueloFechaHoraException(vuelo.getId(), "La hora de llegada no puede ser anterior a la hora de salida.");
        }

        //El vuelo no puede durar mas de 20h
        if (duracion.toHours() > 20) {
            throw new IllegalArgumentException("La duración del vuelo no puede superar las 20 horas.");
        }

        return Optional.of(vueloRepository.save(vuelo));
    }

    public void deleteById(Long id) {
        //SI no se encuentra el vuelo, lanzamos excepción
        if (vueloRepository.findById(id).isEmpty()) {
            throw new VueloNotFoundException(id);
        }
        vueloRepository.deleteById(id);
    }

    public List<Vuelo> findByAvionId(Long avionId) {
        if (vueloRepository.findByAvionId(avionId).isEmpty()){
            //throw new VueloNotFoundException(avionId, "Mensaje personalizado: No se han encontrado vuelos para el avión con ID: " + avionId);
        }
        return vueloRepository.findByAvionId(avionId);
    }

    public List<Avion> getAvionesDisponibles(Long origenId, Long destinoId, String fechaSalida, String fechaLlegada) {
        // Recogemos los aviones que están en el aeropuerto de origen
        List<Avion> avionesEnOrigen = avionRepository.findByAeropuertoId(origenId);

        return avionesEnOrigen.stream()
                .filter(avion -> avionDisponible(avion, LocalDate.parse(fechaSalida), LocalDate.parse(fechaLlegada)))
                .collect(Collectors.toList());
    }

    private boolean avionDisponible(Avion avion, LocalDate fechaSalida, LocalDate fechaLlegada) {
        List<Vuelo> vuelosDelAvion = vueloRepository.findByAvionId(avion.getId());
        for (Vuelo vuelo : vuelosDelAvion) {
            // Si el vuelo se solapa con el nuevo rango, no está disponible
            if (!(vuelo.getFechaLlegada().isBefore(fechaSalida) || vuelo.getFechaSalida().isAfter(fechaLlegada))) {
            return false;
            }
        }
        return true;
    }


}
