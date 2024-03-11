
document.addEventListener("DOMContentLoaded", function () {
    var form = document.getElementById("search_form");
    form.addEventListener("submit", dashboardSearch_profile);
});
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}
async function exists(url) {
    const req = new XMLHttpRequest();
    req.open('get', url);
    req.setRequestHeader('Content-Type', 'application/json');
    req.onload = function () {
        if (req.status == 200) {
            const response = req.responseText;
            console.log(response);
            console.log(typeof response);
            return response;
        } else {
            console.log('Failed to search for favorites');
        }
    };
    req.send();
}


function displaySymbols_profile(responseString) {
    // Get the div element where we will display the list
    const listDiv = document.getElementById("symbol-list-search");

    // Create a new unordered list element
    const ul = document.createElement("ul");

    //create quesryString params

    var jwtQueryString = "&jwt=" + getCookie("jwt");
    var i = 0;
    responseString.forEach(async (result) => {
        var symbolQueryString = "?symbol=" + result.symbol;
        if ((result.symbol).indexOf(".") == -1 && i <= 5) {
            const li = document.createElement("li");
            const div = document.createElement("div");
            const a = document.createElement("a");
            // add the two query parameters

            a.href = "/graph" + symbolQueryString;
            a.textContent = result.symbol;
            const button1 = document.createElement('button');


            var opt =await exists('/exist_favorite' + symbolQueryString + jwtQueryString);
            console.log(opt);
            if (!opt) {
                button1.textContent = 'add_to_favorite';
                button1.onclick = function () {
                    const xhr = new XMLHttpRequest();
                    xhr.open('get', '/add_favorite' + symbolQueryString + jwtQueryString);
                    xhr.setRequestHeader('Content-Type', 'application/json');
                    xhr.onload = function () {
                        if (xhr.status == 200) {
                            const response = xhr.responseText;
                            console.log(response);
                            console.log(typeof response);
                            console.log('stock added correctly to favorites');
                        } else {
                            console.log('Failed to save new favorite stock');
                        }
                    };
                    xhr.send();
                }
            }
            else{
                    button1.textContent = 'delete_from_favorite';
                    button1.onclick = function () {
                        const xhr = new XMLHttpRequest();
                        xhr.open('get', '/delete_favorite' + symbolQueryString + jwtQueryString);
                        xhr.setRequestHeader('Content-Type', 'application/json');
                        xhr.onload = function () {
                            if (xhr.status == 200) {
                                const response = xhr.responseText;
                                console.log(response);
                                console.log(typeof response);
                                console.log('stock added correctly to favorites');
                            } else {
                                console.log('Failed to save new favorite stock');
                            }
                        };
                        xhr.send();
                    }
            }

            div.appendChild(a);
            div.appendChild(button1);
            li.appendChild(div);
            ul.appendChild(li);
            i++;
        }
    });
    listDiv.appendChild(ul);
}

function dashboardSearch_profile(event) {
    event.preventDefault();
    var data = JSON.stringify({ "q": String(document.getElementById("search_bar").value) });
    console.log(data);
    var url = "/search";

    const xhr = new XMLHttpRequest();
    xhr.open('POST', url);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
        if (xhr.status == 200) {
            const response = JSON.parse(xhr.responseText);
            console.log(response);
            console.log(typeof response);

            displaySymbols_profile(response);
            console.log('Form data sent succesfully.');
        } else {
            console.log('Failed to send form data.');
        }
    };
    xhr.send(data);
}