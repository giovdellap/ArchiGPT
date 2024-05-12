export function formatTime(date) {
  const time = new Date(date);
  const h = time.getHours().toString().padStart(2, "0");
  const m = time.getMinutes().toString().padStart(2, "0");
  const s = time.getSeconds().toString().padStart(2, "0");
  return `${h}:${m}:${s}`;
}

export function formatDate(d) {
  const date = new Date(d);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

export function objectToArray(object) {
  const r = Array();
  object.forEach((i) => {
    r.push(i);
  });
  return r;
}

export function isMemberIncluded(member, eventMembers, task_m) {
  if (task_m === member) {
    return true;
  } else {
    for (const m of eventMembers) {
      if (member === m.member) {
        return true;
      }
    }
  }
  return false;
}
