import React from 'react';
import { Container, Row, Accordion, Card, Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faCircle, faTimesCircle } from '@fortawesome/free-solid-svg-icons';


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

    const getStatusColorClass = (status) => {
        switch (status) {
            case "OK":
                return "bg-success";
            case "NEXT":
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
                        <Accordion.Item eventKey={containerIndex} key={containerIndex}>
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
                                <Accordion defaultActiveKey="0">
                                    {container.services ? container.services.map((service, serviceIndex) => (
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
                                    ))
                                    : <Container>No services status available</Container> }
                                </Accordion>
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