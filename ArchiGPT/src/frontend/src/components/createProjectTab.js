import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { Button, Form } from 'react-bootstrap';

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
            return response.json().then((errorData) => {
              window.alert('Failed to generate document: ' + errorData.message);
            });
          }
      })
      .catch((error) => {
				window.alert('Failed to generate project: ' + error.message);
				console.error('Failed to generate project:', error);
      });
  };

  return (
    <Form onSubmit={handleSubmit} style={{ border: '1px solid grey', padding: '20px', maxWidth: '400px'}}>
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
        <FontAwesomeIcon icon={faPlus} /> Create New Project
      </Button>
    </Form>
  );
}

export default CreateProjectTab;
