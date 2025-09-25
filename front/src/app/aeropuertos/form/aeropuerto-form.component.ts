import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AeropuertosService } from '../aeropuertos.service';
import { Aeropuerto } from '../../models/aeropuerto.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Avion } from '../../models/avion.model';

@Component({
  selector: 'app-aeropuerto-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './aeropuerto-form.component.html',
  styleUrl: './aeropuerto-form.component.css'
})
export class AeropuertoFormComponent implements OnInit {


  aeropuertoId: number = 0;
  aeropuerto?: Aeropuerto;
  edicionHabilitada: boolean = false;
  avionesDisponibles: Avion[] = [];
  modoEdicionAviones: boolean = false;
  

    constructor(
      private aeropuertosService: AeropuertosService,
      private router: Router
    ) { }

  ngOnInit(){
    //OBtenemos el ID de la URL
    this.aeropuertoId = Number(this.router.url.split('/').slice(-2, -1)[0]);
    
    //Ahora Cogemos el aeropiuerto con el id AeropuertoId
    this.aeropuertosService.getAeropuertoDetalle(this.aeropuertoId).subscribe(data => {
      this.aeropuerto = data;
    });
  }

  guardarCambiosAeropuerto() {
    if (this.aeropuerto) {
      this.aeropuertosService.updateAeropuerto(this.aeropuertoId, this.aeropuerto).subscribe(() => {
        // Salimos de la Edición
        this.edicionHabilitada = false;
        // Le decimos al usuario que se han guardado los cambios
        console.log('Cambios guardados');
      });
    }
  }

  abrirPopupAviones() {
  // Lógica para cargar aviones disponibles (sin aeropuerto)
  this.aeropuertosService.getAvionesDisponibles().subscribe(aviones => {
    console.log('Aviones disponibles:', aviones); // <-- Añade este log
    this.avionesDisponibles = aviones;
  });
}

agregarAvionAlAeropuerto(avion: Avion) {
  // Lógica para asignar el avión al aeropuerto actual
  this.aeropuertosService.asignarAvionAlAeropuerto(avion.id ? avion.id : 0, this.aeropuerto!.id ? this.aeropuerto!.id : 0).subscribe(() => {
    // Actualiza la lista de aviones del aeropuerto
    this.aeropuerto!.aviones?.push(avion);
    // Opcional: Oculta el modal
    const modal = (window as any).bootstrap.Modal.getInstance(document.getElementById('popupAviones'));
    modal.hide();
  });
}

eliminarAvionDelAeropuerto(avion: Avion) {
  // Lógica para eliminar el avión del aeropuerto
  this.aeropuertosService.eliminarAvionDelAeropuerto(this.aeropuerto!.id, avion.id).subscribe(() => {
    this.aeropuerto!.aviones = this.aeropuerto!.aviones?.filter(a => a.id !== avion.id);
  });
}
  
}


