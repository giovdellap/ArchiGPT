import "../css/Navigator.css";

import { Link, useNavigate } from "react-router-dom";
import face from "../img/face.jpeg";

import logout from "../icons/logout.png";
import user from "../icons/user.png";
import setting from "../icons/setting.png";
import { useState } from "react";

function UserIcon() {
  const [show, setShow] = useState(false);
  const navigate = useNavigate();

  function ToggleUserMenu() {
    setShow(!show);
  }

  const handleLogout = async (event) => {
    event.preventDefault();

    const loggedIn = localStorage.getItem("LoggedUser");
    if (loggedIn) {
      localStorage.clear();
      sessionStorage.clear();
      navigate("/login");
      window.location.replace(window.location.href);
    }
  };

  const toggleDisplayData = () => {
    localStorage.setItem("ProfileData", "true");
    ToggleUserMenu();
    navigate("/home");
  };

  return (
    <div className="UserIcon">
      <img src={user} onClick={ToggleUserMenu}></img>
      {show && (
        <>
          <div id="ProfileDrop">
            <div className="ProfileEntry" onClick={toggleDisplayData}>
              <div className="EntryIcon">
                <img src={user}></img>
              </div>
              <div className="EntryText">Profile</div>
            </div>
            <hr />
            <div className="ProfileEntry" onClick={handleLogout}>
              <div className="EntryIcon">
                <img src={logout}></img>
              </div>
              <div className="EntryText">Log out</div>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default UserIcon;
