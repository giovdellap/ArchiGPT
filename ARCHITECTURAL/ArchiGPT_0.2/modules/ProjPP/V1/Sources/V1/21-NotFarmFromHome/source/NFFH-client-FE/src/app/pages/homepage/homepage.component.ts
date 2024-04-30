import { Component } from '@angular/core';
import { APIService } from '../../connections/api.service';
import { HomepageCard } from '../../model/homepage';


@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent {

  carouselImages: string[] = []
  cardsAvailable = false;
  cards : HomepageCard[] = [];

  constructor(private api: APIService) {
    this.carouselImages = [1, 2, 3].map((n) => `assets/media/hp-carousel-${n}.jpg`);
    this.api.getHomepageCards().subscribe(x => {
      this.cards = x.products;
      this.cardsAvailable = true;
    })
  }



}
