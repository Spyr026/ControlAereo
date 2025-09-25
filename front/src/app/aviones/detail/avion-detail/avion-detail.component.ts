import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Avion } from '../../../models/avion.model';
import { ActivatedRoute } from '@angular/router';
import { AvionesService } from '../../aviones.service';
import { EstadoAvionPipe } from "../../../pipes/estadoAvion.pipe";
import { Aeropuerto } from '../../../models/aeropuerto.model';
import { AeropuertosService } from '../../../aeropuertos/aeropuertos.service';
import { Vuelo } from '../../../models/vuelo.model';
import { VuelosService } from '../../../vuelos/vuelos.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-avion-detail',
  standalone: true,
  imports: [CommonModule, EstadoAvionPipe, FormsModule],
  templateUrl: './avion-detail.component.html',
  styleUrls: ['./avion-detail.component.css']
})
export class AvionDetailComponent implements OnInit {

  avionId: number = 0;
  avion?: Avion;
  aeropuerto?: Aeropuerto;
  vuelos: Vuelo[] = [];
  edicionHabilitada: boolean = false;

  edicionAeropuerto : boolean = false;
  aeropuertosDisponibles: Aeropuerto[] = [];

  constructor(
    private route: ActivatedRoute,
    private avionesService: AvionesService,
    private aeropuertosService: AeropuertosService,
    private vuelosService: VuelosService
  ) {}

  ngOnInit() {
    //Cogemos el ID de la URL
    this.avionId = Number(this.route.snapshot.paramMap.get('id'));

    //Recogemos el avion de la BBDD
    this.avionesService.getAvionById(this.avionId).subscribe(avion => {
      this.avion = avion;
      this.aeropuerto = avion.aeropuerto;
      this.cargarVuelos();
    });
  }

  cargarVuelos() {
    if (!this.avionId) {
      return;
    }
    this.vuelosService.getVuelosByAvionId(this.avionId).subscribe(vuelos => {
      this.vuelos = vuelos;
    });
  }

  guardarCambiosAvion() {
    if (this.avion) {
      this.avionesService.updateAvion(this.avionId, this.avion).subscribe(() => {
        // salimos de la Edición
        this.edicionHabilitada = false;
        // Le decimos al usuario que se han guardado los cambios
        console.log('Cambios guardados');
      });
    }
  }

  guardarCambioAeropuerto() {

    
    
    if (this.vuelos && this.vuelos.length > 0) {
      alert('Avión tiene un vuelo asignado, por favor, revise el vuelo y cambie el avión antes de cambiar el aeropuerto del mismo');
      return;
    }

    if (this.avion) {
      this.avionesService.updateAvion(this.avionId, this.avion).subscribe(() => {
        // salimos de la Edición
        this.edicionAeropuerto = false;
        // Le decimos al usuario que se han guardado los cambios
        console.log('Cambios guardados');
      });
      //Cargar el aeropuerto actualizado
      this.aeropuertosService.getAeropuertoById(this.avion.aeropuerto?.id || 0).subscribe(aeropuerto => {
        this.aeropuerto = aeropuerto;
      });
      //Recargar los vuelos del avion por si acaso
      this.cargarVuelos();  
    }
  }

  getAeropuertosDisponibles() {
    this.aeropuertosService.getAeropuertosConEspacio().subscribe(aeropuertos => {
      this.aeropuertosDisponibles = aeropuertos;
    });
  }

}
