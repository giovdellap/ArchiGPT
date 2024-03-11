import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import AdminQeA from '../QeAManagement/AdminQeA';
import AdminReviews from '../ReviewManagement/AdminReview';
import Navbar from '../../Navbar'


function WorkersManagement() {
  const { id } = useParams();
  const [worker, setWorker] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    fetch(`http://localhost:5002/workers/${id}`)
      .then((response) => response.json())
      .then((data) => setWorker(data[0]))
      .catch((error) => console.log(error));
  }, [id]);

  const handleDeleteWorker = () => {
    fetch(`http://localhost:5002/workers/${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.status === 200) {
          alert("Worker deleted successfully");
          navigate('/admin/workers');
        }
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
    <Navbar />
    <div style={{marginTop:'20px'}} className="container">
      <div className="card my-5">
        <h5 className="card-header">Worker Details</h5>
        <div className="card-body">
          <h5 className="card-title">{worker.name} {worker.surname}</h5>
          <p className="card-text"><strong>Profession:</strong> {worker.profession}</p>
          <p className="card-text"><strong>Location:</strong> {worker.location}</p>
          <p className="card-text"><strong>Description:</strong> {worker.description}</p>
          <p className="card-text"><strong>Email:</strong> {worker.email}</p>
          <p className="card-text"><strong>Phone:</strong> {worker.phone}</p>
          <p className="card-text"><strong>Address:</strong> {worker.address}</p>
          <p className="card-text"><strong>Availability:</strong> {worker.available}</p>
          <p className="card-text"><strong>Password:</strong> {worker.password}</p>
          <button className="btn btn-danger mr-1" onClick={handleDeleteWorker}>Delete</button>
          <br></br>
          <br></br>

          <AdminQeA id={id} />
          <AdminReviews id={id} />
        </div>
      </div>
    </div>
    </>
  );
}

export default WorkersManagement;
