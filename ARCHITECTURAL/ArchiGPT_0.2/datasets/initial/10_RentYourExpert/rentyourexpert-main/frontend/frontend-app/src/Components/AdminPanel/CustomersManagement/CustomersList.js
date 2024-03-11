import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import Navbar from '../../Navbar'


function CustomersList() {
  
  const [customer, setCustomer] = useState([]);

  useEffect(() => {
    const authToken = localStorage.getItem('auth_token')
    fetch("http://localhost:5002/customers")
      .then((response) => response.json())
      .then((data) => setCustomer(data))
      .catch((error) => console.log(error));
  }, []);

  return (
    <>
    <Navbar />
    <div style={{marginTop:'20px'}} className="container">
      <h1>Customers List</h1>
      <div className="list-group">
        {customer.map((customer, index) => (
          <Link
            to={`/admin/customers/${customer.id}`}
            key={index}
            className="list-group-item list-group-item-action"
          >
            {customer.name} {customer.surname}
          </Link>
        ))}
      </div>
      <Link to="/admin/customers/new">
        <button className="btn btn-primary mt-4">Add new customer</button>
      </Link>
    </div>
    </>
  );
}

export default CustomersList;
