import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { Button, Col, Form } from 'react-bootstrap';

function CreateProjectTab({ fetchprojects }) {
  const [projectName, setProjectName] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitted:', projectName);
    
    const createApiUrl = 'http://localhost:5001/project/';

    const formData = new FormData();
    formData.append('project_name', projectName);

    fetch(createApiUrl, {
        method: 'POST',
        mode: 'cors',
        body: formData
    })
        .then((response) => {
          if (response.status === 200) {
            console.log('Created:', projectName);
            fetchprojects()
            setProjectName("")
          } else {
            window.alert('Failed to create project');
            throw new Error('Failed to create project');
          }
      })
      .catch((error) => {
        window.alert('Failed to create project');
        console.error('Failed to create project:', error);
      });
  };

  return (
    <Col style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      <Form onSubmit={handleSubmit} style={{ border: '1px solid grey', padding: '20px' }}>
        <Form.Group controlId="projectName">
          <Form.Label>Project Name</Form.Label>
          <Form.Control 
            type="text" 
            placeholder="Enter project name" 
            value={projectName} 
            onChange={(e) => setProjectName(e.target.value)} 
          />
        </Form.Group>

      <div style={{ margin: '10px' }} />
        <Button variant="success" type="submit">
          <FontAwesomeIcon icon={faPlus} /> Add Project
        </Button>
      </Form>
    </Col>
  );
}

export default CreateProjectTab;
