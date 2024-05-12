import React from 'react'
import { useNavigate } from 'react-router-dom';
import { Footer, Navbar } from "../components";

const AboutPage = () => {
    const navigate = useNavigate();

    const handleNavigate = () => {

      navigate('/products');
      
    };

  return (
    <>
      <Navbar />
      <div className="container my-3 py-3">
        <h1 className="text-center">About Us</h1>
        <hr />
        <p className="lead text-center">
        Welcome to our CDC shop! We are a family-owned business dedicated to providing high-quality tools and equipment. 
        With years of experience in the industry, we understand the importance of having reliable tools to get the job done right.
        At our shop, you'll find a wide variety of tools to meet all of your needs, from hand tools and power tools to specialty equipment and accessories. 
        We carry products from some of the most trusted brands in the business, so you can be confident that you're getting the best quality tools available.
        Our team of knowledgeable staff is always here to help answer any questions you may have and provide expert advice on which tools are best suited for your specific project. 
        We take pride in providing excellent customer service and ensuring that you have a positive shopping experience with us.
        We are passionate about tools and are committed to providing our customers with the best selection and service possible. 
        Thank you for choosing us as your go-to destination for all your tool needs!
        </p>

        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-6 text-center">
                    <button class="btn btn-lg btn-outline-dark" onClick= {handleNavigate}><h2>Our Product</h2></button>
                </div>
            </div>
        </div>
    </div>
      <Footer />
    </>
  )
}

export default AboutPage