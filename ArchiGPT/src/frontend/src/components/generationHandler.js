import React from 'react';
import GenerationView from './generationView';
import UploadFileButton from './uploadFileButton';
import RegenerateButton from './regenerateButton';

function GenerationHandler({ generationMessage, handleGenerate, handleRegenerate, systemSelected, containerSelected, serviceSelected, handleFileUpload, file }) {

    var generationHandler = true
    var itemSelected = ""
    systemSelected !== "" ? itemSelected = systemSelected : itemSelected = containerSelected.container + " - " + containerSelected.assistant
    serviceSelected.service !== "" ? itemSelected = itemSelected + "\n " + serviceSelected.service + " - " + serviceSelected.assistant : itemSelected = itemSelected + ""

    // Use cases without generation
    if(systemSelected !== "" || serviceSelected.assistant === "ServiceDescriptionGenerator" ) generationHandler = false

    return (
        <div className="chat-container"> 
            {itemSelected && itemSelected !== "" && itemSelected !== " - " ?   
                <div className="chat-messages"  style={{ width: '135%', height: '500px' }}>       
                    {generationMessage === "" ?
                            <>
                            <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px' }}>
                                {`No document generated in \n ${itemSelected}`}
                            </div>
                            <div className="chat-input" style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
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
                                {generationHandler ? <RegenerateButton handleRegenerate={handleRegenerate} /> : <></>}
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
