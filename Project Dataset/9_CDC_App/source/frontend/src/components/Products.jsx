import React from "react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";



import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import { Navbar, Footer ,AddCart} from "./";



const Products = () => {
  const [products, setProducts] = useState([]);
  const [orderBy, setOrderBy] = useState('default');
  const [name, setName] = useState('');
  const [loading] = useState(false);



  //fuction that uses the useEffect hook to return all products
  useEffect(() => {
    const getProducts = () => {

      fetch('http://localhost:8000/products', {
        'methods': 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then(response => response.json())
        .then(response => setProducts(response))
        .catch(error => console.log(error))
    };
    if (orderBy === "default") {
      getProducts();
    }


  }, [orderBy]);

  //fuction that uses the useEffect hook to return product by price ASC and DESC
  useEffect(() => {
    const getProductsByPrice = () => {


      if (orderBy === "ASC") {
        fetch(`http://localhost:8000/products?orderby=ASC`, {
          'methods': 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        })
          .then(response => response.json())
          .then(response => setProducts(response))
          .then(orderBy => setOrderBy(orderBy))
          .catch(error => console.log(error))
      }
      if (orderBy === "DESC") {
        fetch(`http://localhost:8000/products?orderby=DESC`, {
          'methods': 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        })
          .then(response => response.json())
          .then(response => setProducts(response))
          .then(orderBy => setOrderBy(orderBy))
          .catch(error => console.log(error))
      }

    };
    getProductsByPrice();

  }, [orderBy]);

  //fuction that uses the useEffect hook to return product by name
  useEffect(() => {
    const getProductsByName = () => {

      fetch(`http://localhost:8000/products?name=${name}`, {
        'methods': 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then(response => response.json())
        .then(response => setProducts(response))
        .catch(error => console.log(error))

    };
    getProductsByName();

  }, [name]);

  //function to handle search by name
  const handleSearchByName = (e) => {
    e.preventDefault()
    fetch(`http://localhost:8000/products?name=${name}`, {
      'methods': 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then(response => setProducts(response))
      .catch(error => console.log(error))
  }

  //fuction to handle select option by price
  const handleOrderPrice = (e) => {
    e.preventDefault();
    setOrderBy(e.target.value);
    setName("");

  }

  const Loading = () => {
    return (
      <>

        <div className="col-12 py-5 text-center">
          <Skeleton height={40} width={560} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
        <div className="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
          <Skeleton height={592} />
        </div>
      </>
    );
  };
  const ShowProducts = () => {
    return (
      <>


        {products.map((product) => {
          return (
            <div id={product.id} key={product.id} class="col-md-4 col-sm-6 col-xs-8 col-12 mb-4">
              <div class="card text-center h-100" key={product.id}>
                <img
                  class="card-img-top p-3"
                  src={product.image_url}
                  alt="Card"
                  height={300}
                />
                <div class="card-body">
                  <h5 class="card-title">
                    {product.name}
                  </h5>
                  <p class="card-text">
                    {product.description.substring(0, 90)}...
                  </p>
                </div>
                <ul class="list-group list-group-flush">
                  <li class="list-group-item lead">$ {product.price}</li>
                </ul>
                <div class="card-body">
                  <Link to={"/product/" + product.id} class="btn btn-dark m-1">
                    Details
                  </Link>
                  <AddCart product_id={product.id}/>
                </div>
              </div>
            </div>

          );
        })}
      </>
    );
  };
  return (
    <>
      <Navbar />
      <div class="container my-3 py-3">
        <div class="row">
          <div class="col-3">
            <select class="form-select m-2 border border-dark" value={orderBy} onChange={handleOrderPrice}>
              <option value="default" selected>Sort by</option>
              <option value="ASC">Price - Lowest to Highest</option>
              <option value="DESC">Price - Highest to Lowest</option>
            </select>
          </div>
          <div class="col-3">
            <div class="input-group m-2">
              <input type="search" class="form-control border border-dark border-end-0" placeholder="Search for name" value={name} onChange={(e) => setName(e.target.value)} aria-label="Search" aria-describedby="button-addon2"></input>
              <button class="btn btn-outline-dark border-start-0 me-3 pt-1" type="button" id="button-addon2" onClick={handleSearchByName}><i class="fa fa-search"></i></button>
            </div>

          </div>

        </div>
        <div class="row">
          <div class="col-12">

            <h2 class="display-5 text-center">Products</h2>
            <hr />
          </div>
        </div>
        <div class="row justify-content-center">
          {loading ? <Loading /> : <ShowProducts />}
        </div>
      </div>
      <Footer />
    </>
  );
};



export default Products;

