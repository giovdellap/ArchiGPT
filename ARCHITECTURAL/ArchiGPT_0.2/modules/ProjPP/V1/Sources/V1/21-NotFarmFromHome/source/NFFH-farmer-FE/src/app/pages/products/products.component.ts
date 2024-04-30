import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent {

  mode: string = "";
  modeControl = new FormControl("");

  constructor() {
    this.modeControl.valueChanges.subscribe(x => this.mode = x || "");
  }
}
