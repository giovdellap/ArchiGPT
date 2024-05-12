import "../css/Navigator.css";
import "../css/Profile.css";

import { Link } from "react-router-dom";
import bell from "../icons/bell.png";
import team from "../icons/team.png";
import event from "../icons/event.png";
import { useEffect, useState } from "react";
import axios from "axios";
import FetchEnpoint from "./EndpointFinder";
import { address, flask_port } from "./Endpoint";

var endpoint = await FetchEnpoint() +"/home/notifications";

function ProfileNotifications() {
  const username = localStorage.getItem("LoggedUser");
  const [notifications, setNotifications] = useState([]);
  const [displayNotifications, setDisplayNotifications] = useState(true);

  // Show user notifications
  const show_notifications = async (event) => {
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

      if (response.data.status === 200) {
        setNotifications(response.data.notifications);
        setDisplayNotifications(true);
      } else if (response.data.status === 201) {
        setDisplayNotifications(false);
      }
    } catch (error) {
      // Request failed
      console.log("[ERROR] Request failed: " + error);
    }
  };

  // The following function will be executed only one time at the beginning of the building of the page
  useEffect(() => {
    show_notifications();
  }, []);

  return (
    <div className="ProfileContent">
      {displayNotifications &&
        notifications.slice().reverse().map((notification, index) => (
          <div
            key={index}
            className="NotificationEntry"
            style={{ width: "100%" }}
          >
            <div className="NotificationIcon">
              <img src={event} alt="Event Icon" />
            </div>
            <div className="NotificatioTitles">
              <h3>{notification[3]}</h3>
              <h4>{notification[2]}</h4>
              <h4>{notification[1]}</h4>
            </div>
          </div>
        ))}
      {!displayNotifications && (
        <div className="NotificationEntry" style={{ width: "100%" }}>
          <div className="NotificationIcon">
            <img src={event} alt="Event Icon" />
          </div>
          <div className="NotificatioTitles">
            <h3>No notification available!</h3>
          </div>
        </div>
      )}
    </div>
  );
}

export default ProfileNotifications;
