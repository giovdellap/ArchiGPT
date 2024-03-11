import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import Navbar from '../../Navbar'


function WorkersList() {
  const [worker, setworker] = useState([]);

  useEffect(() => {
    const authToken = localStorage.getItem('auth_token');
    fetch("http://localhost:5002/workers")
      .then((response) => response.json())
      .then((data) => setworker(data))
      .catch((error) => console.log(error));
  }, []);

  return (
    <>
    <Navbar />
    <div style={{marginTop:'20px'}} className="container">
      <h1 className="my-4">Worker List</h1>
      <div className="list-group">
        {worker.map((worker, index) => (
          <Link
            to={`/admin/workers/${worker.id}`}
            key={index}
            className="list-group-item list-group-item-action"
          >
            {worker.name} {worker.surname}
          </Link>
        ))}
      </div>
      <Link to="/admin/workers/new">
        <button className="btn btn-primary mt-4">Add new worker</button>
      </Link>
    </div>
    </>
  );
}

export default WorkersList;
