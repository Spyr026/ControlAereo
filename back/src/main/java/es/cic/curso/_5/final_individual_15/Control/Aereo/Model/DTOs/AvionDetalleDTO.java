package es.cic.curso._5.final_individual_15.Control.Aereo.Model.DTOs;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Enum.EstadoAvion;

public class AvionDetalleDTO {
    
    private Long id;
    private String matricula;
    private String modelo;
    private String aerolinea;
    private int capacidad;
    private EstadoAvion estado;
    private Long aeropuertoId;
    private String aeropuertoNombre;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoAvion getEstado() {
        return estado;
    }

    public void setEstado(EstadoAvion estado) {
        this.estado = estado;
    }

    public Long getAeropuertoId() {
        return aeropuertoId;
    }

    public void setAeropuertoId(Long aeropuertoId) {
        this.aeropuertoId = aeropuertoId;
    }

    public String getAeropuertoNombre() {
        return aeropuertoNombre;
    }

    public void setAeropuertoNombre(String aeropuertoNombre) {
        this.aeropuertoNombre = aeropuertoNombre;
    }
}
