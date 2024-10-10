import React from 'react';
import { Button, Col, Container, Row } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFolderOpen, faTrash } from '@fortawesome/free-solid-svg-icons';

function CardProject({ listProjects, goToProjectPage, handleDeleteProject}) {

    return (
        <Container className="mt-4">
            <Row>
                {listProjects.map((item, index) => {
                    return (
                        <Col xs={12} sm={6} md={4} lg={3} key={index} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                            <Card className="mb-4 card-projects" style={{ width: '200rem' }}>
                                <Card.Body>
                                    <Card.Title>{item.name}</Card.Title>
                                    <div style={{ display: 'flex', justifyContent: 'center', gap: '10px' }}>
                                        <Button className='form-button red-button' onClick={() => {handleDeleteProject(item.name)}}>
                                            <FontAwesomeIcon icon={faTrash} /> Remove
                                        </Button>
                                        <Button className='form-button' onClick={() => {goToProjectPage(item.name)}}>
                                            <FontAwesomeIcon icon={faFolderOpen} /> Open
                                        </Button>
                                    </div>
                                </Card.Body>
                                <Card.Footer className="text-white text-center bg-dark">Documents: {item.num_documents}</Card.Footer>
                            </Card>
                        </Col>
                    );
                })}
            </Row>
        </Container>
    );    
}

export default CardProject;