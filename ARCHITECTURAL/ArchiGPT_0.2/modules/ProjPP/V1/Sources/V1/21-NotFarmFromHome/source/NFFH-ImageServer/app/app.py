from flask import Flask, request, jsonify, send_file
import os
import base64

app = Flask(__name__)

# Define the directory where you'll store the uploaded images
UPLOAD_FOLDER = 'uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/images/<role>/upload', methods=['POST'])
def upload_image(role):
    
        if role == "farmer":
                
            data = request.get_json()  # Assuming JSON request with base64 data
            
            base64_image = data['base64_image'].replace("data:image/jpeg;base64,","",1)
            
            path_folder = UPLOAD_FOLDER + "/farmer/"
            
            if not os.path.exists(path_folder):
                os.makedirs(path_folder)
            
            # Decode the base64 image data
            image_data = base64.b64decode(base64_image)
            
            # Create a unique filename for the image
            filename = f'image_{len(os.listdir(path_folder)) + 1}.jpeg'
            filepath = os.path.join(path_folder, filename)
            
            # Save the decoded image to the 'uploads' directory
            with open(filepath, 'wb') as image_file:
                image_file.write(image_data)
            
            # Construct the URL for the uploaded image
            image_url = f"http://localhost:8080/images/{role}/{filename}"
        
            return {'url': image_url}, 200
        
        return {'url': "errore"}
    

@app.route('/images/<role>/<image_filename>', methods=['GET'])
def get_image(role,image_filename):
    
    path_folder = f"{UPLOAD_FOLDER}/{role}"
    
    # Construct the path to the image
    image_path = os.path.join(path_folder, image_filename)
    
    # Serve the image using send_file
    return send_file(image_path, as_attachment=False)

if __name__ == '__main__':
    app.run(debug=True, port=9705, host='0.0.0.0')


