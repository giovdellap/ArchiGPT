import React from 'react';
import GenerationView from './generationView';
import UploadFileButton from './uploadFileButton';

function GenerationHandler({ generationMessage, handleGenerate, systemSelected, containerSelected, serviceSelected, handleFileUpload, file }) {

    var itemSelected = ""
    systemSelected !== "" ? itemSelected = systemSelected : itemSelected = containerSelected.container + " - " + containerSelected.assistant
    serviceSelected.service !== "" ? itemSelected = itemSelected + "\n " + serviceSelected.service + " - " + serviceSelected.assistant : itemSelected = itemSelected + ""

    return (
        <div className="chat-container">
            {itemSelected !== "" ?   
                <div className="chat-messages">       
                    {generationMessage === "" ?
                            <>
                            <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px'}}>
                                {`No document generated in \n ${itemSelected}`}
                            </div>
                            <div className="chat-input">
                                <UploadFileButton 
                                    handleFileUpload={handleFileUpload} 
                                    handleFileSend={handleGenerate}
                                    file={file}
                                    showFileInput={itemSelected === "Container Design"}
                                />
                            </div>
                            </>
                        : 
                            <>
                            <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px'}}>
                                {`Document generated in \n ${itemSelected} : `}
                            </div>
                            <GenerationView generationMessage={generationMessage} />
                            </>
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
