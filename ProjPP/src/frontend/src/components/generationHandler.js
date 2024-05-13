import React from 'react';
import GenerationView from './generationView';
import UploadFileButton from './uploadFileButton';

function GenerationHandler() {
    return (
        <div className="chat-container">
            <div className="chat-messages">
                <GenerationView />
            </div>
            <div className="chat-input">
                <UploadFileButton />
            </div>
        </div>
    );
}

export default GenerationHandler;
