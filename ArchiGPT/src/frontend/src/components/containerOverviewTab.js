import React from 'react';
import { Container, Row, Accordion, Card, Button } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle, faCircle, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import ServiceOverviewTab from './serviceOverviewTab';


function ContainerOverviewTab({ projectStatus, setSystemSelected, setContainerSelected, setServiceSelected, containerInfo, fetchContainerInfo }) {

    const containerStatus  = projectStatus.containers

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

    const resetItemSelected = (itemType, itemSelected) => {
        if(itemType === "Container"){
            setContainerSelected({container:containerInfo.name, assistant:itemSelected}); 
            setSystemSelected("")
            setServiceSelected({service: "", assistant: ""})
        }
        if(itemType === "Service"){
            setContainerSelected({container:containerInfo.name, assistant:"MicroServices"}); 
            setSystemSelected("")
            setServiceSelected({service: itemSelected.service, assistant: itemSelected.assistant})
        }
    }


    return (
        <Container className="mt-4">

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
                                        onClick={() => {resetItemSelected("Container", "ContainerDescriptionGenerator")}}
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
                                        onClick={() => {resetItemSelected("Container", "ContainerSpecificationGenerator")}}
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
                                        onClick={() => {resetItemSelected("Container", "MicroServices")}}
                                        disabled={container.MicroServices === "NO"} 
                                    >
                                        <Card.Body>
                                            MicroServices Overview {getStatusIcon(container.MicroServices)}
                                        </Card.Body>
                                    </Button>
                                </Card>
                                {container.name === containerInfo.name && container.services.length !== 0 ? 
                                    <ServiceOverviewTab 
                                        services={container.services}
                                        getStatusIcon={getStatusIcon}
                                        servicesInfo={containerInfo.services}
                                        resetItemSelected = {resetItemSelected}
                                    /> : <></>
                                }
                            </Accordion.Body>
                        </Accordion.Item>
                    )) 
                    : <Container>No containers status available</Container>}
                </Accordion>
            </Row>

        </Container>
    );
}

export default ContainerOverviewTab;
