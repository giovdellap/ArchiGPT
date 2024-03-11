import { useState, useEffect } from "react";
import { useNavigate, Link } from 'react-router-dom';
import Navbar from '../../Navbar'


function RequestList(){
    const [requests, setRequests] = useState([]);

    useEffect(() => {
        fetch("http://localhost:5002/requests")
          .then((response) => response.json())
          .then((data) => setRequests(data))
          .catch((error) => console.log(error));
      }, []);

      const handleDeleteRequest = (id) => {
        fetch(`http://localhost:5002/requests/${id}`, {
            method: 'DELETE',
        })
        .then((response) => {
            if (response.status === 200) {
                const updatedRequests = requests.filter(request => request.id !== id);
                setRequests(updatedRequests);
            }
        })
        .catch((error) => console.log(error));
    };


      return (
        <>
        <Navbar />
        <div style={{marginTop:'20px'}} className="container">
          <div className="row justify-content-center">
            <div className="col-12 col-md-8 col-lg-6">
              <h1 className="text-center mb-4">All Requests</h1>
              <Link to="/admin/requests/new" className="d-block mb-3 btn btn-primary">Add a new request</Link>
              {requests.map((request) => (
                  <div key={request.id} className="card mb-3">
                    <div className="card-body">
                      <p className="card-text">Customer ID: {request.customer_id}</p>
                      <p className="card-text">Worker ID: {request.worker_id}</p>
                      <p className="card-text">Created At: {request.created_at}</p>
                      <button onClick={() => handleDeleteRequest(request.id)} className="btn btn-danger">Delete</button>
                    </div>
                  </div>
              ))}                           
            </div>
          </div>
        </div>
        </>
      )
}

export default RequestList;
