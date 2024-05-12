import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Product } from './connections/connectionTypes';
import { CartProduct } from './model/cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  cart: CartProduct[] = [];

  constructor(
    private cookie: CookieService
    ) {
    if (this.cookie.check("NFFH_cart")) {
      this.cart = JSON.parse(this.cookie.get("NFFH_cart").toString())
    }
  }

  updateProduct(product: Product, quantity: number) {
    var productInCart = false;
    var actualIndex = -1;
    this.cart.forEach(x => {
      if(x.product == product) {
        productInCart = true;
        actualIndex = this.cart.indexOf(x);
      }
    });
    if(productInCart) {
      if(quantity === 0) {
        console.log('in right if')
        this.cart.splice(actualIndex, 1);
        console.log('cart 2', this.cart.toString())
      } else {
        var updatedItem = {...this.cart[actualIndex]};
        updatedItem.quantity = quantity;
        this.cart.splice(actualIndex, 1, updatedItem);
      }
    } else {
      this.cart.push({
        product: product,
        quantity: quantity
      });
    }
    this.cookie.set('NFFH_cart', JSON.stringify(this.cart))
  }

  checkProduct(product: Product) {
    var quantity = 0;
    this.cart.forEach(x => {
      if(x.product.id == product.id && x.product.seller == product.seller) quantity = x.quantity;
    });
    return quantity;
  }

  getCart() {
    return this.cart;
  }

  
}
