# ArchiGPT: an LLM-based solution for the generation of microservices architectures

This repository contains code for replicating the experiments in *"ArchiGPT: an LLM-based solution for the generation of microservices architectures"* paper.

## Prerequisites
- [Docker](https://www.docker.com)
- [OpenAI API Key](https://platform.openai.com)

## Setup
Create a `.env` file in the `ArchiGPT/src/api_handler` [directory](ArchiGPT/src/api_handler) and add the following line
```
OPENAI_API_KEY=<YOUR_OPENAI_API_KEY>
```

## Usage
- Run ArchiGPT:
    ```bash
    cd ArchiGPT/src
    docker compose up
    ```

- If the frontend service doesn't start:
    - Delete containers and images using:
        ```bash
        docker rm <CONTAINER_ID>
        docker rmi <IMAGE_ID>
        ```
    - Re-build and start the solution:
        ```bash
        cd ArchiGPT/src/frontend
        npm i
        cd ..
        docker compose up
        ```

- Frontend is available at `localhost:3000` and *mongo-express* is available at `localhost:8081`.\
*Mongo-express* credentials:
    ```
    user: admin
    password: pass
    ```

- You can download the Archi Dataset from this [link](https://zenodo.org/records/14238664). Use these projects to test the system.

- As an example, [generated_architectures](HumanEvaluation/generated_architectures) folder contains the generated architectures using GPT-4o.


## Assistants prompt
Prompts of the employed assistants are available [here](ArchiGPT/src/api_handler/resources) (the prompts are divided by their employed phase, i.e., `System`, `Container` and `Service`).


## Demo of ArchiGPT
<iframe src="https://drive.google.com/file/d/1IYsILJxv5Ve5Ez1HoTD8qhBEcSNDT8A9/preview" width="640" height="480" allow="autoplay"></iframe>

## Evaluation
- Results of the automated evaluation of ArchiGPT are available [here](Metrics/src_metrics/be_node/src/data):
    - [GPT-4o-mini](Metrics/src_metrics/be_node/src/data/4o-mini-runs) contains the runs using GPT-4o-mini model.
    - [GPT-4o](Metrics/src_metrics/be_node/src/data/4o-runs) contains the runs using GPT-4o model.
    - [Students](Metrics/src_metrics/be_node/src/data/studentProjects) contains the runs of the students.

- [HumanEvaluation](HumanEvaluation) contains the sources of the human evaluation: the [questionnaire](HumanEvaluation/questionnaire.pdf) and the [results](HumanEvaluation/results.xlsx). The files used (ArchiGPT generated outputs) in the questionnaire are available in [docs](HumanEvaluation/docs) and [generated_architectures](HumanEvaluation/generated_architectures).
