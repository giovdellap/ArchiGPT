import React from 'react';

function RegenerateButton({ handleRegenerate }) {

    return (
		<div className="styleRegendiv">
			<button className="file-retry-btn" onClick={handleRegenerate}>    
				<svg 
					xmlns="http://www.w3.org/2000/svg" 
					viewBox="0 0 24 24" 
					fill="none" 
					stroke="currentColor" 
					strokeWidth="2" 
					strokeLinecap="round" 
					strokeLinejoin="round" 
					style={{ width: '20px', height: '20px' }}
				>
				<polyline points="1 4 1 10 7 10"></polyline>
				<path d="M3.51 15a9 9 0 1 0 2.13-9.36L1 10"></path>
				</svg>
			</button>
		</div>
    );
}

export default RegenerateButton;
