import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';
import { revertString } from 'src/app/utils/stringmakeup';

@Component({
  selector: 'app-registrationpage',
  templateUrl: './registrationpage.component.html',
  styleUrls: ['./registrationpage.component.css']
})
export class RegistrationpageComponent {

  registerForm = new FormGroup({
    name: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
    address: new FormControl(''),
    area: new FormControl('')
  })
  areas: string[] = [];
  image: string = "";

  displayError = false;


  constructor(
    private apiService: ApiService,
    private router: Router
  ) {
    this.apiService.getLocationsList().subscribe( x => this.areas = x.areas);
  }

  addImage(imageInput: any) {
    const file: File = imageInput.files[0];

    this.apiService.sendImage(file).subscribe(x => {
      console.log('im', x)
      this.image = x.url
    });
  }

  onSubmit(): void {
    console.log('email', this.registerForm.value.email)
    console.log('pw', this.registerForm.value.password)
    console.log('name', this.registerForm.value.name)
    console.log('area', this.registerForm.value.area)
    console.log('address', this.registerForm.value.address)
    console.log('image', this.image)

    if (this.registerForm.value.email !== '' &&
        this.registerForm.value.password !== '' &&
        this.registerForm.value.name !== '' &&
        this.registerForm.value.area !== '' &&
        this.registerForm.value.address !== '' &&
        this.image !== '') {
          var temp = revertString(this.registerForm.value.name || "")
      this.apiService.register(
        {
          email: <string>this.registerForm.value.email,
          password: <string>this.registerForm.value.password,
          username: <string>temp,
          area: <string>this.registerForm.value.area,
          address: <string>this.registerForm.value.address,
          image: this.image
        }
      ).subscribe(
        res => {
          console.log(res)
          this.displayError = !res.success;
          if (res.success) {
            this.router.navigate([""]);
          }
        }
      )
    }
  }
}
