import React from "react";
import { useState } from "react";






const AddProduct = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("");
  const [price, setPrice] = useState("");
  const [stock, setStock] = useState("");
  const [image_url, setImageUrl] = useState("");




  const handleSubmit = (e) => {
    e.preventDefault();
    var userInfo = localStorage.getItem('auth_token');

    if (userInfo) {
      userInfo = userInfo.slice(1, -1).replaceAll("'", "").split(", ");

    } else {
      alert("Login First!");
      return;
    }


    fetch("http://localhost:8000/products", {
      method: "POST",

      body: JSON.stringify({
        name: name,
        description: description,
        category: category,
        price: price,
        stock: stock,
        image_url: image_url,
      })
    })
      .then((response) => {
        if (response.status === 200) {
          setName("");
          setDescription("");
          setCategory("");
          setPrice("");
          setStock("");
          setImageUrl("");
          alert("Product added successfully");
          window.location.reload();
        }
      })
      .catch(error => console.log(error))
  };


  return (

    <>

      <button type="button" class="btn btn-dark m-2" data-bs-toggle="modal" data-bs-target="#exampleModal">
        Add Product
      </button>


      <div class="modal fade text-start bg-dark" id="exampleModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="exampleModalLabel">Add Product</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form onSubmit={handleSubmit}>
                <input type="hidden" name="_token" value=""></input>
                <div class="form-group">
                  <label class="control-label">Name</label>
                  <div>
                    <input type="text" class="form-control " placeholder="Name" value={name} onChange={(e) => setName(e.target.value)}></input>
                  </div>
                </div>
                <div class="form-group">
                  <label class="control-label">Description</label>
                  <div>
                    <input type="text" class="form-control " placeholder="Description" value={description} onChange={(e) => setDescription(e.target.value)}></input>
                  </div>
                </div>
                <div class="form-group">
                  <label class="control-label">Category</label>
                  <div>
                    <input type="text" class="form-control " placeholder="Category" value={category} onChange={(e) => setCategory(e.target.value)}></input>
                  </div>
                </div>
                <div class="form-group">
                  <label class="control-label">Price</label>
                  <div>
                    <input type="number" class="form-control " placeholder="Price" value={price} onChange={(e) => setPrice(e.target.value)}></input>
                  </div>
                </div>
                <div class="form-group">
                  <label class="control-label">Stock</label>
                  <div>
                    <input type="number" class="form-control " placeholder="Stock" value={stock} onChange={(e) => setStock(e.target.value)}></input>
                  </div>
                </div>
                <div class="form-group">
                  <label class="control-label">Image_url</label>
                  <div>
                    <input type="text" class="form-control " placeholder="Image_url" value={image_url} onChange={(e) => setImageUrl(e.target.value)}></input>
                  </div>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary" onClick={(e) => handleSubmit(e)} data-bs-dismiss="modal">Add</button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default AddProduct;