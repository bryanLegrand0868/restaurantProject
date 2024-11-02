// src/app/services/profile.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = 'http://localhost:8181/usuario';

  constructor(private http: HttpClient) {}

  obtenerPerfil(correo: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/perfil/${correo}`);
  }

  actualizarPerfil(id: number, datos: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/actualizar/${id}`, datos);
  }
}
