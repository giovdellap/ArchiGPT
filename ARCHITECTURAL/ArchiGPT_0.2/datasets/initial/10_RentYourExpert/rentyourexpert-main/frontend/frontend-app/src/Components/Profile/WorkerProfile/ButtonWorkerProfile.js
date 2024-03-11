import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';



function ProfileButton() {
  const [id, setId] = useState('');
  const navigate = useNavigate();
  
  useEffect(() => {
    const authToken = localStorage.getItem('auth_token');
    const authTokenParts = authToken ? authToken.split(';') : [];
    const customerId = authTokenParts[0];
    setId(customerId);
  }, []);

  const handleClick = () => {
    navigate(`/worker_profile/${id}`);
  };

  return (
    <button onClick={handleClick}>View Profile</button>
  );
}

export default ProfileButton;