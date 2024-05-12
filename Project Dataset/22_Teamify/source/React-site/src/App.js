import { BrowserRouter, Routes, Route } from "react-router-dom";
import React, { useState, useEffect } from "react";

import Login from "./Login";
import SignUp from "./SignUp";
import Home from "./Home";
import Reset from "./Reset";
import HomeLoggedIn from "./HomeLoggedIn";
import NewTask from "./NewTask";
import TeamView from "./TeamView";
import Invite from "./components/Invite";
import Teams from "./Teams";
import Task from "./NewTask.js";
import CreateTeam from "./CreateTeam";
import FetchEnpoint from "./components/EndpointFinder";
import ForgotPassword from "./ForgotPassword";

function App() {
  //data è la variabile dello stato, setData è la funzione per settare il suo valore
  const [data, setData] = useState([{}]);
  //use effect viene usato per ottenere la route di interesse. La response delle api
  //viene converita in json e poi caricate dentro la variabili data
  useEffect(() => {
    fetch(FetchEnpoint())
      .then((res) => res.json())
      .then((data) => {
        setData(data);
        console.log(data);
      });
  }, []);
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="login" element={<Login />} />
        <Route path="signup" element={<SignUp />} />
        <Route path='forgotPassword' element={<ForgotPassword />} />
        <Route path="reset" element={<Reset />} />
        <Route path="home" element={<HomeLoggedIn />} />
        <Route path="home/newtask" element={<NewTask />} />
        <Route path="teamview" element={<TeamView />} />
        <Route path="invite" element={<Invite />} />
        <Route path="home/teams" element={<Teams />} />
        <Route path="home/tasks/edittask" element={<Task />} />
        <Route path="home/createteam" element={<CreateTeam />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
