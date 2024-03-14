import "../css/Navigator.css";

import { Link, useNavigate, navigate } from "react-router-dom";

import calendar from "../icons/calendar.png";
import team from "../icons/team.png";
import profile from "../icons/users.png";

function NavBar() {
  const navigate = useNavigate();

  const ToggleTeams = () => {
    navigate("/home/teams");
  };

  const GoToAgenda = () => {
    localStorage.setItem("ProfileData", "false");
    navigate("/home");
  }

  const GoToProfile = () => {
    localStorage.setItem("ProfileData", "true");
    navigate("/home");
  }

  return (
    <div className="NavContainer">
      <div className="IconContainer">
        <div className="IconEntry" style={{cursor:"pointer"}}>
          <img src={calendar} onClick={GoToAgenda}></img>
        </div>
        <div className="IconEntry" style={{cursor:"pointer"}}>
          <img src={team} onClick={ToggleTeams}></img>
        </div>
        <div className="IconEntry" style={{cursor:"pointer"}}>
          <img src={profile} onClick={GoToProfile}></img>
        </div>
      </div>
    </div>
  );
}

export default NavBar;
