import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { Button, Form } from 'react-bootstrap';

function CreateProjectTab() {
  const [projectName, setProjectName] = useState('');
  const [editor, setEditor] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitted:', { projectName, editor });
    
    const createApiUrl = 'http://localhost:5001/project/';

    const formData = new FormData();
    formData.append('project_name', projectName);
    formData.append('editor', editor);

    fetch(createApiUrl, {
        method: 'POST',
        mode: 'cors',
        body: formData
    })
        .then((response) => {
          if (response.status === 200) {
            console.log('Created:', { projectName, editor });
            window.location.reload();
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
    <Form onSubmit={handleSubmit}>
      <Form.Group controlId="projectName">
        <Form.Label>Project Name</Form.Label>
        <Form.Control 
          type="text" 
          placeholder="Enter project name" 
          value={projectName} 
          onChange={(e) => setProjectName(e.target.value)} 
        />
      </Form.Group>

      <Form.Group controlId="editor">
        <Form.Label>Editor</Form.Label>
        <Form.Control 
          type="text" 
          placeholder="Enter editor" 
          value={editor} 
          onChange={(e) => setEditor(e.target.value)} 
        />
      </Form.Group>
	  <div style={{ margin: '10px' }} />
      <Button variant="success" type="submit">
        <FontAwesomeIcon icon={faPlus} /> Add Project
      </Button>
    </Form>
  );
}

export default CreateProjectTab;
