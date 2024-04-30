import { Component } from '@angular/core';
import { CartService } from 'src/app/cart.service';
import { APIService } from 'src/app/connections/api.service';
import { CartProduct, StoreCart } from 'src/app/model/cart';
import { modifyString } from 'src/app/utils/stringmakeup';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.css']
})
export class CartPageComponent {

  cartList: StoreCart[] = [];

  constructor(
    private api: APIService,
    private cartService: CartService
  ) {
    this.cartAdapter();
  }

  cartAdapter() {
    this.cartList = [];
    var cart = this.cartService.getCart()
    cart.forEach(item => {
      var listItem = this.cartList.find(elem => elem.seller.id = item.product.seller);
      if(listItem) {
        this.cartList[this.cartList.indexOf(listItem)].products.push(item);
      } else {
        this.api.getStore(item.product.seller).subscribe(store => {
          var temp = store
          temp.username = modifyString(store.username)
          this.cartList.push({
            seller: temp,
            products: [item]
          })
        })
      }
    })
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
    this.cartService.updateProduct(item.product, newQuantity);
    this.cartAdapter();
  }

  removeItem(item: CartProduct) {
    this.cartService.updateProduct(item.product, 0);
    this.cartAdapter();
  }


}
