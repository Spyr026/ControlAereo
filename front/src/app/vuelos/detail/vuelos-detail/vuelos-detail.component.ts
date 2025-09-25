import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Vuelo } from '../../../models/vuelo.model';
import { VuelosService } from '../../vuelos.service';
import { FormsModule } from '@angular/forms';
import { Aeropuerto } from '../../../models/aeropuerto.model';
import { AeropuertosService } from '../../../aeropuertos/aeropuertos.service';
import { Avion } from '../../../models/avion.model';
import { AvionesService } from '../../../aviones/aviones.service';


@Component({
  selector: 'app-vuelos-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vuelos-detail.component.html',
  styleUrls: ['./vuelos-detail.component.css']
})

export class VuelosDetailComponent implements OnInit{




  //Auxiliares
  vueloId: number=0;
  vuelo?: Vuelo;
  vueloOriginal?: Vuelo; //Para guardar el estado original en caso de cancelar la edición
  //Edición
  edicionHabilitada: boolean = false;
  avionesDisponibles: any[] = [];

  //Aux Cambio Aeropuertos
  edicionAeropuertoOrigen: boolean = false;
  aeropuertosOrigenDisponibles: Aeropuerto[] = [];
  edicionAeropuertoDestino: boolean = false;
  aeropuertosDestinoDisponibles: Aeropuerto[] = [];

  //Aux CAmbio de Avion
  edicionAvion: boolean = false;
  avionesDisponiblesCambio: Avion[] = [];


  constructor(
    private vuelosService: VuelosService, 
    private aeropuertoService: AeropuertosService,
    private avionesService: AvionesService) { }

  ngOnInit(){
    //OBtenemos el ID de la URL
    this.vueloId = Number(window.location.pathname.split('/').pop());
    
    //Ahora Cogemos el vuelo con el id VueloId
    this.vuelosService.getVueloById(this.vueloId).subscribe(data => {
      this.vuelo = data;
      this.vueloOriginal = JSON.parse(JSON.stringify(data)); // Guardamos el estado original
    });

  }

  guardarCambiosVuelo() {
    if (this.vuelo) {
      this.vuelosService.updateVuelo(this.vueloId, this.vuelo).subscribe(() => {
        // Salimos de la Edición
        this.edicionHabilitada = false;
        // Le decimos al usuario que se han guardado los cambios
        console.log('Cambios guardados');
      });
    }
  }

  guardarCambioAeropuertoOrigen() {
    
    //Si el aeropuerto es el mismo, damos un menasaje y no dejamos continuar
    if (this.vuelo?.aeropuertoOrigen?.id === this.vueloOriginal?.aeropuertoOrigen?.id) {
      alert('El aeropuerto de Destino es el mismo, por favor, seleccione otro aeropuerto');
      return;
    }

    //Lanzamos una alerta de confirmación
    if (confirm('¿Estás seguro de que deseas cambiar el aeropuerto de origen? (' 
      + this.vuelo?.aeropuertoOrigen?.nombre + ' -> ' 
      + this.vueloOriginal?.aeropuertoOrigen?.nombre + ')')) {
      if (this.vuelo) {
        this.vuelosService.updateVuelo(this.vueloId, this.vuelo).subscribe(() => {
          // Salimos de la Edición
          this.edicionAeropuertoOrigen = false;
          // Le decimos al usuario que se han guardado los cambios
          console.log('Cambios guardados');
        });
      }
    }
  }

  guardarCambioAeropuertoDestino() {
    //Si el aeropuerto es el mismo, damos un menasaje y no dejamos continuar
    if (this.vuelo?.aeropuertoDestino?.id === this.vueloOriginal?.aeropuertoDestino?.id) {
      alert('El aeropuerto de Origen es el mismo, por favor, seleccione otro aeropuerto');
      return;
    }
    //Si el aeropuerto de destino es el mismo que el de origen, damos un mensaje y no dejamos continuar
    if (this.vuelo?.aeropuertoDestino?.id === this.vuelo?.aeropuertoOrigen?.id) {
      alert('El aeropuerto de destino no puede ser el mismo que el de origen, por favor, seleccione otro aeropuerto');
      return;
    }

    //Lanzamos una alerta de confirmación
    if (confirm('¿Estás seguro de que deseas cambiar el aeropuerto de destino? (' 
      + this.vuelo?.aeropuertoDestino?.nombre + ' -> ' 
      + this.vueloOriginal?.aeropuertoDestino?.nombre + ')')) {
      if (this.vuelo) {
        this.vuelosService.updateVuelo(this.vueloId, this.vuelo).subscribe(() => {
          // Salimos de la Edición
          this.edicionAeropuertoDestino = false;
          // Le decimos al usuario que se han guardado los cambios
          console.log('Cambios guardados');
        });
      }
    }
  }

  getAeropuertosOrigenDisponibles() {
    this.aeropuertoService.getAeropuertos().subscribe(data => {
      this.aeropuertosOrigenDisponibles = data;
    });
  }

  getAeropuertosDestinoDisponibles() {

    //En caso de ser necesario, filtrar los aeropuertos para que no salga el mismo que el de origen
    const origenId = this.vuelo?.aeropuertoOrigen?.id;
    this.aeropuertosDestinoDisponibles = this.aeropuertosDestinoDisponibles.filter(a => a.id !== origenId);

    //Cogemos todos los aeropuertos del backend
    this.aeropuertoService.getAeropuertos().subscribe(data => {
      this.aeropuertosDestinoDisponibles = data;
    });
  }

  getAvionesDisponibles() {

    const origenId = this.vuelo?.aeropuertoOrigen?.id;
    const destinoId = this.vuelo?.aeropuertoDestino?.id;
    const fechaSalida = this.vuelo?.fechaSalida;
    const fechaLlegada = this.vuelo?.fechaLlegada;
    if (typeof origenId !== 'number' || typeof destinoId !== 'number'
      || !fechaSalida || !fechaLlegada
    ) {
      alert('Por favor, seleccione primero los aeropuertos de origen y destino para poder ver correctamente los aviones disponibles.');
      return;
    }

    this.vuelosService.getAvionesDisponibles(
      origenId, destinoId,
      fechaSalida, fechaLlegada
    ).subscribe(data => {
      this.avionesDisponibles = data;
    });
  }

  guardarCambioAvionAsignado() {
    //Si el avion es el mismo, damos un menasaje y no dejamos continuar
    if (this.vuelo?.avion?.id === this.vueloOriginal?.avion?.id) {
      alert('El avión asignado es el mismo, por favor, seleccione otro avión');
      return;
    }

    //Lanzamos una alerta de confirmación
    if (confirm('¿Estás seguro de que deseas cambiar el avión asignado? (' 
      + this.vuelo?.avion?.matricula + ' -> ' 
      + this.vueloOriginal?.avion?.matricula + ')')) {
      if (this.vuelo) {
        this.vuelosService.updateVuelo(this.vueloId, this.vuelo).subscribe(() => {
          // Salimos de la Edición
          this.edicionAvion = false;
          // Le decimos al usuario que se han guardado los cambios
          console.log('Cambios guardados');
        });
      }
    }
  }

}
