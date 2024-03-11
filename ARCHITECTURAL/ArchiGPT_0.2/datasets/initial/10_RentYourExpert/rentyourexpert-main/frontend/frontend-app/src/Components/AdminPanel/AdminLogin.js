import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginAdmin() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();
  

  const handleSubmit = (event) => {
    event.preventDefault();

    const form = event.target;
    const email = form.email.value;
    const password = form.password.value;
    
    let formData = new FormData();
    formData.append('email', email);
    formData.append('password', password);
  
    
    fetch('http://localhost:5002/login_admin/', {
      method: 'POST',
      body: formData
    })
      .then(response => response.json())
      .then(data => {
        if (data.message === 'success') {
          setMessage('Logged in successfully!');
          localStorage.setItem('auth_token', data.auth_token);
          navigate('/admin');  
          window.location.reload()
        } else {
          setMessage('Incorrect email/password!');
        }
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  return (
    <div className="container my-3 py-3">
        <h1 className="text-center">Admin Login</h1>
        <hr />
        <div className="row my-4 h-100">
          <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
            <form onSubmit={handleSubmit}>
              <div className="my-3">
                <label htmlFor="floatingInput" className="form-label">Email address</label>
                <input
                  type="email"
                  className="form-control"
                  id="floatingInput"
                  placeholder="name@example.com"
                  name="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </div>
              <div className="my-3">
                <label htmlFor="floatingPassword" className="form-label">Password</label>
                <input
                  type="password"
                  className="form-control"
                  id="floatingPassword"
                  placeholder="Password"
                  name="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
              <div className="text-center">
                <button className="my-2 mx-auto btn btn-dark" type="submit">
                  Login
                </button>
              </div>
            </form>
            <p className="text-center">{message}</p>
          </div>
        </div>
      </div>
  );
}

export default LoginAdmin;