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
}

class InputEndPoint {
    url = ""
    method = ""
    userStoryIndex = []
}

module.exports = {
    InputProject,
    InputContainer,
    InputService,
    InputEndPoint
}