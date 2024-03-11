import { useNavigate } from 'react-router-dom';

function LogoutCustomerButton() {
  const navigate = useNavigate();
    
  const handleLogout = () => {
  // Elimina il token di autenticazione dalla memoria locale durante il logout
  localStorage.removeItem('auth_token');

  // Naviga alla pagina di logout
  navigate('/');
  }
  return (
    <button className="btn btn-outline-light m-2" onClick={handleLogout}><i className="fa fa-user-plus mr-1"></i>Logout</button>
  );
}

export default LogoutCustomerButton;