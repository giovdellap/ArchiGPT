import "../css/Profile.css";

import setting from "../icons/setting.png";
import { useState } from "react";
import UserInfo from "./UserInfo";
import ProfileNotifications from "./ProfileNotifications";
import PopUp from "./PopUp.js";

export const handleRevert = () => {
  localStorage.setItem("deleteAccount", false);
  window.location.reload();
};

function Profile() {
  const [currentTab, setTab] = useState(1);
  const [show, setShow] = useState(false);

  const deleteAccount = localStorage.getItem("deleteAccount") === "true";

  const handleDeleteAccount = () => {
    localStorage.setItem("deleteAccount", true);
    const get = localStorage.getItem("deleteAccount");
    window.location.reload();
  };

  function ToggleUserMenu() {
    setShow(!show);
  }

  function SetTab(id) {
    setTab(id);
  }

  return (
    <div className="Profile">
      {deleteAccount && (
        <div>
          <PopUp
            type="account"
            message={"Do you want to delete your account?"}
          />
        </div>
      )}
      <div className="ProfileCard">
        <div className="ProfileNav">
          <div className="ProfileNavEntry" onClick={() => SetTab(1)}>
            <input
              type="radio"
              id="Profile"
              name="nav"
              checked={1 == currentTab}
              style={{ cursor: "pointer" }}
            />
            <label for="Profile">My Profile</label>
          </div>
          <div
            className="ProfileNavEntry"
            style={{ paddingTop: "15%", color: "red", cursor: "pointer" }}
            onClick={handleDeleteAccount}
          >
            Delete Account
          </div>
        </div>
        <div id="divider"></div>
        {(currentTab == 1 && <UserInfo></UserInfo>) ||
          (currentTab == 2 && <UserInfo></UserInfo>) ||
          (currentTab == 3 && <ProfileNotifications></ProfileNotifications>)}
      </div>
    </div>
  );
}

export default Profile;
