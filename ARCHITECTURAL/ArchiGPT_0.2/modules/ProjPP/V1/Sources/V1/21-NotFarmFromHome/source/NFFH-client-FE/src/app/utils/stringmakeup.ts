export function modifyString(str: string) {
    return str.replaceAll('_', ' ')
}

export function revertString(str: string) {
    return str.replaceAll(' ', '_')
}