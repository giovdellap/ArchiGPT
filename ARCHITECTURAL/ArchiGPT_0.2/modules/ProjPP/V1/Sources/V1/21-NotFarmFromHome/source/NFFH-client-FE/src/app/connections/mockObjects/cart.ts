import { Cart } from "../connectionTypes";

const cart: Cart = {
    products: [{
        product: {
            id: '1',
            title: 'Cucumbers',
            seller: '22',
            image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
            description: 'Best cucumbers in the area!!',
            price: 1.50,
            weight: true,
            availability: true
        },
        quantity: 3
    },
    {
        product: {
            id: '3',
            title: 'Pumpkins',
            seller: '22',
            image: 'https://cdn.cosedicasa.com/wp-content/uploads/2019/11/zucca.jpg',
            description: 'Only the best for your risotto',
            price: 3.50,
            weight: false,
            availability: true
        },
        quantity: 2
    }]
}

export { cart };
