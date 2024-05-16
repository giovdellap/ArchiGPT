import React, { useState, useEffect } from 'react';
import SystemOverviewTab from '../components/systemOverviewTab';
import GenerationHandler from '../components/generationHandler';
import ProjectHeader from '../components/projectHeader';

function ProjectOverview() {
	const projectName = window.location.pathname.split('/').pop();
	const [projectStatus, setProjectStatus] = useState([]);
	const [projectSystem, setProjectSystem] = useState([]);
	const [systemSelected, setSystemSelected] = useState("");
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
			<GenerationHandler messageSystem={messageSystem} />
			</div>
      	</div>
	  </div>
	);
}

export default ProjectOverview;
