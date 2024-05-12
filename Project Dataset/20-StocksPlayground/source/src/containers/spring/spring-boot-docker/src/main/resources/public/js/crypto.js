
function renderDataInTheTable(valori, tableId) {
  const mytable = document.getElementById(tableId);
  valori.forEach(valore => {
    let newRow = document.createElement("tr");
    Object.keys(valore).forEach((k) => {
      let value = valore[k];
      let cell = document.createElement("td");
      if (k == "change1h" || k == "change1d") {
        cell.style.color = value >= 0 ? "green" : "red";
        cell.innerText = value+'%';
      }
      else{
        cell.innerText = value;
      }
      newRow.appendChild(cell);
    })
    mytable.appendChild(newRow);
  });
}

fetch('http://localhost/crypto_data').then(res => {
  document.getElementById("loader").style.display = "none";
  return res.json();
}).then(dati => {

  const most_trending = dati.most_trending;
  renderDataInTheTable(most_trending, "tb_trending");

  const most_famous = dati.most_famous;
  renderDataInTheTable(most_famous, "tb_famous");

  console.log(dati.treemap);

  const options = {
    container: document.getElementById('treemap'),
    data: dati.treemap,
    background: {
      visible: false
    },
    series: [
      {
        type: 'treemap',
        labelKey: 'symbol',
        gradient: false,
        nodePadding: 2,
        sizeKey: 'marketCap',
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
            formatter: (params) => `$${params.datum.marketCap.toFixed(0)}`,
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
              (datum.marketCap >= 250000 ? 'rgb(190, 85, 96)' : 'rgba(232, 125, 136)')
              : (datum.marketCap >= 250000 ? 'rgb(68, 191, 121)' : 'rgb(195, 233, 163)');

          const stroke = highlighted ? 'black' : 'white';
          return { fill, stroke };
        },
      },
    ],
  };

  agCharts.AgChart.create(options);
}).catch(() => { console.log("ERRORE"); });


