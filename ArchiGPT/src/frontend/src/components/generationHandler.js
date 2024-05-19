import React from 'react';
import GenerationView from './generationView';
import UploadFileButton from './uploadFileButton';

function GenerationHandler({ messageSystem, handleGenerate, systemSelected, handleFileUpload, file }) {

    return (
        <div className="chat-container">
            {systemSelected && systemSelected !== "" ?   
                <div className="chat-messages">       
                    {messageSystem === "" ?
                            <>
                            <GenerationView messageSystem={`No document generated in ${systemSelected}`} />
                            <div className="chat-input">
                                <UploadFileButton 
                                    handleFileUpload={handleFileUpload} 
                                    handleFileSend={handleGenerate}
                                    file={file}    
                                />
                            </div>
                            </>
                        : 
                            <GenerationView messageSystem={messageSystem} />
                    }
                </div>
            : 
                <div className="chat-messages">
                    <GenerationView messageSystem="Click on an assistant to view or generate documents"/>
                </div>
            }
        </div>
    );
}

export default GenerationHandler;
