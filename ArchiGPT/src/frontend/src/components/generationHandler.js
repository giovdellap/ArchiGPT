import React from 'react';
import GenerationView from './generationView';
import { Button, Card, Container } from 'react-bootstrap';

function GenerationHandler({ messageSystem, handleGenerate }) {

    return (
        <div className="chat-container">
            {messageSystem && messageSystem !== "" ?          
                <div className="chat-messages">
                    <GenerationView messageSystem={messageSystem}/>
                </div>
            : 
                <div className="chat-messages">
                    <GenerationView messageSystem="No generation found"/>
                    <Container style={{ margin: '150px', width: '250px' }}>
                        <Card>
                            <Button
                                variant="light"
                                onClick={() => handleGenerate()}
                            >
                                <Card.Body>
                                    Generate Document
                                </Card.Body>
                            </Button>
                        </Card>
                    </Container>
                </div>
            }
        </div>
    );
}

export default GenerationHandler;
