import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';

function AdminReviews({ id }) {
  const [reviews, setReviews] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:5006/catalogue/${id}`)
      .then(response => response.json())
      .then(data => setReviews(data))
      .catch(error => console.log(error));
  }, [id]);

  const handleReviewDelete = (reviewId) => {
    fetch(`http://localhost:5002/catalogue/${id}/${reviewId}`, {
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

  return (
    <div>
      <h2>Reviews</h2>
      <div className="row">
        {reviews.map(reviews => (
          <div className="col-12 mb-3" key={reviews.id}>
            <div className="border bg-light p-3">
              <div>ID: {reviews.customer_id}</div>
              <div>Description: {reviews.description}</div>
              <div>Created At: {reviews.created_at}</div>
              <Button variant="danger" size="sm" onClick={() => handleReviewDelete(reviews.id)}>Delete</Button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default AdminReviews;
