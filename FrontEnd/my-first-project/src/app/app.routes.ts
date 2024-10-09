import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { BienvenidaComponent } from './bienvenida/bienvenida.component';
import { ManejoInventarioComponent } from './manejo-inventario/manejo-inventario.component';
import { NewsComponent } from './news/news.component';
import { ExamenComponent } from './examen/examen.component';
import { ListaUsuariosComponent } from './lista-usuarios/lista-usuarios.component';
import { ManejoPlatillosComponent } from './manejo-platillos/manejo-platillos.component';
import { ManejoOrdenComponent } from './manejo-orden/manejo-orden.component';

export const routes: Routes = [
    {path:'',component:LoginComponent},
    {path:'welcome',component:BienvenidaComponent},
    {path:'inventory',component:ManejoInventarioComponent},
    {path:'news',component:NewsComponent},
    {path: 'examen',component:ExamenComponent},
    {path: 'users',component:ListaUsuariosComponent},
    {path: 'dish',component:ManejoPlatillosComponent},
    {path: 'order',component:ManejoOrdenComponent}
];
