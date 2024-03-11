import React, { useEffect, useState } from 'react';
import {
  MDBCol,
  MDBContainer,
  MDBRow,
  MDBCard,
  MDBCardText,
  MDBCardBody,
  MDBCardImage,
  MDBBtn,
  MDBBreadcrumb,
  MDBBreadcrumbItem,
  MDBProgress,
  MDBProgressBar,
  MDBIcon,
  MDBListGroup,
  MDBListGroupItem,
  MDBCardGroup
} from 'mdb-react-ui-kit';
import { FaGlobe, FaGithub, FaTwitter, FaInstagram, FaFacebook } from 'react-icons/fa';
import { Container, Row, Col, Card, ListGroup, ListGroupItem, Button, Form, Input } from 'react-bootstrap';
import Modal from "react-modal";
import Navbar from '../Navbar';




function QeA({ id }) {
  const [questions, setQuestions] = useState([]);
  const [editMode, setEditMode] = useState(false);
  const [editingQuestionId, setEditingQuestionId] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isWorkerModalOpen, setIsWorkerModalOpen] = useState(false);

  const authToken = localStorage.getItem('auth_token');
  const authTokenParts = authToken ? authToken.split(';') : [];
  const firstAuthTokenPart = authTokenParts.length > 0 ? authTokenParts[0] : null;
  const lastAuthTokenPart = authTokenParts.length > 0 ? authTokenParts[authTokenParts.length - 1] : null;


  useEffect(() => {
    fetch(`http://localhost:5005/catalogue/${id}`)
      .then(response => response.json())
      .then(data => setQuestions(data))
      .catch(error => console.log(error));
  }, [id]);



  const handleAnswerUpdate = (event, questionId) => {
    event.preventDefault();
    const answer = event.target.answer.value;
    const updatedQuestions = questions.map(question => {
      if (question.id === questionId) {
        return { ...question, answer };
      } else {
        return question;
      }
    });
    setQuestions(updatedQuestions);
    fetch(`http://localhost:5005/worker_profile/${id}/qea/${questionId}`, {
      method: 'PUT',
      headers: {
        'Authorization': authToken,
      },
      body: new FormData(event.target),
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Failed to update answer');
      };
      window.location.reload()
    })
    .catch(error => console.error(error));
  };


  const handleAnswerSubmit = (event, questionId) => {
    event.preventDefault();
    const answer = event.target.answer.value;
    fetch(`http://localhost:5005/worker_profile/${id}/qea/${questionId}`, {
      method: 'POST',
      headers: {
        'Authorization': authToken,
      },
      body: new FormData(event.target),
    })
      .then(response => {
        if (response.ok) {
          setQuestions(questions => {
            const updatedQuestions = questions.map(question => {
              if (question.id === questionId) {
                return { ...question, answer };
              } else {
                return question;
              }
            });
            return updatedQuestions;
          });
        } else {
          throw new Error('Failed to add answer');
        }
      })
      .catch(error => console.error(error));
  };

  const handleQuestionSubmit = (event) => {
    event.preventDefault();
    const question = event.target.question.value;
    fetch(`http://localhost:5005/catalogue/${id}`, {
      method: 'POST',
      headers: {
        'Authorization': authToken,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: `question=${question}`,
    })
      .then(response => {
        if (response.ok) {
          setQuestions(questions);
          setIsModalOpen(false);
          window.location.reload();
        } else {
          throw new Error('Failed to add question');
        }
      })
      .catch(error => console.error(error));
  };

  

  if (lastAuthTokenPart=='W' && firstAuthTokenPart === id ) {
    return (
      <>
      <Navbar />
      <div className="w-100" style={{ marginTop:'20px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <MDBCol lg="8">
        <MDBCard className="mb-4">
          <MDBCardBody>
            <MDBCardText>
              <h2>Questions</h2>
              {questions.map(question => (
                <MDBCard key={question.id}>
                  <MDBCardBody style={{ textAlign: 'left' }}>
                  <MDBRow>
                  <MDBCol md="3">
                    <MDBCardImage variant="left" src={question.image_url}  className="rounded-circle" style={{ width: '50px', height: '50px' }} />
                  </MDBCol>
                  <MDBCol md="9">
                    <div>Username: {question.username}</div>
                    <div className="fw-bolder">{question.question}</div>
                    <div>                      
                      <MDBRow>
                        <MDBCol sm="9">
                        {question.answer ? (
                              <div>
                                {editingQuestionId === question.id ? (
                                  <Modal isOpen={isWorkerModalOpen} className="modal-dialog-centered custom-modal">
                                    <div className="modal-dialog" role="document">
                                      <div className="modal-content">
                                        <h2>Edit answer</h2>
                                        <form onSubmit={event => handleAnswerUpdate(event, question.id)}>
                                          <input type="text" name="answer" defaultValue={question.answer} />
                                          <div>
                                            <button type="submit">Submit</button>
                                            <button type="button" onClick={() => window.location.reload()}>Cancel</button>
                                          </div>
                                        </form>
                                      </div>
                                    </div>
                                  </Modal>
                                ) : (
                                  <div>
                                    {question.answer}
                                    <br />
                                    <button className="btn btn-primary" onClick={() => {
                                      setIsWorkerModalOpen(true);
                                      setEditingQuestionId(question.id)
                                    }} style={{ display: 'block', marginTop: '10px' }}>Edit</button>
                                  </div>
                                )}
                              </div>
                            ) : (
                              <form onSubmit={event => handleAnswerSubmit(event, question.id)}>
                              <div className="input-group mb-3">
                              <input type="text" class="form-control rounded" name="answer" placeholder="Enter your answer here" aria-label="Enter your answer here" aria-describedby="submit-answer" />
                                <div className="input-group-append mt-2">
                                  <button style={{ marginLeft: '10px' }} className="btn btn-primary" type="submit" id="submit-answer">Submit</button>
                                </div>
                              </div>
                            </form>
                            )}
                        </MDBCol>
                      </MDBRow>
                    </div>
                    </MDBCol>
                    </MDBRow>
                  </MDBCardBody>
                  <hr />
                </MDBCard>
              ))}
            </MDBCardText>
          </MDBCardBody>
        </MDBCard>
      </MDBCol>
    </div>
    </>
  );
  } else {
    return (
      <div>
       
  <MDBCol lg="12">
    <MDBCard className="mb-4">
      <MDBCardBody>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
          <h2 style={{ textAlign: 'left' }}>Questions</h2>
          <button type="button" class="btn btn-primary" onClick={() => setIsModalOpen(true)}>Add Question</button>
        </div>
        <Modal isOpen={isModalOpen} className="modal-dialog-centered custom-modal">
          <div className="modal-dialog" role="document">
            <div className="modal-content">
              <h2>Add new question</h2>
              <form onSubmit={handleQuestionSubmit}>
                <input
                  type="text"
                  name="question"
                />
                <div>
                  <button type="submit">Submit</button>
                  <button type="button" onClick={() => setIsModalOpen(false)}>Cancel</button>
                </div>
              </form>
            </div>
          </div>
        </Modal>
        <hr />
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)', gap: '1rem' }}>
          {questions.map((question) => (
            <MDBCard key={question.id} className="mb-3" style={{width: '95%', marginBottom: '1rem'}}>
              <MDBCardBody>
              <MDBRow>
                <MDBCol md="2">
                  <MDBCardImage variant="left" src={question.image_url} className="rounded-circle" style={{ width: '50px', height: '50px' }} />
                </MDBCol>
                <MDBCol md="10" className="d-flex align-items-center">
                  <div style={{ textAlign: 'left' }}>
                    <div>Username: {question.username}</div>
                    <div>Question: {question.question}</div>
                    <div>
                      <div>Answer: {question.answer ? question.answer : "Not answered yet..."}</div>
                    </div>
                  </div>
                </MDBCol>
              </MDBRow>
              </MDBCardBody>
            </MDBCard>
          ))}
        </div>
      </MDBCardBody>
    </MDBCard>
  </MDBCol>
 
</div>
     
    );
  }
}

export default QeA;


