import React from 'react';
import { Link } from 'react-router-dom'; 

function Header() {
  return (
    <header style={{ backgroundColor: '#333', color: 'white', padding: '20px', textAlign: 'center', position: 'fixed', top: '0', left: '0', width: '100%', zIndex: '9999' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <Link to="/" style={{ textDecoration: 'none', color: 'white', marginLeft: '20px' }}>
          Back to Homepage
        </Link>
        <h1>Project Name</h1>
        <div style={{ width: '100px' }}></div>
      </div>
    </header>
  );
}

export default Header;
