import React, { useEffect, useState } from 'react';

function AdminQeA({ id }) {
  const [questions, setQuestions] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:5005/catalogue/${id}`)
      .then(response => response.json())
      .then(data => setQuestions(data))
      .catch(error => console.log(error));
  }, [id]);

  const handleDeleteQuestion = (id) => {
    fetch(`http://localhost:5005/question/${id}`, {
        method: 'DELETE',
    })
    .then((response) => {
        if (response.status === 200) {
            const updatedQuestion = questions.filter(questions => questions.id !== id);
            setQuestions(updatedQuestion);
        }
    })
    .catch((error) => console.log(error));
  };

  return (
    <div className="container">
      <h2>Questions</h2>
      <table className="table">
        <thead>
          <tr>
            <th>Username</th>
            <th>Question</th>
            <th>Answer</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {questions.map(question => (
            <tr key={question.id}>
              <td>{question.username}</td>
              <td>{question.question}</td>
              <td>{question.answer ? (
                <div>{question.answer}</div>
              ) : (
                <div>Not answered yet...</div>
              )}</td>
              <td><button className="btn btn-danger" onClick={() => handleDeleteQuestion(question.id)}>Delete</button></td>
            </tr> 
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AdminQeA;
