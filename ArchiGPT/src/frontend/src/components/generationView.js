import React from 'react';

function GenerationView({ generationMessage }) {

    return (
        <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px', textAlign: 'left' }}>
            {generationMessage}
        </div>
    );
}

export default GenerationView;