import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'jquery/dist/jquery.min.js';
import 'bootstrap/dist/js/bootstrap.min.js';
import {
	BrowserRouter as Router,
	Routes,
	Route,
} from "react-router-dom";
import { Container } from 'react-bootstrap';
import './App.css';

function App() {

	const [message, setMessage] = useState("");

	const buildApiUrl = () => {
		let apiUrl = 'http://localhost:5001/?';
		return apiUrl;
	};

	const apiUrl = buildApiUrl();

	useEffect(() => {
		fetch(apiUrl)
			.then((response) => {
				if (response.status === 200) {
					console.log(response.json())
					return response.json();
				}
			})
			.then((data) => {
				console.log(data)
				setMessage(data);
			})
			.catch((error) => {
				console.error("Error fetching data:", error);
			});
	});

  function NotFound() {
		return (
			<Container className="d-flex flex-column justify-content-center align-items-center" id="not-found-container">
				<h1>Fronted is working</h1>
			</Container>
		);
	}

  return (
    <div className="App">
			<Router>
				<Container className="d-flex justify-content-center align-items-center app-container" style={{ minHeight: '90vh' }}>
					<Routes>
						<Route path="*" element={<NotFound />} />
					</Routes>
				</Container>
			</Router>
    </div>
  );
}

export default App;
