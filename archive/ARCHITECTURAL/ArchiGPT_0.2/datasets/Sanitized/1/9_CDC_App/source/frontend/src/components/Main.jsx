import React from "react";
import { useEffect, useState } from "react";
import { Link  } from "react-router-dom";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";
import {AddCart} from "./"

const Home = () => {
  const [products, setProducts] = useState([]);

  const [loading] = useState(false);


  useEffect(() => {
    const getProducts = () => {

      fetch('http://localhost:8000/products?orderby=DESC&limit=3', {
        'methods': 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then(response => response.json())
        .then(response => setProducts(response))
        .catch(error => console.log(error))

    };
    getProducts();

  }, []);

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
      <div class="hero border-1 pb-3">
        <div class="card bg-dark text-white border-0 mx-3" >
          <img
            class="card-img img-fluid"
            src="./homepage.jpg"
            alt="Card"
            height={500}
          />

        </div>
      </div>
      <div class="container my-3 py-3">
        <div class="row">
          <div class="col-12">

            <h2 class="display-5 text-center">Our Best Seller</h2>
            <hr />
          </div>
        </div>
        <div class="row justify-content-center">
          {loading ? <Loading /> : <ShowProducts />}
        </div>
      </div>
    </>
  );



};

export default Home;