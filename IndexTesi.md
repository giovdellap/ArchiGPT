# TITLE 1: ARCHIGPT. ARCHIGPT AN LLM-BASED SOFTWARE FOR THE GENERATION OF SOFTWARE ARCHITECTURE
# TITLE 2: ARCHIGPT. A DATASET FOR THE EVALUATION OF ARCHITECTURAL SPECIFICATIONS

## VERSION 1

1) [Introduction - Comune]()

2) [Microservices Architecture - Comune]()
   1) Overview of Containerization
      1) Introduction to Containers
      2) Benefits of Containerization
      3) Popular Containerization Tools
   2) Microservices Architecture
      1) Introduction to Microservices
      2) Microservices vs Monolithic Architecture
      3) Key Characteristics of Microservices
      4) Communication between Microservices
   3) Integration of Containers and Microservices
      1) Role of Containers in Microservices Deployment
      2) Scaling Microservices with Containers
      3) Challenges in Managing Containerized Microservices

3) [LLM - Comune](https://www.overleaf.com/project/66d97be4ccb0bd4078b2a640)
   1) General Overview
      1) Introduction to Large Language Models (LLMs)
      2) Key Concepts in LLMs
      3) Main Types of LLM Architectures
   2) Evolution
      1) Early NLP Techniques
      2) Introduction of Word Embeddings
      3) Transformers and Attention Mechanisms
      4) Growth of LLMs
   3) OpenAI's GPT and its capabilities
      1) OpenAI’s GPT and its Capabilities
      2) Generative and Assistive Functionalities
      3) ChatGPT API Overview

4) [State of the art - Comune](https://www.overleaf.com/project/65d4cfc777e42fd16398f59b)
   1) SE Tasks (?)
      1) General Overview
      2) Trend Analysis
      3) Full System realization
      4) Requirements Classification
      5) Code Evaluation
   2) Techniques (?)
      1) General Overview
      2) Basic Prompting techniques
      3) Chain of Thoughts
      4) Multiple Agents
   3) Training Data
      1) General Overview
      2) Data Sources
      3) Data Types
   4) Evaluation Data and Benchmark
      1) General Overview and Trends

5) [ArchiGPT Overview - Comune]()
   1) Idea Overview
   2) ArchiGPT Overview
   3) Dataset Overview
   4) Evaluation Overview
   
6) Design ArchiGPT - Non Comune - Focus Giovanni
   1) Overview
   2) Prompt Chaining
   3) Technological/Architectural Choices
      1) Docker
      2) Services
      3) Mongo
      4) External OpenAI API
      5) React
   4) API Handler (interaction with OpenAI API)
   5) Assistants
      1) Description and Purpsoe
      2) How they work (framework prompt)
      3) What results we want
      4) Come sono dati gli esempi (magari confronto?)
         1) One-shot learning
         2) Few-shots (in RAG?)
         3) Fine Tuning (?)

7) Dataset for validation - Non Comune - Focus Eugenio
   1) General Overview - Comune
   2) Student's projects description - Comune 
   3) Projects' selection
   4) Projects' cleaning
   5) Projects' documentation generation (code analysis)
   6) Projects Datametrics

8) Validation - Comune
   1) Defnition of Metrics
   2) Definition of Algoritms used for Metrics
   3) Results on Dataset

9) Conclusion - Comune
   1) Thoughts on Results
   2) Future Challenges
   3) Final Conclusion