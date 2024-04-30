import { HomepageCardResponse } from "../connectionTypes";

const homepageCardsResponse: HomepageCardResponse = {
    cards: [{
        title: 'Cucumbers',
        seller: 'Az. Agr. Casale',
        image: 'https://www.finedininglovers.com/sites/g/files/xknfdk626/files/2022-06/Type%20of%20cucumber.jpg',
        description: 'Best cucumbers in the area!!',
        price: 1.50,
        weight: true
    },
    {
        title: 'Peppers',
        seller: 'Di Giacomo Antonio',
        image: 'https://www.rigeneracremona.it/site_new/wp-content/uploads/2020/07/immagini-peperoni.jpg',
        description: 'Gorgeous peppers, they sell fast',
        price: 2.30,
        weight: true
    },
    {
        title: 'Pumpkins',
        seller: 'AgriCastelli S.R.L.',
        image: 'https://cdn.cosedicasa.com/wp-content/uploads/2019/11/zucca.jpg',
        description: 'Only the best for your risotto',
        price: 3.50,
        weight: false
    }]
}

export { homepageCardsResponse };
