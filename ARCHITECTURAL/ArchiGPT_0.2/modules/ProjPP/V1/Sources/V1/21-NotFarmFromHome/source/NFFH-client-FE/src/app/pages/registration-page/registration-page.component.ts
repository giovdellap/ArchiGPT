import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { APIService } from 'src/app/connections/api.service';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent {

  registerForm = new FormGroup({
    name: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl('')
  }) 
  displayError = false;

  constructor(
    private apiService: APIService,
    private router: Router
  ) {}

  onSubmit(): void {
    if (this.registerForm.value.email !== '' && 
        this.registerForm.value.password !== '' &&
        this.registerForm.value.name !== '') {
      this.apiService.register(
        <string>this.registerForm.value.email, 
        <string>this.registerForm.value.password, 
        <string> this.registerForm.value.name)
        .subscribe(
        res => {
          console.log(res)
          this.displayError = !res.success;     
          if (res) {
            this.router.navigate([""]);
          }     
        }
      )
    }
  }  
}
