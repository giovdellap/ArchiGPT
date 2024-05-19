import React from 'react';

function LoadingScreen ({ messageText }) {
  return (
    <div className="loading-screen" style={{ margin: '100px'}}>
      <div className="spinner"></div>
      <div style={{ margin: '30px'}}>{messageText}</div>
    </div>
  );
};

export default LoadingScreen;