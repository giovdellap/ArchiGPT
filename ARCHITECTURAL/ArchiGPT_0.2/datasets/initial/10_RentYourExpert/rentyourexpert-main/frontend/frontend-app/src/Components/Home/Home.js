import React from 'react';
import { Image , Container, Row, Col, Button } from 'react-bootstrap';


function Home() {
  return (
   
    <Container className="mt-5">
      <Row className="justify-content-center">
      <Image src={require('./worker3.jpg')} alt="Banner Image" style={{ height:'300px', width:'700px'}} />
        <Col md={8}>
          <h1 className="text-center mb-5">Welcome to Rent Your Expert!</h1>
          <p className="text-center">
            Rent Your Expert is the ultimate platform for connecting customers with top-notch professionals in various fields. Whether you're looking for a plumber, a lawyer, a graphic designer, or anything in between, we've got you covered. Our platform offers a seamless experience for both parties, allowing customers to find the right expert for their needs and professionals to showcase their skills and grow their business.
          </p>
        </Col>
      </Row>
      <Row className="justify-content-center mt-5">
        <Col md={{ span: 4 }}>
          <h3 className="text-center mb-4">Customers</h3>
          <div className="d-flex justify-content-center mb-4">
            <Button className="btn btn-dark" variant="primary" href="/login_customer" size="lg">
              Sign In
            </Button>
          </div>
          <p className="text-center">Join our community as a customer and gain access to our network of professionals.</p>
        </Col>
        <Col md={{ span: 4 }}>
          <h3 className="text-center mb-4">Experts</h3>
          <div className="d-flex justify-content-center mb-4">
            <Button className="btn btn-dark" variant="primary" href="/login" size="lg">
              Sign In
            </Button>
          </div>
          <p className="text-center">Ready to offer your expertise? Sign in as a professional and start growing your business.</p>
        </Col>
      </Row>
      <Row className="justify-content-center mt-5">
        <Col md={{ span: 4 }}>
          <div className="d-flex justify-content-center">
            <Button style={{marginBottom:'100px'}}variant="outline-primary" href="/admin" size="lg">
              Admin Panel
            </Button>
          </div>
        </Col>
      </Row>
    </Container>
    
  );
}

export default Home;
