import "../css/Navigator.css";
import edit from "../icons/edit.png";
import face from "../img/face.jpeg";
import "../css/Profile.css";
import user from "../icons/user.png";

import axios from "axios";
import { useEffect, useState } from "react";

import { address, flask_port } from "./Endpoint";
import FetchEnpoint from "./EndpointFinder";

// Flask server endpoints
var endpoint = await FetchEnpoint()+"/home/profile";
var endpoint_modify_info = await FetchEnpoint()+"/home/modify-info";
var endpoint_modify_password =  await FetchEnpoint()+"/home/modify-password";

function UserInfo() {
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [email, setEmail] = useState("");
  const [birth, setBirth] = useState("");
  const username = localStorage.getItem("LoggedUser");
  const decryptedusername = localStorage.getItem("username");


  const [password, setPassword] = useState("");
  const [editFlag, setEdit] = useState(false);
  const [editPass, setPass] = useState(false);

  let hiddenPassword = "";


  // Edit user information
  const ToggleEdit = async (event) => {
    setEdit(!editFlag);

    if (editFlag === true) {
      // Get new data
      var new_name = document.getElementById("name").value;
      var new_surname = document.getElementById("surname").value;
      var new_email = document.getElementById("email").value;
      var new_birth = document.getElementById("birth").value;

      try {
        // Send a POST request to the /home/modify-info endpoint of the Flask server
        const response = await axios
          .post(endpoint_modify_info, {
            username,
            new_name,
            new_surname,
            new_email,
            new_birth,
          })
          .catch(function (error) {
            if (error.response) {
              // Print error data
              console.log("Data: " + error.response.data);
              console.log("Status: " + error.response.status);
              console.log("Headers: " + error.response.headers);
            }
          });

        // If the modification was successful, update the initial constants
        if (response.data.status === 200) {
          console.log(response.data.message);

          setName(new_name);
          setSurname(new_surname);
          setEmail(new_email);
          setBirth(new_birth);

          sessionStorage.setItem("dataUpdate_alert", "true");
          window.location.replace(window.location.href);
        } else if (response.data.status === 500) {
          console.log(response.data.message);
          sessionStorage.setItem("dataUpdateErr_alert", "true");
          window.location.replace(window.location.href);
        }
      } catch (error) {
        // Request failed
        console.log("[ERROR] Request failed: " + error);
      }
    }
  };

  // Edit user password
  const ToggleEditPass = async (event) => {
    setPass(!editPass);

    if (editPass === true) {
      // Get new data
      var old_password = document.getElementById("old_password").value;
      var new_password = document.getElementById("new_password_1").value;
      var new_password_2 = document.getElementById("new_password_2").value;

      if (old_password === "" || new_password === "" || new_password_2 === "") {
        // MATTEO: Sostituisci alert con console.log; poi, guarda come ho fatto io negli altri alert e replica
        sessionStorage.setItem("fields_alert", "true");
        window.location.replace(window.location.href);
      } 
      else if (old_password !== new_password && new_password === new_password_2) {
        if(new_password.length >= 8) {
          try {
            // Send a POST request to the /home/modify-info endpoint of the Flask server
            const response = await axios
              .post(endpoint_modify_password, {
                username,
                old_password,
                new_password,
              })
              .catch(function (error) {
                if (error.response) {
                  // Print error data
                  console.log("Data: " + error.response.data);
                  console.log("Status: " + error.response.status);
                  console.log("Headers: " + error.response.headers);
                }
              });
  
            // The modification was successful
            if (response.data.status === 200) {
              console.log(response.data.message);
              show_data();
              sessionStorage.setItem("rightPassword_alert", "true");
              window.location.replace(window.location.href);
            } 
            else if (response.data.status === 500) {
              sessionStorage.setItem("db_alert", "true");
              window.location.replace(window.location.href);
            } 
            else if (response.data.status === 400) {
              console.log(response.data.message);
              sessionStorage.setItem("wrongPassword_alert", "true");
              window.location.replace(window.location.href);
            }
          } catch (error) {
            // Request failed
            console.log("[ERROR] Request failed: " + error);
          }
        }
        else {
          sessionStorage.setItem("password8_alert", "true");
          window.location.replace(window.location.href); // For alert purposes only
          console.log("The new password must contain at least 8 characters!");
        }
        
      } else if (
        old_password === new_password ||
        old_password === new_password_2
      ) {
        sessionStorage.setItem("passwordSameAsOld_alert", "true");
        window.location.replace(window.location.href);
      } else if (new_password !== new_password_2) {
        sessionStorage.setItem("differentPasswords_alert", "true");
        window.location.replace(window.location.href);
      }
    }
  };

  // Show user data
  const show_data = async (event) => {
    try {
      // Send a POST request to the /home/profile endpoint of the Flask server
      const response = await axios
        .post(endpoint, {
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

      setName(response.data["name"]);
      setSurname(response.data["surname"]);
      setBirth(response.data["birth"]);
      setEmail(response.data["email"]);
      for (let i = 0; i < response.data["password"].length; i++) {
        hiddenPassword += "*";
      }
      setPassword(hiddenPassword);
    } catch (error) {
      // Request failed
      console.log("[ERROR] Request failed: " + error);
    }
  };

  // The following function will be executed only one time at the beginning of the building of the page
  useEffect(() => {
    show_data();
  }, []);

  return (
    <div className="ProfileContent">
      <div className="ContentEntry">
        <div className="HorizontalContainer">
          <div className="ProfileIcon">
            <img src={user}></img>
          </div>
          <div className="ProfileInfos">
            <h2>{decryptedusername}</h2>
          </div>
        </div>
      </div>
      <div className="ContentEntry">
        <div className="VerticalContainer">
          <div className="Row">
            <div className="Title">Account Information</div>
            <div className="EditButton" onClick={ToggleEditPass}>
              {(editPass && <>Confirm</>) || (!editPass && <>Edit</>)}
              <img src={edit}></img>
            </div>
          </div>
          <div className="Row">
            <div className="TextEntry">
              <h4>Password</h4>
              {(!editPass && (
                <>
                  <h2>{password}</h2>
                </>
              )) ||
                (editPass && (
                  <>
                    <input type="password" id="old_password" />
                  </>
                ))}
            </div>
            {editPass && (
              <>
                <div className="TextEntry">
                  <h4>New password</h4>
                  <input type="password" id="new_password_1" />
                </div>
                <div className="TextEntry">
                  <h4>Repeat new password</h4>
                  <input type="password" id="new_password_2" />
                </div>
              </>
            )}
          </div>
        </div>
      </div>
      <div className="ContentEntry">
        <div className="VerticalContainer">
          <div className="Row">
            <div className="Title">Personal Information</div>
            <div className="EditButton" onClick={ToggleEdit}>
              {(editFlag && <>Confirm</>) || (!editFlag && <>Edit</>)}
              <img src={edit}></img>
            </div>
          </div>
          <div className="Row">
            <div className="TextEntry">
              <h4>Name</h4>
              {(!editFlag && (
                <>
                  <h2>{name}</h2>
                </>
              )) ||
                (editFlag && (
                  <>
                    <input type="text" defaultValue={name} id="name" />
                  </>
                ))}
            </div>
            <div className="TextEntry">
              <h4>Surname</h4>
              {(!editFlag && (
                <>
                  <h2>{surname}</h2>
                </>
              )) ||
                (editFlag && (
                  <>
                    <input type="text" defaultValue={surname} id="surname" />
                  </>
                ))}
            </div>
          </div>
          <div className="Row">
            <div className="TextEntry">
              <h4>Email</h4>
              {(!editFlag && (
                <>
                  <h2>{email}</h2>
                </>
              )) ||
                (editFlag && (
                  <>
                    <input type="text" defaultValue={email} id="email" />
                  </>
                ))}
            </div>
            <div className="TextEntry">
              <h4>Birth Date</h4>
              {(!editFlag && (
                <>
                  <h2>{birth}</h2>
                </>
              )) ||
                (editFlag && (
                  <>
                    <input type="date" defaultValue={birth} id="birth" />
                  </>
                ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UserInfo;
