import React from 'react';

function UploadFileButton({ handleFileUpload, handleFileSend, file }) {

    return (
        <div>
            <input type="file" onChange={handleFileUpload} />
            <button className="file-send-btn" onClick={handleFileSend} disabled={!file}>Send</button>
        </div>
    );
}

export default UploadFileButton;
