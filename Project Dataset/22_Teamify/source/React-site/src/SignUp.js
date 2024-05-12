import "./css/App.css";
import "./css/Login.css";

import React, { useEffect, useState } from "react";
import { Link, useNavigate, Navigate } from "react-router-dom";
import Alert from "./components/Alert.tsx";
import axios from "axios";
import FetchEnpoint from "./components/EndpointFinder";
import { address, flask_port } from "./components/Endpoint";

var endpoint = await FetchEnpoint()+"/signup";
const loggedIn = localStorage.getItem("LoggedUser");

function SignUp() {
  useEffect(() => {
    setName(localStorage.getItem("name"));
    setSurname(localStorage.getItem("surname"));
    setBirth(localStorage.getItem("birth"));
    setEmail(localStorage.getItem("email"));
    setUsername(localStorage.getItem("username"));
    if(name=="null") setName("");
    if(surname=="null") setSurname("");
    if(birth=="null") setBirth("");
    if(email=="null") setEmail("");
    if(username=="null") setUsername("");
  }, []);

  useEffect(() => {
    const timeout = setTimeout(() => {
      handleMissingFields();
      handleRequestFailed();
      handlePasswordsNotMatching();
      handlePasswordNotOf8Characters();
      handleusernameExists();
    }, 1000);

    return () => clearTimeout(timeout);
  }, []);


  // Form data
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [birth, setBirth] = useState("");
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [password_again, setPasswordAgain] = useState("");

  // For redirecting
  const navigate = useNavigate();

  /* ALERT SECTION */

  // Handler for missing fields error
  const missingFields = sessionStorage.getItem("fields_alert") === "true";
  const handleMissingFields = () => {
    sessionStorage.setItem("fields_alert", "false");
  };

  // Handler for passwords not matching error
  const passwordsNotMatching =
    sessionStorage.getItem("passwords_alert") === "true";
  const handlePasswordsNotMatching = () => {
    sessionStorage.setItem("passwords_alert", "false");
  };

  // Handler for passwords not composed of 8 characters error
  const passwordNotOf8Characters =
    sessionStorage.getItem("password8_alert") === "true";
  const handlePasswordNotOf8Characters = () => {
    sessionStorage.setItem("password8_alert", "false");
  };

  // Handler for request failed error
  const requestFailed =
    sessionStorage.getItem("request_failed_alert") === "true";
  const handleRequestFailed = () => {
    sessionStorage.setItem("request_failed_alert", "false");
  };

  const usernameExists =
    sessionStorage.getItem("username_exists_alert") === "true";
  const handleusernameExists = () => {
    sessionStorage.setItem("username_exists_alert", "false");
  };

  /* END OF ALERT SECTION */

  // Handler for the event 'Submit of a form'
  const handleSubmit = async (event) => {
    event.preventDefault();

    // Set form values
    localStorage.setItem("name", name);
    localStorage.setItem("surname", surname);
    localStorage.setItem("birth", birth);
    localStorage.setItem("email", email);
    localStorage.setItem("username", username);

    if (
      name === "" ||
      surname === "" ||
      birth === "" ||
      email === "" ||
      username === "" ||
      password === "" ||
      password_again === ""
    ) {
      // There is at least one mandatory field that has not been filled
      sessionStorage.setItem("fields_alert", "true");
      window.location.replace(window.location.href); // For alert purposes only
      console.log("All the fields must be filled!");
    } else if (password !== password_again) {
      // Passwords do not match
      sessionStorage.setItem("passwords_alert", "true");
      window.location.replace(window.location.href); // For alert purposes only
      console.log("The inserted passwords do not match!");
    } else if (password.length < 8) {
      // The password does not contain at least 8 characters
      sessionStorage.setItem("password8_alert", "true");
      window.location.replace(window.location.href); // For alert purposes only
      console.log("The password must contain at least 8 characters!");
    } else {
      try {
        // Send a POST request to the /signup endpoint of the Flask server
        const response = await axios.post(endpoint, {
          name,
          surname,
          birth,
          email,
          username,
          password,
        });
    
        // If the signup has been successfully performed, then redirect the user to the Login page.
        if (response.status === 200) {
          sessionStorage.setItem("sign_up_alert", "true");
    
          // Erase form values
          localStorage.clear();
    
          // Redirect
          navigate("/login");
        }
        else if (response.status === 400){
          sessionStorage.setItem("username_exists_alert", "true");
          window.location.replace(window.location.href); // For alert purposes only
          console.log("[ERROR] Request failed: ");
        }
      } catch (error) {
        // Request failed
        sessionStorage.setItem("request_failed_alert", "true");
        window.location.replace(window.location.href); // For alert purposes only
        console.log("[ERROR] Request failed: " + error);
      }
    }
  }
    

  return (
    <div className="App">
      {loggedIn && <Navigate to="/home" />}
      <div className="TopBar">
        <div className="BarHeading">
          <Link to="/" style={{ color: "inherit", textDecoration: "inherit" }}>
            Teamify
          </Link>
        </div>
        <div className="barSignUp">
          Already have an account?
          <div
            className="Link"
            style={{ paddingLeft: "2%", fontSize: "large" }}
          >
            <Link to="/login">Sign In</Link>
          </div>
        </div>
      </div>
      {missingFields && (
        <Alert onClick={handleMissingFields} state="danger">
          All the fields must be filled!
        </Alert>
      )}

      {passwordsNotMatching && (
        <Alert onClick={handlePasswordsNotMatching} state="danger">
          The inserted passwords do not match!
        </Alert>
      )}

      {passwordNotOf8Characters && (
        <Alert onClick={handlePasswordNotOf8Characters} state="danger">
          The password must contain at least 8 characters!
        </Alert>
      )}

      {requestFailed && (
        <Alert onClick={handleRequestFailed} state="danger">
          Error: Signup failed
        </Alert>
      )}
      {usernameExists && (
        <Alert onClick={handleusernameExists} state="danger">
          Error: This username is already registered
        </Alert>
      )}
      <div className="SignUpBackground">
        <div className="CardL">
          <div className="CardHeading">Register your account</div>
          <form onSubmit={handleSubmit}>
            <div className="InputEntry">
              <div className="InputLabel">Name</div>
              <input
                className="InputField"
                type="text"
                placeholder="Enter your name"
                id="name"
                defaultValue={localStorage.getItem("name")}
                onChange={(event) => setName(event.target.value)}
              ></input>
            </div>
            <div className="InputEntry">
              <div className="InputLabel">Surname</div>
              <input
                className="InputField"
                type="text"
                placeholder="Enter your surname"
                id="surname"
                defaultValue={localStorage.getItem("surname")}
                onChange={(event) => setSurname(event.target.value)}
              ></input>
            </div>
            <div className="InputEntry">
              <div className="InputLabel">Birth Date</div>
              <input
                className="InputField"
                type="date"
                placeholder="dd/mm/yyy"
                id="birth"
                defaultValue={localStorage.getItem("birth")}
                onChange={(event) => setBirth(event.target.value)}
              ></input>
            </div>
            <div className="InputEntry">
              <div className="InputLabel">Email</div>
              <input
                className="InputField"
                type="text"
                placeholder="your.email@example.com"
                id="email"
                defaultValue={localStorage.getItem("email")}
                onChange={(event) => setEmail(event.target.value)}
              ></input>
            </div>
            <div className="InputEntry">
              <div className="InputLabel">Username</div>
              <input
                className="InputField"
                type="text"
                placeholder="Enter your username"
                id="username"
                defaultValue={localStorage.getItem("username")}
                onChange={(event) => setUsername(event.target.value)}
              ></input>
            </div>
            <div className="InputEntry">
              <div className="InputLabel">Password (at least 8 characters)</div>
              <input
                className="InputField"
                type="password"
                placeholder="Enter your password"
                id="password1"
                onChange={(event) => setPassword(event.target.value)}
              ></input>
            </div>
            <div className="InputEntry">
              <div className="InputLabel">Repeat Password</div>
              <input
                className="InputField"
                type="password"
                placeholder="Enter again your password"
                id="password2"
                onChange={(event) => setPasswordAgain(event.target.value)}
              ></input>
            </div>
            <hr />
            <input type="submit" value={"Register"} id="Login"></input>
          </form>
        </div>
      </div>
    </div>
  );
}

export default SignUp;
