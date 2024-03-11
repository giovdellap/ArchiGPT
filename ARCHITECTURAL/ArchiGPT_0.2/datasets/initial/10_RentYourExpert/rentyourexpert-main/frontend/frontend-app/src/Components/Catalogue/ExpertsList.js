import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import LogoutCustomerButton from '../Logout/Logout';
import  Navbar  from '../Navbar';
import { Image, Card, Col, Button, Row, Form } from "react-bootstrap";

function ExpertsList() {
  const authToken = localStorage.getItem('auth_token');
  const [experts, setExperts] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const authToken = localStorage.getItem('auth_token')
    fetch("http://localhost:5000/catalogue")
      .then((response) => response.json())
      .then((data) => setExperts(data))
      .catch((error) => console.log(error));
  }, []);

  const handleSearch = (event) => {
    event.preventDefault();
      fetch(`http://localhost:5000/catalogue?filter=${searchTerm}`)
         .then(response => response.json())
         .then((data) => setExperts(data))
         .catch(error => console.error(error));
  }

  return (
    <>
      <Navbar />
      
      <div style={{marginTop:'20px'}} className="container my-5">
      <h1 className="my-4">Expert List</h1>
        <Form className="my-4 d-flex" onSubmit={handleSearch}>
          <Form.Group className="mr-2 flex-grow-1">
            <Form.Control
              type="text"
              placeholder="Search by name, location, description or profession"
              value={searchTerm}
              onChange={(event) => setSearchTerm(event.target.value)}
            />
          </Form.Group>
          <Button variant="primary" type="submit" className="ml-3">
            Search
          </Button>
        </Form>
      <Row>
        {experts.map((expert, index) => (
          <Col md={4} sm={12} key={index}>
            <Card className="mb-4 shadow-sm">
              <Card.Body>
                <Card.Img variant="top" src={expert.image_url} className="rounded-circle" style={{ width: '150px', height: '150px'}} />
                <Card.Title>{expert.name} {expert.surname}</Card.Title>
                <Card.Text>{expert.profession}, {expert.location}</Card.Text>
                <Link to={`/catalogue/${expert.id}`}>
                  <Button variant="outline-dark">View Profile</Button>
                </Link>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </div>
    </>
  );
}

export default ExpertsList;
