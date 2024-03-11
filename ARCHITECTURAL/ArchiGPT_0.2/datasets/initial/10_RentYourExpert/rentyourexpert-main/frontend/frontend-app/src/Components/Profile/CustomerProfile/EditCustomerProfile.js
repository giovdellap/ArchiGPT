import React, { useState, useEffect } from 'react';
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

function EditProfile(props) {
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const [formSubmitted, setFormSubmitted] = useState(false);

  useEffect(() => {
    setName(props.customer.name);
    setSurname(props.customer.surname);
    setEmail(props.customer.email);
    setUsername(props.customer.username);
    setPassword(props.customer.password);
  }, [props.customer]);

  const handleSubmit = (event) => {
    event.preventDefault();

    const form = event.target;
    const name = form.name.value;
    const surname = form.surname.value;
    const username = form.username.value;
    const email = form.email.value;

    let formData = new FormData();
    formData.append('name', name);
    formData.append('surname', surname);
    formData.append('username', username);
    formData.append('email', email);

    fetch(`http://localhost:5003/customer_profile/${props.customer.id}`, {
      method: 'PUT',
      body: formData
    })
      .then(response => response.text())
      .then(data => {
        setMessage(data);
        setFormSubmitted(true); // Imposta formSubmitted a true dopo che il form è stato inviato con successo
        props.setShowEditProfile(false); // Nasconde il componente padre dopo l'aggiornamento
        window.location.reload() //aggiorna perchè sennò non vedi le modifiche
      })
      .catch(error => {
        console.error('Error:', error);
        setFormSubmitted(true); // Imposta formSubmitted a true dopo che il form è stato inviato con errore
      });
  };


  return (
    <MDBCard className="mb-4">
  <MDBCardBody>
    <form onSubmit={handleSubmit}>
      <MDBRow>
        <MDBCol sm="3">
          <MDBCardText>Username:</MDBCardText>
        </MDBCol>
        <MDBCol sm="9">
          <div className="form my-3">
            <input name="username" type="text" className="form-control" value={username} onChange={(e) => setUsername(e.target.value)} />
          </div>
        </MDBCol>
      </MDBRow>
      <hr />
      <MDBRow>
        <MDBCol sm="3">
          <MDBCardText>Name:</MDBCardText>
        </MDBCol>
        <MDBCol sm="9">
          <div className="form my-3">
            <input name="name" type="text" className="form-control" value={name} onChange={(e) => setName(e.target.value)} />
          </div>
        </MDBCol>
      </MDBRow>
      <hr />
      <MDBRow>
        <MDBCol sm="3">
          <MDBCardText>Surname:</MDBCardText>
        </MDBCol>
        <MDBCol sm="9">
          <div className="form my-3">
            <input name="surname" type="text" className="form-control" value={surname} onChange={(e) => setSurname(e.target.value)} />
          </div>
        </MDBCol>
      </MDBRow>
      <hr />
      <MDBRow>
        <MDBCol sm="3">
          <MDBCardText>Email:</MDBCardText>
        </MDBCol>
        <MDBCol sm="9">
          <div className="form my-3">
            <input name="email" type="text" className="form-control" value={email} onChange={(e) => setEmail(e.target.value)} />
          </div>
        </MDBCol>
      </MDBRow>
      <hr />
    
      <div className="text-center">
        <button type="submit" className="my-2 mx-auto btn btn-dark">Update</button>
      </div>
    </form>
    {formSubmitted && <p>{message}</p>}
  </MDBCardBody>
</MDBCard>
  );
}

export default EditProfile;