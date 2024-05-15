import React from 'react';
import { Container, Row, Accordion, Card } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faExclamationTriangle, faTimesCircle } from '@fortawesome/free-solid-svg-icons';


function SystemOverviewTab({ projectStatus }) {

    const projectData = projectStatus

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
            <Row>
                <Accordion defaultActiveKey="0">
                    {projectData.containers ? projectData.containers.map((container, containerIndex) => (
                        <Accordion.Item eventKey={containerIndex} key={containerIndex}>
                            <Accordion.Header >
							<div style={{ display:'flex', gap: '10px' }}>
                                {container.name}
                                {getStatusIcon(container.status)}
							</div>
                            </Accordion.Header>
                            <Accordion.Body>
                                <Accordion defaultActiveKey="0">
                                    {container.services.map((service, serviceIndex) => (
                                        <Accordion.Item eventKey={serviceIndex} key={serviceIndex}>
                                            <Accordion.Header>
											<div style={{ display:'flex', gap: '10px' }}>
												{service.name}
												{getStatusIcon(service.status)}
											</div>
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                {service.features.map((feature, featureIndex) => (
                                                	<Card key={featureIndex} className={getStatusColorClass(feature.status)}>
														<Card.Title>{feature.name}</Card.Title>
											 		 </Card>
                                                ))}
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    ))}
                                </Accordion>
									<Card className={getStatusColorClass(container.specifications)}>
										<Card.Title>Specifications</Card.Title>
							 		</Card>
									 <Card className={getStatusColorClass(container.description)}>
										<Card.Title>Description</Card.Title>
							 		</Card>
                            </Accordion.Body>
                        </Accordion.Item>
                    )) 
                    : <Container>No status available</Container>}
                </Accordion>
            </Row>
        </Container>
    );
}

export default SystemOverviewTab;
