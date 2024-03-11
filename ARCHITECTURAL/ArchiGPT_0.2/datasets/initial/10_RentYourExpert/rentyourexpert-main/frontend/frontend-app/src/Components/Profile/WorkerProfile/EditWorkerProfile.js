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

function EditWorkerProfile(props) {

  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [email, setEmail] = useState('');
  const [profession, setProfession] = useState('');
  const [location,setLocation] = useState('');
  const [description,setDescription] = useState('');
  const [phone,setPhone] = useState('');
  const [address,setAddress] = useState('');
  const [available,setAvailable] = useState('');

  const [message, setMessage] = useState('');
  const [formSubmitted, setFormSubmitted] = useState(false);

  useEffect(() => {
    setName(props.worker.name);
    setSurname(props.worker.surname);
    setEmail(props.worker.email);
    setProfession(props.worker.profession);
    setLocation(props.worker.location);
    setDescription(props.worker.description);
    setPhone(props.worker.phone);
    setAddress(props.worker.address);
    setAvailable(props.worker.available);
  }, [props.worker]);

  const handleSubmit = (event) => {
    event.preventDefault();

    const form = event.target;
    const name = form.name.value;
    const surname = form.surname.value;
    const email = form.email.value;
    const profession = form.profession.value;
    const location = form.location.value;
    const description = form.description.value;
    const phone = form.phone.value;
    const address = form.address.value;
    const available = form.available.value;


    let formData = new FormData();
    formData.append('name', name);
    formData.append('surname', surname);
    formData.append('email', email);
    formData.append('profession', profession);
    formData.append('location', location);
    formData.append('description', description);
    formData.append('phone', phone);
    formData.append('address', address);
    formData.append('available', available);


    fetch(`http://localhost:5001/worker_profile/${props.worker.id}`, {
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
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Profession:</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <div className="form my-3">
                <input name="profession" type="text" className="form-control" value={profession} onChange={(e) => setProfession(e.target.value)} />
              </div>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Location:</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <div className="form my-3">
                <input name="location" type="text" className="form-control" value={location} onChange={(e) => setLocation(e.target.value)} />
              </div>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Description:</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <div className="form my-3">
                <input name="description" type="text" className="form-control" value={description} onChange={(e) => setDescription(e.target.value)} />
              </div>
            </MDBCol>
          </MDBRow>
          <hr />
          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Phone:</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <div className="form my-3">
                <input name="phone" type="text" className="form-control" value={phone} onChange={(e) => setPhone(e.target.value)} />
              </div>
            </MDBCol>
          </MDBRow>
          <hr />  

          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Address:</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <div className="form my-3">
                <input name="address" type="text" className="form-control" value={address} onChange={(e) => setAddress(e.target.value)} />
              </div>
            </MDBCol>
          </MDBRow>
          <hr />  

          <MDBRow>
            <MDBCol sm="3">
              <MDBCardText>Available:</MDBCardText>
            </MDBCol>
            <MDBCol sm="9">
              <div className="form my-3">
                <input 
                  name="available" 
                  type="text" 
                  className="form-control" 
                  value={available} 
                  onChange={(e) => setAvailable(e.target.value)} />
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

export default EditWorkerProfile;