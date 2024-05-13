import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import CardAssistant from './cardAssistant';

function FlowOverview() {
    const [listAssistants, setListAssistants] = useState([]);

    function fetchAssistants() {
		const assistantsApiUrl = 'http://localhost:5001/assistant' ;
		fetch(assistantsApiUrl)
			.then((response) => {
				if (response.status === 200) {
					return response.json();
				} else {
					throw new Error('Failed to fetch assistants');
				}
			})
			.then((assistantsData) => {
				console.log(assistantsData.list_assistants)
				setListAssistants(assistantsData.list_assistants)
			})
			.catch((error) => {
				console.error('Error fetching assistants:', error);
			});
	};

    useEffect(() => {
        fetchAssistants()
	}, []);


    return (
			<div>
				{listAssistants && listAssistants.length > 0 ? (
						<>
							<CardAssistant listAssistants={listAssistants} />
						</>
					) : (
						<Container className="mt-4"><h4>No assistants available</h4></Container>
					)}
			</div>
    );
}

export default FlowOverview;