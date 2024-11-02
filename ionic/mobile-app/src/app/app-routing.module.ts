import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';  

// app-routing.module.ts
const routes: Routes = [
  { path: 'login', loadChildren: () => import('./Login/Login.module').then(m => m.LoginPageModule) },
  { path: 'register', loadChildren: () => import('./register/register.module').then(m => m.RegisterPageModule) },
  { path: 'tabs', loadChildren: () => import('./tabs/tabs.module').then(m => m.TabsPageModule), canActivate: [AuthGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'profile',loadChildren: () => import('./profile/profile.module').then( m => m.ProfilePageModule)},
  { path: 'profile', loadChildren: () => import('./profile/profile.module').then(m => m.ProfilePageModule) }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
