import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})

export class AddComponent {

  productGroup = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
    price: new FormControl(0),
    weight: new FormControl('false')
  });

  image: string = "";
  completed: boolean = false;
  error: boolean = false;

  constructor(private api: ApiService, private user: UserService) {}

  addImage(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    this.api.sendImage(file).subscribe(x => {
      this.image = x.url
    });
  }

  sendProduct() {
    console.log(this.user.getEmail(), this.user.getUsername())
    if(this.productGroup.value.title != '' &&
    this.productGroup.value.description != '' &&
    this.productGroup.value.price != 0 &&
    this.image != '') {
      this.api.addProduct({
        title: this.productGroup.value.title as string,
        description: this.productGroup.value.description as string,
        seller: this.user.getUsername(),
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
}
