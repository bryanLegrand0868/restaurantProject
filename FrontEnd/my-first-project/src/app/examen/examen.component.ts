import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-examen',
  standalone: true,
  imports: [CommonModule, FormsModule,HttpClientModule],
  templateUrl: './examen.component.html',
  styleUrl: './examen.component.css'
})
export class ExamenComponent {
  examen:any = [];

  nuevoExamen = {
    noParcial: null,
    fechaExamen: '',
    nota: null,
    carnet: ''
  };

  constructor(private http:HttpClient){
    this.buscarExamenes();
  }

  buscarExamenes(){
    this.servicioBuscarExamenes().subscribe(
      (e:any) => this.examen = e
    )
  }

  servicioBuscarExamenes():Observable<any>{
    return this.http.get<any>("http://localhost:8181/examen/buscar");
  }

  guardarExamen() {
    this.servicioGuardarExamen(this.nuevoExamen).subscribe(
      (response: any) => {
        console.log('Examen guardado:', response);
        this.buscarExamenes();  
      },
      (error: any) => {
        console.error('Error al guardar el examen:', error);
      }
    );
  }

  servicioGuardarExamen(examen: any): Observable<any> {
    return this.http.post<any>("http://localhost:8181/examen/guardar", examen);
  }  
  
}
