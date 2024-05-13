import React from 'react';
import { Button, Col, Container, Row } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFolderOpen, faTrash } from '@fortawesome/free-solid-svg-icons';

function CardProject({ listProjects, goToProjectPage}) {

    return (
        <Container className="mt-4">
            <Row>
                {listProjects.map((item, index) => {
                    return (
                        <Col xs={12} sm={12} md={12} lg={12} key={index}>
                            <Card className="mb-4 card-projects">
                                <Card.Body>
                                    <Card.Title>{item.name}</Card.Title>
                                    <Card.Text>{item.id}</Card.Text>
                                    <div style={{ display: 'flex', justifyContent: 'center', gap: '10px' }}>
                                        <Button className='form-button red-button' onClick={() => {}}>
                                            <FontAwesomeIcon icon={faTrash} /> Remove
                                        </Button>
                                        <Button className='form-button' onClick={() => {goToProjectPage(item.id)}}>
                                            <FontAwesomeIcon icon={faFolderOpen} /> Open
                                        </Button>
                                    </div>
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

export default CardProject;