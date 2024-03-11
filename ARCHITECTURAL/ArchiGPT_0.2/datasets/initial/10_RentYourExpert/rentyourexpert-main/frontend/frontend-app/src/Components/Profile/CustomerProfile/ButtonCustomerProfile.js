import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';



function ProfileButton() {
  const authToken = localStorage.getItem('auth_token');
  const authTokenParts = authToken ? authToken.split(';') : [];
  const customerId = authTokenParts[0];
  const { id } = parseInt(customerId)
  const navigate = useNavigate();
  

  const handleClick = () => {
    navigate(`/customer_profile/${id}`);
  };

  return (
    <button onClick={handleClick}>View Profile</button>
  );
}



export default ProfileButton;