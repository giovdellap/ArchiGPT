import { BaseStore, Product } from "../connections/connectionTypes";

export interface CartProduct {
    product: Product;
    quantity: number;
}

export interface StoreCart {
    seller: BaseStore,
    products: CartProduct[]
}