import { Component, OnInit } from '@angular/core';
import { ProfileService } from '../Services/profile.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  perfil: any = {
    nombre: '',
    apellido: '',
    nit: null,
    correoElectronico: '',
    contrasenia: ''
  };

  constructor(private profileService: ProfileService, private navCtrl: NavController) {}

  ngOnInit() {
    const correo = localStorage.getItem('correoElectronico');
    if (correo) {
      this.profileService.obtenerPerfil(correo).subscribe(
        (data) => this.perfil = data,
        (error) => console.error(error)
      );
    }
  }

  actualizarPerfil() {
    const idUsuario = this.perfil.idusuario; // asegúrate de que el ID esté disponible en el objeto perfil
    const datosActualizados = {
      nombre: this.perfil.nombre,
      apellido: this.perfil.apellido,
      nit: this.perfil.nit,
      contrasenia: this.perfil.contrasenia
    };

    this.profileService.actualizarPerfil(idUsuario, datosActualizados).subscribe(
      () => {
        alert('Perfil actualizado con éxito');
        this.navCtrl.navigateBack('/tabs');
      },
      (error) => {
        console.error('Error actualizando perfil', error);
        alert('Hubo un problema al actualizar el perfil');
      }
    );
  }
}
