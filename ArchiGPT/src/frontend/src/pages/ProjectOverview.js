import React, { useState, useEffect } from 'react';
import SystemOverviewTab from '../components/systemOverviewTab';
import GenerationHandler from '../components/generationHandler';
import ProjectHeader from '../components/projectHeader';

function ProjectOverview() {
	const projectName = window.location.pathname.split('/').pop();
	const [projectStatus, setProjectStatus] = useState([]);
	const [projectSystem, setProjectSystem] = useState([]);
	const [systemSelected, setSystemSelected] = useState("ContainerDesigner");
	const [messageSystem, setMessageSystem] = useState("");


    function fetchProjectStatus() {

		const projectApiUrl = `http://localhost:5001/project/status` ;

		fetch(projectApiUrl + `?project_name=${projectName}`)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					throw new Error('Failed to fetch project status');
				}
			})
			.then((data) => {
				setProjectStatus(data)
			})
			.catch((error) => {
				console.error('Error fetching project status:', error);
			});
	};

	function fetchProjectSystem() {

		const projectApiUrl = `http://localhost:5001/project/system` ;

		fetch(projectApiUrl + `?project_name=${projectName}`)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					throw new Error('Failed to fetch project system');
				}
			})
			.then((data) => {
				setProjectSystem(data)
			})
			.catch((error) => {
				console.error('Error fetching project system:', error);
			});
	};

	function updateMessage(){
		projectSystem.forEach(system => {
			if (system.name === systemSelected) {
				setMessageSystem(system.message);
			}
		});
	}

	const handleGenerate = () => {
        
        const generateApiUrl = 'http://localhost:5001/generation/generateSystem';
    
        const formData = new FormData();
        formData.append('project_name', projectName);
        formData.append('assistant', systemSelected);

        fetch(generateApiUrl, {
            method: 'POST',
            mode: 'cors',
            body: formData
        })
            .then((response) => {
              if (response.status === 200) {
                console.log('Message sent:', { projectName, systemSelected });
                // window.location.reload();
              } else {
                window.alert('Failed to generate document');
                throw new Error('Failed to generate document');
              }
          })
          .catch((error) => {
            window.alert('Failed to generate document');
            console.error('Failed to generate document:', error);
          });
      };


    useEffect(() => {
        fetchProjectStatus()
		fetchProjectSystem()
		updateMessage()
	}, [systemSelected]);


	return (
	<div>
		<ProjectHeader projectName={projectName}/>
		<div style={{ display: 'flex', paddingTop: '80px' }}>
			<div style={{ flex: 1, borderRight: '1px solid #ccc' }}>
			<SystemOverviewTab projectStatus={projectStatus} setSystemSelected={setSystemSelected} />
			</div>
			<div style={{ flex: 2 }}>
			<GenerationHandler messageSystem={messageSystem} handleGenerate={handleGenerate}/>
			</div>
      	</div>
	  </div>
	);
}

export default ProjectOverview;
