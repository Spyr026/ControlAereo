package es.cic.curso._5.final_individual_15.Control.Aereo.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Aeropuerto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Column(unique = true, nullable = false, length = 3)
    private String codigoIATA;
    private String ciudad;

    private int maximoAviones; // Máximo de aviones permitidos en el aeropuerto

    @OneToMany(mappedBy = "aeropuerto")
    @JsonIgnore //NO lo quiero en el JSON
    private List<Avion> aviones = new ArrayList<>();

    @OneToMany(mappedBy = "aeropuertoOrigen", cascade = CascadeType.ALL)
    @JsonIgnore //NO lo quiero en el JSON
    private List<Vuelo> vuelosOrigen = new ArrayList<>();

    @OneToMany(mappedBy = "aeropuertoDestino", cascade = CascadeType.ALL)
    @JsonIgnore //NO lo quiero en el JSON
    private List<Vuelo> vuelosDestino = new ArrayList<>();

    // Getters & Setters

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

    public int getMaximoAviones() {
        return maximoAviones;
    }

    public void setMaximoAviones(int maximoAviones) {
        this.maximoAviones = maximoAviones;
    }

    public List<Avion> getAviones() {
        return aviones;
    }

    public void setAviones(List<Avion> aviones) {
        this.aviones = aviones;
    }

    public void addAvion(Avion avion) {
        this.aviones.add(avion);
        avion.setAeropuerto(this);
    }

    public void removeAvion(Avion avion) {
        this.aviones.remove(avion);
        avion.setAeropuerto(null);
    }

    public List<Vuelo> getVuelosOrigen() {
        return vuelosOrigen;
    }

    public void setVuelosOrigen(List<Vuelo> vuelosOrigen) {
        this.vuelosOrigen = vuelosOrigen;
    }

    public void addVueloOrigen(Vuelo vuelo) {
        this.vuelosOrigen.add(vuelo);
        vuelo.setAeropuertoOrigen(this);
    }

    public void removeVueloOrigen(Vuelo vuelo) {
        this.vuelosOrigen.remove(vuelo);
        vuelo.setAeropuertoOrigen(null);
    }

    public List<Vuelo> getVuelosDestino() {
        return vuelosDestino;
    }

    public void setVuelosDestino(List<Vuelo> vuelosDestino) {
        this.vuelosDestino = vuelosDestino;
    }

    public void addVueloDestino(Vuelo vuelo) {
        this.vuelosDestino.add(vuelo);
        vuelo.setAeropuertoDestino(this);
    }

    public void removeVueloDestino(Vuelo vuelo) {
        this.vuelosDestino.remove(vuelo);
        vuelo.setAeropuertoDestino(null);
    }

    //HashCode & Equals

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((codigoIATA == null) ? 0 : codigoIATA.hashCode());
        result = prime * result + ((ciudad == null) ? 0 : ciudad.hashCode());
        result = prime * result + maximoAviones;
        result = prime * result + ((aviones == null) ? 0 : aviones.hashCode());
        result = prime * result + ((vuelosOrigen == null) ? 0 : vuelosOrigen.hashCode());
        result = prime * result + ((vuelosDestino == null) ? 0 : vuelosDestino.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aeropuerto other = (Aeropuerto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (codigoIATA == null) {
            if (other.codigoIATA != null)
                return false;
        } else if (!codigoIATA.equals(other.codigoIATA))
            return false;
        if (ciudad == null) {
            if (other.ciudad != null)
                return false;
        } else if (!ciudad.equals(other.ciudad))
            return false;
        if (maximoAviones != other.maximoAviones)
            return false;
        if (aviones == null) {
            if (other.aviones != null)
                return false;
        } else if (!aviones.equals(other.aviones))
            return false;
        if (vuelosOrigen == null) {
            if (other.vuelosOrigen != null)
                return false;
        } else if (!vuelosOrigen.equals(other.vuelosOrigen))
            return false;
        if (vuelosDestino == null) {
            if (other.vuelosDestino != null)
                return false;
        } else if (!vuelosDestino.equals(other.vuelosDestino))
            return false;
        return true;
    }

    
    //ToString
    @Override
    public String toString() {
        return "Aeropuerto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigoIATA='" + codigoIATA + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", maximoAviones=" + maximoAviones +
                ", aviones=" + aviones +
                ", vuelosOrigen=" + vuelosOrigen +
                ", vuelosDestino=" + vuelosDestino +
                '}';
    }

}
