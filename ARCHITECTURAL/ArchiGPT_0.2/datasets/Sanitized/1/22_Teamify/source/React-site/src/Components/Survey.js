import "../css/Navigator.css";

import { useEffect } from "react";
import "../css/Survey.css";
import chat from "../icons/chat.png";
import cancel from "../icons/cancel.png";
import face from "../img/face.jpeg";
import { useState } from "react";
import { address, flask_port } from "./Endpoint";
import FetchEnpoint from "./EndpointFinder";
import axios from "axios";
const endpoint1 = await FetchEnpoint() + "/home/teams/team/survey";
const endpoint2 = await FetchEnpoint() + "/vote";

function Survey(props) {
  const [vote, setShow] = useState(false);

  const [checkedVote, setCheckedVote] = useState(
    props.entries.map((data) => {
      if (data[2]) return data[3];
    })
  );
  const [poolID, SetPool] = useState(props.id);
  const decryptedUsername = localStorage.getItem("username");
  const username = localStorage.getItem("LoggedUser");
  const [voted, setVoted] = useState(false);

  // console.log(vote);
  async function ToggleVote() {
    if (vote) {
      await axios
        .post(endpoint2, {
          pool_id: poolID,
          username: username,
          option_id: checkedVote,
        })
        .then((response) => {
          // console.log(response);
          if (response.data.status === 200) {
            window.location.replace(window.location.href);
          }
        })
        .catch(function (error) {
          if (error.response) {
            // Print error data
            console.log("Data: " + error.response.data);
            console.log("Status: " + error.response.status);
            console.log("Headers: " + error.response.headers);
          }
        });
    }
    setShow(!vote);
  }

  function OnSurveyChange(e) {
    setCheckedVote(e.target.value);
  }

  useEffect(() => {
    props.entries.map((data) => {
      if (data[2]) setCheckedVote(data[3]);
    });
    SetPool(props.id);
  }, []);

  useEffect(() => {
    axios
      .get(endpoint1, {
        params: {
          survey: poolID,
          username: decryptedUsername,
        },
      })
      .then((response) => {
        console.log(poolID, response.data);
        setVoted(response.data === "true");
      })
      .catch((error) => console.log(error));
  });

  return (
    <div className="SurveyContainer">
      <div className="SurveyHeader">
        <h2>{props.title}</h2>
      </div>

      <div className="SurveyBody">
        {props.entries.map(function (data, i) {
          const color = data[3] == checkedVote ? "green" : "red";
          if (!vote)
            return (
              <div className="SurveyEntry">
                <h2>{data[0]}</h2>
                <div className="SurveyValue">
                  <h2>{data[1]}</h2>
                  <div
                    className="SurveyBar"
                    style={{
                      width:
                        data[0] == 0
                          ? data[0] + "%"
                          : (data[0] / props.votes) * 100 + "%",
                      backgroundColor: color,
                    }}
                  ></div>
                  {data[3] == checkedVote && <h3>You voted this</h3>}
                </div>
              </div>
            );
          else
            return (
              <div className="SurveyInputEntry">
                <input
                  type="radio"
                  id={data[1]}
                  name={props.title}
                  value={data[3]}
                  checked={data[3] == checkedVote}
                  onChange={OnSurveyChange}
                ></input>
                <label for={data[1]}>{data[1]}</label>
              </div>
            );
        })}
      </div>
      <div className="SurveyFooter">
        <div>
          <h2>
            Total votes: {props.votes} • Created by {props.author} •{" "}
            {props.date}
          </h2>
        </div>
        <div className="SurveyButtonCont">
          <div className="SurveyButton" onClick={ToggleVote}>
            <h2>
              {(voted && !vote && "Edit Vote") ||
                (voted && vote && "Confirm") ||
                (vote && "Confirm") ||
                (!vote && "Vote")}
            </h2>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Survey;
