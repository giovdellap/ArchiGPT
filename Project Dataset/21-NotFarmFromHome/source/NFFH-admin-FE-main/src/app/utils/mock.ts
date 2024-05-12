import { Areas, GetProductsResponse, ImageResponse, LoginResponse, ProductResponse } from "../model/connectionModel";
import { Product } from "../model/product";

const mockUser: LoginResponse = {
  token: "",
  success: true,
  id: "1",
  username: "aaa"
}

const imageResponse: ImageResponse = {
  url: "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Colosseo_2020.jpg/800px-Colosseo_2020.jpg",
}

const product: Product = {
  id: "1",
  title: "Zucchine stupende",
  seller: "1",
  image: "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Colosseo_2020.jpg/800px-Colosseo_2020.jpg",
  description: "Ao sono stupende",
  price: 2,
  weight: true,
  availability: false
}

const productResponse: ProductResponse = {
  id: "1",
  title: "Zucchine stupende",
  success: true
}

const productsResponse: GetProductsResponse = {
  page: 1,
  total: 1,
  products: [
    {
      id: "1",
      title: "Zucchine stupende",
      seller: "1",
      image: "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Colosseo_2020.jpg/800px-Colosseo_2020.jpg",
      description: "Ao sono stupende",
      price: 2,
      weight: true,
      availability: false
    },
    {
      id: "1",
      title: "Zucchine stupende",
      seller: "1",
      image: "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Colosseo_2020.jpg/800px-Colosseo_2020.jpg",
      description: "Ao sono stupende",
      price: 2,
      weight: true,
      availability: false
    },
    {
      id: "1",
      title: "Zucchine stupende",
      seller: "1",
      image: "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Colosseo_2020.jpg/800px-Colosseo_2020.jpg",
      description: "Ao sono stupende",
      price: 2,
      weight: true,
      availability: false
    },
    {
      id: "2",
      title: "Papate fantastiche",
      seller: "1",
      image: "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Colosseo_2020.jpg/800px-Colosseo_2020.jpg",
      description: "Ao sono stupende",
      price: 2,
      weight: true,
      availability: true
    },
    {
      id: "3",
      title: "Peperoni bellissimi e dolcissimi",
      seller: "1",
      image: "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Colosseo_2020.jpg/800px-Colosseo_2020.jpg",
      description: "Ao sono stupende",
      price: 2,
      weight: true,
      availability: false
    }
  ]
}

const areasList: Areas = {
  areas: ['Rome', 'Milan', 'Turin', 'Bologna', 'Bari', 'Firenze', 'Genova', 'Pescara', 'Padova', 'Potenza']
}

export { areasList, imageResponse, mockUser, product, productResponse, productsResponse };

