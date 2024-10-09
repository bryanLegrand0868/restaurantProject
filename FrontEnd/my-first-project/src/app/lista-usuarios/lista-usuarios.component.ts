import { CommonModule } from '@angular/common';
import { HttpClient,HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [FormsModule,HttpClientModule,CommonModule],
  templateUrl: './lista-usuarios.component.html',
  styleUrl: './lista-usuarios.component.css'
})
export class ListaUsuariosComponent {
  usuarios:any = [];
  constructor(private http:HttpClient){
    this.buscarUsuarios();
  }

  buscarUsuarios(){
    this.servicioBuscarUsuarios().subscribe(
      (u:any) => this.usuarios = u
    )
  }

  servicioBuscarUsuarios():Observable<any>{
    return this.http.get<any>("http://localhost:8181/usuario/buscar");
  }
}