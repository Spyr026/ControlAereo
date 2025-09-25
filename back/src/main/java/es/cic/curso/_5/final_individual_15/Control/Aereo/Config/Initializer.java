package es.cic.curso._5.final_individual_15.Control.Aereo.Config;

import es.cic.curso._5.final_individual_15.Control.Aereo.Model.*;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Enum.EstadoAvion;
import es.cic.curso._5.final_individual_15.Control.Aereo.Model.Enum.EstadoVuelo;
import es.cic.curso._5.final_individual_15.Control.Aereo.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Initializer {

	@Bean
	CommandLineRunner initData(AeropuertoRepository aeropuertoRepo, AvionRepository avionRepo, VueloRepository vueloRepo) {
		return args -> {
			// Aeropuertos
			Aeropuerto a1 = new Aeropuerto(); a1.setNombre("Barajas"); a1.setCodigoIATA("MAD"); a1.setCiudad("Madrid"); a1.setMaximoAviones(5);
			Aeropuerto a2 = new Aeropuerto(); a2.setNombre("El Prat"); a2.setCodigoIATA("BCN"); a2.setCiudad("Barcelona"); a2.setMaximoAviones(10);
			Aeropuerto a3 = new Aeropuerto(); a3.setNombre("Malaga"); a3.setCodigoIATA("AGP"); a3.setCiudad("Malaga"); a3.setMaximoAviones(8);
			Aeropuerto a4 = new Aeropuerto(); a4.setNombre("Sevilla"); a4.setCodigoIATA("SVQ"); a4.setCiudad("Sevilla"); a4.setMaximoAviones(6);
			Aeropuerto a5 = new Aeropuerto(); a5.setNombre("Bilbao"); a5.setCodigoIATA("BIO"); a5.setCiudad("Bilbao"); a5.setMaximoAviones(4);
			Aeropuerto a6 = new Aeropuerto(); a6.setNombre("El que no tiene que salir al crear un avion"); a6.setCodigoIATA("VLC"); a6.setCiudad("Valencia"); a6.setMaximoAviones(0);
			aeropuertoRepo.save(a1); aeropuertoRepo.save(a2); aeropuertoRepo.save(a3); aeropuertoRepo.save(a4); aeropuertoRepo.save(a5); aeropuertoRepo.save(a6);

			// Aviones
			for (int i = 1; i <= 10; i++) {
				Avion avion = new Avion();
				avion.setMatricula("EC-" + (100 + i));
				avion.setModelo("Airbus A320");
				avion.setAerolinea(i % 2 == 0 ? "Iberia" : "Vueling");
				avion.setCapacidad(180);
				avion.setEstado(EstadoAvion.EN_TIERRA);

				// Asignar aeropuerto según el índice
				if (i <= 2) {
					avion.setAeropuerto(a1);
				} else if (i <= 4) {
					avion.setAeropuerto(a2);
				} else if (i <= 6) {
					avion.setAeropuerto(a3);
				} else if (i <= 8) {
					avion.setAeropuerto(a4);
				} else {
					avion.setAeropuerto(a5);
				}

				avionRepo.save(avion);
			}

			

			// Vuelos
			Vuelo v1 = new Vuelo();
			v1.setNumeroVuelo("IB1001"); v1.setAeropuertoOrigen(a1); v1.setAeropuertoDestino(a2); v1.setAvion(avionRepo.findById(1L).orElse(null)); v1.setEstado(EstadoVuelo.PROGRAMADO);
			v1.setFechaSalida(java.time.LocalDate.of(2025, 9, 10)); v1.setHoraSalida(java.time.LocalTime.of(8, 30));
			v1.setFechaLlegada(java.time.LocalDate.of(2025, 9, 10)); v1.setHoraLlegada(java.time.LocalTime.of(10, 15));

			Vuelo v2 = new Vuelo();
			v2.setNumeroVuelo("VY2002"); v2.setAeropuertoOrigen(a2); v2.setAeropuertoDestino(a3); v2.setAvion(avionRepo.findById(2L).orElse(null)); v2.setEstado(EstadoVuelo.EN_VUELO);
			v2.setFechaSalida(java.time.LocalDate.of(2025, 9, 11)); v2.setHoraSalida(java.time.LocalTime.of(12, 0));
			v2.setFechaLlegada(java.time.LocalDate.of(2025, 9, 11)); v2.setHoraLlegada(java.time.LocalTime.of(13, 45));

			Vuelo v3 = new Vuelo();
			v3.setNumeroVuelo("IB3003"); v3.setAeropuertoOrigen(a3); v3.setAeropuertoDestino(a4); v3.setAvion(avionRepo.findById(3L).orElse(null)); v3.setEstado(EstadoVuelo.ATERRIZADO);
			v3.setFechaSalida(java.time.LocalDate.of(2025, 9, 12)); v3.setHoraSalida(java.time.LocalTime.of(15, 20));
			v3.setFechaLlegada(java.time.LocalDate.of(2025, 9, 12)); v3.setHoraLlegada(java.time.LocalTime.of(17, 5));

			Vuelo v4 = new Vuelo();
			v4.setNumeroVuelo("VY4004"); v4.setAeropuertoOrigen(a4); v4.setAeropuertoDestino(a5); v4.setAvion(avionRepo.findById(4L).orElse(null)); v4.setEstado(EstadoVuelo.CANCELADO);
			v4.setFechaSalida(java.time.LocalDate.of(2025, 9, 13)); v4.setHoraSalida(java.time.LocalTime.of(9, 0));
			v4.setFechaLlegada(java.time.LocalDate.of(2025, 9, 13)); v4.setHoraLlegada(java.time.LocalTime.of(10, 40));

			Vuelo v5 = new Vuelo();
			v5.setNumeroVuelo("IB5005"); v5.setAeropuertoOrigen(a5); v5.setAeropuertoDestino(a1); v5.setAvion(avionRepo.findById(5L).orElse(null)); v5.setEstado(EstadoVuelo.PROGRAMADO);
			v5.setFechaSalida(java.time.LocalDate.of(2025, 9, 14)); v5.setHoraSalida(java.time.LocalTime.of(18, 10));
			v5.setFechaLlegada(java.time.LocalDate.of(2025, 9, 14)); v5.setHoraLlegada(java.time.LocalTime.of(20, 0));

			vueloRepo.save(v1); vueloRepo.save(v2); vueloRepo.save(v3); vueloRepo.save(v4); vueloRepo.save(v5);
		};
	}
}
