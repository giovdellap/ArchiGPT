import React from 'react';

function GenerationView({ generationMessage }) {

    const isEndpointsMessage = generationMessage.startsWith("ENDPOINTS:");
    let endpointsData = [];

    if (isEndpointsMessage) {
        try {
          const jsonStartIndex = generationMessage.indexOf("[");
          endpointsData = JSON.parse(generationMessage.slice(jsonStartIndex));
        } catch (error) {
          console.error('Error parsing generationMessage JSON:', error);
        }
      }

    return (
        <div className="balloon" style={{ whiteSpace: 'pre-line', margin: '50px', textAlign: 'left' }}>
            {!isEndpointsMessage && generationMessage}

            {isEndpointsMessage && (
                <>
                <h4>ENDPOINTS:</h4>

                {/* Render the table if data exists */}
                {endpointsData.length > 0 && (
                    <table border="1" cellPadding="10">
                    <thead>
                        <tr>
                        <th>HTTP Method</th>
                        <th>Endpoint URL</th>
                        <th>Description</th>
                        <th>User Stories</th>
                        </tr>
                    </thead>
                    <tbody>
                        {endpointsData.map((endpoint, index) => (
                        <tr key={index}>
                            <td>{endpoint.Method}</td>
                            <td>{endpoint.URL}</td>
                            <td>{endpoint.Description}</td>
                            <td>{endpoint.UserStories.length > 0 ? endpoint.UserStories.join(", ") : "N/A"}</td>
                        </tr>
                        ))}
                    </tbody>
                    </table>
                )}
                </>
            )}
        </div>
    );
}

export default GenerationView;