import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';


function Login() {
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
  
    
    fetch('http://localhost:5001/worker_login/', {
      method: 'POST',
      body: 
        formData
    })
      .then(response => response.json())
      .then(data => {
        if (data.message === 'success') {
          setMessage('Logged in successfully!');
          console.log(data.auth_token);
          localStorage.setItem('auth_token', data.auth_token);
          const authToken = localStorage.getItem('auth_token');
          const authTokenParts = authToken ? authToken.split(';') : [];
          const workerId  = authTokenParts.length > 0 ? authTokenParts[0] : null;
          navigate(`/worker_profile/${workerId}`);
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
    <h1 className="text-center">Login as a Worker</h1>
    <hr />
    <div className="row my-4 h-100">
    <div className="col-md-4 col-lg-4 col-sm-8 mx-auto">
    <form onSubmit={handleSubmit}>
    <div className="my-3">
    <label htmlFor="email" className="form-label">Email</label>
    <input type="email" className="form-control" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} />
    </div>
    <div className="my-3">
    <label htmlFor="password" className="form-label">Password</label>
    <input type="password" className="form-control" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} />
    </div>
    <button type="submit" className="btn btn-primary mb-3">Login</button>
    </form>
    <div className="text-center">
    <p>Don't have an account? <a href="/register" className="text-info">Register here</a>.</p>
    </div>
    {message && <div className="alert alert-danger">{message}</div>}
    </div>
    </div>
    </div>
  );
}

export default Login;
