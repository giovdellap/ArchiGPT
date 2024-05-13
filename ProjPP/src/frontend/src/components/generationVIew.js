import React, { useEffect, useState} from 'react';
import { Container } from 'react-bootstrap';

function GenerationView() {
	const [threadID, serThreadID] = useState("thread_fpJyJ2gi48NcV090bo036ln9");
	const [message, setMessage] = useState({});

	const buildApiUrl = () => {
		let apiUrl = 'http://localhost:5001/thread/' + threadID + "/message";

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
				console.log(data.last_message)
				setMessage(data.last_message);
			})
			.catch((error) => {
				console.error("Error fetching data:", error);
			});
	}, [apiUrl]);


    return (
			<div>
				<Container className="mt-4" style={{ whiteSpace: 'pre-line' }}>
					<h4>{message.content}</h4>
				</Container>
			</div>
    );
}

export default GenerationView;