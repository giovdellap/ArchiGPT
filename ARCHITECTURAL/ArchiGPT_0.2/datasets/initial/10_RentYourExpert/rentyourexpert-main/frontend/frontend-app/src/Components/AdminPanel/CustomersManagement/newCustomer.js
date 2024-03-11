import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { Container, Form, Button, Row, Col } from 'react-bootstrap';
import Navbar from '../../Navbar'


function AddCustomerForm() {
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [password, setPassword] = useState('');
  const [username, setUsername] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();

    const form = event.target;
    const email = form.email.value;
    const password = form.password.value;
    const name = form.name.value;
    const surname = form.surname.value;
    const username = form.username.value;

    let formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);
    formData.append('name', name);
    formData.append('surname', surname);
    formData.append('username', username);

    fetch("http://localhost:5002/customers", {
      method: "POST",
      body: 
        formData
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Customer added:", data);
        alert("Customer added successfully");
        navigate('/admin/customers');  
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
    <Navbar />
    <Container style={{marginTop:'20px'}}>
      <h1>Add New Customer</h1>
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
          </Col>
          <Col>
            <Form.Group controlId="username">
              <Form.Label>Username:</Form.Label>
              <Form.Control type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
            </Form.Group>
            <Form.Group controlId="password">
              <Form.Label>Password:</Form.Label>
              <Form.Control type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
            </Form.Group>
          </Col>
        </Row>
        <br></br>
        <Button variant="primary" type="submit">
          Add Customer
        </Button>
      </Form>
    </Container>
    </>
  );
}

export default AddCustomerForm;
