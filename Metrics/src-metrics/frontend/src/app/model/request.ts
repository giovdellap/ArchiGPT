export class MetricRequest {
    modelName: string = ""
    
    runs: InputRun[] = []
}

export class InputRun {
    projects: InputProject[] = []
}

export class InputProject {
    name: string = ""
    containers: InputContainer[] = []
}

export class InputContainer {
    name: string = ""
    userStories: string[] = []
    services: InputService[] = []
}

export class InputService {
    name: string = ""
    type: string = ""
    endpoints: InputEndPoint[] = []
    pages: InputPage[] = []
}

export class InputEndPoint {
    url: string = ""
    method: string = ""
    userStoryIndex: number[] = []
}

class InputPage {
    path: string = ""
    userStories: number[] = []
}

