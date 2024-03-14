import "../css/Navigator.css";

import { useEffect } from "react";

import "../css/chat.css";
import chat from "../icons/chat.png";
import cancel from "../icons/cancel.png";
import face from "../img/face.jpeg";
import account from "../icons/user.png";
import { useState } from "react";
import { address, flask_port } from "./Endpoint";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import io from 'socket.io-client';
import FetchEnpoint from "./EndpointFinder";

const username = localStorage.getItem('LoggedUser');
var endpointGetTeamsFromUser = await FetchEnpoint()+"/getJoinedTeams";
var endpointGetTeamMembers = await FetchEnpoint()+"/membersGivenTeam";
var endpointSendMessageToTeam = await FetchEnpoint()+"/sendMessageToTeam";
var endpointGetMessages = await FetchEnpoint()+"/getMessages";

function Chat() {
  const [show, setShow] = useState(false);
  const [teams, setTeams] = useState([]);
  const [ids, setTeamIds] = useState([]);
  const [showTeams, setShowTeams] = useState(false);
  const [showMessages, setShowMessages] = useState(false);
  const [messages, setMessages] = useState([]);
  const [members, setMembers] = useState([]);

  var teamId;

  // Message: [sender, text, date, time]


  function ToggleChat() {
    setShow(!show); 
  }


  async function OnChatSubmit(e) {
    var code = (e.keyCode ? e.keyCode : e.which);
    if (code == 13) { // I have pressed 'Enter'                        
      e.preventDefault();
      var text = e.target.value;
      console.log("text sumbitted: " + text);
      e.target.value = "";

      teamId = ids[document.getElementById("chosenTeam").value];

      // Send message request
      try {
        const response = await axios
          .post(endpointSendMessageToTeam, {
            text,
            username,
            teamId,
            members,
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
          console.log("The message "+text+" has been sent!");
        }
      } 
      catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }
    }
  }

  // Get teams of the user
  async function get_teams() {
    try {
      // Send a POST request to the corresponding endpoint of the Flask server
      const response = await axios
        .post(endpointGetTeamsFromUser, {
          username,
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
        setTeams(response.data.teams);
        setTeamIds(response.data.ids);
        setShowTeams(true);
      } else if (response.data.status === 201) {
        setTeams([]);
        setTeamIds([]);
        setShowTeams(false);
      }
    } catch (error) {
      // Request failed
      console.log("[ERROR] Request failed: " + error);
    }
  }

  async function handleTeamChange() {
    var selectedOption = document.getElementById("chosenTeam").value;

    // -1 if 'Select a team...'
    if (selectedOption === -1) {
      document.getElementById('chatInput').disabled= true;
    }
    else if (selectedOption >= 0) {
      document.getElementById('chatInput').disabled = false;
      teamId = ids[selectedOption];

      // Get team members
      try {
        const response = await axios
          .post(endpointGetTeamMembers, {
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
          setMembers(response.data.members);
        }
        else if (response.data.status === 400) {
          console.log(response.data.message);
        }
      } 
      catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }



      // Get team messages
      try {
        const response = await axios
          .post(endpointGetMessages, {
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
          setMessages(response.data.messages);
          setShowMessages(true);
        }
      } 
      catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }

      const scrollChat = document.getElementById('ChatBody');
      scrollChat.scrollTop = scrollChat.scrollHeight;
    }
  }

  useEffect(() => {
    get_teams();
  }, []);

  return (
    <div className="Bubble">
        {
            !show &&
            (
                <>
                    <img src={chat} onClick={ToggleChat} id="bubbleChat"></img>
                </>
            )
            ||
            show &&
            (
                <div className="ChatContainer">
                    <div className="ChatTopBar">
                        <select id="chosenTeam" style={{marginLeft: 15, marginTop: 10}} onChange={handleTeamChange}>
                          <option value={-1}>Select a team...</option>
                          {showTeams && teams.map((team, index) => (
                          <option value={index} id={index}>{team}</option>
                          ))}
                        </select>
                        {!showTeams && (
                            <h3>No team available</h3>
                        )}
                        <img src={cancel} onClick={ToggleChat}></img>
                    </div>
                    <hr></hr>
                    <div className="ChatBody" id="ChatBody">
                      {showMessages && messages.map((message, index) => (
                        <div className="ChatEntry">
                            <img src={account}></img>
                            <div className="ChatEntryText">
                                <h2>
                                    {message[0]}
                                </h2>
                                <h3>
                                    {message[1]}
                                </h3>
                                <h5>
                                    {message[2]} - {message[3]}
                                </h5>
                            </div>
                        </div>
                      ))}
                    </div>
                    <hr></hr>
                    <input type="text" onKeyDown={OnChatSubmit} id="chatInput">
                    </input>
                 </div>
            )
        }
    </div>
  );
}

export default Chat;
