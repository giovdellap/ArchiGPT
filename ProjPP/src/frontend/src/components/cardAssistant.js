import React, { useState } from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';

function CardAssistant({ listAssistants: assistants }) {
    const [listAssistants, setListAssistants] = useState(assistants);

    return (
        <Container className="mt-4">
            <Row>
                {listAssistants.map((item, index) => {
                    return (
                        <Col xs={12} sm={12} md={12} lg={12} key={index}>
                            <Card className="mb-4 card-assistants">
                                <Card.Body>
                                    <Card.Title>{item.name}</Card.Title>
                                    <Card.Text>{item.id}</Card.Text>
                                </Card.Body>
                                <Card.Footer className="text-white text-center bg-dark">{item.model}</Card.Footer>
                            </Card>
                        </Col>
                    );
                }
                )}
            </Row>
        </Container>
    );
}

export default CardAssistant;