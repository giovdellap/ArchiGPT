# Teamify

- [Overview](#overview)
- [Key Features](#key-features)
  - [Personal Agenda](#personal-agenda)
  - [Team Creation and Member Managment](#team-creation-and-member-management)
  - [Shared Task Managment](#shared-task-management-events)
  - [Survey Creation](#survey-creation)
  - [Real-Time Team Chat](#real-time-team-chat)
- [Prerequisites](#prerequisites)
- [Installation and Launch](#installation-and-launch)

## Overview

Welcome to Teamify, designed to revolutionize the way you work together with your team. Teamify empowers you to effortlessly manage tasks, coordinate with team members, organize events, conduct surveys, and engage in real-time chat - all in one intuitive platform.

## Key Features

### Personal Agenda

Take control of your schedule with a personalized agenda. Create, organize, and prioritize tasks with ease. Never miss a deadline or forget an important commitment again.

### Team Creation and Member Management

Seamlessly create teams for different projects, departments, or initiatives. Invite team members and manage their roles and permissions, ensuring everyone is on the same page.

### Shared Task Management (Events)

Teamify takes task collaboration to the next level with shared tasks, known as "Events." Easily create Events and invite to collaborate other team members.

### Survey Creation

Gather valuable insights and opinions from your team using Teamify's built-in survey tool. Create customized surveys, distribute them among team members, and collect responses in a structured and organized manner.

### Real-time Team Chat

Stay connected and facilitate quick communication within your team using the integrated chat feature. Discuss projects, share ideas, and resolve issues in real-time, ensuring effective collaboration regardless of geographical distances.

# Prerequisites

Before you begin, make sure you have the following prerequisites in place:

- **Internet Connection:** Ensure that you have an active internet connection.

- **Docker:** Docker is a fundamental requirement for this project. You can download it from the official Docker website based on your operating system:

  - For Windows and macOS: [Docker Desktop](https://www.docker.com/products/docker-desktop)
  - For Linux: [Docker Engine](https://www.docker.com/products/docker-desktop)

- **Git:** Git is essential for version control. If you don't have Git installed, you can download it from the official website based on your operating system:
  - [Git Downloads](https://git-scm.com/downloads)

Please follow the installation instructions provided in the links above to set up these prerequisites on your machine. Having these in place will ensure a smooth experience working with this project.

## Installation and launch

1. **Open a Terminal or Command Prompt:** Depending on your operating system, open a terminal or command prompt. This is where you'll run Git commands.

2. **Navigate to the Directory Where You Want to Clone the Repository:** Use the `cd` command to navigate to the directory where you want to store the cloned repository. For example, if you want to clone the repository into a folder named "my_project" on your desktop, you can do this:

   ```bash
   cd ~/Desktop
   ```

Replace ~/Desktop with the path to your desired directory.

3. **Clone the Github repository:** Use the git clone command followed by the URL of the Git repository you want to clone. For example, if you want to clone a repository hosted on GitHub, the URL might look like this:

   ```bash
   gh repo clone luca-zanchetta/Teamify
   ```

4. **Move to Teamify Repository:** Use another time the 'cd' command to navigate to the directory Teamify

   ```bash
   cd path/to/Teamify

   ```

5. **Start Docker:** In order to initiate the project, it is necessary to have Docker running. Use the UI to open it.

6. **Start the Docker Containers:** Once the containers are built (or if they are already built), you can start the project by running:

   ```bash
    docker-compose up -d
   ```

   The -d flag runs the containers in detached mode, which means they will run in the background.

7. **Start using Teamify:** Now, open your browser, copy paste this URL and enjoy Teamify!
   http://localhost:3000

8. **Stop Teamify:** When you're done working on Teamify, you can stop the Docker containers by running:
   ```bash
    docker-compose down
   ```
   This will stop and remove the containers but keep the project files intact.
