import { MyOrders } from "../connectionTypes";

const myOrders: MyOrders = {
    orders: [
        {
            products: [
                {
                    product: {
                        id: '1', 
                        title: 'Peperoni',
                        seller: '1',
                        image: '',
                        description: '',
                        price: 3,
                        weight: true,
                        availability: true
                    },
                    quantity: 3
                },
                {
                    product: {
                        id: '3', 
                        title: 'Cicoria (non il rapper)',
                        seller: '1',
                        image: '',
                        description: '',
                        price: 2,
                        weight: true,
                        availability: true
                    },
                    quantity: 3
                }
            ],
            commission: 2,
            pickup: new Date(),
            total: 25,
            accepted: true,
            withdrawn: false
        },
        {
            products: [
                {
                    product: {
                        id: '4', 
                        title: 'Lenticchie',
                        seller: '2',
                        image: '',
                        description: '',
                        price: 3,
                        weight: true,
                        availability: true
                    },
                    quantity: 3
                },
                {
                    product: {
                        id: '3', 
                        title: 'Pomodori',
                        seller: '1',
                        image: '',
                        description: '',
                        price: 2,
                        weight: true,
                        availability: true
                    },
                    quantity: 3
                }
            ],
            commission: 2,
            pickup: new Date(),
            total: 25,
            accepted: true,
            withdrawn: false
        }
    ]
}

export { myOrders };
