import {React, useEffect, useState} from "react";
import { Navigate, useLocation, useNavigate, Link } from "react-router-dom";
import TopBar from "./TopBar";
import UserIcon from "./UserIcon";
import Notifications from "./Notifications";
import Alert from "./Alert.tsx";
import placeholder from "../img/placeholder_profile.jpeg";
import "../css/Invite.css";

import axios from "axios";

import {address, flask_port} from "./Endpoint";
import FetchEnpoint from "./EndpointFinder";

let endpointAcceptTeam = await FetchEnpoint()+"/acceptInvite";
let endpointRejectTeam = await FetchEnpoint()+"/rejectInvite";
let endpointAcceptEvent = await FetchEnpoint()+"/acceptEventInvite";
let endpointRejectEvent = await FetchEnpoint()+"/rejectEventInvite";

function Invite() {
  const username = localStorage.getItem("LoggedUser");
  const location = useLocation();
  const [admin, setAdmin] = useState("");
  const [team, setTeam] = useState("");
  const [event_title, setEventTitle] = useState("");
  const [description, setDescription] = useState("");
  const [teamId, setTeamId] = useState("");

  // Event management
  const [event_id, setEventId] = useState("");
  const [member, setMember] = useState("");
  const [state, setState] = useState("");

  const navigate = useNavigate();


  async function handleReject() {
    if(team) {
      try {
        // Send a POST request to the corresponding endpoint of the Flask server
        const response = await axios
          .post(endpointRejectTeam, {
            username,
            teamId,
            admin,
          })
          .catch(function (error) {
            if (error.response) {
              // Print error data
              console.log("Data: " + error.response.data);
              console.log("Status: " + error.response.status);
              console.log("Headers: " + error.response.headers);
            }
          });
  
        if (response.data.status === 200) {
          navigate("/home");
        }
      } catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }
    }
    else if(event_title) {
      try {
        // Send a POST request to the corresponding endpoint of the Flask server
        const response = await axios
          .post(endpointRejectEvent, {
            username,
            event_id,
            teamId,
            admin,
            event_title,
          })
          .catch(function (error) {
            if (error.response) {
              // Print error data
              console.log("Data: " + error.response.data);
              console.log("Status: " + error.response.status);
              console.log("Headers: " + error.response.headers);
            }
          });
  
        if (response.data.status === 200) {
          navigate("/home");
        }
      } catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }
    }
  };
  

  async function handleAcceptance() {
    if(team) {
      try {
        // Send a POST request to the corresponding endpoint of the Flask server
        const response = await axios
          .post(endpointAcceptTeam, {
            username,
            teamId,
          })
          .catch(function (error) {
            if (error.response) {
              // Print error data
              console.log("Data: " + error.response.data);
              console.log("Status: " + error.response.status);
              console.log("Headers: " + error.response.headers);
            }
          });
  
        if (response.data.status === 200) {
          const link='/teamview?id='+teamId;
          navigate(link);
        }
      } catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }
    }
    else if(event_title) {
      try {
        // Send a POST request to the corresponding endpoint of the Flask server
        const response = await axios
          .post(endpointAcceptEvent, {
            username,
            event_id,
            teamId,
          })
          .catch(function (error) {
            if (error.response) {
              // Print error data
              console.log("Data: " + error.response.data);
              console.log("Status: " + error.response.status);
              console.log("Headers: " + error.response.headers);
            }
          });
  
        if (response.data.status === 200) {
          navigate("/home");
        }
      } catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }
    }
  };


  const ToggleDisplayAgenda = () => {
    localStorage.setItem("ProfileData", "false");
    navigate("home");
  };


  useEffect(() => {
    if (location.state) {
      if (location.state.description) {
        setDescription(location.state.description);
      }
      if (location.state.admin) {
        setAdmin(location.state.admin);
      }
      if (location.state.team) {
        setTeam(location.state.team);
      }
      if (location.state.id) {
        setTeamId(location.state.id);
      }
      if (location.state.event_id) {
        setEventId(location.state.event_id);
      }
      if (location.state.team_id) {
        setTeamId(location.state.team_id);
      }
      if (location.state.member) {
        setMember(location.state.member);
      }
      if (location.state.state) {
        setState(location.state.state);
      }
      if (location.state.event_title) {
        setEventTitle(location.state.event_title);
      }
    }
  }, [location.state]);


  
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
      <div className="central-container">
        <div className="container text-center ">
          <div className="align-content-center text-center mt-5">
            <div className="row mt-5">
              <div className="rounded-circle overflow-hidden mt-5">
                <img
                  src={placeholder}
                  className="img-fluid border border-2"
                  style={{ borderRadius: "50%" }}
                  width="15%"
                  height="15%"
                />
              </div>
            </div>
            <div className="row text-center">
              <div
                className="mx auto mt-2 mb-1"
                style={{ fontSize: "27px", fontWeight: "bold" }}
              >
                {admin || "Someone"}
              </div>
            </div>
            <div className="row text-center">
              <div className="mx auto" style={{ fontSize: "20px" }}>
                invited you to join
              </div>
            </div>
          </div>
          <div className="align-content-center text-center">
            <div className="mt-3 invite-container">
              <div className="mt-2">{team || event_title || "No team/event available"}</div>
              <div className="mt-2">
                {description || "No description available"}
              </div>
              <br />
              <div className="row text-center">
                <div className="col-md-2"></div>
                <div className="col-md-4">
                  <button
                    className="btn btn-outline-danger"
                    onClick={handleReject}
                  >
                    Reject
                  </button>
                </div>
                <div className="col-md-4">
                  <button
                    className="btn btn-outline-success"
                    onClick={handleAcceptance}
                  >
                    Accept{" "}
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Invite;
