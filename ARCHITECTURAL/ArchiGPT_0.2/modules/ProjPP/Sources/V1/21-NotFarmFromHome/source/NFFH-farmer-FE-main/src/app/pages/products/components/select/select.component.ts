import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { Component } from '@angular/core';
import { tap } from 'rxjs';
import { Product } from 'src/app/model/product';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.css']
})
export class SelectComponent {

  products: Product[] = [];
  available: Product[] = [];
  not_available: Product[] = [];
  modified: string[] = []

  completed: boolean = false;
  error: boolean = false;

  constructor(private api: ApiService) {
    this.api.getAllProducts().pipe(
      tap(x => {
        x.forEach(item => {
          if(item.availability) {
            this.available.push(item);
          } else {
            this.not_available.push(item);
          }
        })
      })
    ).subscribe()

  }

  drop(event: CdkDragDrop<Product[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      console.log(event.item)
      console.log(event.container.data)
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
        );
      this.modified.push(event.container.data[0].id)
    }
  }

  save() {
    var toSend: Product[] = [];
    this.available.forEach(x => x.availability = true)
    this.not_available.forEach(x => x.availability = false)
    this.available.forEach(x => {
      var found = this.modified.find(item => item === x.id);
      if(found != undefined) toSend.push(x);
    });
    this.not_available.forEach(x => {
      var found = this.modified.find(item => item === x.id);
      if(found != undefined) toSend.push(x);
    });
    toSend.forEach(x => this.api.modifyProduct(x).subscribe(res => {if(!res.success) this.error = true}));
    this.completed = true;

  }

}
