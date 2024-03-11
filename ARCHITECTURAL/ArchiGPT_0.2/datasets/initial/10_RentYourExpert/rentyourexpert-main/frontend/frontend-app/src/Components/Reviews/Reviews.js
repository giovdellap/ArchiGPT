import React, { useEffect, useState } from 'react';
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
import { FaGlobe, FaGithub, FaTwitter, FaInstagram, FaFacebook } from 'react-icons/fa';
import { Container, Row, Col, Card, ListGroup, ListGroupItem, Button, Form, Input } from 'react-bootstrap';
import Modal from "react-modal";
import Navbar from '../Navbar';



function Reviews({ id }) {
    const [reviews, setReviews] = useState([]);
    const authToken = localStorage.getItem('auth_token');
    const authTokenParts = authToken ? authToken.split(';') : [];
    const firstAuthTokenPart = authTokenParts.length > 0 ? authTokenParts[0] : null;
    const lastAuthTokenPart = authTokenParts.length > 0 ? authTokenParts[authTokenParts.length - 1] : null;
    const [editMode, setEditMode] = useState(false);
    const [editReviewId, setEditReviewId] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);

    useEffect(() => {
        fetch(`http://localhost:5006/catalogue/${id}`)
          .then(response => response.json())
          .then(data => setReviews(data))
          .catch(error => console.log(error));
      }, [id]);


      const handleReviewSubmit = (description) => {
        fetch(`http://localhost:5006/catalogue/${id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': authToken
          },
          body: `description=${description}`
        })
          .then(response => {
            if (response.status === 200) {
              alert('Recensione aggiunta con successo');
              setIsModalOpen(false)
              // Aggiornare la lista delle recensioni
              fetch(`http://localhost:5006/catalogue/${id}`)
              .then(response => response.json())
              .then(data => setReviews(data))
              .catch(error => console.log(error));
            } else {
              alert('Tu e il worker non siete ancora amici!');
            }
          })
          .catch(error => console.log(error));
      }



      const handleReviewUpdate = (event, reviewId) => {
        event.preventDefault();
        const description = event.target.elements.description.value;
        const updatedReviews = reviews.map(review => {
          if (review.id === reviewId) {
            return { ...review, description };
          } else {
            return review;
          }
        });
        setReviews(updatedReviews);
        setEditMode(false);
        fetch(`http://localhost:5006/catalogue/${id}/${reviewId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': authToken
          },
          body: `description=${description}`,
                  
        })
        .then(response => {
          if (!response.ok) {
            throw new Error('Failed to update review');
          }
        })
        .catch(error => console.error(error));
      };

      const handleReviewDelete = (reviewId) => {
        fetch(`http://localhost:5006/catalogue/${id}/${reviewId}`, {
          method: 'DELETE',
          headers: {
            'Authorization': localStorage.getItem('auth_token')
          }
        })
        .then(response => {
          if (response.status === 200) {
            alert('Recensione eliminata con successo');
            // Aggiornare la lista delle recensioni
            fetch(`http://localhost:5006/catalogue/${id}`)
              .then(response => response.json())
              .then(data => setReviews(data))
              .catch(error => console.log(error));
          } else {
            alert('Errore nella delete');
          }
        })
        .catch(error => console.log(error));
      };

      const handleEditModalOpen = () => {
        setIsEditModalOpen(true);
      };

if(lastAuthTokenPart=='C'){
  return(
    <div>
  <MDBCol lg="12">
    <MDBCard className="mb-4">
      <MDBCardBody>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
          <h2 style={{ textAlign: 'left' }}>Reviews</h2>
          <button type="button" className="btn btn-primary" onClick={() => setIsModalOpen(true)}>Add Review</button>
        </div>
        <Modal isOpen={isModalOpen} className="modal-dialog-centered custom-modal">
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <h2>Add new review</h2>
              <form onSubmit={e => {
                  e.preventDefault();
                  handleReviewSubmit(e.target.elements.description.value);
                }}>
                <label htmlFor="description">Description:</label>
                <input type="text" id="description" name="description" />
                <div>
                  <button type="submit" >Submit</button>
                  <button type="button" onClick={() =>  window.location.reload()}>Cancel</button>
                </div>
              </form>
            </div>
          </div>
        </Modal>
        <hr />
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(1, 1fr)', gap: '1rem' }}>
        {reviews.map((review) => (
            <MDBCard key={review.id} className="mb-3" style={{width: '99%', marginBottom: '1rem', textAlign: 'left'}}>
            <MDBCardBody>
              <MDBRow>
                <MDBCol size="2">
                  <MDBCardImage variant="top" src={review.image_url} className="rounded-circle" style={{ width: '50px', height: '50px' }} />
                </MDBCol>
                <MDBCol size="10">
                  <div>
                    <div>{review.username}:</div>
                    <div>{review.description}</div>
                    <div>{review.created_at}</div>
                    {firstAuthTokenPart === review.customer_id.toString() && (
                      <div>
                        {editMode && review.id === editReviewId ? (
                          <Modal isOpen={isEditModalOpen} toggle={() => setIsEditModalOpen(false)} className="modal-dialog-centered custom-modal">
                            <div className="modal-dialog" role="document">
                              <div className="modal-content">
                                <h2>Edit Review</h2>
                                <form onSubmit={(event) => handleReviewUpdate(event, review.id)}>
                                  <label htmlFor="description">Description:</label>
                                  <input type="text" className="form-control" id="description" name="description" defaultValue={review.description} />
                                  <div>
                                    <button type="submit">Update</button>
                                    <button type="button" onClick={() => window.location.reload()}>Cancel</button>
                                  </div>
                                </form>
                              </div>
                            </div>
                          </Modal>
                        ) : (
                          null
                        )}
                        <div class="d-flex justify-content-end">
                          {editMode && review.id === editReviewId ? (
                            null
                          ) : (
                            <div>
                              <button className="btn btn-primary mr-2" onClick={() => {
                                handleEditModalOpen();
                                setEditMode(true);
                                setEditReviewId(review.id);
                              }}>Edit</button>
                              <button class="btn btn-danger" onClick={() => handleReviewDelete(review.id)}>Delete</button>
                            </div>
                          )}
                        </div>
                      </div>
                    )}
                  </div>
                </MDBCol>
              </MDBRow>
            </MDBCardBody>
          </MDBCard>
          ))}
        </div>
      </MDBCardBody>
    </MDBCard>
  </MDBCol>
</div>

)


}
else{
  return(
    <>
      <Navbar />
      <div className="w-100" style={{ marginTop:'20px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
  <MDBCol lg="8">
  <MDBCard className="mb-4">
  <MDBCardBody>
    <MDBCardText>
      <h2>Reviews</h2>
      {reviews.map(review => (
        <MDBCard key={review.id}>
          <MDBCardBody style={{ display: 'flex', alignItems: 'left' }}>
            <MDBCardImage variant="top" src={review.image_url} className="rounded-circle" style={{ width: '50px', height: '50px' }} />
            <div style={{ marginLeft: '1rem' }}>
              <div>Username: {review.username}</div>
              <div>Description: {review.description}</div>
              <div>Created At: {review.created_at}</div>
            </div>
          </MDBCardBody>
        </MDBCard>
      ))}
    </MDBCardText>
  </MDBCardBody>
</MDBCard>
  </MDBCol>
</div>
</>
)
}


}

export default Reviews;
