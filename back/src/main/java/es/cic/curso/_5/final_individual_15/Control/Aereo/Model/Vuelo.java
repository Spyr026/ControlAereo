package es.cic.curso._5.final_individual_15.Control.Aereo.Model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Enum.EstadoVuelo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroVuelo;

    @ManyToOne
    @JsonIgnoreProperties({"vuelosOrigen", "vuelosDestino", "aviones"})
    private Aeropuerto aeropuertoOrigen;

    @ManyToOne
    @JsonIgnoreProperties({"vuelosDestino", "vuelosOrigen", "aviones"})
    private Aeropuerto aeropuertoDestino;

    @ManyToOne
    @JsonIgnoreProperties("aeropuerto")
    //SI los aeropuertos los tengo en los de arriba para que los quiero desde el avion
    private Avion avion;

    @Enumerated(EnumType.STRING)
    private EstadoVuelo estado;

    private LocalDate fechaSalida;
    private LocalDate fechaLlegada;

    private LocalTime horaSalida;
    private LocalTime horaLlegada;


    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public void setNumeroVuelo(String numeroVuelo) {
        this.numeroVuelo = numeroVuelo;
    }

    public Aeropuerto getAeropuertoOrigen() {
        return aeropuertoOrigen;
    }

    public void setAeropuertoOrigen(Aeropuerto aeropuertoOrigen) {
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public Aeropuerto getAeropuertoDestino() {
        return aeropuertoDestino;
    }

    public void setAeropuertoDestino(Aeropuerto aeropuertoDestino) {
        this.aeropuertoDestino = aeropuertoDestino;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public EstadoVuelo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVuelo estado) {
        this.estado = estado;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }
    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }
    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }
    public LocalTime getHoraSalida() {
        return horaSalida;
    }
    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }
    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }
    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

}
