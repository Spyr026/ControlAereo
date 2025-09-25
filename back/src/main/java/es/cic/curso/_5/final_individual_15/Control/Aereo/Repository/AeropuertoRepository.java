package es.cic.curso._5.final_individual_15.Control.Aereo.Repository;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Long> {
}
