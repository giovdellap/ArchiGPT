
document.addEventListener("DOMContentLoaded", function () {
  var form = document.getElementById("search_form");
  form.addEventListener("submit", dashboardSearch);
});

function renderDataInTheTable(valori, tableId, isGainer) {
  const mytable = document.getElementById(tableId);
  valori.forEach(valore => {
    let newRow = document.createElement("tr");
    Object.keys(valore).forEach((k) => {
      let value = valore[k];
      let cell = document.createElement("td");
      if (k == "change") {
        cell.style.color = isGainer ? "green" : "red";
      }
      cell.innerText = value;
      newRow.appendChild(cell);
    })
    mytable.appendChild(newRow);
  });
}

function renderTicker(valori) {
  const tickers = document.getElementById("tickers");

  valori.forEach(valore => {
    let newDiv = document.createElement("div");
    newDiv.className = "ticker_item";
    let text = "";
    Object.keys(valore).forEach((k) => {
      if (k == "name") {
        text += valore[k] + ": ";
      }
      if (k == "change") {
        let newSpan = document.createElement("span");
        newSpan.style.color = Number(valore[k].slice(0, -1)) >= 0 ? "green" : "red";
        newSpan.innerText = valore[k];
        newDiv.innerText = text;
        newDiv.appendChild(newSpan);
      }
    });
    tickers.appendChild(newDiv);
  })
}

function renderNews(valori) {
  const container = document.getElementById("news_container");

  valori.forEach(valore => {
    let newDivCont = document.createElement("div");
    let newDivText = document.createElement("div");

    let newDivImg = document.createElement("img");
    newDivImg.src = valore.image;

    newDivCont.className = "new";
    newDivText.className = "text";

    let newLink = document.createElement("a");
    newLink.href = valore.url;
    newLink.innerText = valore.headline;

    let newParg = document.createElement("p");
    newParg.innerText = valore.summary;

    let newCont = document.createElement("div");
    newCont.className = "new_content"

    newCont.appendChild(newLink);
    newCont.appendChild(newParg);

    let newPie = document.createElement("div");
    newPie.className = "new_info";

    let newInfo1 = document.createElement("div");
    newInfo1.innerHTML = '<i class="bi bi-pen"></i> ' + valore.source;

    let newInfo2 = document.createElement("div");
    newInfo2.innerHTML = '<i class="bi bi-calendar-date"></i> ' + valore.datetime;

    let newInfo3 = document.createElement("div");
    newInfo3.innerHTML = '<i class="bi bi-tag"></i> ' + valore.category;

    newPie.appendChild(newInfo1);
    newPie.appendChild(newInfo2);
    newPie.appendChild(newInfo3);

    newDivText.appendChild(newCont);
    newDivText.appendChild(newPie);

    newDivCont.appendChild(newDivImg);
    newDivCont.appendChild(newDivText);
    container.appendChild(newDivCont);
  });
}

function renderBoxes(data, gainer, loser) {
  const index = document.getElementById("index");
  index.innerHTML = data.index.name;

  const indexIcon = document.getElementById("indexIcon");
  const indexChange = document.getElementById("indexChange");
  indexChange.innerHTML = data.index.change + "%";

  if (data.index.change >= 0) {
    indexIcon.className = "bi bi-chevron-double-up";
    indexIcon.style.color = "green";
    indexChange.style.color = "green";
  }
  else {
    indexIcon.className = "bi bi-chevron-double-down";
    indexIcon.style.color = "red";
    indexChange.style.color = "red";
  }

  const sector = document.getElementById("sector");
  sector.innerHTML = data.sector.name;

  const sectorIcon = document.getElementById("sectorIcon");
  const sectorChange = document.getElementById("sectorChange");
  sectorChange.innerHTML = data.sector.change + "%";

  if (data.sector.change >= 0) {
    sectorIcon.className = "bi bi-chevron-double-up";
    sectorIcon.style.color = "green";
    sectorChange.style.color = "green";
  }
  else {
    sectorIcon.className = "bi bi-chevron-double-down";
    sectorIcon.style.color = "red";
    sectorChange.style.color = "red";
  }

  const mostGainer = document.getElementById("mostGainer");
  mostGainer.innerHTML = gainer.name;

  const gainerIcon = document.getElementById("gainerIcon");
  const gainerChange = document.getElementById("gainerChange");
  gainerChange.innerHTML = gainer.change;

  gainerIcon.className = "bi bi-chevron-double-up";
  gainerIcon.style.color = "green";
  gainerChange.style.color = "green";


  const mostLoser = document.getElementById("mostLoser");
  mostLoser.innerHTML = loser.name;

  const loserIcon = document.getElementById("loserIcon");
  const loserChange = document.getElementById("loserChange");
  loserChange.innerHTML = loser.change;

  loserIcon.className = "bi bi-chevron-double-down";
  loserIcon.style.color = "red";
  loserChange.style.color = "red";
}

