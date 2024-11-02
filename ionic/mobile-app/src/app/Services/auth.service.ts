import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8181/usuario';

  constructor(private http: HttpClient) {}

  login(correoElectronico: string, contrasenia: string): Observable<any> {
    const usuario = { correoElectronico, contrasenia };
    return this.http.post(`${this.apiUrl}/login`, usuario).pipe(
      tap((response: any) => {
        localStorage.setItem('authToken', response.token);
        localStorage.setItem('usuarioId', response.idusuario); // Guardar el ID del usuario
        localStorage.setItem('nombreCliente', `${response.nombre} ${response.apellido}`); // Guardar el nombre completo
      })
    );
  }
  

  registrar(datosUsuario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/registrar`, datosUsuario).pipe(
      tap((response: any) => {
        localStorage.setItem('authToken', response.token);
        localStorage.setItem('usuarioId', response.idusuario); // Guardar el ID del usuario
        localStorage.setItem('nombreCliente', `${response.nombre} ${response.apellido}`); // Guardar el nombre completo
      })
    );
  }  
}
