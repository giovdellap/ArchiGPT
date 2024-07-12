import React from 'react';
import { Container, Row, Card, Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faCircle, faTimesCircle } from '@fortawesome/free-solid-svg-icons';


function SystemOverviewTab({ projectStatus, setSystemSelected}) {

    const systemStatus  = projectStatus.system

    const getStatusIcon = (status) => {
        switch (status) {
            case "OK":
                return <FontAwesomeIcon icon={faCheckCircle} className="text-success" />;
            case "NEXT":
                return <FontAwesomeIcon icon={faCircle}  color='orange' className="text-next" />;
            case "NO":
                return <FontAwesomeIcon icon={faTimesCircle} className="text-danger" />;
            default:
                return null;
        }
    };


    return (
        <Container className="mt-4">

            <Row style={{ marginBottom: '20px' }}>
                {systemStatus ? systemStatus.map((system) => (
                    <div key={system.name}>
                            <Card>
                                <Button
                                    variant="light"
                                    onClick={() => {setSystemSelected(system.name)}}
                                    disabled={system.status === "NO"} 
                                >
                                    <Card.Body>
                                        {system.name} {getStatusIcon(system.status)}
                                    </Card.Body>
                                </Button>
                            </Card>
                    </div>
                    ))  
                    : <Container>No system status available</Container>}
            </Row>

        </Container>
    );
}

export default SystemOverviewTab;
