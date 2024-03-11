import React, { useEffect, useState } from 'react';
import WorkerAcceptedRequests from './WorkerAcceptedRequests';
import Navbar from '../../Navbar';
import { 
  MDBCol,
  MDBContainer,
  MDBRow,
  MDBCard,
  MDBCardText,
  MDBCardBody,
  MDBCardTitle,
  MDBCardImage,
  MDBBtn,
  MDBBreadcrumb,
  MDBBreadcrumbItem,
  MDBProgress,
  MDBProgressBar,
  MDBIcon,
  MDBListGroup,
  MDBListGroupItem
} from 'mdb-react-ui-kit';

function WorkerRequests({ id }) {
  const [requests, setRequests] = useState([]);


  useEffect(() => {
    fetch(`http://localhost:5001/worker_profile/${id}/pending_requests`)
      .then(response => response.json())
      .then(data => setRequests(data))
      .catch(error => console.log(error));
  }, [id]);


  const handleRequestResponse = (requestId, accepted) => {
    fetch(`http://localhost:5004/worker_profile/${id}/request/${requestId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Authorization': localStorage.getItem('auth_token')
      },
      body: `accepted=${accepted}`
    })
      .then(response => {
        if (response.status == 200) {
          alert("Request succesfully managed")
          // Aggiornare la lista delle recensioni
          fetch(`http://localhost:5001/worker_profile/${id}/pending_requests`)
          .then(response => response.json())
          .then(data => setRequests(data))
          .catch(error => console.log(error));
          window.location.reload(false);
        } else {
          alert('Cannot delete the request, something has happened');
        }
      }).catch(error => console.log(error));
  };

  return (
    <>
      <Navbar />
    <MDBRow style={{ marginTop:'20px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <MDBCol lg="4">
        <MDBCard className="mb-4">
          <MDBCardBody style={{ textAlign: 'left' }}>
            <MDBCardTitle>Pending Requests</MDBCardTitle>
                {requests
              .filter(request => !request.accepted)
              .map(request => (
                <MDBCard key={request.id} className="mb-3">
                  <MDBCardBody>
                  <MDBCardImage variant="top" src={request.image_url}  className="rounded-circle" style={{ width: '50px', height: '50px' }} />
                    <div>Name: {request.name}</div>
                    <div>Surname: {request.surname}</div>
                    <div>Username: {request.username}</div>
                    <button className="btn btn-primary" onClick={() => handleRequestResponse(request.id, 1)}>Accept</button>
                    <button className="btn btn-danger" onClick={() => handleRequestResponse(request.id, 0)}>Delete</button>
                  </MDBCardBody>
                </MDBCard>
            ))}
          </MDBCardBody>
        </MDBCard>
      </MDBCol>
      <MDBCol lg="4">
        <MDBCard className="mb-4">
          <MDBCardBody style={{ textAlign: 'left' }}>
            <MDBCardTitle>Accepted Requests</MDBCardTitle>
            <WorkerAcceptedRequests id={id} />
          </MDBCardBody>
        </MDBCard>
      </MDBCol>
    </MDBRow>
    </>
  );
}

export default WorkerRequests;