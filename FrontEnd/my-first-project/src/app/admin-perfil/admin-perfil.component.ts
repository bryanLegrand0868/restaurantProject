import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-admin-perfil',
  standalone: true,
  imports: [FormsModule, HttpClientModule, CommonModule, RouterModule],
  templateUrl: './admin-perfil.component.html',
  styleUrls: ['./admin-perfil.component.css']  
})
export class AdminPerfilComponent implements OnInit {
  private apiUrl = 'http://localhost:8181'; // Ajusta esto a la URL de tu backend
  admin: any = null; 
  newPassword: string = ''; 
  isLoading: boolean = true;
  error: string | null = null;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.getAdminInfo(); 
  }

  getAdminInfo() {
    this.isLoading = true;
    this.error = null;
    this.http.get(`${this.apiUrl}/admin/buscar/1`)
      .pipe(
        catchError(this.handleError)
      )
      .subscribe(
        (data) => {
          this.admin = data;
          this.isLoading = false;
        },
        (error) => {
          console.error('Error fetching admin info:', error);
          this.error = 'Error al cargar la información del administrador';
          this.isLoading = false;
        }
      );
  }

  updatePassword() {
    if (!this.admin) {
      this.error = 'No se ha cargado la información del administrador';
      return;
    }

    if (!this.newPassword) {
      this.error = 'Por favor, introduce una nueva contraseña';
      return;
    }

    this.admin.contrasenia = this.newPassword; 
    this.http.post(`${this.apiUrl}/admin/guardar`, this.admin)
      .pipe(
        catchError(this.handleError)
      )
      .subscribe(
        response => {
          alert('Contraseña actualizada con éxito');
          this.newPassword = '';
          this.error = null;
        }, 
        error => {
          console.error('Error updating password:', error);
          this.error = 'Error al actualizar la contraseña';
        }
      );
  }

  logout() {
    localStorage.removeItem('userSession');
    this.router.navigate(['/login']);  
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // Error del lado del cliente o de red
      console.error('An error occurred:', error.error.message);
    } else {
      // El backend retornó un código de respuesta sin éxito.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Retorna un observable con un mensaje de error orientado al usuario
    return throwError('Algo salió mal; por favor, inténtalo de nuevo más tarde.');
  }
}