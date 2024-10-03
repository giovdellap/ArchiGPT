class DataMetrics {
    set_id = 0
    set_name = ""
    user_stories = []
    links = []
    db = false
}

class ProjectData {
    name = ""
    userStories = 0
    metrics = []
}

module.exports = {
    ProjectData,
    DataMetrics
}