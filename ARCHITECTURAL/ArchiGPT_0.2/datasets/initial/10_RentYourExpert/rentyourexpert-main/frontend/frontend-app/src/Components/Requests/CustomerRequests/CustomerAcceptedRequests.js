import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import {
  MDBCol,
  MDBContainer,
  MDBRow,
  MDBCard,
  MDBCardText,
  MDBCardBody,
  MDBCardImage,
  MDBBtn,
  MDBBreadcrumb,
  MDBBreadcrumbItem,
  MDBProgress,
  MDBProgressBar,
  MDBIcon,
  MDBListGroup,
  MDBListGroupItem,
  MDBCardGroup
} from 'mdb-react-ui-kit';





function CustomerAcceptedRequests({ id }) {
  const [requests, setRequests] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:5003/customer_profile/${id}/accepted_requests`)
      .then(response => response.json())
      .then(data => setRequests(data))
      .catch(error => console.log(error));
  }, [id]);

  const handleDeleteRequest = (requestId) => {
    fetch(`http://localhost:5004/customer_profile/${id}/request/${requestId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('auth_token')
      }
    })
      .then(response => {
        if (response.ok) {
          const updatedRequests = requests.filter(request => request.id !== requestId);
          setRequests(updatedRequests);
        } else {
          alert('Cannot delete the request, something has happened');
        }
      })
      .catch(error => console.log(error));
  };

  return (
    <div className="mt-4">
    <div className="list-group">
      {requests.map((request) => (
        <div className="list-group-item" key={request.id}>
          <div className="d-flex justify-content-between align-items-center">
          <MDBCardImage variant="top" src={request.image_url}  className="rounded-circle" style={{ width: '50px', height: '50px' }} />
            <Link to={`/catalogue/${request.worker_id}`}>
              <h5 className="mb-1">{request.name} {request.surname}</h5>
              </Link>
              <p className="mb-1">{request.profession}</p>
            
            {request.accepted && <button className="btn btn-danger" onClick={() => handleDeleteRequest(request.id)}>Delete</button>}
          </div>
        </div>
      ))}
    </div>
  </div>
  );
}

export default CustomerAcceptedRequests;