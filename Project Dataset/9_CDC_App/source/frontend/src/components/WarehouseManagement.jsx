import * as React from 'react';
import { useEffect, useState } from "react";
import Skeleton from "react-loading-skeleton";
import {useNavigate, useParams} from "react-router-dom";




const WarehouseManagement = ()=>{

  const {id} = useParams();
  const [product, setProduct] = useState([]);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

    useEffect(() => {
        const getProduct = () => {
          setLoading(true);
          fetch(`http://localhost:8000/products/${id}`,{
          
          'methods':'GET',
          })
          .then(response => response.json())
          .then(response => setProduct(response))
          .then(setLoading(false))
          .catch(err => {
                console.log(err);
          })
          
      };
        getProduct();
      }, [id]);

      const handleDeleteProduct = () => {

        fetch(`http://localhost:8000/products/${id}`, {
            method: "DELETE", 
          })
            .then((response) => {
              if (response.status === 200) {
                  alert("Product deleted successfully");
                  navigate('/merchants'); 
              }
            })
            .catch((error) => console.log(error));
        
      
      };

      const Loading = () => {
        return (
          <>
            <div class="container my-5 py-2">
              <div class="row">
                <div class="col-md-6 py-3">
                  <Skeleton height={400} width={400} />
                </div>
                <div class="col-md-6 py-5">
                  <Skeleton height={30} width={250} />
                  <Skeleton height={90} />
                  <Skeleton height={40} width={70} />
                  <Skeleton height={50} width={110} />
                  <Skeleton height={120} />
                  <Skeleton height={40} width={110} inline={true} />
                  <Skeleton className="mx-3" height={40} width={110} />
                </div>
              </div>
            </div>
          </>
        );
      };
      const ShowProduct = () => {
        return (
          <>
            {product.map((product) => {
                    return (
                      
                        <div class="container my-5 py-2">
                          <div class="row">
                            <div class="col-md-6 col-sm-12 py-3">
                              <img
                                class="img-fluid"
                                src={product.image_url}
                                alt={product.name}
                                width="400px"
                                height="400px"
                              />
                            </div>
                            <div class="col-md-6 col-md-6 py-5">
                              <h4 class="text-uppercase text-muted">{product.category}</h4>
                              <h1 class="display-5">{product.name}</h1>
                              <h3 class="display-6  my-4">${product.price}</h3>
                              <p class="lead">{product.description}</p>
                              <button type='button'class="btn btn-outline-dark">
                                  Update
                                </button>
                                <button type="button" class="btn btn-outline-danger mx-3" onClick={handleDeleteProduct}>
                                  Delete
                                </button>  
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
        <div class="container my-3 py-3">
            <div class="row">
            <div class="col-12">
                <h2 class="display-5 text-center">Product</h2>
                <hr />
            </div>
            </div>
            <div class="row">
            {loading ? <Loading /> : <ShowProduct />}
            </div>
        </div>
        </>
    )
}

export default WarehouseManagement;