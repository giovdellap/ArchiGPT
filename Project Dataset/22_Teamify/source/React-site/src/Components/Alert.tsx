import React from "react";
import { useState } from "react";
import "../css/Alert.css";

interface Props {
  children: ReactNode;
  state: string;
  onClick: () => void;
}

function Alert({ children, state, onClick }: Props) {
  const [visible, visibleState] = useState(true);
  const class_for_color = "alert alert-" + state.toString() + " mb-0 mt-0";

  if (visible)
    return (
      <div className="row">
        <div className={class_for_color} role="alert">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            fill="currentColor"
            className="bi bi-x custom-icon"
            viewBox="0 0 16 16"
            key={children}
            onClick={() => {
              visibleState(false);
              onClick();
            }}
          >
            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z" />
          </svg>
          {children}
        </div>
      </div>
    );
}

export default Alert;
