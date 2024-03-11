import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import Navbar from './Navbar';


const AboutPage = () => {
  return (
    <>
    <Navbar />
    <Container style={{ marginTop: '20px'}}>
      <Row>
        <Col>
          <h1>About Us</h1>
          <p>Our project aims to simplify the process of finding and hiring professionals by providing a streamlined platform that connects clients with experts in their field. Through the use of microservices, Docker, Flask, and React, we have created an efficient and user-friendly application that empowers both clients and professionals to make the most of their skills and expertise. As a team of passionate computer science engineering students, we are thrilled to have had the opportunity to bring this project to life and contribute to the advancement of our field.</p>
          <p>Our team members are Eleonora Beatrice Rossi, Lorenzo Aloisi, and Andrea Dito.</p>
        </Col>
      </Row>
    </Container>
    </>
  );
};

export default AboutPage;