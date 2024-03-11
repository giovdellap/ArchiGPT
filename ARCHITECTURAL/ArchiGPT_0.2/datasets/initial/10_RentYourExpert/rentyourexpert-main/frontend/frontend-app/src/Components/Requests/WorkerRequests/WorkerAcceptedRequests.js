import React, { useEffect, useState } from 'react';
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

function WorkerAcceptedRequests({ id }) {
  const [requests, setRequests] = useState([]);


  useEffect(() => {
    fetch(`http://localhost:5001/worker_profile/${id}/accepted_requests`)
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
          fetch(`http://localhost:5001/worker_profile/${id}/accepted_requests`)
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
    <div>
      {requests
          .filter(request => request.accepted)
          .map(request => (
            <MDBCard key={request.id} className="mb-3">
              <MDBCardBody>
              <MDBCardImage variant="top" src={request.image_url}  className="rounded-circle" style={{ width: '50px', height: '50px' }} />
                <div>Name: {request.name}</div>
                <div>Surname: {request.surname}</div>
                <div>Username: {request.username}</div>
                <div>Accepted: {request.accepted ? 'yes' : 'no'}</div>
                {!request.accepted && <button className="btn btn-primary" onClick={() => handleRequestResponse(request.id, 1)}>Accept</button>}
                {request.accepted && <button className="btn btn-danger" onClick={() => handleRequestResponse(request.id, 0)}>Delete</button>}
              </MDBCardBody>
            </MDBCard>
          ))}
    </div>
  );
}

export default WorkerAcceptedRequests;