import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Avion } from '../../../models/avion.model';
import { AvionesService } from '../../aviones.service';
import { RouterModule } from '@angular/router';
import { EstadoAvionPipe } from "../../../pipes/estadoAvion.pipe";
import { FormsModule } from '@angular/forms';
import { Aeropuerto } from '../../../models/aeropuerto.model';
import { AeropuertosService } from '../../../aeropuertos/aeropuertos.service';

@Component({
  selector: 'app-aviones-list',
  standalone: true,
  imports: [
    CommonModule, 
    RouterModule, 
    EstadoAvionPipe,
    FormsModule
  ],
  templateUrl: './aviones-list.component.html',
  styleUrls: ['./aviones-list.component.css']
})


export class AvionesListComponent implements OnInit {

  //Auxiliares
  aviones: Avion[] = [];
  aeropuertosConEspacio: Aeropuerto[] = []; // Lista de aeropuertos con espacio disponible

  nuevoAvion: Avion = {
      id: undefined, // temporal, el backend asignará el ID real
      matricula: '',
      modelo: '',
      aerolinea: '',
      capacidad: 1,
      estado: 'EN_TIERRA',
      aeropuerto: undefined
    };

  constructor(
    private avionesService: AvionesService,
    private aeropuertosService: AeropuertosService
  ) { }

  ngOnInit(){
    //Recogemos los aviones del backend
    this.avionesService.getAviones().subscribe(data => {
      this.aviones = data;
    });

    
  }

  getAeropuertosConEspacio() {
    this.aeropuertosService.getAeropuertosConEspacio().subscribe(data => {
      this.aeropuertosConEspacio = data;
    });
  }

  getAllAeropuertos() {
    this.aeropuertosService.getAeropuertos().subscribe(data => {
      this.aeropuertosConEspacio = data;
    });
  }

  /**
   * Función para guardar un nuevo avión
   * Llama al servicio para crear el avión y actualiza la lista local
   * Resetea el objeto nuevoAvion para permitir la creación de otro avión
   */
  guardarAvion() {
    this.avionesService.createAvion(this.nuevoAvion).subscribe(data => {
      this.aviones.push(data);
      //Guardamos el id que se le ha dado al avion
      const idAsignado = data.id;
      //SI tiene el id, y le hemos pasado un aeropuerto
      if (idAsignado && this.nuevoAvion.aeropuerto) {
        //Le añadimos al array de Aviones en el aeropuerto
        this.aeropuertosService.asignarAvionAlAeropuerto(idAsignado, this.nuevoAvion.aeropuerto.id!).subscribe(() => {
          // Avión asignado correctamente
        });
      }
      this.nuevoAvion = {
        id: undefined, // temporal, el backend asignará el ID real
        matricula: '',
        modelo: '',
        aerolinea: '',
        capacidad: 1,
        estado: 'EN_TIERRA',
        aeropuerto: undefined
      };
    });
  }

  eliminarAvion(avion: Avion) {
    if (avion.id && confirm(`¿Estás seguro de que deseas eliminar el avión con matrícula ${avion.matricula}? Esta acción no se puede deshacer.`)) {
      this.avionesService.deleteAvion(avion.id).subscribe(() => {
        // Actualizamos la lista local de aviones
        this.aviones = this.aviones.filter(a => a.id !== avion.id);
      }, (err) => {
        alert('No se pudo eliminar el avión. Es posible que tenga vuelos asignados o que el backend haya devuelto un error.');
      }
      );
    }
  }

}
