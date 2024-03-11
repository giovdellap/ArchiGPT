import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Form, Button, Row, Col } from 'react-bootstrap';
import Navbar from '../../Navbar'


function AddWorkerForm() {
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [profession, setProfession] = useState("");
  const [location, setLocation] = useState("");
  const [description, setDescription] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();

    const form = event.target;
    const name = form.name.value;
    const surname = form.surname.value;
    const profession = form.profession.value;
    const location = form.location.value;
    const description = form.description.value;
    const email = form.email.value;
    const phone = form.phone.value;
    const address = form.address.value;
    const password = form.password.value;

    let formData = new FormData();
    formData.append("name", name);
    formData.append("surname", surname);
    formData.append("profession", profession);
    formData.append("location", location);
    formData.append("description", description);
    formData.append("email", email);
    formData.append("phone", phone);
    formData.append("address", address);
    formData.append("password", password);

    fetch("http://localhost:5002/workers", {
      method: "POST",
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Worker added:", data);
        alert("Worker added successfully");
        navigate("/admin/workers");
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
   <Navbar />
    <Container style={{marginTop:'20px'}}>
      <h1>Add New Worker</h1>
      <Form onSubmit={handleSubmit}>
        <Row>
          <Col>
            <Form.Group controlId="email">
              <Form.Label>Email:</Form.Label>
              <Form.Control type="text" value={email} onChange={(e) => setEmail(e.target.value)} />
            </Form.Group>
            <Form.Group controlId="name">
              <Form.Label>Name:</Form.Label>
              <Form.Control type="text" value={name} onChange={(e) => setName(e.target.value)} />
            </Form.Group>
            <Form.Group controlId="surname">
              <Form.Label>Surname:</Form.Label>
              <Form.Control type="text" value={surname} onChange={(e) => setSurname(e.target.value)} />
            </Form.Group>
            <Form.Group controlId="profession">
              <Form.Label>Profession:</Form.Label>
              <Form.Control type="text" value={profession} onChange={(e) => setProfession(e.target.value)} />
            </Form.Group>
            <Form.Group controlId="location">
              <Form.Label>Location:</Form.Label>
              <Form.Control type="text" value={location} onChange={(e) => setLocation(e.target.value)} />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="phone">
              <Form.Label>Phone:</Form.Label>
              <Form.Control type="tel" value={phone} onChange={(e) => setPhone(e.target.value)} />
            </Form.Group>
            <Form.Group controlId="address">
              <Form.Label>Address:</Form.Label>
              <Form.Control type="text" value={address} onChange={(e) => setAddress(e.target.value)} />
            </Form.Group>
            <Form.Group controlId="password">
              <Form.Label>Password:</Form.Label>
              <Form.Control type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
            </Form.Group>
            <Form.Group controlId="description">
              <Form.Label>Description:</Form.Label>
              <Form.Control as="textarea" rows={3} value={description} onChange={(e) => setDescription(e.target.value)} />
            </Form.Group>
          </Col>
        </Row>  
        <br></br>
        <Button variant="primary" type="submit">
          Add Worker
        </Button>
      </Form>
    </Container>
</>
  );
}

export default AddWorkerForm;
