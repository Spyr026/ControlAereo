package es.cic.curso._5.final_individual_15.Control.Aereo.Model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Enum.EstadoAvion;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matricula;
    private String modelo;
    private String aerolinea;
    private int capacidad;

    @Enumerated(EnumType.STRING)
    private EstadoAvion estado;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JsonIgnoreProperties("aviones") 
    // Para que voy a querer los aviones cuando consulte un aeropuerto desde un avion...
    private Aeropuerto aeropuerto;

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

    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }
    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    //hashCode & equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
        result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
        result = prime * result + ((aerolinea == null) ? 0 : aerolinea.hashCode());
        result = prime * result + capacidad;
        result = prime * result + ((estado == null) ? 0 : estado.hashCode());
        result = prime * result + ((aeropuerto == null) ? 0 : aeropuerto.hashCode());
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
        Avion other = (Avion) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (matricula == null) {
            if (other.matricula != null)
                return false;
        } else if (!matricula.equals(other.matricula))
            return false;
        if (modelo == null) {
            if (other.modelo != null)
                return false;
        } else if (!modelo.equals(other.modelo))
            return false;
        if (aerolinea == null) {
            if (other.aerolinea != null)
                return false;
        } else if (!aerolinea.equals(other.aerolinea))
            return false;
        if (capacidad != other.capacidad)
            return false;
        if (estado != other.estado)
            return false;
        if (aeropuerto == null) {
            if (other.aeropuerto != null)
                return false;
        } else if (!aeropuerto.equals(other.aeropuerto))
            return false;
        return true;
    }


    // toString
    @Override
    public String toString() {
        return "Avion [id=" + id + ", matricula=" + matricula + ", modelo=" + modelo + ", aerolinea=" + aerolinea + ", capacidad=" + capacidad + ", estado=" + estado + "]";
    }

}
