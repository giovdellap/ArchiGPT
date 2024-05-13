import React, { useState, useEffect } from 'react';
import FlowOverview from '../components/flowOverview';
import GenerationHandler from '../components/generationHandler';

function Home() {
	const [data, setData] = useState(null);

	const buildApiUrl = () => {
		let apiUrl = 'http://localhost:5001/assistant';

		return apiUrl;
	};

	const apiUrl = buildApiUrl();

	useEffect(() => {
		fetch(apiUrl)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				}
			})
			.then((data) => {
				setData(data);
			})
			.catch((error) => {
				console.error("Error fetching data:", error);
			});
	}, [apiUrl]);


	return (
		<div style={{ display: 'flex', height: '100vh' }}>
		<div style={{ flex: 1, borderRight: '1px solid #ccc' }}>
		  <FlowOverview />
		</div>
		<div style={{ flex: 1 }}>
		  <GenerationHandler />
		</div>
	  </div>
	);
}

export default Home;
