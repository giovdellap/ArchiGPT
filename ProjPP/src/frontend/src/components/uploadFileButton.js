import React from 'react';

function UploadFileButton() {
    const handleFileUpload = () => {
        // Logic to handle file upload
    };

    const handleFileSend = () => {
        // Logic to send uploaded file
    };

    return (
        <div>
            <input type="file" onChange={handleFileUpload} />
            <button className="file-send-btn" onClick={handleFileSend}>Send</button>
        </div>
    );
}

export default UploadFileButton;
