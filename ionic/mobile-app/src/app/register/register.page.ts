import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage {
  nombre = '';
  apellido = '';
  correoElectronico = '';
  contrasenia = '';
  registroError = '';

  constructor(private http: HttpClient, private navCtrl: NavController) {}

  registrar() {
    // Validar que todos los campos estén completos
    if (!this.nombre || !this.apellido || !this.correoElectronico || !this.contrasenia) {
      this.registroError = 'Todos los campos son obligatorios.';
      return;
    }

    const nuevoUsuario = {
      nombre: this.nombre,
      apellido: this.apellido,
      correoElectronico: this.correoElectronico,
      contrasenia: this.contrasenia,
    };

    this.http.post('http://localhost:8181/usuario/registrar', nuevoUsuario).subscribe(
      () => {
        // Redirigir a la página de inicio de sesión después del registro exitoso
        this.navCtrl.navigateRoot('/login');
      },
      (error) => {
        // Asigna el mensaje de error si lo recibe del backend
        this.registroError = error.error || 'Error en el registro. Inténtalo de nuevo.';
      }
    );
  }
}
