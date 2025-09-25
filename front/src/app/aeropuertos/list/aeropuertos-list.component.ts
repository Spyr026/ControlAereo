import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Aeropuerto } from '../../models/aeropuerto.model';
import { AeropuertosService } from '../aeropuertos.service';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-aeropuertos-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './aeropuertos-list.component.html',
  styleUrls: ['./aeropuertos-list.component.css']
})


export class AeropuertosListComponent implements OnInit {

  aeropuertos: Aeropuerto[] = [];
  aeropuertoAEliminar?: Aeropuerto;
  aforo: number = 0;

  //Booleano para el checkbox del campo IATA al crear
  isIataEditable = false;

  nuevoAeropuerto: Aeropuerto = {
    nombre: '',
    ciudad: '',
    codigoIATA: '',
    maximoAviones: 0
  };

  constructor(
    private aeropuertosService: AeropuertosService
  ) { }

  ngOnInit() {
    this.aeropuertosService.getAeropuertos().subscribe(data => {
      this.aeropuertos = data;
    });
  }

  guardarAeropuerto() {
    this.aeropuertosService.createAeropuerto(this.nuevoAeropuerto).subscribe(data => {
      this.aeropuertos.push(data);
      this.nuevoAeropuerto = {
        nombre: '',
        ciudad: '',
        codigoIATA: '',
        maximoAviones: 0
      };
    });
  }

  abrirModalAeropuerto() {
    this.nuevoAeropuerto = {
      nombre: '',
      ciudad: '',
      codigoIATA: '',
      maximoAviones: 0
    };
    // Lógica para abrir el modal (puede variar según la librería/modal que uses)
  }

  onIataCheckboxChange() {
    if (!this.isIataEditable) {
      // Si se deshabilita, autocompleta de nuevo
      this.nuevoAeropuerto.codigoIATA = this.nuevoAeropuerto.ciudad.substring(0, 3).toUpperCase();
    }
  }

  // En el método que maneja el cambio de ciudad:
  onCiudadChange() {
    if (!this.isIataEditable) {
      this.nuevoAeropuerto.codigoIATA = this.nuevoAeropuerto.ciudad.substring(0, 3).toUpperCase();
    }
  }

  abrirModalEliminar(aeropuerto: Aeropuerto) {
    this.aeropuertoAEliminar = aeropuerto;
  }

  eliminarAeropuerto(id: number | undefined) {
    if (id) {
      this.aeropuertosService.deleteAeropuerto(id).subscribe(() => {
        this.aeropuertos = this.aeropuertos.filter(a => a.id !== id);
        this.aeropuertoAEliminar = undefined;
        // Cerrar el modal usando Bootstrap Modal API
        const modal = document.getElementById('modalConfirmarEliminar');
        if (modal) {
          // @ts-ignore
          const bsModal = window.bootstrap?.Modal?.getOrCreateInstance(modal);
          if (bsModal) {
            bsModal.hide();
          }
        }
      });
    }
  }


}
