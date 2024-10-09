export interface Ingrediente {
    idIngredientes: number;
    nombre: string;
    stock: number;
  }
  
  export interface IngredientePlatillo {
    ingrediente: Ingrediente;
    cantidadIngredientes: number;
  }
  
  export interface Platillo {
    idplatillo?: number;
    descripcion: string;
    precio: number;
    foto: string;
    ingredientesList: IngredientePlatillo[];
  }
  
  // Si tienes más modelos relacionados, puedes agregarlos aquí
  export interface Usuario {
    id: number;
    nombre: string;
    email: string;
  }
  
  export interface Pedido {
    id: number;
    usuario: Usuario;
    platillos: Platillo[];
    fecha: Date;
    total: number;
  }