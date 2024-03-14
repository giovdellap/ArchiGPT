import React from 'react'
import "./App.css";

import {WarehouseManagement, Products, ProductDetails,Account} from "./components";
import Home from "./pages/Home";
import MerchantPage from './pages/MerchantPage';
import AboutPage from './pages/AboutPage';
import ContactPage from './pages/ContactPage';
import Cart from './pages/Cart';
import { BrowserRouter, Routes, Route} from "react-router-dom";






function App() {
  //per quanto riguarda products dovrebbero starci due endpoint uno per i prodotti visibili all'utente e uno per il mercante cosi sto facendo 
  // /warehouse per merchant and /products per gli utenti



  return (

    <div className="App">
      <BrowserRouter>
      
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="/merchants" element={<MerchantPage/>} />
        <Route path="/products" element={<Products/>} />
        <Route path="/product/:id" element={<ProductDetails/>} />
        <Route path="/about" element={<AboutPage/>} />
        <Route path="/contact" element={<ContactPage/>} />
        <Route path="/warehouse/:id" element={<WarehouseManagement/>} />
        <Route path="/cart/:user_id" element={<Cart/>} />
        <Route path="/account" element={<Account/>} />
        
      </Routes>
    
    </BrowserRouter>

    </div>
  ) 
      
}

export default App;