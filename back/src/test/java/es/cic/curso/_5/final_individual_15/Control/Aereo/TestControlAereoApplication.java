package es.cic.curso._5.final_individual_15.Control.Aereo;

import org.springframework.boot.SpringApplication;

public class TestControlAereoApplication {

	public static void main(String[] args) {
		SpringApplication.from(ControlAereoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
