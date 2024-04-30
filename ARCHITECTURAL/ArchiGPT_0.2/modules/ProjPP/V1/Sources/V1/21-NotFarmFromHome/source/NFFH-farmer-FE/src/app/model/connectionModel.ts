/**
 * LOGIN
 */

import { Product } from "./product"

export interface LoginRequest {
    email: string,
    password: string
}

export interface SignUpRequest {
  email: string,
  password: string,
  address: string,
  image: string,
  username: string,
  area: string
}

export interface LoginResponse {
    token: string,
    success: boolean,
    id: string,
    username: string,
}

export interface SignupResponse {
  token: string,
  success: boolean,
  error: string,
  id: string,
  username: string
}

export interface ImageResponse {
  url: string,
}

export interface ProductRequest {
  title: string,
  seller: string,
  image: string,
  description: string,
  price: number,
  weight: boolean,
  availability: boolean
}

export interface ProductResponse {
  id: string,
  title: string,
  success: boolean
}

export interface GetProductsResponse {
  page: number,
  total: number,
  products: Product[]
}

export interface Areas {
  areas: string[]
}

export interface AreasResponse {
  id: number, areaName: string
}

export interface ImageRequest {
  base64_image: string,
}
