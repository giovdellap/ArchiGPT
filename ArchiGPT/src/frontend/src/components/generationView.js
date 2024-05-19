import React from 'react';

function GenerationView({ messageSystem }) {

    return (
        <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px' }}>
            {messageSystem}
        </div>
    );
}

export default GenerationView;