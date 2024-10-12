import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { BienvenidaComponent } from './bienvenida/bienvenida.component';
import { ManejoInventarioComponent } from './manejo-inventario/manejo-inventario.component';
import { NewsComponent } from './news/news.component';
import { ExamenComponent } from './examen/examen.component';
import { ListaUsuariosComponent } from './lista-usuarios/lista-usuarios.component';
import { ManejoPlatillosComponent } from './manejo-platillos/manejo-platillos.component';
import { ManejoOrdenComponent } from './manejo-orden/manejo-orden.component';
import { AdminPerfilComponent } from './admin-perfil/admin-perfil.component';
import { AuthGuard } from './auth.guard';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'welcome', component: BienvenidaComponent, canActivate: [AuthGuard] },
    { path: 'inventory', component: ManejoInventarioComponent, canActivate: [AuthGuard] },
    { path: 'news', component: NewsComponent, canActivate: [AuthGuard] },
    { path: 'examen', component: ExamenComponent, canActivate: [AuthGuard] },
    { path: 'users', component: ListaUsuariosComponent, canActivate: [AuthGuard] },
    { path: 'dish', component: ManejoPlatillosComponent, canActivate: [AuthGuard] },
    { path: 'order', component: ManejoOrdenComponent, canActivate: [AuthGuard] },
    { path: 'miPerfil', component: AdminPerfilComponent, canActivate: [AuthGuard] },
    { path: '', redirectTo: '/welcome', pathMatch: 'full' },
    { path: '**', redirectTo: '/welcome' }
];
