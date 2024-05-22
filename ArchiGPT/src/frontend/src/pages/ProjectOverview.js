import React, { useState, useEffect } from 'react';
import SystemOverviewTab from '../components/systemOverviewTab';
import GenerationHandler from '../components/generationHandler';
import ProjectHeader from '../components/projectHeader';
import LoadingScreen from '../components/loadingScreen';

function ProjectOverview() {
	const projectName = window.location.pathname.split('/').pop();
	const [projectStatus, setProjectStatus] = useState([]);
	const [projectSystem, setProjectSystem] = useState([]);
	const [systemSelected, setSystemSelected] = useState("");
	const [messageSystem, setMessageSystem] = useState("");
	const [showLoadingScreen, setShowLoadingScreen] = useState(false);
	const [file, setFile] = useState(null);


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

	const handleFileUpload = (event) => {
        const uploadedFile = event.target.files[0];
        setFile(uploadedFile);
    };

	const handleGenerate = () => {

        setShowLoadingScreen(true)
        const generateApiUrl = 'http://localhost:5001/generation/generateSystem';
    
        const formData = new FormData();
        formData.append('project_name', projectName);
        formData.append('assistant', systemSelected);
		if (systemSelected === 'ContainerDesigner')
		{
			formData.append('userstories', file);
		}
        fetch(generateApiUrl, {
            method: 'POST',
            mode: 'cors',
            body: formData
        })
            .then((response) => {
              if (response.status === 200) {
				return response.json();
              } else {
                window.alert('Failed to generate document');
                throw new Error('Failed to generate document');
              }
          	})
			.then((generationResult) => {
				fetchProjectStatus()
				setMessageSystem(generationResult.content)
				setShowLoadingScreen(false)
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
	// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [systemSelected]);


	return (
	<div>
		<ProjectHeader projectName={projectName}/>
		<div style={{ display: 'flex', paddingTop: '80px' }}>
			<div style={{ flex: 1, borderRight: '1px solid #ccc' }}>
			<SystemOverviewTab projectStatus={projectStatus} setSystemSelected={setSystemSelected} />
			</div>
			<div style={{ flex: 2 }}>
				{showLoadingScreen ?
					<LoadingScreen messageText="Wait for document generation ..."/>
						:
					<GenerationHandler 
						messageSystem={messageSystem} 
						handleGenerate={handleGenerate} 
						systemSelected={systemSelected} 
						handleFileUpload={handleFileUpload}
						file={file}
					/>
				}
			</div>
      	</div>
	  </div>
	);
}

export default ProjectOverview;
