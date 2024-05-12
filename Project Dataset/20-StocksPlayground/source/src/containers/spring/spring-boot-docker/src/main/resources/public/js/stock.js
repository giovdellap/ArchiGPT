function createCandlestickChart(data) {
    data = JSON.parse(data);
    var categories = [];
    var ohlc = [];

    for (var i = 0; i < data.t.length; i++) {
      var hour = ('0'+new Date(data.t[i] * 1000).getHours()).slice(-2);
      var min = ('0'+new Date(data.t[i] * 1000).getMinutes()).slice(-2);
      var category = new Date(data.t[i] * 1000).toLocaleString('default', { month: 'short' }) + ' ' + new Date(data.t[i] * 1000).getDate().toString() +" "+hour+":"+min;
      console.log(category);
      categories.push(category);
      var tuple = {
        x: category,
        y: [data.o[i], data.h[i], data.l[i], data.c[i]]
      };
      ohlc.push(tuple);
    }

    var options = {
      series: [{
        data: ohlc
      }],
      chart: {
        type: 'candlestick',
        height: 350,
        toolbar: {
          show: false,
        }
      },
      tooltip: {
        theme: 'dark',
      },
      title: {
        text: 'Price Chart',
        align: 'left',
        style: {
          color: 'white',
        }
      },
      xaxis: {
        type: 'category',
        categories: categories,
        tickPlacement: 'between',
        max:ohlc.length,
        labels: {
          style: {
            colors: 'white',
          },
        }
      },
      yaxis: {
        tooltip: {
          enabled: true
        },
        labels: {
          style: {
            colors: 'white',
          },
        }
      }
    };

    var chart = new ApexCharts(document.querySelector("#chart"), options);
    chart.render();
}

function renderInfo(data){
    const price = document.getElementById("price");
    price.innerHTML = data.price;

    const change = document.getElementById("change");
    change.innerHTML = data.change + '%';
    change.style.color = data.change >= 0 ? "green" : "red";

    const high = document.getElementById("high");
    high.innerHTML = data.high;
    
    const low = document.getElementById("low");
    low.innerHTML = data.low;
}

// Managing the default value
var queryString = (window.location.search == "" || window.location.search == "?") ? '?symbol=AAPL' : window.location.search; 
const urlParams = new URLSearchParams(queryString);
console.log(queryString);

fetch('http://localhost/graph_data' + queryString).then(res => {
  var title = document.getElementById("title-nav");
  var symbol = urlParams.get('symbol');
  title.innerHTML = symbol + " Summary";
  fetch('http://localhost/stock_data?symbol=' + symbol).then(ret => {
    return ret.json();
  }).then(info => { renderInfo(info); });
  document.getElementById("loader").style.display = "none";
  return res.json();
}).then(risp => {
  createCandlestickChart(risp);
}).catch(() => { console.log("ERRORE"); });

function addToFavorites() {
	var symbol = urlParams.get('symbol');
	var jwt = getCookie("jwt");
	fetch('http://localhost/add_favorite?symbol=' + symbol + '&jwt=' + jwt, {credentials: 'include'}).then(res => res.text()).then( res => {
		if(res == "true") {
			alert("Added to favorites!");
		}
		else {
			alert("Error adding to favorites!");
		}
	}).then(_ => location.reload());
}

function removeFromFavoritesStock() {
	var symbol = urlParams.get('symbol');
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

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

function renderFavoriteButton() {
    if (getCookie("jwt") == null) {
        document.getElementById("favorites-button").style.display = "none";
        return;
    }
    var jwt = getCookie("jwt");
    var symbol = urlParams.get('symbol');
    fetch('http://localhost/is_in_favorites?symbol=' + symbol + '&jwt=' + jwt, {credentials: 'include'}).then(res => res.text()).then(res => {
        const favoriteSpan = document.getElementById("favorites-span");
        const favoriteText = document.getElementById("favorites-text");
        const favoriteButton = document.getElementById("favorites-button");
        if(res == "true") {
            favoriteButton.onclick = removeFromFavoritesStock;
            favoriteSpan.style.setProperty('font-variation-settings', "'FILL' 1, 'wght' 400, 'GRAD' 0, 'opsz' 48");
            favoriteText.innerHTML = "Remove from favorites";
        }
        else {
            favoriteButton.onclick = addToFavorites;
            favoriteText.innerHTML = "Add to favorites";
        }
    });
}
