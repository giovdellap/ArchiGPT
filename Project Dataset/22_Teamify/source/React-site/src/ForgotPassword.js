import "./css/App.css";
import "./css/Login.css";
import { Link, useNavigate } from "react-router-dom";
import Alert from "./components/Alert.tsx";

import React, { useEffect, useState } from "react";
import axios from "axios";
import FetchEnpoint from "./components/EndpointFinder";

import { address, flask_port } from "./components/Endpoint";

var endpoint = await FetchEnpoint()+"/resetRequest";

function ForgotPassword() {
  // Form data
  const missingFields = sessionStorage.getItem("fields_alert") === "true";
  const handleMissingFields = () => {
    sessionStorage.setItem("fields_alert", "false");
  };
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [password_again, setPasswordAgain] = useState("");

  useEffect(() => {
    const timeout = setTimeout(() => {
      handleMissingFields();
    }, 1000);

    return () => clearTimeout(timeout);
  }, []);

  
  // For redirecting
  const navigate = useNavigate();

  // Handler for the event 'Submit of a form'
  const handleSubmit = async (event) => {
    event.preventDefault();

    if (username !== "") {
      try {
        // Send a POST request to the /reset endpoint of the Flask server
        const response = await axios.post(endpoint, { username });

        // If the reset has been successfully performed, then redirect the user to the Login page.
        // Sarebbe più carino un avviso di successo o qualcosa di simile
        if (response.status === 200) {
          sessionStorage.setItem("reset_success", "true");
          navigate("/login");
        } else if (response.status === 400) {
          alert(
            "[ERROR] Something bad happened: reset was unsuccessful :("
          );
        } else if (response.status === 404) {
          alert("[ERROR] The user was not found in the system.");
        }
      } catch (error) {
        // Error message
        console.log("[ERROR] Request failed: " + error);
      }
    } else {
      // There is at least one mandatory field that has not been filled
      sessionStorage.setItem("fields_alert", "true");
      window.location.replace(window.location.href); // For alert purposes only
    }
  };

  return (
    <div className="App">
      <div className="TopBar">
        <div className="BarHeading">
          <Link to="/" style={{ color: "inherit", textDecoration: "inherit" }}>
            Teamify
          </Link>
        </div>
        <div className="barSignUp">
          Don't have an account?
          <div
            className="Link"
            style={{ paddingLeft: "2%", fontSize: "large" }}
          >
            <Link to="/signup">Sign Up</Link>
          </div>
        </div>
      </div>
      <div className="SignUpBackground">
        
        <div className="CardL">
        {missingFields && (
              <Alert onClick={handleMissingFields} state="danger">
                Username field must be filled to invite!
              </Alert>
            )}
          <div className="CardHeading">Request a password reset</div>
          <form onSubmit={handleSubmit}>
            <div className="InputEntry">
              <div className="InputLabel">Username</div>
              <input
                className="InputField"
                type="text"
                placeholder="Enter your username"
                id="username"
                onChange={(event) => setUsername(event.target.value)}
              ></input>
            </div>
            <hr />
            <input type="submit" value={"Reset password"} id="Login"></input>
          </form>
        </div>
      </div>
    </div>
  );
}

export default ForgotPassword;
