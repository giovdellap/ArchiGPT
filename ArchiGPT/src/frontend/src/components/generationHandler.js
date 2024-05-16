import React from 'react';
import GenerationView from './generationView';

function GenerationHandler({ messageSystem }) {
    return (
        <div className="chat-container">
            {messageSystem && messageSystem !== "" ?          
                <div className="chat-messages">
                    <GenerationView messageSystem={messageSystem}/>
                </div>
            : 
                <div className="chat-messages">
                    <GenerationView messageSystem="No generation found"/>
                    <button onClick={() => { } }>Generate</button>
                </div>
            }
        </div>
    );
}

export default GenerationHandler;
