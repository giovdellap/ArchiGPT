import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
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
          this.displayError = !res.success;
          if (res.success) {
            this.router.navigate(["home"]);
          }
        }
      )
    }
  }
}
