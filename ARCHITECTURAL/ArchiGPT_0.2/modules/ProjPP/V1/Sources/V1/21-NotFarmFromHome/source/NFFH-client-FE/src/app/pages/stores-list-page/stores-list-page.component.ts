import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { switchMap, tap } from 'rxjs';
import { BaseStore } from 'src/app/connections/connectionTypes';
import { modifyString } from 'src/app/utils/stringmakeup';
import { APIService } from '../../connections/api.service';

@Component({
  selector: 'app-stores-list-page',
  templateUrl: './stores-list-page.component.html',
  styleUrls: ['./stores-list-page.component.css']
})
export class StoresListPageComponent {

  currentPage: number = 0;
  total: number = 0;
  stores: BaseStore[] = [];

  area: string = '';
  areas: string[] = [];
  areaSelector = new FormControl();

  showError = false;

  constructor(private api: APIService) {
    this.api.getLocationsList().subscribe(res => {
      this.areas = res.areas;
    })

    this.areaSelector.valueChanges.pipe(
      tap(x => this.area = x),
      switchMap(x => this.api.getStoresList(1, x))
    ).subscribe(res => {
      this.currentPage = res.page;
      this.total = res.total;
      this.stores = res.stores;
      this.stores.forEach(item => item.username = modifyString(item.username))
      this.showError = res.total === 0;
    });

  }

  changePageEvent(page: number) {
    console.log('aaaaa', page);
    this.currentPage = page;
    this.api.getStoresList(page, this.area).subscribe(res => {
      this.currentPage = res.page;
      this.total = res.total;
      this.stores = res.stores;
      this.stores.forEach(item => item.username = modifyString(item.username))

    })
  } 
  
}
