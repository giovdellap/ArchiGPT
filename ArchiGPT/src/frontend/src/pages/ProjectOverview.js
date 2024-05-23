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
	const [assistantSelected, setAssistantSelected] = useState({container:"",name:""});
	const [containerInfo, setContainerInfo] = useState("");
	const [generationMessage, setGenerationMessage] = useState("");
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

	function fetchContainerInfo(container_name) {

		const containerApiUrl = `http://localhost:5001/generation/getContainer` ;

		fetch(containerApiUrl + `?project_name=${projectName}&container_name=${container_name}`)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					throw new Error('Failed to fetch container info');
				}
			})
			.then((data) => {
				setContainerInfo(data)
			})
			.catch((error) => {
				console.error('Error fetching container info: ', error);
			});
	};

	function updateMessage(){

		if(systemSelected !== ""){
			projectSystem.forEach(system => {
				if (system.name === systemSelected) {
					setGenerationMessage(system.message);
				}
			});
		}
		
		if(assistantSelected.name !== "") {
			if(assistantSelected.name === "ContainerDescriptionGenerator")
				setGenerationMessage(containerInfo.ContainerDescriptionGenerator)
			if(assistantSelected.name === "ContainerSpecificationGenerator")
				setGenerationMessage(containerInfo.ContainerSpecificationGenerator)
		}

	}

	const handleFileUpload = (event) => {
        const uploadedFile = event.target.files[0];
        setFile(uploadedFile);
    };

	const handleGenerate = () => {

        setShowLoadingScreen(true)
		var generateApiUrl = ''
        const formData = new FormData();
		formData.append('project_name', projectName);

		if(systemSelected !== ""){
			generateApiUrl = 'http://localhost:5001/generation/generateSystem';
			formData.append('assistant', systemSelected);
			if (systemSelected === 'ContainerDesigner'){
				formData.append('userstories', file);
			}
		} else {
			generateApiUrl = 'http://localhost:5001/generation/generateContainer';
			formData.append('assistant', assistantSelected.name);
			formData.append('container', assistantSelected.container);
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
				setGenerationMessage(generationResult.content)
				setShowLoadingScreen(false)
			})
			.catch((error) => {
				window.alert('Failed to generate document');
				console.error('Failed to generate document:', error);
			});
      };



    useEffect(() => {
		fetchProjectSystem()
		fetchProjectStatus()
		updateMessage()
	// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [systemSelected, assistantSelected]);


	return (
	<div>
		<ProjectHeader projectName={projectName}/>
		<div style={{ display: 'flex', paddingTop: '80px' }}>
			<div style={{ flex: 1, borderRight: '1px solid #ccc' }}>
			<SystemOverviewTab projectStatus={projectStatus} 
				setSystemSelected={setSystemSelected} 
				setAssistantSelected={setAssistantSelected}
				fetchContainerInfo={fetchContainerInfo}
			/>
			</div>
			<div style={{ flex: 2 }}>
				{showLoadingScreen ?
					<LoadingScreen messageText="Wait for document generation ..."/>
						:
					<GenerationHandler 
						generationMessage={generationMessage} 
						handleGenerate={handleGenerate} 
						systemSelected={systemSelected} 
						assistantSelected={assistantSelected}
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
