import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import LogoutCustomerButton from '../Logout/Logout';
import QeA from '../QeA/QeA';
import Reviews from '../Reviews/Reviews';
import { Image, Card, Col, Button, Row } from "react-bootstrap";
import  Navbar  from '../Navbar';
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
  MDBListGroupItem
} from 'mdb-react-ui-kit';
import { FaGlobe, FaGithub, FaTwitter, FaInstagram, FaFacebook } from 'react-icons/fa';


function ExpertDetail() {
  const { id } = useParams();
  const [expert, setExpert] = useState({});
  const authToken = localStorage.getItem('auth_token');
 
  
  const sendRequest = () => {
    fetch(`http://localhost:5004/catalogue/${id}`, {
      method: 'POST',
      headers: {
        'Authorization': authToken
      }
    })
    .then((response) => {
      if (response.ok) {
        console.log('Request sent successfully');
        alert('Request sent successfully');
        // aggiorna il componente per visualizzare la richiesta inviata
      } else {
        alert('Failed to send request');
      }
    })
    .catch((error) => console.log(error));
  }

  useEffect(() => {
    fetch(`http://localhost:5000/catalogue/${id}`)
      .then((response) => response.json())
      .then((data) => setExpert(data[0]))
      .catch((error) => console.log(error));
  }, [id]);

  return (
    <>
    <Navbar />
    <div style={{marginTop:'20px'}}>
    <h1 className="mb-4">Expert Details</h1>
    <MDBRow >
  <MDBCol lg="4">
    <MDBCard className="mb-4">
      <MDBCardBody className="text-center">
        <Card.Img variant="top" src={expert.image_url}  className="rounded-circle" style={{ width: '150px', height: '150px' }} />
        <p className="text-dark mb-1">{expert.name} {expert.surname}</p>
        <p className="text-muted mb-1">{expert.profession}</p>
        <p className="text-muted mb-4">{expert.location}</p>
        <div className="d-flex justify-content-center mb-2">
        <button type="button" class="btn btn-primary" onClick={sendRequest}>Send Request</button>
        </div>
      </MDBCardBody>
    </MDBCard>

    <MDBCard className="mb-4 mb-lg-0">
  <MDBCardBody className="p-0">
    <MDBListGroup className="rounded-3">
      <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
        <FaGlobe className="text-warning" />
        <a target="_blank" rel="noreferrer">
        www.website.com
        </a>
      </MDBListGroupItem>
      <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
        <FaGithub style={{ color: '#333333' }} />
        <a target="_blank" rel="noreferrer">
        www.github.com
        </a>
      </MDBListGroupItem>
      <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
        <FaTwitter style={{ color: '#55acee' }} />
        <a target="_blank" rel="noreferrer">
        www.twitter.com
        </a>
      </MDBListGroupItem>
      <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
        <FaInstagram style={{ color: '#ac2bac' }} />
        <a target="_blank" rel="noreferrer">
        www.instagram.com
        </a>
      </MDBListGroupItem>
      <MDBListGroupItem className="d-flex justify-content-between align-items-center p-3">
        <FaFacebook style={{ color: '#3b5998' }} />
        <a target="_blank" rel="noreferrer">
        www.facebook.com
        </a>
      </MDBListGroupItem>
    </MDBListGroup>
  </MDBCardBody>
</MDBCard>
  </MDBCol>

  <MDBCol lg="8">
    <MDBCard className="mb-4">
      <MDBCardBody>
        <MDBRow>
          <MDBCol sm="3">
            <MDBCardText>Full Name</MDBCardText>
          </MDBCol>
          <MDBCol sm="9">
            <MDBCardText className="text-muted">{expert.name} {expert.surname}</MDBCardText>
          </MDBCol>
        </MDBRow>
        <hr />
        <MDBRow>
          <MDBCol sm="3">
            <MDBCardText>Email</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
            <MDBCardText className="text-muted">{expert.email}</MDBCardText>            
          </MDBCol>
        </MDBRow>
        <hr />
        <MDBRow>
          <MDBCol sm="3">
            <MDBCardText>Phone</MDBCardText>
          </MDBCol>
          <MDBCol sm="9">
            <MDBCardText className="text-muted">{expert.phone}</MDBCardText>
            </MDBCol>
        </MDBRow>
        <hr />
        <MDBRow>
          <MDBCol sm="3">
            <MDBCardText>Availability</MDBCardText>
          </MDBCol>
          <MDBCol sm="9">
            <MDBCardText className="text-muted">{expert.available ? 'Available' : 'Not available'}</MDBCardText>
          </MDBCol>
        </MDBRow>
        <hr />
        </MDBCardBody>
      </MDBCard>
      <QeA id={id} />
      <Reviews id={id} />
    </MDBCol>
  </MDBRow>
  </div>
</>
  );
} 
export default ExpertDetail;



