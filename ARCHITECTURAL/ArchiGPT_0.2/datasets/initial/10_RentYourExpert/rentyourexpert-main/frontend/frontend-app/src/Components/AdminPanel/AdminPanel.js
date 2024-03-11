import React from 'react';
import { Link } from 'react-router-dom';
import LogoutCustomerButton from '../Logout/Logout';
import { Button, ButtonGroup } from 'react-bootstrap';
import CustomersList from './CustomersManagement/CustomersList'
import Navbar from '../Navbar'

function AdminPanel() {
  return (
    <>
    <Navbar />
    <div style={{ display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column", minHeight: "70vh" }}>
      <h1>Welcome to the Admin Panel</h1>
      <p>This is the place where you can manage customers, workers, and requests.</p>
    </div>
    </>
  );
}

export default AdminPanel;