function displaySymbols(responseString) {
  // Get the div element where we will display the list
  const listDiv = document.getElementById("symbol-list-search");

  if (JSON.stringify(responseString) === JSON.stringify([])) {
    listDiv.innerHTML = "No result";
  }
  else {
    // Create a new unordered list element
    const ul = document.createElement("ul");
    responseString.forEach((result) => {
      if ((result.symbol).indexOf(".") == -1) {
        const li = document.createElement("li");
        const a = document.createElement("a");
        a.href = `/graph?symbol=${result.symbol}`;
        a.textContent = result.symbol;
        li.appendChild(a);
        ul.appendChild(li);
      }
    });
    // Replace the contents of the listDiv with the new ul element
    listDiv.innerHTML = "";
    listDiv.appendChild(ul);
  }
}

fetch('http://localhost/data').then(res => {
  fetch('http://localhost/news').then(risp => {
    return risp.json();
  }).then(news => {
    renderNews(news);
  });
  document.getElementById("loader").style.display = "none";
  return res.json();
}).then(dati => {

  const tickers = dati.tickers;
  renderTicker(tickers);

  const gainers = dati.gainers;
  renderDataInTheTable(gainers, "tb_gainers", true);

  const losers = dati.losers;
  renderDataInTheTable(losers, "tb_losers", false);

  fetch('http://localhost/boxes').then(data => {
    return data.json();
  }).then(box => {
    renderBoxes(box, gainers[0], losers[0]);
  });

  const options = {
    container: document.getElementById('treemap'),
    data: dati.treemap,
    background: {
      visible: false
    },
    series: [
      {
        type: 'treemap',
        labelKey: 'name',
        gradient: false,
        nodePadding: 2,
        sizeKey: 'markCap',
        tileStroke: 'white',
        tileStrokeWidth: 1,
        labelShadow: {
          enabled: false,
        },
        groupFill: 'transparent',
        title: {
          color: 'white',
        },
        subtitle: {
          color: 'white',
        },
        labels: {
          value: {
            name: 'Market Capitalization',
            formatter: (params) => `$${params.datum.markCap.toFixed(0)}`,
          },
        },
        groupStrokeWidth: 0,
        tileStroke: '#292929',
        tileStrokeWidth: 1,
        highlightGroups: false,
        highlightStyle: {
          text: {
            color: undefined,
          },
        },
        formatter: ({ depth, parent, highlighted, datum }) => {
          if (depth < 2) {
            return {};
          }

          const fill =
            datum.change < 0 ?
              (datum.markCap >= 250000 ? 'rgb(190, 85, 96)' : 'rgba(232, 125, 136)')
              : (datum.markCap >= 250000 ? 'rgb(68, 191, 121)' : 'rgb(195, 233, 163)');

          const stroke = highlighted ? 'black' : 'white';
          return { fill, stroke };
        },
      },
    ],
  };

  agCharts.AgChart.create(options);
}).catch(() => { console.log("ERRORE"); });

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
      displaySymbols(response);
      console.log('Form data sent succesfully.');
    } else {
      console.log('Failed to send form data.');
    }
  };
  xhr.send(data);
}

