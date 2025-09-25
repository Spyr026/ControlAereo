import { Component, OnInit } from '@angular/core';
import { Vuelo } from '../../../models/vuelo.model';
import { VuelosService } from '../../vuelos.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Aeropuerto } from '../../../models/aeropuerto.model';
import { Avion } from '../../../models/avion.model';
import { AeropuertosService } from '../../../aeropuertos/aeropuertos.service';

@Component({
  selector: 'app-vuelos-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './vuelos-list.component.html',
  styleUrls: ['./vuelos-list.component.css']
})


export class VuelosListComponent implements OnInit {

  //Auxiliares
  nuevoVuelo: Vuelo = {
    numeroVuelo: '',
    aeropuertoOrigen: undefined,
    aeropuertoDestino: undefined,
    avion: undefined,
    estado: 'PROGRAMADO',
    fechaSalida: '',
    fechaLlegada: '',
    horaSalida: '',
    horaLlegada: ''
  };

  aeropuertosDisponibles: Aeropuerto[] = [];
  avionesDisponibles: Avion[] = [];

  //Chequeo de horas y fechas
  hoy: string = new Date().toISOString().split('T')[0];
  minHoraSalida: string = new Date().toTimeString().slice(0, 5);

  fechaSalidaInvalida = false;
  horaSalidaInvalida = false;
  fechaLlegadaInvalida = false;
  horaLlegadaInvalida = false;

  codigoErrorFechaSalida = 0;
  mensajeErrorFechaSalida = '';
  codigoErrorHoraSalida = 0;
  mensajeErrorHoraSalida = '';
  codigoErrorFechaLlegada = 0;
  mensajeErrorFechaLlegada = '';
  codigoErrorHoraLlegada = 0;
  mensajeErrorHoraLlegada = '';

  vuelos: Vuelo[] = [];


  constructor(
    private vuelosService: VuelosService,
    private aeropuertosService: AeropuertosService
  ) { }

  ngOnInit() {
    this.vuelosService.getVuelos().subscribe(data => {
      this.vuelos = data;
    });
  }

  guardarVuelo() {
    this.vuelosService.createVuelo(this.nuevoVuelo).subscribe(() => {
      // Recargar la lista de vuelos después de crear uno nuevo
      this.vuelosService.getVuelos().subscribe(data => {
        this.vuelos = data;
      });
    });

    this.nuevoVuelo = {
      numeroVuelo: '',
      aeropuertoOrigen: undefined,
      aeropuertoDestino: undefined,
      avion: undefined,
      estado: 'PROGRAMADO',
      fechaSalida: new Date().toISOString().split('T')[0],
      fechaLlegada: '',
      horaSalida: new Date().toTimeString().slice(0, 5),
      horaLlegada: ''
    };
  }

  abrirModalVuelo() {
    //Obtenemos los aeropuertos y aviones disponibles
    this.getAeropuertos();
    this.getAvionesDisponibles();
     //Le damos hora y fecha actual a la salida
    this.nuevoVuelo = {
      numeroVuelo: '',
      aeropuertoOrigen: undefined,
      aeropuertoDestino: undefined,
      avion: undefined,
      estado: 'PROGRAMADO',
      fechaSalida: new Date().toISOString().split('T')[0],
      fechaLlegada: '',
      horaSalida: new Date().toTimeString().slice(0, 5),
      horaLlegada: ''
    };

  }

  getAeropuertos() {
    this.aeropuertosService.getAeropuertos().subscribe(data => {
      this.aeropuertosDisponibles = data;
    });
  }

  getAvionesDisponibles() {
  const origenId = this.nuevoVuelo.aeropuertoOrigen?.id;
  const destinoId = this.nuevoVuelo.aeropuertoDestino?.id;
  const fechaSalida = this.nuevoVuelo.fechaSalida;
  const fechaLlegada = this.nuevoVuelo.fechaLlegada;

  if (
    origenId !== undefined && //Si el origen esta definido
    destinoId !== undefined && //Si el destino esta definido
    fechaSalida && //Si la fecha de salida esta definida
    fechaLlegada //Si la fecha de llegada esta definida
  ) { //Le hacemos la request al metodo
    this.vuelosService.getAvionesDisponibles(
      origenId,
      destinoId,
      fechaSalida,
      fechaLlegada
    ).subscribe(data => {
      this.avionesDisponibles = data;
    });
  } else {
    // Si falta algún dato, no llamar al servicio y limpiar la lista
    this.avionesDisponibles = [];
  }
}

  eliminarVuelo(id: number | undefined) {
    if (id !== undefined && confirm('¿Estás seguro de que deseas eliminar este vuelo?')) {
      this.vuelosService.deleteVuelo(id).subscribe(() => {
        // Recargar la lista de vuelos después de eliminar uno
        this.vuelosService.getVuelos().subscribe(data => {
          this.vuelos = data;
        });
      });
    }
  }

