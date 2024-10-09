import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { response } from 'express';

@Component({
  selector: 'app-manejo-inventario',
  standalone: true,
  imports: [CommonModule,FormsModule,HttpClientModule],
  templateUrl: './manejo-inventario.component.html',
  styleUrl: './manejo-inventario.component.css'
})
export class ManejoInventarioComponent {

  constructor(private http:HttpClient){ }

  ingrediente ={
    idingredientes: null,
    nombre: '',
    stock: 0
  };

  ingredients: any[] = [];
  editingIndex: number | null = null;
  updatedStock: number = 0;

  ngOnInit(){
    this.getIngredientes();
  }

  addIngrediente() {
    this.http.post('http://localhost:8181/ingredientes/guardar'
    , this.ingrediente).subscribe(response =>{
      this.getIngredientes(); //guardo el ingrediente con el id autoincrementado.

      this.ingrediente.nombre = '';
      this.ingrediente.stock = 0;
    });
  }

  getIngredientes(){
    this.http.get('http://localhost:8181/ingredientes/buscar').subscribe((data: any) =>{
      this.ingredients = data;
      console.log(data)
    })
  }

  enableEditing(index: number, stock: number){
    this.editingIndex = index;
    this.updatedStock = stock;
  }

  updateStock(ingredient: any) {
    const cantidad = this.updatedStock;
  
    // Verificación adicional
    if (this.updatedStock < 0) {
      console.error('Error: cantidad inválida', { cantidad });
      return;
    }
  
    console.log('Actualizando stock:', { ingredient, cantidad });
  
    this.http.put(`http://localhost:8181/ingredientes/actualizarStock/${ingredient.idIngredientes}/${cantidad}`, null)
      .subscribe(() => {
        this.getIngredientes();
        this.editingIndex = null;
      }, error => {
        console.error('Error al actualizar el stock:', error);
      });
  }  

}
