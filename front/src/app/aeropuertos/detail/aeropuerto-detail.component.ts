import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Aeropuerto } from '../../models/aeropuerto.model';
import { AeropuertosService } from '../aeropuertos.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-aeropuerto-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './aeropuerto-detail.component.html',
  styleUrls: ['./aeropuerto-detail.component.css']
})


  export class AeropuertoDetailComponent implements OnInit {

    aeropuertoId: number = 0;
    aeropuerto?: Aeropuerto;

    constructor(
      private aeropuertosService: AeropuertosService,
      private router: Router
    ) { }

  ngOnInit(){
    //OBtenemos el ID de la URL
    this.aeropuertoId = Number(this.router.url.split('/').pop());
    
    //Ahora Cogemos el aeropiuerto con el id AeropuertoId
    this.aeropuertosService.getAeropuertoDetalle(this.aeropuertoId).subscribe(data => {
      this.aeropuerto = data;
    });
  }

}
