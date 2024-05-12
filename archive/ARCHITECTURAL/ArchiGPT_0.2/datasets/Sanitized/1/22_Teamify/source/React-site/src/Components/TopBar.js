import "../css/Navigator.css";

import { Link } from "react-router-dom";

export default function TopBar() {
  return (
    <div className="TopContainer">
      <div className="BarEntry">
        Function 1
        <div className="DropContent">
          <div className="DropEntry">sub function 1</div>
          <div className="DropEntry">sub function 2</div>
          <div className="DropEntry">sub function 3</div>
        </div>
      </div>
      <div className="BarEntry">
        Function 2
        <div className="DropContent">
          <div className="DropEntry">sub function 1</div>
          <div className="DropEntry">sub function 2</div>
          <div className="DropEntry">sub function 3</div>
        </div>
      </div>
      <div className="BarEntry">
        Function 3
        <div className="DropContent">
          <div className="DropEntry">sub function 1</div>
          <div className="DropEntry">sub function 2</div>
          <div className="DropEntry">sub function 3</div>
        </div>
      </div>
    </div>
  );
}
