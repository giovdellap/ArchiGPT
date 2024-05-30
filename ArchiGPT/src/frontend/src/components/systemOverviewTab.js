import React from 'react';
import { Container, Row, Accordion, Card, Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faCircle, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import ServiceOverviewTab from './serviceOverviewTab';


function SystemOverviewTab({ projectStatus, setSystemSelected, setAssistantSelected, fetchContainerInfo }) {

    const containerStatus  = projectStatus.containers
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
                                    onClick={() => {setSystemSelected(system.name); setAssistantSelected({container:"",name:""})}}
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

            <Row>
                <Accordion defaultActiveKey="0" onSelect={(eventKey) => {
                    if (eventKey !== null) {
                        fetchContainerInfo(containerStatus[eventKey].name);
                    }
                }}>
                    {containerStatus ? containerStatus.map((container, containerIndex) => (
                        <Accordion.Item eventKey={containerIndex} key={container.name}>
                            <Accordion.Header >
                                {container.name}
                            </Accordion.Header>
                            <Accordion.Body>
                                <Card>
                                    <Button
                                        variant="light"
                                        onClick={() => {setAssistantSelected({container:container.name, name:"ContainerDescriptionGenerator"}); setSystemSelected("")}}
                                        disabled={container.ContainerDescriptionGenerator === "NO"} 
                                    >
                                        <Card.Body>
                                            Description {getStatusIcon(container.ContainerDescriptionGenerator)}
                                        </Card.Body>
                                    </Button>
                                </Card>
                                <Card>
                                    <Button
                                        variant="light"
                                        onClick={() => {setAssistantSelected({container:container.name, name:"ContainerSpecificationGenerator"}); setSystemSelected("")}}
                                        disabled={container.ContainerSpecificationGenerator === "NO"} 
                                    >
                                        <Card.Body>
                                            Specification {getStatusIcon(container.ContainerSpecificationGenerator)}
                                        </Card.Body>
                                    </Button>
                                </Card>
                                <Card>
                                    <Button
                                        variant="light"
                                        onClick={() => {setAssistantSelected({container:container.name, name:"MicroServices"}); setSystemSelected("")}}
                                        disabled={container.MicroServices === "NO"} 
                                    >
                                        <Card.Body>
                                            MicroServices Overview {getStatusIcon(container.MicroServices)}
                                        </Card.Body>
                                    </Button>
                                </Card>
                                <ServiceOverviewTab 
                                    services={container.services}
                                    getStatusIcon={getStatusIcon}
                                />
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
