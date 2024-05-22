import React from 'react';

function UploadFileButton({ handleFileUpload, handleFileSend, file, showFileInput }) {

    return (
        <div>
            <input type="file" onChange={handleFileUpload} disabled={!showFileInput} />
            <button className="file-send-btn" onClick={handleFileSend} disabled={!file && showFileInput}>Send</button>
        </div>
    );
}

export default UploadFileButton;
