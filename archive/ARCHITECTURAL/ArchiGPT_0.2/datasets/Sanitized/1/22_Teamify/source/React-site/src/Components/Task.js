import { useNavigate, useLocation } from "react-router-dom";
import "../css/task.css";
import { handleRevert } from "./Profile";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { formatTime } from "../support.js";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { objectToArray } from "../support.js";
import { address, flask_port } from "./Endpoint";
import FetchEnpoint from "./EndpointFinder";
const endpoint = await FetchEnpoint() + "/home/event/members";
const endpoint3 = await FetchEnpoint() + "/home/team/member";
const endpoint2 = await FetchEnpoint();
interface Props {
  task: Object;
}

function Task({ task }: Props) {

  const decryptedUsername = localStorage.getItem("username");
  const username = localStorage.getItem("LoggedUser");
  const navigate = useNavigate();
  const [members, setMembers] = useState([]);
  const [teamMembers, setTeamMembers] = useState([]);
  const formattedTime = formatTime(task.end);
  const [completeButton, setButtonText] = useState("Complete");
  const [isExpanded, setIsExpanded] = useState(false);

  const toggleAccordion = () => {
    setIsExpanded(!isExpanded);
  };

  const handleDelete = async () => {
    if (task.member === decryptedUsername) {
      localStorage.setItem("task_to_delete", task.id);
      localStorage.setItem("delete", true);
      window.location.reload();
    } else {
      sessionStorage.setItem("not_auth", true);
      window.location.reload();
    }
  };

  const handleComplete = async () => {
    try {
      const response = await axios.put(
        endpoint2 + `/home/completetask/${task.id}`
      );

      console.log(response.data.message); // Display the response message
      if (response.status === 200) {
        const new_status = response.data[1];
        if (new_status === "completed") {
          setButtonText("Restore");
        } else {
          setButtonText("Complete");
        }
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  //in this way you can pass the props through the navigate
  const handleEdit = () => {
    if (task.member === decryptedUsername) {
      const modify = task.type === "event" ? "event" : "personal";
      if (modify === "event") {
        axios
          .get(endpoint3, {
            params: {
              id: task.id,
            },
          })
          .then((response) => {
            const res = response.data;
            //console.log("RES meme\n", res.member_list);
            navigate("/home/tasks/edittask", {
              state: {
                task: task,
                modify: modify,
                members: members,
                team: res.member_list,
                previousPage: window.location.href,
              },
            });
          })
          .catch((error) => {
            console.error("Error fetching team data:", error);
          });
      } else {
        navigate("/home/tasks/edittask", {
          state: {
            task: task,
            previousPage: window.location.href,
          },
        });
      }
    } else {
      sessionStorage.setItem("not_auth", true);
      window.location.reload();
    }
  };

  const handleClosure = () => {
    window.location.reload();
  };

  useEffect(() => {
    if (task.type === "event") {
      axios
        .get(endpoint, {
          params: {
            eventId: task.id,
          },
        })
        .then((response) => {
          const res = response.data;
          console.log(res);
          setMembers(res);
        })
        .catch((error) => {
          console.error("Error fetching team data:", error);
        });
    }
  }, [isExpanded]);

  useEffect(() => {
    if (task.status === "completed") {
      setButtonText("Restore");
    }
  }, [task.status]);

  //bisogna cambiare i pulsanti e metterli cliccabili solo se admin
  return (
    <div className="MessageContainer" id="message-container">
      <div className="CardPopUP">
        <div
          className="row"
          onClick={handleClosure}
          style={{
            textAlign: "right",
            marginLeft: "400px",
            marginRight: "10px",
          }}
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            className="bi bi-x-lg"
            viewBox="0 0 16 16"
          >
            <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8 2.146 2.854Z" />
          </svg>
        </div>
        <div className="row">
          <h5 style={{ textAlign: "center" }}>
            <b>{task.title}</b>
          </h5>
          <hr />
        </div>
        <div className="row">
          <p>
            <b>Date: </b>
            {task.start.toLocaleString()} - {formattedTime}
          </p>
        </div>
        <div className="row">
          <p>
            <b> Description: </b> {task.description}
          </p>
        </div>
        {task.type === "event" && (
          <div className="accordion mb-3">
            <div className="accordion-header" onClick={toggleAccordion}>
              <button className="btn btn-light" type="button">
                Event Members
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="16"
                  height="16"
                  fill="currentColor"
                  className="bi bi-caret-down"
                  viewBox="0 0 16 16"
                >
                  <path d="M3.204 5h9.592L8 10.481 3.204 5zm-.753.659 4.796 5.48a1 1 0 0 0 1.506 0l4.796-5.48c.566-.647.106-1.659-.753-1.659H3.204a1 1 0 0 0-.753 1.659z" />
                </svg>
              </button>
            </div>
            {isExpanded && (
              <div
                className="accordion-content overflow-auto"
                style={{ maxHeight: "75px" }}
              >
                <ul>
                  {members.map((memberObj, index) => (
                    <li key={index}>
                      {memberObj.member === decryptedUsername
                        ? "You"
                        : memberObj.member}
                    </li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        )}

        <Container>
          <Row>
            <Col xs={6}>
              <button
                className="btn btn-block"
                style={{
                  border: "solid #c5fdc8",
                  color: "black",
                  width: "100%",
                  height: "100%",
                }}
                onClick={handleDelete}
              >
                <Row>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="16"
                    height="16"
                    fill="currentColor"
                    className="bi bi-trash mt-3"
                    viewBox="0 0 16 16"
                  >
                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z" />
                    <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z" />
                  </svg>
                </Row>
                <Row>
                  <p> Delete </p>
                </Row>
              </button>
            </Col>
            <Col xs={6}>
              <button
                className="btn btn-block"
                style={{
                  border: "solid #c5fdc8",
                  color: "black",
                  width: "100%",
                  height: "100%",
                }}
                id="complete"
                onClick={handleComplete}
              >
                <Row id="icon">
                  {completeButton === "Complete" ? (
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="16"
                      height="16"
                      fill="currentColor"
                      className="bi bi-check-circle mt-3"
                      viewBox="0 0 16 16"
                    >
                      <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z" />
                      <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z" />
                    </svg>
                  ) : (
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="16"
                      height="16"
                      fill="currentColor"
                      className="bi bi-check-circle-fill mt-3"
                      viewBox="0 0 16 16"
                    >
                      <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z" />
                    </svg>
                  )}
                </Row>
                <Row>
                  <p style={{ marginRight: "20px" }}> {completeButton} </p>
                </Row>
              </button>
            </Col>
          </Row>
          <Row>
            <Col>
              <p>
                <button
                  className="btn btn-block mt-2 "
                  style={{
                    border: "solid #c5fdc8",
                    color: "black",
                    width: "100%",
                  }}
                  onClick={handleEdit}
                >
                  <div className="mt-2 mb-2">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="16"
                      height="16"
                      fill="currentColor"
                      className="bi bi-pencil-square"
                      viewBox="0 0 16 16"
                      style={{ marginRight: "5px" }}
                    >
                      <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
                      <path
                        fillRule="evenodd"
                        d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"
                      />
                    </svg>
                    Edit
                  </div>
                </button>
              </p>
            </Col>
          </Row>
        </Container>
      </div>
    </div>
  );
}

export default Task;
