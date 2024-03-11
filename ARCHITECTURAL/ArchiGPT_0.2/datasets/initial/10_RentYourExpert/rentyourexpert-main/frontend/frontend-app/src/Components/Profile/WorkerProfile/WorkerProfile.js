import React, { useEffect, useState } from 'react';
import WorkerRequests from '../../Requests/WorkerRequests/WorkerRequests';
import LogoutworkerButton from '../../Logout/Logout';
import QeA from '../../QeA/QeA';
import Reviews from '../../Reviews/Reviews';
import WorkerAcceptedRequests from '../../Requests/WorkerRequests/WorkerAcceptedRequests';
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
import Navbar from '../../Navbar';
import EditWorkerProfile from './EditWorkerProfile';

function WorkerProfile() {
  const authToken = localStorage.getItem('auth_token');
  const authTokenParts = authToken ? authToken.split(';') : [];
  const id  = authTokenParts.length > 0 ? authTokenParts[0] : null;
  const [worker, setWorker] = useState({});
  const [showEditProfile, setShowEditProfile] = useState(false); // Aggiunto lo stato "showEditProfile"

  

  useEffect(() => {
    fetch(`http://localhost:5001/worker_profile/${id}`)
      .then((response) => response.json())
      .then((data) => setWorker(data[0]))
      .catch((error) => console.log(error));
  }, [id]);

  
  const handleUpdateProfile = () => {
    setShowEditProfile(true); // Impostare showEditProfile su true al click sul pulsante "Update Profile"
  };
  
  return (

    <>
    <Navbar />
    <div style={{marginTop:'20px'}} className="container mt-5">
      <h1 className="mb-4">Worker Profile</h1>
      <div className="row">
        <div className="col-md-4">
          <MDBCard className="mb-4">
            <MDBCardBody className="text-center">
            <MDBCardImage variant="top" src={worker.image_url}  className="rounded-circle" style={{ width: '150px', height: '150px' }} />
                    <p className="text-muted mb-1">{worker.name} {worker.surname}</p>
                    <p className="text-muted mb-4">{worker.email}</p>
                    <div className="d-flex justify-content-center mb-2">
                    <button type="button" className="btn btn-primary" onClick={handleUpdateProfile}>
                        Update Profile
                      </button> 
                    </div>
            </MDBCardBody>
          </MDBCard>
        </div>
        <div className="col-md-8">
    {showEditProfile ? ( // Aggiunto condizionale per visualizzare EditWorkerProfile o la seconda card
              <EditWorkerProfile worker={worker} setShowEditProfile={setShowEditProfile}/>
            ) : (
      <MDBCard className="mb-4">
        <MDBCardBody>
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Name</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.name}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Surname</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.surname}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Email</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.email}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Profession</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.profession}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Location</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.location}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Description</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.description}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Phone</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.phone}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Address</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.address}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Available</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{worker.available}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />

        </MDBCardBody>
      </MDBCard>
      )}
      
    </div>
  </div>
</div>
      </>        

   
      );
          };

export default WorkerProfile;





  