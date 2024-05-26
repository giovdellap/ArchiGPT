assistans_100 = [
    'System_1',
    'System_2',
    'Container_1',
    'Container_2'
]

assistants_45 = [
    'Util_1'
]

def getSleepTime(assistant):
    if assistant in assistans_100:
        return 100
    elif assistant in assistants_45:
        return 45
    else:
        return 60