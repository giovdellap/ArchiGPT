import { Component } from '@angular/core';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'farmer-fe';
  logged = false;

  constructor(private user: UserService) {
    this.user.getObservable().subscribe(x => {
      if (x) {
        this.logged = true;
      }
    })
  }
}

