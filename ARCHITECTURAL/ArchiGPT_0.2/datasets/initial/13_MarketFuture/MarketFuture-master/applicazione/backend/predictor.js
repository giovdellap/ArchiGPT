function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send(  );
    return xmlHttp.responseText;
}

function prendistock(nomeStock){
    data=httpGet("http://localhost:5000/getStock/"+nomeStock)
    data=JSON.parse(data)
    dati=data.dati
    console.log(dati)
    forecast=data.forecast
    //console.log(data)
    month={
        "Jan":'01',
        "Feb":'02',
        "Mar":'03',
        "Apr":'04',
        "May":'05',
        "Jun":'06',
        "Jul":'07',
        "Aug":'08',
        "Sep":'09',
        "Oct":'10',
        "Nov":'11',
        "Dec":'12'
    }
    x=[]
    y=[]
    x2=[]
    y2=[]
    //loops used to adapt date format of the stocks to the date format month-year
    for (var i=0;i<forecast.length;i++){
        vecchioFromato=forecast[i][0]
        anno=vecchioFromato.slice(12,16)
        giorno=vecchioFromato.slice(5,7)
        mese=month[vecchioFromato.slice(8,11)]
        nuovoFormato=anno+"-"+mese+"-"+giorno
        x2.push(nuovoFormato)
        y2.push(forecast[i][1])
    }
    for (var i=0;i<dati.length;i++){
        vecchioFromato=dati[i][0]
        anno=vecchioFromato.slice(12,16)
        giorno=vecchioFromato.slice(5,7)
        mese=month[vecchioFromato.slice(8,11)]
        nuovoFormato=anno+"-"+mese+"-"+giorno
        x.push(nuovoFormato)
        y.push(dati[i][1])
    }

    prova={"x":x,"y":y,"type":"scatter"}
    var z = []
    for (var i=0;i < x.length; i++){
        z.push(x[i].slice(8,16))
    }
    var trace1 = {
        type: "scatter",
        mode: "lines",
        name: 'normal',
        x: x,
        y: y,
        line: {color: '#17BECF'}
    }
    
    var trace2 = {
        type: "scatter",
        mode: "lines",
        name: 'predicted',
        x: x2,
        y: y2,
        line: {color: '#7F7F7F'}
    }
    valori=[trace1,trace2]

    var layout2 = {
    title: 'Time Series with Rangeslider',
    xaxis: {
        autorange: true,
        range: ['2015-01-01', '2024-01-01'],
        rangeselector: {buttons: [
            {
            count: 1,
            label: '1m',
            step: 'month',
            stepmode: 'backward'
            },
            {
            count: 6,
            label: '6m',
            step: 'month',
            stepmode: 'backward'
            },
            {step: 'all'}
        ]},
        rangeslider: {range: ['2015-01-01', '2024-01-01']},
        type: 'date'
    },
    yaxis: {
        autorange: true,
        range: [86.8700008333, 138.870004167],
        type: 'linear'
    }
    };
    
    tester = document.getElementById('tester');
    tester.innerHTML=""
    Plotly.newPlot('tester',valori,layout2)
}

function changeListStock(){
    selettore= document.getElementById("selettore")
    listaStocks=JSON.parse(httpGet("/getStocksList"))
    set=new Set();
    listaStocks.forEach(element => {
        set.add(element["nomeStock"])
    })

    set.forEach(element => {
        telement=document.createElement("option")
        telement.setAttribute("value",element)
        telement.innerHTML=element
        selettore.appendChild(telement)
    });

}

changeListStock()

versione=0
//selector
document.getElementById("bottonePredict").addEventListener("click",function(){
    versione++
    selettore= document.getElementById("selettore")
    valore=selettore.value
    if(valore=="Open this select menu"){
        return
    }
    prendistock(valore)

    selettore=document.getElementById("tester")


    componentElement='http://localhost:5000/prendiGrafici?versione='+versione
    telement=document.createElement("img")
    telement.setAttribute("src",componentElement)
    
    selettore.appendChild(telement)
    
})
