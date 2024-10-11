import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit} from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-news',
  standalone: true,
  imports: [CommonModule, FormsModule,HttpClientModule],
  templateUrl: './news.component.html',
  styleUrl: './news.component.css'
})
export class NewsComponent implements OnInit {
  news: any = { idnews: null, titulo: '', descripcion: '', imagen: null, fechaPublicacion: '' };
  newsList: any = [];

  constructor(private http: HttpClient) {}
    
  ngOnInit() {
    this.loadNews();
  }

  loadNews() {
    this.http.get<any[]>('http://localhost:8181/news/buscar').subscribe(
      data => {
        this.newsList = data;
        console.log('Noticias cargadas:', this.newsList);
      },
      error => console.error('Error cargando noticias:', error)
    );
  }

  saveNews() {
    const formData = new FormData();
    formData.append('titulo', this.news.titulo);
    formData.append('descripcion', this.news.descripcion);
    formData.append('fechaPublicacion', this.formatDate(this.news.fechaPublicacion));
  
    if (this.news.imagen instanceof File) {
      formData.append('imagen', this.news.imagen, this.news.imagen.name);
    }
  
    if (this.news.idnews) {
      // Actualizar noticia existente
      this.http.put(`http://localhost:8181/news/actualizar/${this.news.idnews}`, formData)
        .subscribe(
          () => {
            console.log('Noticia actualizada');
            this.loadNews();
            this.resetForm();
          },
          error => console.error('Error actualizando noticia:', error)
        );
    } else {
      // Crear nueva noticia
      this.http.post('http://localhost:8181/news/guardar', formData)
        .subscribe(
          () => {
            console.log('Noticia guardada');
            this.loadNews();
            this.resetForm();
          },
          error => console.error('Error guardando noticia:', error)
        );
    }
  }
  
  private formatDate(date: string | Date): string {
    if (date instanceof Date) {
      return date.toISOString().split('T')[0];
    }
    return date;
  }
  
  editNews(noticia: any) {
    this.news = { ...noticia, imagen: null };
    // Si la fecha viene como string, conviértela a objeto Date
    if (typeof this.news.fechaPublicacion === 'string') {
      this.news.fechaPublicacion = new Date(this.news.fechaPublicacion);
    }
  }

  deleteNews(idnews: number) {
    this.http.delete(`http://localhost:8181/news/eliminar/${idnews}`)
      .subscribe({
        next: () => {
          console.log('Noticia eliminada');
          this.loadNews();
        },
        error: (err) => console.error('Error eliminando noticia:', err)
      });
  }

  resetForm() {
    this.news = {
      idnews: null,
      titulo: '', 
      descripcion: '',
      imagen: null,
      fechaPublicacion: ''
    };
  }

  onFileSelected(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.news.imagen = file;  // Guarda el archivo directamente, no lo conviertas a base64
      console.log('Imagen seleccionada:', file.name);
    }
  } 
}
