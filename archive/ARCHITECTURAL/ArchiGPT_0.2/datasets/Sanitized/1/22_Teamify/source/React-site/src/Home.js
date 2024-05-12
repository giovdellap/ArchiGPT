import "./css/App.css";
import "./css/Homepage.css";

import { TopBar, BarHeading } from "./css/Login.css";
import { Link, Navigate } from "react-router-dom";

import calendar from "./icons/calendar.png";
import team from "./icons/team.png";
import profile from "./icons/users.png";
import back from "./img/background_green.jpeg";
import chat from "./icons/chat.png";
import event from "./icons/event_icon.png";

const loggedIn = localStorage.getItem("LoggedUser");

function Home() {
  const containerStyle = {
    background: `url(${back}) no-repeat center center fixed`,
    backgroundSize: "cover",
  };
  return (
    <div className="App">
      {loggedIn && <Navigate to="/home" />}
      <div className="TopBar">
        <div className="BarHeading">
          <Link to="/" style={{ color: "inherit", textDecoration: "inherit" }}>
            Teamify
          </Link>
        </div>
        <div className="MenuOptions"></div>
        <div className="Buttons">
          <div className="LogIn">
            <Link
              to="/login"
              style={{ color: "inherit", textDecoration: "inherit" }}
            >
              Log In
            </Link>
          </div>
          <div className="SignUp">
            <Link
              to="/signup"
              style={{ color: "inherit", textDecoration: "inherit" }}
            >
              Sign Up
            </Link>
          </div>
        </div>
      </div>
      <div
        className="CenterContainer"
        style={{ ...containerStyle, height: "100%" }}
      >
        <h1 style={{ marginTop: "6%" }}>Teamify</h1>
        <h5 style={{ marginTop: "1%", marginBottom: "3%" }}>
          Organize, Collaborate, and Achieve Team Goals
        </h5>
        <div className="CardContainer">
          <div className="Card">
            <div className="CardIcon">
              <img src={calendar}></img>
            </div>
            <div className="CardText" style={{ marginLeft: "3%" }}>
              <h4>Intuitive Weekly Calendar</h4>
              <h5>
                View all your work and events in a single, clear, and intuitive
                view. The weekly calendar provides a comprehensive overview of
                planned activities for you and your team.
              </h5>
            </div>
          </div>
          <div className="Card">
            <div className="CardIcon">
              <img src={team}></img>
            </div>
            <div className="CardText" style={{ marginLeft: "3%" }}>
              <h4>Collaborative Workgroups</h4>
              <h5>
                Team creation is the process of assembling a group of
                individuals with diverse skills, expertise, and roles to work
                collectively towards a common goal or project.
              </h5>
            </div>
          </div>
          <div className="Card">
            <div className="CardIcon">
              <img src={event}></img>
            </div>
            <div className="CardText" style={{ marginLeft: "3%" }}>
              <h4>Personal task and shared event</h4>
              <h5>
                From managing personal and shared tasks with ease to organizing
                collaborative events seamlessly, Teamify simplifies teamwork,
                ensuring nothing gets overlooked.
              </h5>
            </div>
          </div>
          <div className="Card">
            <div className="CardIcon">
              <img src={chat}></img>
            </div>
            <div className="CardText" style={{ marginLeft: "3%" }}>
              <h4>Team communication</h4>
              <h5>
                Versatile and user-friendly platform for team members to
                communicate effectively. It fosters collaboration by enabling
                real-time conversations and easy access to team discussions.
              </h5>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
