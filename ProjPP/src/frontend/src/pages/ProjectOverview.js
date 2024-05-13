import React, { useState, useEffect } from 'react';
import SystemOverviewTab from '../components/systemOverviewTab';
import GenerationHandler from '../components/generationHandler';
import ProjectHeader from '../components/projectHeader';

function ProjectOverview() {
	const [data, setData] = useState({});

    function fetchProjectInfo() {
		const projectApiUrl = 'http://localhost:5001/assistant' ;
		fetch(projectApiUrl)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					throw new Error('Failed to fetch project');
				}
			})
			.then((projectData) => {
				console.log(projectData.list_assistants)
				setData(projectData.list_assistants)
			})
			.catch((error) => {
				console.error('Error fetching project:', error);
			});
	};

    useEffect(() => {
        fetchProjectInfo()
	}, []);


	return (
	<div>
		<ProjectHeader />
		<div style={{ display: 'flex', paddingTop: '80px' }}>
			<div style={{ flex: 1, borderRight: '1px solid #ccc' }}>
			<SystemOverviewTab projectData={data} />
			</div>
			<div style={{ flex: 2 }}>
			<GenerationHandler />
			</div>
      	</div>
	  </div>
	);
}

export default ProjectOverview;
