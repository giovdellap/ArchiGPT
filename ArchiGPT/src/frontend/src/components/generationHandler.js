import React from 'react';
import GenerationView from './generationView';

function GenerationHandler() {
    return (
        <div className="chat-container">
            <div className="chat-messages">
                <GenerationView />
            </div>
        </div>
    );
}

export default GenerationHandler;
