import "./css/App.css";
import "./css/Login.css";

import React, { useEffect, useState } from "react";
import { Link, useNavigate, Navigate } from "react-router-dom";
import TopBar from "./components/TopBar";
import Alert from "./components/Alert.tsx";
import NavBar from "./components/NavBar";

import UserIcon from "./components/UserIcon";
import Notifications from "./components/Notifications";
import axios from "axios";

import { address, flask_port } from "./components/Endpoint";
import FetchEnpoint from "./components/EndpointFinder";
const endpoint = (await FetchEnpoint()) + "/home/newteam";

function CreateTeam() {
  useEffect(() => {
    //useEffect viene chiamato a fine render del component
    handleRequestFailed();
    handleMissingFields();
  }, []);

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  const username = localStorage.getItem("LoggedUser");
  const navigate = useNavigate();
  const [teams, setTeams] = useState([]);

  const ToggleDisplayAgenda = () => {
    localStorage.setItem("ProfileData", "false");
    navigate("/home");
  };

  const handleBack = () => {
    navigate("/home/teams");
  };
  const requestFailed =
    sessionStorage.getItem("request_failed_alert") === "true";
  const handleRequestFailed = () => {
    sessionStorage.setItem("request_failed_alert", "false");
  };
  const missingFields =
    sessionStorage.getItem("missing_field_alert") === "true";
  const handleMissingFields = () => {
    sessionStorage.setItem("missing_field_alert", "false");
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Set form values
    localStorage.setItem("name", name);
    localStorage.setItem("description", description);

    if (name !== "") {
      try {
        // Send a POST request to the /signup endpoint of the Flask server
        const response = await axios.post(endpoint, {
          username,
          name,
          description,
        });

        // If the signup has been successfully performed, then redirect the user to the Login page.
        if (response.status === 200) {
          localStorage.removeItem("name");
          localStorage.removeItem("description");
          localStorage.setItem("teamCreated_alert", "true");
          // Redirect
          navigate("/home/teams");
        }
      } catch (error) {
        // Request failed
        sessionStorage.setItem("request_failed_alert", "true");
        window.location.replace(window.location.href); // For alert purposes only
        console.log("[ERROR] Request failed: " + error);
      }
    } else {
      // There is at least one mandatory field that has not been filled
      sessionStorage.setItem("missing_field_alert", "true");
      window.location.replace(window.location.href); // For alert purposes only
      console.log("All the fields must be filled!");
    }
  };

  return (
    <div className="App">
      {!username && <Navigate to="/login" />}
      <div className="TopBar">
        <div className="BarHeading">
          <Link
            to="/"
            style={{ color: "inherit", textDecoration: "inherit" }}
            onClick={ToggleDisplayAgenda}
          >
            Teamify
          </Link>
        </div>

        <div className="Buttons">
          <Notifications></Notifications>
          <UserIcon></UserIcon>
        </div>
      </div>
      <div className="SideContainer">
        <NavBar></NavBar>
        <div className="container">
          {requestFailed && (
            <Alert onClick={handleRequestFailed} state="danger">
              Error: there already exist a team with same name and description
            </Alert>
          )}
          {missingFields && (
            <Alert onClick={handleMissingFields} state="danger">
              Error: You must insert the team name
            </Alert>
          )}
          <div className="row mt-4 mb-2" style={{ textAlign: "left" }}>
            <div className="col">
              <h1>Teams</h1>
            </div>
            <div
              className="col mt-3"
              style={{ textAlign: "right", cursor: "pointer" }}
              onClick={handleBack}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="25"
                height="25"
                fill="currentColor"
                className="bibi-arrow-90deg-left"
                viewBox="0 0 16 16"
              >
                <path
                  fillRule="evenodd"
                  d="M1.146 4.854a.5.5 0 0 1 0-.708l4-4a.5.5 0 1 1 .708.708L2.707 4H12.5A2.5 2.5 0 0 1 15 6.5v8a.5.5 0 0 1-1 0v-8A1.5 1.5 0 0 0 12.5 5H2.707l3.147 3.146a.5.5 0 1 1-.708.708l-4-4z"
                />
              </svg>
            </div>
          </div>
          <div className="CenterContainer">
            <div className="CardL align-items-center justify-content-center">
              <div className="CardHeading">
                <form onSubmit={handleSubmit}>
                  <div className="InputEntry">
                    <div className="InputLabel">Team name</div>
                    <input
                      className="InputField"
                      type="text"
                      placeholder=""
                      id="name"
                      defaultValue={localStorage.getItem("name")}
                      onChange={(event) => setName(event.target.value)}
                    ></input>
                  </div>
                  <div className="InputEntry">
                    <div className="InputLabel">Team description</div>
                    <input
                      className="InputField"
                      type="text"
                      placeholder="(optional)"
                      id="surname"
                      defaultValue={localStorage.getItem("description")}
                      onChange={(event) => setDescription(event.target.value)}
                    ></input>
                  </div>

                  <hr />
                  <input type="submit" value={"Create"} id="Login"></input>
                </form>
              </div>
            </div>
            <div className="mt-5" style={{ textAlign: "right" }}></div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CreateTeam;
