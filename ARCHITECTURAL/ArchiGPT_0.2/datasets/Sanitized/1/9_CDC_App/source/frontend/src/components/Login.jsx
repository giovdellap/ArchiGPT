import React from "react";
import { useState } from "react";







const Login = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    
    
  
   
    const handleSubmit =  (e) => {
      e.preventDefault();
      e.preventDefault();
  
      fetch("http://localhost:8003/login", {
      method: "POST",
      
      body: JSON.stringify({
        email: email,
        password: password
        
      })
    })
    .then((response) => {
      if (response.status === 200) {
        response.text().then(data =>localStorage.setItem('auth_token', data));
        setEmail("");
        setPassword("");
        alert("User logged successfully");
        window.location.reload();
      } else if(response.status === 500){
        alert("Wrong email or pasword!");
      }
    })
    .catch(error => console.log(error))
    };


    return (
      
      <>
        
        <button type="button" class="btn btn-outline-dark m-2" data-bs-toggle="modal" data-bs-target="#myModal">
            <i class="fa fa-sign-in m-1"></i> 
          Login
        </button>

        <div class="modal fade  bg-dark overflow-auto" id="myModal" tabIndex="-1" aria-labelledby="exampleModalLabel" role="dialog" aria-hidden="true" data-bs-backdrop="false">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Login</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
                <div class="modal-body">
                <form onSubmit={handleSubmit}>
                      <input type="hidden" name="_token" value=""></input>
                      <div class="form-group">
                          <label class="control-label">Email</label>
                          <div>
                              <input type="email" class="form-control " placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)}></input>
                          </div>
                      </div>
                      <div class="form-group">
                          <label class="control-label">Password</label>
                          <div>
                          <input type="password" class="form-control " placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}></input>
                          </div>
                      </div>
                     
              </form>
        
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                  <button type="button" class="btn btn-primary" onClick={(e) => handleSubmit(e)} data-bs-dismiss="modal">Login</button>
                </div>
            </div>
          </div>
        </div>
      </>
      );
  };
  
  export default Login;