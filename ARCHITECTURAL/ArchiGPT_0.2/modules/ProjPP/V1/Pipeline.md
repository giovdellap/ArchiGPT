# ProjPP Pipeline

## Glossary

- ProjSet: Projects Dataset
- TMSource: Training Material Source

## Activities

- Projects classification

## ProjSet Status

1. initial
  - The dataset is indexed and ordered
  - Each project has been evaluated in terms of User Stories, Documentation and code specs
2. classified
  - projects are classified within 5 levels:
    1. Complete Projects
    2. Projects that miss Architectural Documentation sections
    3. Projects that need better User Stories
    4. Projects that need both Architectural Documentation and User Stories expansion
    5. Nearly unusable projects
    6. Unusable projects, to be discarded
3. sanitized
  - all the projects in this dataset have compliant User Stories and isolated documentation in a LLM-readable format
  - source code has been "cleaned" to reduce dataset dimensions
4. final
   - all projects have scaffolded source code, "Project Standard" technical documentation and compliant User Stories

## TMSource composition

- Books: books regarding Software Architecture dDesign in general
- Documentation: Documentation of languages, frameworks and useful services
- Spec Books: books regarding a particular language/framework implementation