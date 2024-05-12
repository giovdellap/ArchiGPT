import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { switchMap, tap } from 'rxjs';
import { CartService } from 'src/app/cart.service';
import { APIService } from 'src/app/connections/api.service';
import { Product } from 'src/app/connections/connectionTypes';
import { CartProduct } from 'src/app/model/cart';
import { modifyString, revertString } from 'src/app/utils/stringmakeup';

@Component({
  selector: 'app-store-page',
  templateUrl: './store-page.component.html',
  styleUrls: ['./store-page.component.css']
})
export class StorePageComponent {

  storeID: string = '';
  name: string = '';
  location: string = '';
  image: string = '';
  mapsURL = "https://www.google.com/maps/search/?api=1&query=";

  products: CartProduct[] = [];
  page: number = 0;
  total: number = 0;

  constructor(
    private api: APIService,
    private cart: CartService,
    private route: ActivatedRoute
  ) {
    this.name = revertString(String(this.route.snapshot.paramMap.get('name')));
    this.api.getStore(this.name).pipe(
      tap(res => {
        this.name = res.username;
        this.name = modifyString(this.name)
        this.location = res.address;
        this.location = modifyString(this.location)
        this.image = res.image;
        this.mapsURL = this.mapsURL + encodeURI(this.location);
      }),
      switchMap(res => this.api.getProducts(this.name, 1))
    ).subscribe(res => {
      this.page = res.page;
      this.total = res.total;
      this.products = this.getCartProducts(res.products);
    })
  }

  changePageEvent(page: number) {
    console.log('aaaaa', page);
    this.page = page;
    this.api.getProducts(this.storeID, page).subscribe(res => {
      this.page = res.page;
      this.total = res.total;
      this.products = this.getCartProducts(res.products);
    })
  }

  getCartProducts(products: Product[]): CartProduct[] {
    var toReturn: CartProduct[] = [];
    products.forEach(x => {
      toReturn.push({
        product: x,
        quantity: this.cart.checkProduct(x)
      })
    });
    return toReturn;
  }

  newQuantity(item: CartProduct, add: boolean) {
    console.log('1', item, '2', add);
    var newQuantity = item.quantity;
    if(add) {
      newQuantity = newQuantity+1;
    } else {
      newQuantity = newQuantity-1;
    }
    console.log(newQuantity)
    this.cart.updateProduct(item.product, newQuantity);
    const index = this.products.indexOf(item);
    var newItem = {...this.products[index]};
    newItem.quantity = newQuantity;
    this.products.splice(index, 1, newItem);
    console.log(newItem)
  }

}
