
function httpGet(theUrl) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false); // false for synchronous request
    xmlHttp.send();
    return xmlHttp.responseText;
}
res = httpGet("/numberOfUsers")
res = JSON.parse(res)
valore = res[0]["conta"] - 1
console.log(valore)

data = httpGet("http://localhost:3001/getUsers")
data = JSON.parse(data)
console.log(data)

inserimento = document.getElementById("myusers")
console.log(inserimento)
elemento = `    <div class="container">
            <div class="row">
            <div class="col-12">
                <table class="table table-bordered">
                <thead>
                    <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Username Name</th>
                    <th scope="col">Delete</th>
                    </tr>
                </thead>
                <tbody>`

for (var i = 0; i < data.length; i++) {
    username = data[i]["username"]
    if (username == "admin") {
        continue
    }
    elemento += `
<tr>
    <th scope="row">`+ i + `</th>
    <td>`+ username + `</td>
    <td>
    
    <form action="/deleteUserFromUsername" method="post" name="dati">
        <input type="hidden"  name="username"  value="`+ username + `">
    <button type="submit" onclick="alert('sicuro??')" class="btn btn-danger">  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
<path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
</svg></button>
</form>
    </td>
</tr>
`

}
elemento += `
                </tbody>
                    </table>
                </div>
                </div>
            </div>
            `
telement = document.createElement("div")
telement.innerHTML = elemento
inserimento.appendChild(telement)



document.getElementById("descrizione").innerHTML += "<br>The number of active users of the application is : " + valore


