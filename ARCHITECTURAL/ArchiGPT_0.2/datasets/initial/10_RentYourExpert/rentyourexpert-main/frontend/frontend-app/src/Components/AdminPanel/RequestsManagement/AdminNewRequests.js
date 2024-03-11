import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import { Container, Form, Button, Row, Col } from 'react-bootstrap';
import Navbar from '../../Navbar'


function AdminNewRequest(){
    const [customerId, setCustomerId] = useState("");
    const [workerId, setWorkerId] = useState("");
    const navigate = useNavigate();

    const handleAddRequest = (event) => {
        event.preventDefault();

        const formData = new FormData();
        formData.append('customer_id', customerId);
        formData.append('worker_id', workerId);
        formData.append('accepted', 0)

        fetch('http://localhost:5002/requests', {
            method: 'POST',
            body: formData,
        })
        .then((response) => {
            if (response.status === 200) {
                alert('Request added successfully');
                navigate('/admin/requests');
            }
            else{
              alert("Failed to add the request");
              navigate('/admin/requests');
            }
        })
        .catch((error) => console.log(error));
    };

    return (
        <>
        <Navbar />
        <Container style={{marginTop:'20px'}}>
      <h1>Add New Request</h1>
      <Form onSubmit={handleAddRequest}>
        <Row>
          <Col>
            <Form.Group controlId="customer_id">
              <Form.Label>customer_id:</Form.Label>
              <Form.Control type="text" value={customerId} onChange={(e) => setCustomerId(e.target.value)} />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="worker_id">
              <Form.Label>worker_id:</Form.Label>
              <Form.Control type="text" value={workerId} onChange={(e) => setWorkerId(e.target.value)} />
            </Form.Group>
          </Col>
        </Row>
        <br></br>
        <Button variant="primary" type="submit">
          Add Request
        </Button>
      </Form>
    </Container>
        </>
    )
}
export default AdminNewRequest;
