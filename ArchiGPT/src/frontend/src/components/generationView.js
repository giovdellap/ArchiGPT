import React from 'react';

function GenerationView({ messageSystem }) {

    return (
        <div className="balloon" style={{ whiteSpace: 'pre-line' }}>
            {messageSystem}
        </div>
    );
}

export default GenerationView;