package es.cic.curso._5.final_individual_15.Control.Aereo.Repository;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Vuelo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    List<Vuelo> findByAeropuertoOrigenId(Long aeropuertoId);

    List<Vuelo> findByAeropuertoDestinoId(Long aeropuertoId);

    List<Vuelo> findByAvionId(Long avionId);

    List<Vuelo> findByEstado(String string);
}
