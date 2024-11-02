// src/app/tab1/tab1.module.ts
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular'; // Importa IonicModule

import { Tab1PageRoutingModule } from './tab1-routing.module';
import { Tab1Page } from './tab1.page';

// Importa los módulos de PrimeNG
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,           // Asegúrate de importar IonicModule aquí
    Tab1PageRoutingModule,
    CardModule,            // Importa PrimeNG CardModule aquí
    ButtonModule           // Importa PrimeNG ButtonModule aquí
  ],
  declarations: [Tab1Page],
  schemas: [CUSTOM_ELEMENTS_SCHEMA] // Agrega CUSTOM_ELEMENTS_SCHEMA
})
export class Tab1PageModule {}
