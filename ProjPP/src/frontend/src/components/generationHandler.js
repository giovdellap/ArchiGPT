import React, { useEffect, useState} from 'react';
import GenerationView from './generationVIew';

function GenerationHandler() {
	const [threadID, serThreadID] = useState(null);

    useEffect(() => {
        
	}, []);


    return (
			<div>
				<GenerationView threadID={threadID} />
			</div>
    );
}

export default GenerationHandler;