import React from 'react'
import { NavLink } from 'react-router-dom'
import Login from './Login'
import Registration from './Registration'


const Navbar = () => {
    var userInfo =localStorage.getItem('auth_token');
    if (userInfo){
         userInfo =userInfo.slice(1,-1).replaceAll("'","").split(", ");
    }

    console.log(userInfo);
    return (
        <>
        <nav class="navbar navbar-expand-lg navbar-light bg-light py-3 sticky-top">
            <div class="container">
                <NavLink className="navbar-brand fw-bold fs-4 px-2" to="/"> CDC SHOP</NavLink>
            
                    <ul className="navbar-nav m-auto my-2 text-center">
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/">Home </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/products">Products</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/about">About</NavLink>
                        </li>
                        <li classname="nav-item">
                            <NavLink className="nav-link" to="/contact">Contact</NavLink>
                        </li>
                        {userInfo && userInfo[5]==='a' ?   <li classname="nav-item">
                            <NavLink className="nav-link" to="/merchants">Merchant Panel</NavLink>
                        </li> :
                        <li></li>
                        }
                    </ul>
                   
                    {userInfo ? <div>Benvenuto {userInfo[1]}! <NavLink to="/account" className="btn btn-outline-dark m-2"><i class="fa fa-user m-1"></i>Account</NavLink>
                    <NavLink to={"/cart/"+ userInfo[0]} className="btn btn-outline-dark m-2"><i class="fa fa-shopping-cart m-1"></i> Cart </NavLink>
                    </div> 
                    : <div><Login/><Registration/></div>}
                    
                    
                    
                
               
            </div>
            
        </nav>
        
    </>
    )
}

export default Navbar