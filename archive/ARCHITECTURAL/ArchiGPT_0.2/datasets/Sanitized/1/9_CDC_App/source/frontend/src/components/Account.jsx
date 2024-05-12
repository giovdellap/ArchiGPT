import React from "react";
import { useNavigate } from 'react-router-dom';
import { Footer, Navbar } from "../components";

function Account() {
    const navigate = useNavigate();
    var userInfo =localStorage.getItem('auth_token');
    if (userInfo){
         userInfo =userInfo.slice(1,-1).replaceAll("'","").split(", ");
    }

    const handleLogout = () => {

        localStorage.removeItem('auth_token');


        navigate('/');
    }
    return (
        <>
            <Navbar />
            <div class="container my-3 py-3">
                <h1 class="text-center">Account Info</h1>
                <hr />
                <div class="row my-4 h-100">
                    <div class="col-md-6 col-lg-6 col-sm-12 mx-auto">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Nome: {userInfo[1]}</h5>
                                <h5 class="card-title">Email: {userInfo[2]}</h5>
                                <h5 class="card-title">Password: {userInfo[4]}</h5>
                            </div>
                            <div class="text-center">
                                <button
                                    class="btn btn-outline-dark m-2"
                                    onClick={handleLogout}><i className="fa fa-sign-out m-1"></i>

                                    Logout
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
            <Footer />
            </>
            );
};

export default Account;