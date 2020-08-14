/**
 * 
 */
// Main chart
var ctxL = document.getElementById("lineChart").getContext('2d');
var myLineChart = new Chart(ctxL, {
  type: 'line',
  data: {
    labels: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday"],
    datasets: [{
      label: "Number of Operations",
      fillColor: "#fff",
      backgroundColor: 'rgba(255, 255, 255, .3)',
      borderColor: 'rgba(255, 255, 255)',
      data: graphArray,
    }]
  },
  options: {
    legend: {
      labels: {
        fontColor: "#fff",
      }
    },
    scales: {
      xAxes: [{
        gridLines: {
          display: true,
          color: "rgba(255,255,255,.25)"
        },
        ticks: {
          fontColor: "#fff",
        },
      }],
      yAxes: [{
        display: true,
        gridLines: {
          display: true,
          color: "rgba(255,255,255,.25)"
        },
        ticks: {
          fontColor: "#fff",
        },
      }],
    }
  }
});