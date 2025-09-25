package es.cic.curso._5.final_individual_15.Control.Aereo.Model.DTOs;

import java.util.List;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;

public class AeropuertoDetalleDto {
    
    private Long id;
    private String nombre;
    private String codigoIATA;
    private String ciudad;
    private List<Avion> aviones;
    private List<Vuelo> vuelosOrigen;
    private List<Vuelo> vuelosDestino;
    // getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCodigoIATA() {
        return codigoIATA;
    }
    public void setCodigoIATA(String codigoIATA) {
        this.codigoIATA = codigoIATA;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public List<Avion> getAviones() {
        return aviones;
    }
    public void setAviones(List<Avion> aviones) {
        this.aviones = aviones;
    }
    public List<Vuelo> getVuelosOrigen() {
        return vuelosOrigen;
    }
    public void setVuelosOrigen(List<Vuelo> vuelosOrigen) {
        this.vuelosOrigen = vuelosOrigen;
    }
    public List<Vuelo> getVuelosDestino() {
        return vuelosDestino;
    }
    public void setVuelosDestino(List<Vuelo> vuelosDestino) {
        this.vuelosDestino = vuelosDestino;
    }
    
}
