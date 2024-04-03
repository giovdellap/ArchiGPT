function renderUserBanner() {
  const unknown = document.getElementById("unknown");
  const signed = document.getElementById("signed");
  const profile = document.getElementById("prof_sidebar");
  const jwt = getCookie("jwt");
  if (!jwt) {
    unknown.style.display = "block";
    signed.style.display = "none";
    profile.style.display = "none";
  } else {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', '/isAuthenticated?jwt=' + jwt);
    xhr.onload = function () {
      if (xhr.status === 200) {
        var resp = JSON.parse(xhr.responseText)
        const username = resp.username;
        console.log(username);
        const profile_btn = document.getElementById("profile-name");
        profile_btn.innerText = username[0].toUpperCase();
        unknown.style.display = "none";
        signed.style.display = "block";
        profile.style.display = "block";
      }
    };
    xhr.send();
  }
}

function getProfile() {
  const email = document.getElementById("input-email");
  const username = document.getElementById("input-username");
  const name = document.getElementById("input-first-name");
  const surname = document.getElementById("input-last-name");
  const description = document.getElementById("desc");
  const left_name = document.getElementById("left-name");
  const left_email = document.getElementById("left-email");
  const image = document.getElementById("image");
  const jwt = getCookie("jwt");
  const xhr = new XMLHttpRequest();
  xhr.open('GET', '/isAuthenticated?jwt=' + jwt);
  xhr.onload = function () {
    if (xhr.status === 200) {
      const profile = JSON.parse(xhr.responseText);
      image.innerHTML = profile.username[0].toUpperCase();
      username.value = profile.username;
      email.value = profile.email;
      name.value = profile.name[0].toUpperCase() + profile.name.slice(1);
      surname.value = profile.surname[0].toUpperCase() + profile.surname.slice(1);
      description.value = profile.description;
      left_email.innerHTML = profile.email;
      left_name.innerHTML = profile.name[0].toUpperCase() + profile.name.slice(1) + ' ' + profile.surname[0].toUpperCase() + profile.surname.slice(1);
    }
  };
  xhr.send();
}

function logout() {
  document.cookie = 'jwt=; Max-Age=-1';
  window.location.href = "/";
}

function edit() {
  document.getElementById('input-email').removeAttribute('readonly');
  document.getElementById('input-username').removeAttribute('readonly');
  document.getElementById('input-first-name').removeAttribute('readonly');
  document.getElementById('input-last-name').removeAttribute('readonly');
  document.getElementById('desc').removeAttribute('readonly');
  document.getElementById("input-password").removeAttribute('readonly');

  const edit = document.getElementById("edit");
  const save = document.getElementById("save");

  edit.style.display = "none";
  save.style.display = "block";
}

function save() {
  const email = document.getElementById('input-email');
  const username = document.getElementById('input-username');
  const name = document.getElementById('input-first-name');
  const surname = document.getElementById('input-last-name');
  const password = document.getElementById('input-password');
  const desc = document.getElementById('desc');

  const edit = document.getElementById("edit");
  const save = document.getElementById("save");

  var re = new RegExp("[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$");
  if (!re.test(email.value)) {
    alert("Invalid email");
  }
  else if (username.value == "") {
    alert("Username is required");
  }
  else if (name.value == "") {
    alert("Name is required");
  }
  else if (surname.value == "") {
    alert("Surname is required");
  }
  else if (password.value == "") {
    alert("Insert your password if you don't want to change it, a different one otherwise.")
  }
  else {
    email.setAttribute('readonly', true);
    username.setAttribute('readonly', true);
    name.setAttribute('readonly', true);
    surname.setAttribute('readonly', true);
    desc.setAttribute('readonly', true);
    password.setAttribute('readonly', true);

    edit.style.display = "block";
    save.style.display = "none";
    console.log(password.value);

    const jsonObject = {
      "name": name.value,
      "surname": surname.value,
      "username": username.value,
      "email": email.value,
      "password": password.value,
      "description": desc.value
    };

    console.log(jsonObject);

    const jwt = getCookie("jwt");

    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/auth/update_user?jwt=' + jwt);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
      if (xhr.status == 200) {
        const response = JSON.parse(xhr.responseText);
        const token = response.token;
        document.cookie = `jwt=${token}; Path=/; SameSite=Strict;`;
        console.log('Form data sent succesfully.');
      } else {
        console.log('Failed to send form data.');
      }
    };
    xhr.send(JSON.stringify(jsonObject));
  }
}

function deleteAccount() {
  var q = confirm("Are you sure you want to delete the account?");
  if (q === true) {
    const jwt = getCookie("jwt");
    const xhr = new XMLHttpRequest();
    xhr.open('GET', '/api/auth/delete?jwt=' + jwt);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
      if (xhr.status == 200) {
        document.cookie = 'jwt=; Max-Age=-1';
        window.location.href = "/";
        console.log('Account deleted');
      } else {
        console.log('Error');
      }
    };
    xhr.send();
  } else {
    window.location.href = "/profile";
  }
}

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

//***********************************************************************************
function renderFavList() {
  const fav_list = document.getElementById("favorites-list");
  var queryString = "?jwt=" + getCookie("jwt");
  const xhr = new XMLHttpRequest();
  const url = "/favorite_data";
  xhr.open('GET', url + queryString);
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.onload = function () {
    if (xhr.status == 200) {
      const resp = JSON.parse(xhr.responseText);

      fav_list.innerHTML = '';

      resp.forEach(function(result) {
        const li = document.createElement('li');
        li.innerHTML = '<a href="/stock.html?symbol=' + result + '&time_window=W">' + result + '</a>&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="removeFromFavoritesProfile(\'' + result + '\')"><span class="material-symbols-outlined">delete</span></button>';
        fav_list.appendChild(li);
      });
      console.log('Form data sent succesfully.');
    } else {
      console.log('Failed to send form data.');
    }
  };
  xhr.send();

  let sidebar = document.querySelector(".sidebar");
  let sidebarBtn = document.querySelector(".sidebarBtn");
}

function removeFromFavoritesProfile(symbol) {
  var jwt = getCookie("jwt");
  fetch('http://localhost/delete_favorite?symbol=' + symbol + '&jwt=' + jwt, {credentials: 'include'}).then(res => res.text()).then(res => {
    if(res == "true") {
      alert("Removed from favorites!");
    }
    else {
      alert("Error removing from favorites!");
    }
  }).then(_ => location.reload());
}
