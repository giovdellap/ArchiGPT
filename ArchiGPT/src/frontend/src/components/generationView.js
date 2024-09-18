import React from 'react';
import ReactMarkdown from 'react-markdown';

function GenerationView({ generationMessage }) {

    return (
        <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px', textAlign: 'left' }}>
            {generationMessage}
            <ReactMarkdown>{generationMessage}</ReactMarkdown>
        </div>
    );
}

export default GenerationView;