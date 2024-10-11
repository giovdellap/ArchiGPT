import React, { useEffect, useState } from 'react';
import { Col } from 'react-bootstrap';
import Card from 'react-bootstrap/Card';
import CardProject from '../components/cardProject';
import CreateProjectTab from '../components/createProjectTab';
import { useNavigate } from 'react-router-dom';
import ProjectForm from '../components/projectForm';
import LoadingScreen from '../components/loadingScreen';


function Home() {
	const navigate = useNavigate();
    const [listProjects, setListProjects] = useState([]);
	const [showLoadingScreen, setShowLoadingScreen] = useState(false);

    function fetchprojects() {
		const projectsApiUrl = 'http://localhost:5001/project' ;
		fetch(projectsApiUrl)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					throw new Error('Failed to fetch projects');
				}
			})
			.then((projectsData) => {
				setListProjects(projectsData.list_projects)
			})
			.catch((error) => {
				console.error('Error fetching projects:', error);
			});
	};

	function handleDeleteProject (projectName) {
        const deleteApiUrl = 'http://localhost:5001/project/';

        fetch(deleteApiUrl, {
            method: 'DELETE',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "project_name": projectName
            })
        })
            .then((response) => {
              if (response.status === 200) {
                console.log('Deleted:', { projectName });
				fetchprojects()
              } else {
                window.alert('Failed to delete project');
                throw new Error('Failed to delete project');
              }
          })
          .catch((error) => {
            window.alert('Failed to delete project');
            console.error('Failed to delete project:', error);
          });

    };

	function handleGenerationProject (projectName, generateAll) {
		setShowLoadingScreen(true)
		let generationApiUrl = ''

        if (generateAll) generationApiUrl = 'http://localhost:5003/metrics/generateAllProjects';
		else generationApiUrl = 'http://localhost:5003/metrics/generateProject';
		const formData = new FormData();
		formData.append('project_name', projectName);

        fetch(generationApiUrl, {
            method: 'POST',
            mode: 'cors',
            body: formData
        })
            .then((response) => {
              if (response.status === 200) {
				return response.json();
              } else {
				return response.json().then((errorData) => {
					window.alert('Failed to generate document: ' + errorData.message);
					throw new Error('Failed to generate document: ' + errorData.message);
				});
              }
          	})
			.then((generationResult) => {
				fetchprojects()
				setShowLoadingScreen(false)
				saveFile(generateAll ? 'all_projects.json' : `${projectName}.json`, JSON.stringify(generationResult));
			})
			.catch((error) => {
				setShowLoadingScreen(false);
				window.alert('Failed to generate project: ' + error.message);
				console.error('Failed to generate project:', error);
			});
    };

	function saveFile(fileName, fileContent){
		const blob = new Blob([fileContent], { type: 'application/json' });
		const link = document.createElement('a');
		link.href = URL.createObjectURL(blob);
		link.download = fileName;
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);
	};

	function GoToProjectPage(projectName) {
		navigate(`/project/${projectName}`);
	}

    useEffect(() => {
        fetchprojects()
	}, []);


	return (
		<div style={{ display: 'flex', padding: '300px' }}>

		  <div style={{ flex: '1', position: 'sticky', top: '20px', height: 'fit-content', width: '300px' }}>
			<ProjectForm handleGenerationProject={handleGenerationProject}/>
		  </div>

		  <div style={{ flex: '1', paddingLeft: '200px' }}>
		  	<div style={{ flex: '1', position: 'sticky', top: '20px', height: 'fit-content', paddingLeft:'460px' }}>
				<CreateProjectTab fetchprojects={fetchprojects} />
			</div>
			
			<div style={{ marginTop: '20px' }}>
			  {listProjects && listProjects.length > 0 ? (
				<CardProject 
				  listProjects={listProjects} 
				  goToProjectPage={GoToProjectPage} 
				  handleDeleteProject={handleDeleteProject}
				/>
			  ) : (
				<Col xs={12} sm={6} md={4} lg={12} style={{ display: 'flex', justifyContent: 'right', alignItems: 'right' }}>
					<Card className="mb-4 card-projects" style={{ width: '83rem'}}>
						No projects available
					</Card>
				</Col>
			  )}
			</div>
		  </div>

		  {showLoadingScreen ? <LoadingScreen messageText="Wait for document generation ..."/> : <></>}
	
		</div>
	);	
}

export default Home;