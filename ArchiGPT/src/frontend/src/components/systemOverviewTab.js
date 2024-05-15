import React from 'react';
import { Container, Row, Accordion, Card } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faExclamationTriangle, faTimesCircle } from '@fortawesome/free-solid-svg-icons';


function SystemOverviewTab({ projectStatus }) {

    const containerStatus  = projectStatus.containers
    const systemStatus  = projectStatus.system

    const getStatusIcon = (status) => {
        switch (status) {
            case "OK":
                return <FontAwesomeIcon icon={faCheckCircle} className="text-success" />;
            case "PROGRESS":
                return <FontAwesomeIcon icon={faExclamationTriangle} className="text-warning" />;
            case "NO":
                return <FontAwesomeIcon icon={faTimesCircle} className="text-danger" />;
            default:
                return null;
        }
    };

    const getStatusColorClass = (status) => {
        switch (status) {
            case "OK":
                return "bg-success";
            case "PROGRESS":
                return "bg-warning";
            case "NO":
                return "bg-danger";
            default:
                return "";
        }
    };


    return (
        <Container className="mt-4">

            <Row style={{ marginBottom: '20px' }}>
                {systemStatus ? systemStatus.map((system) => (
                    <div key={system.id}>
                        <Card>
                            <Card.Body>
                                {system.name} {getStatusIcon(system.status)}
                            </Card.Body>
                        </Card>
                    </div>
                    ))  
                    : <Container>No system status available</Container>}
            </Row>

            <Row>
                <Accordion defaultActiveKey="0">
                    {containerStatus ? containerStatus.map((container, containerIndex) => (
                        <Accordion.Item eventKey={containerIndex} key={containerIndex}>
                            <Accordion.Header >
                                {container.name}
                            </Accordion.Header>
                            <Accordion.Body>
                                <Accordion defaultActiveKey="0">
                                    {container.services.map((service, serviceIndex) => (
                                        <Accordion.Item eventKey={serviceIndex} key={serviceIndex}>
                                            <Accordion.Header>
												{service.name}
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <Card className={getStatusColorClass(service.datastructures)}>
                                                    <Card.Title>Datastructures</Card.Title>
                                                </Card>
                                                <Card className={getStatusColorClass(service.endpoints)}>
                                                    <Card.Title>Endpoints</Card.Title>
                                                </Card>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    ))}
                                </Accordion>
                                    <Card className={getStatusColorClass(container.containerDescription)}>
										<Card.Title>Description</Card.Title>
							 		</Card>
									<Card className={getStatusColorClass(container.containerTechnologies)}>
										<Card.Title>Technologies</Card.Title>
							 		</Card>
                            </Accordion.Body>
                        </Accordion.Item>
                    )) 
                    : <Container>No containers status available</Container>}
                </Accordion>
            </Row>

        </Container>
    );
}

export default SystemOverviewTab;
