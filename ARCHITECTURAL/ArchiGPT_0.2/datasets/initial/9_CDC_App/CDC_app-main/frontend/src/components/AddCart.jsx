import * as React from 'react';
import { useState } from "react";

const AddCart = ({product_id}) => {


  const [quantity] = useState(1);
  

  //function that insert product into cart
  const handleAddCart = (e) => {
    e.preventDefault();
    var userInfo = localStorage.getItem('auth_token');

    if (userInfo) {
      userInfo = userInfo.slice(1, -1).replaceAll("'", "").split(", ");
      
    } else {
      alert("Login First!");
      return;
    }


    fetch("http://localhost:8001/cart", {
      method: "POST",


      body: JSON.stringify({
        user_id: userInfo[0],
        product_id,
        quantity: quantity
      })
    })
      .then((response) => {
        if (response.status === 200) {
          alert("Item added to cart")

        }
      })
      .catch(error => console.log(error))
  };
  return (
    <button type="button" class="btn btn-dark m-1" onClick={handleAddCart}>Add to Cart</button>
  );

};

export default AddCart;
