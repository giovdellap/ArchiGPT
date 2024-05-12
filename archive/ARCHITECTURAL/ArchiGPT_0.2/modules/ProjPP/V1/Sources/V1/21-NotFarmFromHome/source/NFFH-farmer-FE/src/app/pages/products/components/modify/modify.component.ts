import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Product } from 'src/app/model/product';
import { ApiService } from 'src/app/services/api.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-modify',
  templateUrl: './modify.component.html',
  styleUrls: ['./modify.component.css']
})
export class ModifyComponent {

  products: Product[] = [];
  item: Product = {} as Product;
  selected: boolean = false;

  productGroup = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    price: new FormControl(0),
    weight: new FormControl('false')
  });

  image: string = "";
  completed: boolean = false;
  error: boolean = false;


  constructor(
    private api: ApiService,
    private user: UserService
  ) {
    this.api.getAllProducts().subscribe(res => this.products = res);
  }

  modifyProduct(item: Product) {
    this.item = item;
    this.selected = true;
    this.productGroup.setValue({
      title: item.title,
      description: item.description,
      price: item.price,
      weight: String(item.weight)
    });
  }

  addImage(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    this.api.sendImage(file).subscribe(x => {
      this.image = x.url
    });
  }

  sendProduct() {
    if(this.productGroup.value.title != '' &&
    this.productGroup.value.description != '' &&
    this.productGroup.value.price != 0 &&
    this.image != '') {
      this.api.modifyProduct(
      {
        id: this.item.id,
        title: this.productGroup.value.title as string,
        description: this.productGroup.value.description as string,
        seller: this.user.getId(),
        image: this.image,
        price: this.productGroup.value.price as number,
        weight: Boolean(this.productGroup.value.weight),
        availability: false
      }).subscribe(res => {
        this.completed = res.success;
        this.error = !res.success;
      })
    }
  }

  deleteProduct(item: Product) {
    this.api.deleteProduct(item.id).subscribe(x => {
      if(x.success) this.products.splice(this.products.indexOf(item), 1);
    })
  }

}
