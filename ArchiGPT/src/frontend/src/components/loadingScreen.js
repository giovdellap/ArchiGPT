import React from 'react';

function LoadingScreen ({ messageText }) {
  return (
    <div className="loading-overlay" style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0, 0, 0, 0.5)', zIndex: 999, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      <div className="loading-screen" style={{ backgroundColor: 'white', padding: '20px', borderRadius: '10px' }}>
        <div style={{ margin: '30px'}}></div>
        <div className="spinner"></div>
        <div style={{ margin: '30px'}}>{messageText}</div>
      </div>
    </div>
  );  
};

export default LoadingScreen;