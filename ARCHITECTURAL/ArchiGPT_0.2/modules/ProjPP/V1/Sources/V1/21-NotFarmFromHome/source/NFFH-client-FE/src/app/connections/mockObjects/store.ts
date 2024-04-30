import { BaseStore, Product, StoreProducts } from "../connectionTypes";

const store: BaseStore = {
    id: '22',
    username: 'Le verdure der pupone',
    image: 'https://prd-images2-gazzanet.gazzettaobjects.it/Vrx_zCddELufYaisByBKQyudaAM=/712x402/smart/www.mediagol.it/assets/uploads/201911/gettyimages-689445278.jpg',
    address: 'Via Zagarolese, 00132 Roma RM',
    area: 'Rome'
}

const products: Product[] = [
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    },
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    },
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    },
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    },
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    },
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    },
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    },
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    },
    {
        id: '1',
        title: 'Cucumbers',
        seller: '22',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true,
        availability: true
    }
]

const storeProducts1: StoreProducts = {
    products: products,
    page: 1,
    total: 2
}

const storeProducts2: StoreProducts = {
    products: products,
    page: 2,
    total: 2
}

export { store, storeProducts1, storeProducts2 };
