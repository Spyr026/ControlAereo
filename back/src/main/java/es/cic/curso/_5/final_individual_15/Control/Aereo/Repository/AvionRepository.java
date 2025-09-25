package es.cic.curso._5.final_individual_15.Control.Aereo.Repository;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Avion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {

    List<Avion> findByAeropuertoId(Long aeropuertoId);

    List<Avion> findByAeropuertoIsNull();
}
