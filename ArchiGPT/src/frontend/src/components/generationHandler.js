import React from 'react';
import GenerationView from './generationView';
import UploadFileButton from './uploadFileButton';

function GenerationHandler({ generationMessage, handleGenerate, systemSelected, assistantSelected, handleFileUpload, file }) {

    var itemSelected = ""
    systemSelected !== "" ? itemSelected = systemSelected : itemSelected = assistantSelected.container

    return (
        <div className="chat-container">
            {itemSelected !== "" ?   
                <div className="chat-messages">       
                    {generationMessage === "" ?
                            <>
                            <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px'}}>
                                {`No document generated in ${itemSelected}`}
                            </div>
                            <div className="chat-input">
                                <UploadFileButton 
                                    handleFileUpload={handleFileUpload} 
                                    handleFileSend={handleGenerate}
                                    file={file}
                                    showFileInput={itemSelected === "ContainerDesigner"}
                                />
                            </div>
                            </>
                        : 
                            <GenerationView generationMessage={generationMessage} />
                    }
                </div>
            : 
                <div className="chat-messages">
                    <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px'}}>
                        {`Click on an assistant to view or generate documents`}
                    </div>
                </div>
            }
        </div>
    );
}

export default GenerationHandler;
