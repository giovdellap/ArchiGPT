import React, { useState, useEffect } from 'react';
import SystemOverviewTab from '../components/systemOverviewTab';
import GenerationHandler from '../components/generationHandler';
import ProjectHeader from '../components/projectHeader';
import LoadingScreen from '../components/loadingScreen';
import ContainerOverviewTab from '../components/containerOverviewTab';

function ProjectOverview() {
	const projectName = window.location.pathname.split('/').pop();
	const [projectStatus, setProjectStatus] = useState([]);
	const [projectSystem, setProjectSystem] = useState([]);
	const [systemSelected, setSystemSelected] = useState("");
	const [containerSelected, setContainerSelected] = useState({container:"",assistant:""});
	const [serviceSelected, setServiceSelected] = useState({service:"",assistant:""});
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
		} else if(containerSelected.assistant !== "" && serviceSelected.service === "") {
			if(containerSelected.assistant === "ContainerDescriptionGenerator")
				setGenerationMessage(containerInfo.ContainerDescriptionGenerator)
			if(containerSelected.assistant === "ContainerSpecificationGenerator")
				setGenerationMessage(containerInfo.ContainerSpecificationGenerator)
			if(containerSelected.assistant === "MicroServices")
				setGenerationMessage(containerInfo.MicroServices)
		} else if(containerSelected.assistant !== "" && serviceSelected.service !== "") {
			if(serviceSelected.assistant === "ServiceDescriptionGenerator")
				setGenerationMessage(containerInfo.services.find(service => service.name === serviceSelected.service).description)
			if(serviceSelected.assistant === "ServiceSpecificationGenerator")
				setGenerationMessage(containerInfo.services.find(service => service.name === serviceSelected.service).ServiceSpecificationGenerator)
			if(serviceSelected.assistant === "ServiceEndpointGenerator")
				setGenerationMessage(containerInfo.services.find(service => service.name === serviceSelected.service).ServiceEndpointGenerator)
			if(serviceSelected.assistant === "ServicePageGenerator")
				setGenerationMessage(containerInfo.services.find(service => service.name === serviceSelected.service).ServicePageGenerator)
		} 

	}

	const handleFileUpload = (event) => {
        const uploadedFile = event.target.files[0];
        setFile(uploadedFile);
    };

	const handleGenerate = () => {

        setShowLoadingScreen(true)
		var generateApiUrl = 'http://localhost:5001/generation/'
        const formData = new FormData();
		formData.append('project_name', projectName);

		if(systemSelected !== ""){
			generateApiUrl = generateApiUrl + 'generateSystem';
			formData.append('assistant', systemSelected);
			if (systemSelected === 'Container Design'){
				formData.append('userstories', file);
			}
		} else if (containerSelected !== ""){
			if(serviceSelected.service !== ""){
				generateApiUrl = generateApiUrl + 'generateService';
				formData.append('container', containerSelected.container);
				formData.append('service', serviceSelected.service);
				formData.append('assistant', serviceSelected.assistant);
			} else {
				generateApiUrl = generateApiUrl + 'generateContainer';
				formData.append('assistant', containerSelected.assistant);
				formData.append('container', containerSelected.container);
			}
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
				if(systemSelected === "") fetchContainerInfo(containerSelected.container)
				setGenerationMessage(generationResult.content)
				setShowLoadingScreen(false)
			})
			.catch((error) => {
				window.alert('Failed to generate document');
				console.error('Failed to generate document:', error);
			});
    };

	const handleRegenerate = () => {

        setShowLoadingScreen(true)
		var generateApiUrl = 'http://localhost:5001/generation/'
        const formData = new FormData();
		formData.append('project_name', projectName);

		if (containerSelected !== ""){
			if(serviceSelected.service !== ""){
				generateApiUrl = generateApiUrl + 'regenerateService';
				formData.append('container', containerSelected.container);
				formData.append('service', serviceSelected.service);
				formData.append('assistant', serviceSelected.assistant);
			} else {
				generateApiUrl = generateApiUrl + 'regenerateContainer';
				formData.append('assistant', containerSelected.assistant);
				formData.append('container', containerSelected.container);
			}
		}

        fetch(generateApiUrl, {
            method: 'PATCH',
            mode: 'cors',
            body: formData
        })
            .then((response) => {
              if (response.status === 200) {
				return response.json();
              } else {
                window.alert('Failed to regenerate document');
                throw new Error('Failed to regenerate document');
              }
          	})
			.then((generationResult) => {
				fetchProjectStatus()
				if(systemSelected === "") fetchContainerInfo(containerSelected.container)
				setGenerationMessage(generationResult.content)
				setShowLoadingScreen(false)
			})
			.catch((error) => {
				window.alert('Failed to regenerate document');
				console.error('Failed to regenerate document:', error);
			});
    };


    useEffect(() => {
		fetchProjectSystem()
		fetchProjectStatus()
		updateMessage()
	// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [systemSelected, containerSelected]);


	return (
	<div>
		<ProjectHeader projectName={projectName}/>
		<div style={{ display: 'flex', paddingTop: '100px'}}>
			<div style={{ flex: 1, borderRight: '1px solid #ccc' }}>
			<SystemOverviewTab 
				projectStatus={projectStatus} 
				setSystemSelected={setSystemSelected} 
			/>
			<ContainerOverviewTab 
				projectStatus={projectStatus} 
				containerInfo={containerInfo}
				setSystemSelected={setSystemSelected} 
				setContainerSelected={setContainerSelected}
				setServiceSelected={setServiceSelected}
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
						handleRegenerate={handleRegenerate}
						systemSelected={systemSelected} 
						containerSelected={containerSelected}
						serviceSelected={serviceSelected}
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
