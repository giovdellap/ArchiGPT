import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import CardProject from '../components/cardProject';
import CreateProjectTab from '../components/createProjectTab';
import { useNavigate } from 'react-router-dom';


function Home() {
	const navigate = useNavigate();
    const [listProjects, setListProjects] = useState([]);

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

	function GoToProjectPage(projectName) {
		navigate(`/project/${projectName}`);
	}

    useEffect(() => {
        fetchprojects()
	}, []);


    return (
			<div>
				<div style={{ margin: '10px' }} />
				<CreateProjectTab fetchprojects={fetchprojects}/>
				{listProjects && listProjects.length > 0 ? (
						<>
							<CardProject listProjects={listProjects} goToProjectPage={GoToProjectPage} handleDeleteProject={handleDeleteProject}/>
						</>
					) : (
						<Container className="mt-4"><h4>No projects available</h4></Container>
					)}
			</div>
    );
}

export default Home;