import React, { useState } from 'react';
import { Link } from 'react-router-dom';


function RegisterCustomer() {
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
  
    const handleSubmit = (event) => {
    event.preventDefault();

    const form = event.target;
    const name = form.name.value;
    const surname = form.surname.value;
    const username = form.username.value;
    const email = form.email.value;
    const password = form.password.value;

    let formData = new FormData();
    formData.append('name', name);
    formData.append('surname', surname);
    formData.append('username', username);
    formData.append('email', email);
    formData.append('password', password);


    fetch('http://localhost:5003/register_customer/', {
      method: 'POST',
      body: formData
    })
      .then(response => response.json())
      .then(data => {
        setMessage(data.message);
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  return (
    <div className="container my-3 py-3">
        <h1 className="text-center">Customer Registration Form</h1>
        <hr />
        <div className="row my-4 h-100">
          <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
            <form onSubmit={handleSubmit}>
              <div className="form my-3">
                <label htmlFor="name">Name:</label>
                <input name="name" type="text" className="form-control" value={name} onChange={(e) => setName(e.target.value)} />
              </div>
              <div className="form my-3">
                <label htmlFor="surname">Surname:</label>
                <input name="surname" type="text" className="form-control" value={surname} onChange={(e) => setSurname(e.target.value)} />
              </div>
              <div className="form my-3">
                <label htmlFor="email">Email:</label>
                <input name="email" type="text" className="form-control" value={email} onChange={(e) => setEmail(e.target.value)} />
              </div>
              <div className="form my-3">
                <label htmlFor="username">Username:</label>
                <input name="username" type="text" className="form-control" value={username} onChange={(e) => setUsername(e.target.value)} />
              </div>
              <div className="form my-3">
                <label htmlFor="password">Password:</label>
                <input name="password" type="password" className="form-control" value={password} onChange={(e) => setPassword(e.target.value)} />
              </div>
              <div className="text-center">
                <button type="submit" className="my-2 mx-auto btn btn-dark">Register</button>
              </div>
            </form>
            <p>{message}</p>
            <div className="my-3">
              <p>Already have an account? <Link to="/login_customer" className="text-decoration-underline text-info">Login</Link></p>
            </div>
          </div>
        </div>
      </div>
  );
}

export default RegisterCustomer;

