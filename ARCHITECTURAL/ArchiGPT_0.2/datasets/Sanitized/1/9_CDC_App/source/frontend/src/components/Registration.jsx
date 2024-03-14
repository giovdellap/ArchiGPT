import React from "react";
import { useState } from "react";
import  {Link} from "react-router-dom";





const Registration = () => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    
  
   
    const handleSubmit =  (e) => {
      e.preventDefault();
  
          fetch("http://localhost:8003/register", {
          method: "POST",
          
          body: JSON.stringify({
            name: name,
            email: email,
            password: password,
            phone : "111111111"
            
          })
        })
        .then((response) => {
          if (response.status === 200) {
            setName("");
            setEmail("");
            setPassword("");
            alert("User added successfully");
            window.location.reload();
          } else if(response.status === 500){
            alert("User already exists!");
          }
        })
        .catch(error => console.log(error))
    };


    return (
      
      <>
      
      <button type="button" class="btn btn-outline-dark m-2" data-bs-toggle="modal" data-bs-target="#exampleModal">
        <i class="fa fa-user-plus m-1"></i> 
        Register
      </button>


      <div class="modal fade bg-dark overflow-auto" id="exampleModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" data-bs-backdrop="false">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
              <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Register</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
            <div class="modal-body">
            <form onSubmit={handleSubmit}>
                    <input type="hidden" name="_token" value=""></input>
                    <div class="form-group">
                        <label class="control-label"> Full Name</label>
                        <div>
                            <input type="text" class="form-control " placeholder="Enter Your Name" value={name} onChange={(e) => setName(e.target.value)}></input>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Email</label>
                        <div>
                        <input type="email" class="form-control " placeholder="name@example.com" value={email} onChange={(e) => setEmail(e.target.value)}></input>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label">Password</label>
                        <div>
                        <input type="password" class="form-control " placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)}></input>
                        </div>
                    </div>
                    <div className="my-3">
                        <p>Already has an account? <Link to="/" className="text-decoration-underline text-info">Login</Link> </p>
                    </div>
                    
            </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary" onClick={(e) => handleSubmit(e)} data-bs-dismiss="modal">Register</button>
            </div>
          </div>
        </div>
      </div>
      </>
    );
  };
  
  export default Registration;