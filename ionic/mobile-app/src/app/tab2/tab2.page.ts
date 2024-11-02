// src/app/tab2/tab2.page.ts
import { Component, OnInit } from '@angular/core';
import { NewsService } from '../Services/news.service';

@Component({
  selector: 'app-tab2',
  templateUrl: './tab2.page.html',
  styleUrls: ['./tab2.page.scss'],
})
export class Tab2Page implements OnInit {
  noticias: any[] = [];

  constructor(private newsService: NewsService) {}

  ngOnInit() {
    this.newsService.obtenerNoticias().subscribe(
      (data) => this.noticias = data,
      (error) => console.error(error)
    );
  }

  onCancel(noticia: any) {
    // Lógica para la acción de cancelar
    console.log('Acción cancelar para la noticia:', noticia);
  }

  onSave(noticia: any) {
    // Lógica para la acción de guardar
    console.log('Acción guardar para la noticia:', noticia);
  }
}
