var questionnumber

async function send(){

    document.getElementById("btn1").disabled = true;
    document.getElementById("btn2").disabled = true;

    var radios = document.getElementsByName("answer");
    var correct = null;

    for (var i = 0, length = radios.length; i < length; i++) {
        if (radios[i].checked) {
            correct = radios[i].value;
            break;
        }
    }

    if (correct==null) {
        alert("Select a correct answer")
    }
    else {

        var qn = document.getElementById("question");
        questionnumber = qn.value;

        if (questionnumber=="") {
            alert("Select question number")
        }
        else {
            var data = {'correct':correct, 'qn':questionnumber};
            var url = "https://localhost:443/start";
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify(data) // body data type must match "Content-Type" header
            });
            response.json().then((data) => {
                timeo(10);
                console.log(data.msg)
            })
            
        }

    }

}



async function end(){

            localStorage['lastQuestion'] = "1"
            document.getElementById("question").value=1
            document.getElementById("btn1").disabled = true;
            document.getElementById("btn2").disabled = true;

            var data = {'game':'end'};
            var url = "https://localhost:443/end";
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json'
                },
                body: JSON.stringify(data) // body data type must match "Content-Type" header
            });

            

            response.json().then((data) => {
                alert(data.msg)
                document.getElementById("btn1").disabled = false;
                document.getElementById("btn2").disabled = false;
            })


}


function setQN(){
    if(!localStorage.hasOwnProperty("lastQuestion")){
        localStorage["lastQuestion"] = "1"
    }
    document.getElementById("question").value = localStorage["lastQuestion"]
}

function timeo(remaining){
    if (remaining==-1) {
        document.getElementById("question").value=(parseInt(questionnumber)+1).toString()
        localStorage["lastQuestion"] = document.getElementById("question").value
        document.getElementById("btn1").disabled = false;
        document.getElementById("btn2").disabled = false;
        return
    }
    document.getElementById("countdown").innerHTML = remaining
    setTimeout(function() {
        timeo(remaining-1)
    }, 1000)

}