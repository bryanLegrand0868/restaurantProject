import { Component } from '@angular/core';
import { AuthService } from '../Services/auth.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage {
  correoElectronico = '';
  contrasenia = '';
  loginError = '';

  constructor(private authService: AuthService, private navCtrl: NavController) {}

  login() {
    this.authService.login(this.correoElectronico, this.contrasenia).subscribe(
      (response: any) => {
        localStorage.setItem('authToken', response.token);
        this.navCtrl.navigateRoot('/tabs');
      },
      (error) => {
        this.loginError = 'Correo electrónico o contraseña incorrectos';
      }
    );
  }

  irARegistro() {
    this.navCtrl.navigateForward('/register');
  }
  

}
