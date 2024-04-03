
document.addEventListener("DOMContentLoaded", function() {
  var form = document.getElementById("search_form");
  form.addEventListener("submit", dashboardSearch);
});

function displaySymbols(responseString) {
  // Get the div element where we will display the list
  const listDiv = document.getElementById("symbol-list-search");

  // Create a new unordered list element
  const ul = document.createElement("ul");
  var i = 0;
  responseString.forEach((result) => {

    if((result.symbol).indexOf(".")==-1 && i <=5) {
      const li = document.createElement("li");
      const a = document.createElement("a");
      // add the two query parameters
    a.href = `/graph?symbol=`+ result.symbol;
      a.textContent = result.symbol;
      li.appendChild(a);
      ul.appendChild(li);
      i++;
    }
  });
  // Replace the contents of the listDiv with the new ul element
  listDiv.innerHTML = "";
  listDiv.appendChild(ul);
}

function dashboardSearch(event) {
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

      displaySymbols(response);
      console.log('Form data sent succesfully.');
    } else {
      console.log('Failed to send form data.');
    }
  };
  xhr.send(data);
}