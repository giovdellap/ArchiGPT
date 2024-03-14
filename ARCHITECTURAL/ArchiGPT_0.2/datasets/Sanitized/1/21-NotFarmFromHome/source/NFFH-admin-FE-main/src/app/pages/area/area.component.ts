import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { switchMap, tap } from 'rxjs';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-area',
  templateUrl: './area.component.html',
  styleUrls: ['./area.component.css']
})
export class AreaComponent {
  areas: string[] = []
  newAreaForm = new FormGroup({
    area: new FormControl(''),
  })
  displayError = false;

  constructor(private api: ApiService) {
    this.api.getAreas().subscribe(x => this.areas = x)
  }
  sendNewArea() {
    if(this.newAreaForm.value.area) {
      this.api.newArea(this.newAreaForm.value.area).pipe(
        switchMap(() => this.api.getAreas()),
        tap(x => this.areas = x)
      ).subscribe()

    }
  }
}
