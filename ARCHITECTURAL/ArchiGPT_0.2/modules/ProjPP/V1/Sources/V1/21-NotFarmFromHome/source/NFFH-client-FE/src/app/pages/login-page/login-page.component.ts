import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { APIService } from 'src/app/connections/api.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {
  
  loginForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl('')
  }) 
  displayError = false;
  
  constructor(
    private apiService: APIService,
    private router: Router
    ) {}

  onSubmit(): void {
    if (this.loginForm.value.email !== '' && 
        this.loginForm.value.password !== '') {
      this.apiService.login(
        <string>this.loginForm.value.email, <string>this.loginForm.value.password)
        .subscribe(
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
