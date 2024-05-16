TO START THE SYSTEM:

1) Add a .env file in the following directory: ArchiGPT/src/api_handler
    content of the .env file: OPENAI_API_KEY=<YOUR KEY>>

2)from the repo directory execute:
cd ArchiGPT/src
docker compose up

3)If the frontend service doesn't start:
    1) delete containers and images
    2) from the repo directory:
        cd ArchiGPT/src/frontend
        npm i
        cd ..
        docker compose up

4) frontend is available at localhost:3000
    mongo-express is available at localhost:8081
    mongo express credentials:
    user: admin
    password: pass


ASSISTANTS INSTRUCTIONS:
Assistant instructions are located here:
ArchiGPT/src/api_handler_resources
