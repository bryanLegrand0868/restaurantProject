import { CommonModule } from '@angular/common';
import { HttpClient,HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-lista-usuarios',
  standalone: true,
  imports: [FormsModule,HttpClientModule,CommonModule, RouterModule],
  templateUrl: './lista-usuarios.component.html',
  styleUrl: './lista-usuarios.component.css'
})
export class ListaUsuariosComponent {
  usuarios:any = [];
  constructor(private http:HttpClient, private router: Router){
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

  logout() {
    localStorage.removeItem('userSession');
    this.router.navigate(['/login']);  
  }

}
