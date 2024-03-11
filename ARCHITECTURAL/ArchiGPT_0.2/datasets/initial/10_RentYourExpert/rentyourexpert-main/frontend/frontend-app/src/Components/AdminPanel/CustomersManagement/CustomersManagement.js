import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import AdminQeA from '../QeAManagement/AdminQeA';
import AdminReviews from '../ReviewManagement/AdminReview';
import Navbar from '../../Navbar'


function CustomerManagement() {
  const { id } = useParams();
  const [customer, setCustomer] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    fetch(`http://localhost:5002/customers/${id}`)
      .then((response) => response.json())
      .then((data) => setCustomer(data[0]))
      .catch((error) => console.log(error));
  }, [id]);

  const handleDeleteCustomer = () => {
    fetch(`http://localhost:5002/customers/${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.status === 200) {
          alert("Customer deleted successfully");
          navigate('/admin/customers');  
        }
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
    <Navbar />
    <div style={{marginTop:'20px'}} className="container mt-5">
      <div className="card">
        <h5 className="card-header">Customer Details</h5>
        <div className="card-body">
          <p className="card-text"><strong>Username:</strong> {customer.username}</p>
          <p className="card-text"><strong>Name:</strong> {customer.name}</p>
          <p className="card-text"><strong>Surname:</strong> {customer.surname}</p>
          <p className="card-text"><strong>Email:</strong> {customer.email}</p>
          <p className="card-text"><strong>Password:</strong> {customer.password}</p>
          <button className="btn btn-danger mt-3" onClick={handleDeleteCustomer}>Delete</button>
        </div>
      </div>
    </div>
    </>
  );  
} 

export default CustomerManagement;
