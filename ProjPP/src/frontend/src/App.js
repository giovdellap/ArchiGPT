import React from 'react';
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
import Home from './pages/Home';
import ProjectOverview from './pages/ProjectOverview';

function App() {

  return (
    <div className="App">
			<Router>
				<Container className="d-flex justify-content-center align-items-center app-container" style={{ minHeight: '90vh' }}>
					<Routes>
						<Route path="/" element={<Home />} />
						<Route path="/project/:id" element={<ProjectOverview/>} />
					</Routes>
				</Container>
			</Router>
    </div>
  );
}

export default App;
