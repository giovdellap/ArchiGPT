import React, { useState } from 'react';
import { Form, Button, Modal, Tooltip, OverlayTrigger, ListGroup } from 'react-bootstrap';

// List of projects with descriptions
const projectOptions = [
  { name: 'OneSport', description: 'The project strives to serve as a one-stop destination for comprehensive sports news, event updates, and a convenient platform for purchasing tickets to various sporting events.' },
  { name: 'NFFH', description: 'Not Far(m) From Home is a platform that allows a direct interaction between local farmers and consumers, with the main purpose of being “km 0”.' },
  { name: 'EFarmers', description: 'E-Farmers is an e-commerce website that aims to connect local farmers with customers who are interested in buying fresh and locally sourced products.' },
  { name: 'RentYourExpert', description: 'RentYourExpert is a platform that connects individuals who require expert services with skilled professionals across various fields.' },
  { name: 'CDC', description: 'This is a project for building an E-commerce platform.' },
  { name: 'EventTicket', description: 'A web application providing a set of services aimed at the management and proposals of events and distribution of tickets.' },
  { name: 'Teamify', description: 'Teamify is a versatile software solution designed to simplify personal task management and promoved group collaboration.' },
  { name: 'RecipeCove', description: 'The RecipeCove project consists of a web application about recipes, food and cooking.' },
];

const ProjectForm = ({ handleGenerationProject, generationResult }) => {
  const [selectedProject, setSelectedProject] = useState('');
  const [generateAll, setGenerateAll] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (selectedProject || generateAll) {
      setShowModal(true);
    } else {
      alert('Please select a project to generate.');
    }
  };

  const handleConfirm = () => {
    setShowModal(false);
    handleGenerationProject(selectedProject)
    console.log(selectedProject, generationResult)
    saveFile(generateAll ? 'all_projects.json' : `${selectedProject}.json`, JSON.stringify(generationResult));
  };

  const saveFile = (fileName, fileContent) => {
    const blob = new Blob([fileContent], { type: 'application/json' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = fileName;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  const renderTooltip = (description) => (
    <Tooltip id="button-tooltip">{description}</Tooltip>
  );

  const handleProjectSelection = (projectName) => {
    setSelectedProject(projectName);
    setGenerateAll(false);
  };

  return (
    <div>
      <Form onSubmit={handleSubmit} style={{ border: '1px solid grey', padding: '20px', maxWidth: '400px' }}>
        <Form.Group controlId="projectSelection">
          <Form.Label>Ready-To-Generate Projects</Form.Label>
          <ListGroup>
            {projectOptions.map((project, index) => (
              <OverlayTrigger
                key={index}
                placement="right"
                delay={{ show: 250, hide: 400 }}
                overlay={renderTooltip(project.description)}
              >
                <ListGroup.Item
                  action
                  onClick={() => handleProjectSelection(project.name)}
                  active={selectedProject === project.name}
                >
                  {project.name}
                </ListGroup.Item>
              </OverlayTrigger>
            ))}
            <ListGroup.Item
              action
              onClick={() => {
                setGenerateAll(true);
                setSelectedProject('');
              }}
              active={generateAll}
            >
              Generate All Projects
            </ListGroup.Item>
          </ListGroup>
        </Form.Group>
      </Form>

      {/* Modal for confirmation */}
      <Modal show={showModal} onHide={() => setShowModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Confirm Project Generation</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {generateAll 
            ? 'All projects will be fully generated. The process is estimated to take approximately 40-50 minutes. Proceed?' 
            : `The project "${selectedProject}" will be fully generated. The process is estimated to take approximately 3-5 minutes. Proceed?`}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleConfirm}>
            Proceed
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default ProjectForm;
