import React from 'react'
import { NavLink } from 'react-router-dom'
import LogoutCustomerButton from './Logout/Logout';


const Navbar = () => {
    const authToken = localStorage.getItem('auth_token');
    const authTokenParts = authToken ? authToken.split(';') : [];
    const lastAuthTokenPart = authTokenParts.length > 0 ? authTokenParts[authTokenParts.length - 1] : null; 
    const id  = authTokenParts[0]
    if(lastAuthTokenPart === 'C'){
    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark py-3 sticky-top">
            <div className="container">
                <NavLink className="navbar-brand fw-bold fs-4 px-2" to="/"> Rent Your Expert</NavLink>
                <button className="navbar-toggler mx-2" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav m-auto my-2 text-center">
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/">Home </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/Catalogue">Experts</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/about">About</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/contact">Contact</NavLink>
                        </li>
                    </ul>
                    <div className="buttons text-center">
                        <NavLink to={`/customer_profile/${id}`} className="btn btn-outline-light"><i className="fa fa-user-plus mr-1"></i> Profile</NavLink>
                        <LogoutCustomerButton />
                    </div>
                </div>
                


            </div>
        </nav>
    )

    }
    if(lastAuthTokenPart === 'W'){

        return (
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark py-3 sticky-top">
                <div className="container">
                    <NavLink className="navbar-brand fw-bold fs-4 px-2" to="/"> Rent Your Expert</NavLink>
                    <button className="navbar-toggler mx-2" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
    
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav m-auto my-2 text-center">
                            <li className="nav-item">
                                <NavLink className="nav-link" to={`/worker_profile/${id}`}>Profile </NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to={`/worker_profile/${id}/QeA`}>Questions</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to={`/worker_profile/${id}/requests`}>Requests</NavLink>
                            </li>
                            <li className="nav-item">
                            <NavLink className="nav-link" to={`/worker_profile/${id}/reviews`}>Reviews</NavLink>
                            </li>
                        </ul>
                        <div className="buttons text-center">
                            <LogoutCustomerButton />
                        </div>
                    </div>
    
    
                </div>
            </nav>
        )


    }

    if(lastAuthTokenPart === 'A'){

        return (
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark py-3 sticky-top">
                <div className="container">
                    <NavLink className="navbar-brand fw-bold fs-4 px-2" to="/"> Rent Your Expert</NavLink>
                    <button className="navbar-toggler mx-2" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
    
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav m-auto my-2 text-center">
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/admin/customers">Customers </NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/admin/workers">Workers</NavLink>
                            </li>
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/admin/requests">Requests</NavLink>
                            </li>
                            <li className="nav-item">
                            <NavLink className="nav-link" to="/about">About</NavLink>
                            </li>
                        </ul>
                        <div className="buttons text-center">
                            <LogoutCustomerButton />
                        </div>
                    </div>
    
    
                </div>
            </nav>
        )


    }
}

export default Navbar