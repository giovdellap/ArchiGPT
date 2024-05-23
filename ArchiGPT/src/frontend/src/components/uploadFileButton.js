import React from 'react';

function UploadFileButton({ handleFileUpload, handleFileSend, file, showFileInput }) {

    return (
        <div>
            {showFileInput ?
                <>
                    <input type="file" onChange={handleFileUpload}/>
                    <button className="file-send-btn" onClick={handleFileSend} disabled={!file}>Send</button>
                </>
                : 
                <button className="file-send-btn" onClick={handleFileSend}>Generate</button>
            }
        </div>
    );
}

export default UploadFileButton;
