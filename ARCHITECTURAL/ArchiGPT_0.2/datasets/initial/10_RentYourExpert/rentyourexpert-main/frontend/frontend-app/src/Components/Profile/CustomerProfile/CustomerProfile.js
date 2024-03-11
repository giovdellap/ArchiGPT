import React, { useEffect, useState } from 'react';
import CustomerRequests from '../../Requests/CustomerRequests/CustomerRequests';
import LogoutCustomerButton from '../../Logout/Logout';
import CustomerAcceptedRequests from '../../Requests/CustomerRequests/CustomerAcceptedRequests';
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
import EditCustomerProfile from './EditCustomerProfile';


function CustomerProfile() {
  const authToken = localStorage.getItem('auth_token');
  const authTokenParts = authToken ? authToken.split(';') : [];
  const id  = authTokenParts.length > 0 ? authTokenParts[0] : null;
  const [customer, setCustomer] = useState({});
  const [showEditProfile, setShowEditProfile] = useState(false); // Aggiunto lo stato "showEditProfile"

  useEffect(() => {
    fetch(`http://localhost:5003/customer_profile/${id}`)
      .then((response) => response.json())
      .then((data) => setCustomer(data[0]))
      .catch((error) => console.log(error));
  }, [id]);

  const handleUpdateProfile = () => {
    setShowEditProfile(true); // Impostare showEditProfile su true al click sul pulsante "Update Profile"
  };

  return (
    
       <>
      <Navbar />
      <div style={{marginTop:'20px'}} className="container mt-5">
  <h1 className="mb-4">Customer Profile</h1>
  <div className="row">
    <div className="col-md-4">
      <MDBCard className="mb-4">
        <MDBCardBody className="text-center">
            <MDBCardImage variant="top" src={customer.image_url}  className="rounded-circle" style={{ width: '150px', height: '150px' }} />
                <p className="text-dark mb-1">{customer.username}</p>
                <p className="text-muted mb-1">{customer.name} {customer.surname}</p>
                <p className="text-muted mb-4">{customer.email}</p>
                <div className="d-flex justify-content-center mb-2">
                <button type="button" className="btn btn-primary" onClick={handleUpdateProfile}>
                    Update Profile
                  </button> 
                </div>
        </MDBCardBody>
      </MDBCard>
    </div>
    <div className="col-md-8">
    {showEditProfile ? ( // Aggiunto condizionale per visualizzare EditCustomerProfile o la seconda card
              <EditCustomerProfile customer={customer} setShowEditProfile={setShowEditProfile}/>
            ) : (
      <MDBCard className="mb-4">
        <MDBCardBody>
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Username</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{customer.username}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Name</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{customer.name}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Surname</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{customer.surname}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Email</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{customer.email}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Password</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <MDBCardText className="text-muted">{customer.password}</MDBCardText>
            </MDBCol>
          </MDBRow>
          <hr/>
        </MDBCardBody>
      </MDBCard>
      )}
      <MDBRow>
        <MDBCol>
          <MDBCard className="mb-4">
            <MDBCardBody>
              <MDBCardTitle>Pending Requests</MDBCardTitle>
              <CustomerRequests id={id} />
            </MDBCardBody>
          </MDBCard>
        </MDBCol>
        <MDBCol>
          <MDBCard className="mb-4">
            <MDBCardBody>
              <MDBCardTitle>Accepted Requests</MDBCardTitle>
              <CustomerAcceptedRequests id={id} />
            </MDBCardBody>
          </MDBCard>
        </MDBCol>
      </MDBRow>
    </div>
  </div>
</div>
      </>             
      
       
  );
}

export default CustomerProfile;