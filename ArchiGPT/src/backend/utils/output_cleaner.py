def cleanOutput(message, section):
    temp = ""
    if message[0] == "`":
        print('SONO NELL IF' + section, message)
        temp = message.replace("```json", "")
    else:
        temp = message
    res = temp.replace("`", "")
    return res