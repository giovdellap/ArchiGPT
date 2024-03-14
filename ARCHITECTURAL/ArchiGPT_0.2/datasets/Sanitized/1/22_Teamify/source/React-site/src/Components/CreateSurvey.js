import "../css/Navigator.css";

import { useEffect } from "react";
import axios from "axios";

import "../css/Survey.css"
import { useState } from "react";
import cancel from "../icons/cancel.png"
import { address, flask_port } from "./Endpoint";
import FetchEnpoint from "./EndpointFinder";

const endpointCreate= await FetchEnpoint() + "/createPool";
const endpointIsAdmin = await FetchEnpoint() + "/teamDetails";

function CreateSurvey() {

  const [show, setShow] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [testAdmin, setTestAdmin] = useState(false);
  const [data, setData] = useState(false);
  const [options,setOptions] = useState(1)
  const queryParameters = new URLSearchParams(window.location.search);
  const id = queryParameters.get("id");
  const username = localStorage.getItem("LoggedUser");
  const decryptedUsername = localStorage.getItem("username");
  

  function AddOption() {
    setOptions(options+1);
  }

  function RemoveOptions(e) {
    for (let index = parseInt(e.target.className); index < options-1; index++) {
      document.getElementById(index).value = document.getElementById(index+1).value;
    }
    setOptions(options-1);
  }

  function ToggleCreate() {
    setShow(!show)
  }

  const Confirm = async (event) =>{
    event.preventDefault();
    console.log(endpointCreate);

    var elements =document.getElementsByClassName("option")
    var date = document.getElementById("date").value
    var title = document.getElementById("title").value
    console.log(date)
    console.log(title)
    var values = []
    for (let index = 0; index < options; index++) {
      values.push(elements[index].value)
    }
    setShow(false);
    setOptions(1);

    try {
      // Send a POST request to the endpoint of the Flask server
      const response = await axios
        .post(endpointCreate, {
          text:title,
          due_date:date,
          admin:username,
          team:id,
          options:values
        }).then((response) => {
          setData(response.data[0]);
          if (response.data.status === 200) {
            window.location.replace(window.location.href);
          }
        }).catch(function (error) {
          if (error.response) {
            // Print error data
            console.log("Data: " + error.response.data);
            console.log("Status: " + error.response.status);
            console.log("Headers: " + error.response.headers);
          }
        });
    } catch (error) {
      // Request failed
      console.log("[ERROR] Request failed: " + error);
    }
}

    if(testAdmin===false){
      try {
        // Send a GET request to the endpoint of the Flask server
        const response1 = axios
          .get(endpointIsAdmin, {
            params: {
              id: id
            }
          }).then((response) => {
            var admins=response.data[0]["admins"]
            if(admins.includes(decryptedUsername)) setIsAdmin(true);
            console.log(isAdmin);

          }).catch(function (error) {
            if (error.response) {
              // Print error data
              console.log("Data: " + error.response.data);
              console.log("Status: " + error.response.status);
              console.log("Headers: " + error.response.headers);
            }
          });
      } catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }
      setTestAdmin(true);
    }

    return (
      isAdmin && (
        <div className="SurveySectionTopBar">
        {
          !show &&
          (
            <div className="CreateSurvey" onClick={ToggleCreate}>
              <h2>
                Add Survey
              </h2>           
            </div>
            )
            ||
            show && 
            (
            <div className="SurveyContainer">
              <div className="SurveyHeader">
                <h2>Create New Survey</h2>
              </div>
              <div className="SurveyHeader">
                <input type="text" placeholder ="insert title here" id="title"></input>
                <input type="date" placeholder ="insert title here" id="date"></input>
              </div>
            <div className="SurveyBody">
            {
              [...Array(options)].map((a,i) => (
                <div className="SurveyEntry" style={{justifyContent:"spaceAround"}}>
                  <span class="dot"></span>
                  <input type="text" placeholder ="insert option here" className="option" id={i}></input>
                  <div className="XIcon" onClick={RemoveOptions}> <img src={cancel} class={i}></img></div>
                </div> 
              ))
            }
              <div className="SurveyEntry" style={{justifyContent:"spaceAround"}}>
                <span class="dot"></span>
                <input type="text" placeholder ="Add Another option" className="option" onFocus={(e) => e.target.blur()}onClick={AddOption} readOnly={true}></input>
              </div> 
            </div>
    
            <div className="SurveyFooter" style={{justifyContent: "flex-end"}}>
              <div className="CreateSurvey" onClick={ToggleCreate}>
                <h2 style={{color:"white"}}>
                  cancel
                </h2>           
              </div>
              <div className="CreateSurvey" style={{ marginLeft:"1%"}} onClick={Confirm}>
                <h2 style={{color:"white"}}>
                  confirm
                </h2>           
              </div>
            </div>
          </div>
          )
        }
        </div>
      )
    );
    
  
}

export default CreateSurvey;