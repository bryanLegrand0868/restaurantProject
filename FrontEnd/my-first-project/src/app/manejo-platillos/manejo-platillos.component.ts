import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Platillo, Ingrediente, IngredientePlatillo } from './manejo-platillos.model';
import { RouterModule, Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-manejo-platillos',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule, MatSlideToggleModule, ButtonModule],
  templateUrl: './manejo-platillos.component.html',
  styleUrl: './manejo-platillos.component.css'
})
export class ManejoPlatillosComponent {
  platillos: Platillo[] = [];
  ingredientes: Ingrediente[] = [];
  ingredienteSeleccionado: Ingrediente | null = null;
  cantidadSeleccionada: number = 1
  nuevoPlatillo: Platillo = {
    descripcion: '',
    precio: 0,
    foto: '',
    ingredientesList: []
  };
  imagePreview: string | null = null;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.getPlatillos();
    this.getIngredientes();
  }

  getPlatillos() {
    this.http.get<Platillo[]>('http://localhost:8181/platillo/buscar').subscribe(
      (data: Platillo[]) => {
        this.platillos = data;
        console.log('Platillos cargados:', this.platillos);
      },
      error => {
        console.error('Error al cargar platillos:', error);
      }
    );
  }

  getIngredientes() {
    this.http.get<Ingrediente[]>('http://localhost:8181/ingredientes/buscar').subscribe(
      (data: Ingrediente[]) => {
        this.ingredientes = data;
      },
      error => {
        console.error('Error al cargar ingredientes:', error);
      }
    );
  }

  createPlatillo() {
    const newPlatillo: Platillo = {
      descripcion: this.nuevoPlatillo.descripcion,
      precio: this.nuevoPlatillo.precio,
      foto: this.nuevoPlatillo.foto,
      ingredientesList: this.nuevoPlatillo.ingredientesList.map((ingredientePlatillo: IngredientePlatillo) => ({
        ingrediente: {
          idIngredientes: ingredientePlatillo.ingrediente.idIngredientes,
          nombre: ingredientePlatillo.ingrediente.nombre,
          stock: ingredientePlatillo.ingrediente.stock
        },
        cantidadIngredientes: ingredientePlatillo.cantidadIngredientes
      }))
    };
  
    // Verifica si es una creación o una actualización
    if (this.nuevoPlatillo.idplatillo) {
      // Si el platillo tiene un ID, hacemos una actualización
      this.http.put<Platillo>(`http://localhost:8181/platillo/actualizar/${this.nuevoPlatillo.idplatillo}`, newPlatillo).subscribe(
        (response: Platillo) => {
          console.log('Platillo updated successfully', response);
          this.getPlatillos();  // Refrescamos la lista de platillos
          this.resetForm();
        },
        (error: any) => {
          console.error('Error updating platillo', error);
        }
      );
    } else {
      // Si no tiene ID, creamos uno nuevo
      this.http.post<Platillo>('http://localhost:8181/platillo/guardar', newPlatillo).subscribe(
        (response: Platillo) => {
          console.log('Platillo created successfully', response);
          this.getPlatillos();
          this.resetForm();
        },
        (error: any) => {
          console.error('Error creating platillo', error);
        }
      );
    }
  }  

  addIngrediente(ingrediente: Ingrediente | null, cantidad: number) {
    if (ingrediente && !this.nuevoPlatillo.ingredientesList.some(i => i.ingrediente.idIngredientes === ingrediente.idIngredientes)) {
      const ingredientePlatillo: IngredientePlatillo = {
        ingrediente: ingrediente,
        cantidadIngredientes: cantidad
      };
      this.nuevoPlatillo.ingredientesList.push(ingredientePlatillo);
    }
  }

  removeIngrediente(index: number) {
    this.nuevoPlatillo.ingredientesList.splice(index, 1);
  }


  deletePlatillo(idplatillo: number) {
    this.http.delete(`http://localhost:8181/platillo/eliminar/${idplatillo}`).subscribe(
      () => {
        console.log('Platillo eliminado correctamente');
        this.getPlatillos();  // Recarga la lista de platillos después de eliminar
      },
      (error) => {
        console.error('Error al eliminar el platillo:', error);
      }
    );
  }  
  confirmDelete(platillo: Platillo) {
    const confirmacion = confirm(`¿Estás seguro que deseas eliminar el platillo "${platillo.descripcion}"?`);
    
    if (confirmacion) {
      this.deletePlatillo(platillo.idplatillo!);  // Asegúrate de que el platillo tenga un ID antes de intentar eliminarlo
    }
  }
  

  resetForm() {
    this.nuevoPlatillo = {
      descripcion: '',
      precio: 0,
      foto: '',
      ingredientesList: []
    };
    this.imagePreview = null;
  }

  onImageSelected(event: any) {
    const file: File = event.target.files[0];
    const reader = new FileReader();

    reader.onload = () => {
      this.nuevoPlatillo.foto = reader.result as string;
      this.imagePreview = reader.result as string;
    };

    reader.readAsDataURL(file);
  }

  enableEditing(platillo: Platillo) {
    this.nuevoPlatillo = {
      idplatillo: platillo.idplatillo,  // Incluimos el ID para poder editarlo
      descripcion: platillo.descripcion,
      precio: platillo.precio,
      foto: platillo.foto,
      ingredientesList: platillo.ingredientesList.map(ingredientePlatillo => ({
        ingrediente: { 
          idIngredientes: ingredientePlatillo.ingrediente.idIngredientes,
          nombre: ingredientePlatillo.ingrediente.nombre,
          stock: ingredientePlatillo.ingrediente.stock
        },
        cantidadIngredientes: ingredientePlatillo.cantidadIngredientes
      }))
    };
  
    this.imagePreview = platillo.foto;  // Mostrar la imagen actual en la previsualización
  }

  logout() {
    localStorage.removeItem('userSession');
    this.router.navigate(['/login']);  
  }
  
}



