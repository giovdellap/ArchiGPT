import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-loginpage',
  templateUrl: './loginpage.component.html',
  styleUrls: ['./loginpage.component.css']
})
export class LoginpageComponent {

  loginForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl('')
  }) 
  displayError = false;
  
  constructor(
    private apiService: ApiService,
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
          if (res) {
            this.router.navigate([""]);
          }     
        }
      )
    }
  }

}
