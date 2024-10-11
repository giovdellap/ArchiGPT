class InputProject {
    name = ""
    containers = []
}

class InputContainer {
    name = ""
    userStories = []
    services = []
}

class InputService {
    name = ""
    type = ""
    endpoints = []
    pages = []
}

class InputEndPoint {
    url = ""
    method = ""
    userStoryIndex = []
}

class InputPage {
    path = ""
    userStories = []
}

module.exports = {
    InputProject,
    InputContainer,
    InputService,
    InputEndPoint,
    InputPage
}