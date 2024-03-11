import React from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import Navbar from './Navbar';

const ContactPage = () => {
  return (
    <>
    <Navbar />
    <Container style={{ marginTop: '20px'}}>
      <Row>
        <Col>
          <h1>Contact Us</h1>
          <p>Fill out the form below to get in touch with our team.</p>
          <Form>
            <Form.Group controlId="formBasicName">
              <Form.Label>Name</Form.Label>
              <Form.Control type="text" placeholder="Enter your name" />
            </Form.Group>

            <Form.Group controlId="formBasicEmail">
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter your email" />
            </Form.Group>

            <Form.Group controlId="formBasicMessage">
              <Form.Label>Message</Form.Label>
              <Form.Control as="textarea" rows={3} placeholder="Enter your message" />
            </Form.Group>

            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
    </>
  );
};

export default ContactPage;