  // Validación para fechas y horas
  validarFechaSalidaVuelo() {
    const hoyDate = new Date(this.hoy);
    const fechaSalida = new Date(this.nuevoVuelo.fechaSalida);

    //Si no hay una fecha de salida
    if (!this.nuevoVuelo.fechaSalida) {
      this.codigoErrorFechaSalida = 3;
      this.mensajeErrorFechaSalida = "La fecha de salida es obligatoria.";
      return;
    }

    //Si la fecha de salida es anterior a hoy
    if (fechaSalida < hoyDate) {
      this.codigoErrorFechaSalida = 2;
      this.mensajeErrorFechaSalida = "La fecha de salida no puede ser anterior a hoy.";
      return;
    }

    //Si la fecha de salida es posterior a la fecha de llegada
    if (this.nuevoVuelo.fechaLlegada && fechaSalida > new Date(this.nuevoVuelo.fechaLlegada)) {
      this.codigoErrorFechaSalida = 1;
      this.mensajeErrorFechaSalida = "La fecha de salida no puede ser posterior a la fecha de llegada.";
      return;
    }
    this.codigoErrorFechaSalida = 0;
    this.mensajeErrorFechaSalida = '';
  }

  validarHoraSalidaVuelo() {
    const horaSalida = this.nuevoVuelo.horaSalida;
    const horaAhora = new Date().toTimeString().slice(0, 5);

    if (!horaSalida) {
      this.codigoErrorHoraSalida = 4;
      this.mensajeErrorHoraSalida = "La hora de salida es obligatoria.";
      return;
    }
    if (this.nuevoVuelo.fechaSalida === this.hoy && horaSalida < horaAhora) {
      this.codigoErrorHoraSalida = 8;
      this.mensajeErrorHoraSalida = "La hora de salida no puede ser anterior a la actual.";
      return;
    }
    this.codigoErrorHoraSalida = 0;
    this.mensajeErrorHoraSalida = '';
  }

  validarFechaLlegadaVuelo() {
    const fechaSalida = new Date(this.nuevoVuelo.fechaSalida);
    const fechaLlegada = new Date(this.nuevoVuelo.fechaLlegada);

    if (!this.nuevoVuelo.fechaLlegada) {
      this.codigoErrorFechaLlegada = 5;
      this.mensajeErrorFechaLlegada = "La fecha de llegada es obligatoria.";
      return;
    }
    if (fechaLlegada < fechaSalida) {
      this.codigoErrorFechaLlegada = 7;
      this.mensajeErrorFechaLlegada = "La fecha de llegada no puede ser anterior a la de salida.";
      return;
    }
    this.codigoErrorFechaLlegada = 0;
    this.mensajeErrorFechaLlegada = '';
  }

  validarHoraLlegadaVuelo() {
    const fechaSalida = new Date(this.nuevoVuelo.fechaSalida);
    const fechaLlegada = new Date(this.nuevoVuelo.fechaLlegada);
    const horaSalida = this.nuevoVuelo.horaSalida;
    const horaLlegada = this.nuevoVuelo.horaLlegada;

    if (!horaLlegada) {
      this.codigoErrorHoraLlegada = 6;
      this.mensajeErrorHoraLlegada = "La hora de llegada es obligatoria.";
      return;
    }
    if (fechaLlegada.toDateString() === fechaSalida.toDateString() && horaLlegada <= horaSalida) {
      this.codigoErrorHoraLlegada = 9;
      this.mensajeErrorHoraLlegada = "La hora de llegada no puede ser anterior o igual a la hora de salida si es el mismo día.";
      return;
    }
    this.codigoErrorHoraLlegada = 0;
    this.mensajeErrorHoraLlegada = '';
  }

  onFechaSalidaChange() {
    this.validarFechaSalidaVuelo();
    this.validarHoraSalidaVuelo();
    this.validarFechaLlegadaVuelo();
    this.validarHoraLlegadaVuelo();
  }
  onFechaLlegadaChange() {
    this.validarFechaSalidaVuelo();
    this.validarHoraSalidaVuelo();
    this.validarFechaLlegadaVuelo();
    this.validarHoraLlegadaVuelo();
  }
  onHoraSalidaChange() {
    this.validarFechaSalidaVuelo();
    this.validarHoraSalidaVuelo();
    this.validarFechaLlegadaVuelo();
    this.validarHoraLlegadaVuelo();
  }
  onHoraLlegadaChange() {
    this.validarFechaSalidaVuelo();
    this.validarHoraSalidaVuelo();
    this.validarFechaLlegadaVuelo();
    this.validarHoraLlegadaVuelo();
  }

}

