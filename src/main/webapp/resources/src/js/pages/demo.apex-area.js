/**
 * Theme: Hyper - Responsive Bootstrap 4 Admin Dashboard
 * Author: Coderthemes
 * Module/App: Apex Area Charts
 */

//
// Basic Area Chart
//

var options = {
  chart: {
    height: 380,
    type: 'area',
    zoom: {
      enabled: false
    }
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    width: 3,
    curve: 'straight'
  },
  colors: ["#fa5c7c"],
  series: [{
    name: "STOCK ABC",
    data: series.monthDataSeries1.prices
  }],
  title: {
    text: 'Fundamental Analysis of Stocks',
    align: 'left'
  },
  subtitle: {
    text: 'Price Movements',
    align: 'left'
  },
  labels: series.monthDataSeries1.dates,
  xaxis: {
    type: 'datetime',
  },
  yaxis: {
    opposite: true
  },
  legend: {
    horizontalAlign: 'left'
  },
  grid: {
    row: {
      colors: ['#f1f3fa', 'transparent'] // takes an array which will be repeated on columns
    },
    borderColor: '#f1f3fa'
  },
  responsive: [{
    breakpoint: 600,
    options: {
      chart: {
        toolbar: {
          show: false
        }
      },
      legend: {
        show: false
      },
    }
  }]
}

var chart = new ApexCharts(
  document.querySelector("#basic-area"),
  options
);

chart.render();


//
// Splite Area
//

var options = {
  chart: {
    height: 380,
    type: 'area',
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    width: 3,
    curve: 'smooth'
  },
  colors: ["#727cf5", "#6c757d"],
  series: [{
    name: 'Series 1',
    data: [31, 40, 28, 51, 42, 109, 100]
  }, {
    name: 'Series 2',
    data: [11, 32, 45, 32, 34, 52, 41]
  }],

  xaxis: {
    categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"],
  },
  tooltip: {
    fixed: {
      enabled: false,
      position: 'topRight'
    }
  },
  grid: {
    row: {
      colors: ['transparent', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.2
    },
    borderColor: '#f1f3fa'
  }
}

var chart = new ApexCharts(
  document.querySelector("#spline-area"),
  options
);

chart.render();

//
// AREA CHART - DATETIME X-AXIS
//

var options = {
  annotations: {
    yaxis: [{
      value: 30,
      borderColor: '#999',
      yAxisIndex: 0,
      label: {
        show: true,
        text: 'Support',
        style: {
          color: "#fff",
          background: '#00E396'
        }
      }
    }],
    xaxis: [{
      value: new Date('14 Nov 2012').getTime(),
      borderColor: '#999',
      yAxisIndex: 0,
      label: {
        show: true,
        text: 'Rally',
        style: {
          color: "#fff",
          background: '#775DD0'
        }
      }
    }]
  },
  chart: {
    type: 'area',
    height: 380,
    scroller: {
      enabled: true
    }
  },
  dataLabels: {
    enabled: false
  },
  series: [{
      data: dateSeries1
    },

  ],
  markers: {
    size: 0,
    style: 'hollow',
  },
  xaxis: {
    type: 'datetime',
    min: new Date('01 Mar 2012').getTime()
  },
  yaxis: {
    // min: 24,
  },
  tooltip: {
    key: {
      format: 'dd MMM yyyy'
    }
  },
  fill: {
    type: 'gradient',
    gradient: {
      shadeIntensity: 1,
      opacityFrom: 0.7,
      opacityTo: 0,
      stops: [0, 40, 100]
    }
  },

}

var chart = new ApexCharts(
  document.querySelector("#area-chart-datetime"),
  options
);

chart.render();

var resetCssClasses = function (activeEl) {
  var els = document.querySelectorAll("button");
  Array.prototype.forEach.call(els, function (el) {
    el.classList.remove('active');
  });

  activeEl.target.classList.add('active')
}

document.querySelector("#one_month").addEventListener('click', function (e) {
  resetCssClasses(e)
  chart.updateOptions({
    xaxis: {
      min: new Date('28 Jan 2013').getTime(),
      max: new Date('27 Feb 2013').getTime(),
    }
  })
})

document.querySelector("#six_months").addEventListener('click', function (e) {
  resetCssClasses(e)
  chart.updateOptions({
    xaxis: {
      min: new Date('27 Sep 2012').getTime(),
      max: new Date('27 Feb 2013').getTime(),
    }
  })
})

document.querySelector("#one_year").addEventListener('click', function (e) {
  resetCssClasses(e)
  chart.updateOptions({
    xaxis: {
      min: new Date('27 Feb 2012').getTime(),
      max: new Date('27 Feb 2013').getTime(),
    }
  })
})

document.querySelector("#ytd").addEventListener('click', function (e) {
  resetCssClasses(e)
  chart.updateOptions({
    xaxis: {
      min: new Date('01 Jan 2013').getTime(),
      max: new Date('27 Feb 2013').getTime(),
    }
  })
})

document.querySelector("#all").addEventListener('click', function (e) {
  resetCssClasses(e)
  chart.updateOptions({
    xaxis: {
      min: undefined,
      max: undefined,
    }
  })
})

document.querySelector("#ytd").addEventListener('click', function () {

})

//
// AREA WITH NEGATIVE VALUES
//

var options = {
  chart: {
    height: 412,
    type: 'area',
    // zoom: {
    //     enabled: false
    // }
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    width: 2,
    curve: 'straight'
  },
  colors: ["#0acf97", "#ffbc00"],
  series: [{
    name: 'North',
    data: [{
      x: 1996,
      y: 322
    },
    {
      x: 1997,
      y: 324
    },
    {
      x: 1998,
      y: 329
    },
    {
      x: 1999,
      y: 342
    },
    {
      x: 2000,
      y: 348
    },
    {
      x: 2001,
      y: 334
    },
    {
      x: 2002,
      y: 325
    },
    {
      x: 2003,
      y: 316
    },
    {
      x: 2004,
      y: 318
    },
    {
      x: 2005,
      y: 330
    },
    {
      x: 2006,
      y: 355
    },
    {
      x: 2007,
      y: 366
    },
    {
      x: 2008,
      y: 337
    },
    {
      x: 2009,
      y: 352
    },
    {
      x: 2010,
      y: 377
    },
    {
      x: 2011,
      y: 383
    },
    {
      x: 2012,
      y: 344
    },
    {
      x: 2013,
      y: 366
    },
    {
      x: 2014,
      y: 389
    },
    {
      x: 2015,
      y: 334
    }
    ]
  }, {
    name: 'South',
    data: [

      {
        x: 1996,
        y: 162
      },
      {
        x: 1997,
        y: 90
      },
      {
        x: 1998,
        y: 50
      },
      {
        x: 1999,
        y: 77
      },
      {
        x: 2000,
        y: 35
      },
      {
        x: 2001,
        y: -45
      },
      {
        x: 2002,
        y: -88
      },
      {
        x: 2003,
        y: -120
      },
      {
        x: 2004,
        y: -156
      },
      {
        x: 2005,
        y: -123
      },
      {
        x: 2006,
        y: -88
      },
      {
        x: 2007,
        y: -66
      },
      {
        x: 2008,
        y: -45
      },
      {
        x: 2009,
        y: -29
      },
      {
        x: 2010,
        y: -45
      },
      {
        x: 2011,
        y: -88
      },
      {
        x: 2012,
        y: -132
      },
      {
        x: 2013,
        y: -146
      },
      {
        x: 2014,
        y: -169
      },
      {
        x: 2015,
        y: -184
      }
    ]
  }],
  xaxis: {
    type: 'datetime',
    axisBorder: {
      show: false
    },
    axisTicks: {
      show: false
    }
  },
  yaxis: {
    tickAmount: 4,
    floating: false,

    labels: {
      style: {
        color: '#8e8da4',
      },
      offsetY: -7,
      offsetX: 0,
    },
    axisBorder: {
      show: false,
    },
    axisTicks: {
      show: false
    }
  },
  fill: {
    opacity: 0.5,
    gradient: {
      enabled: false
    }
  },
  tooltip: {
    x: {
      format: "yyyy",
    },
    fixed: {
      enabled: false,
      position: 'topRight'
    }
  },
  grid: {
    yaxis: {
      lines: {
        offsetX: -30
      }
    },
    padding: {
      left: 0
    },
    borderColor: '#f1f3fa'
  },
}

var chart = new ApexCharts(
  document.querySelector("#area-chart-negative"),
  options
);

chart.render();


//
// SELECTION - GITHUB STYLE
//

var optionsarea2 = {
  chart: {
    type: 'area',
    height: 160,
    background: '#F6F8FA',
    toolbar: {
      show: false,
    },
    events: {
      mounted: function (chart) {
        var commitsEl = document.querySelector('p span.commits');
        var commits = chart.getSeriesTotalXRange(chart.w.globals.minX, chart.w.globals.maxX)

        commitsEl.innerHTML = commits
      },
      updated: function (chart) {
        var commitsEl = document.querySelector('.cmeta span.commits');
        var commits = chart.getSeriesTotalXRange(chart.w.globals.minX, chart.w.globals.maxX)

        commitsEl.innerHTML = commits
      }
    }
  },
  colors: ['#FF7F00'],
  stroke: {
    width: 0,
    curve: 'smooth'
  },
  dataLabels: {
    enabled: false
  },
  fill: {
    opacity: 1,
    type: 'solid'
  },
  series: [{
    name: 'commits',
    data: githubdata.series
  }],
  yaxis: {
    tickAmount: 3,
    labels: {
      show: false
    }
  },
  xaxis: {
    type: 'datetime',
  },
  grid: {
    row: {
      colors: ['transparent', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.2
    },
    borderColor: '#f1f3fa'
  }
}

var chartarea2 = new ApexCharts(
  document.querySelector("#area-chart-github2"),
  optionsarea2
);

chartarea2.render();

var options = {
  chart: {
    height: 200,
    type: 'area',
    background: '#F6F8FA',
    // toolbar: {
    //   show: false,
    // },
    selection: {
      xaxis: {
        min: new Date('05 Jan 2014').getTime(),
        max: new Date('04 Jan 2015').getTime()
      }
    },
    events: {
      selection: function (chart, e) {
        chartarea2.updateOptions({
          xaxis: {
            min: e.xaxis.min,
            max: e.xaxis.max
          }
        }, false, false)
      },
      updated: function (chart, e) {
        chartarea2.updateOptions({
          xaxis: {
            min: e.config.xaxis.min,
            max: e.config.xaxis.max
          },
        }, false, false)
      }
    },

  },
  colors: ['#0acf97'],
  dataLabels: {
    enabled: false
  },
  stroke: {
    width: 0,
    curve: 'smooth'
  },

  series: [{
    name: 'commits',
    data: githubdata.series
  }],
  fill: {
    opacity: 1,
    type: 'solid'
  },
  legend: {
    position: 'top',
    horizontalAlign: 'left'
  },
  xaxis: {
    type: 'datetime'
  },
  grid: {
    row: {
      colors: ['transparent', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.2
    },
    borderColor: '#f1f3fa'
  }
}

var chart = new ApexCharts(
  document.querySelector("#area-chart-github"),
  options
);

chart.render();


//
// STACKED AREA
//

var options = {
  chart: {
    height: 422,
    type: 'area',
    stacked: true,
    scroller: {
      enabled: true
    },
    events: {
      selection: function (chart, e) {
        console.log(new Date(e.xaxis.min))
      }
    },

  },
  colors: ['#727cf5', '#0acf97', '#e3eaef'],
  dataLabels: {
    enabled: false
  },
  stroke: {
    width: 2,
    curve: 'smooth'
  },

  series: [{
    name: 'South',
    data: generateDayWiseTimeSeries(new Date('11 Feb 2017').getTime(), 20, {
      min: 10,
      max: 60
    })
  },
  {
    name: 'North',
    data: generateDayWiseTimeSeries(new Date('11 Feb 2017').getTime(), 20, {
      min: 10,
      max: 20
    })
  },

  {
    name: 'Central',
    data: generateDayWiseTimeSeries(new Date('11 Feb 2017').getTime(), 20, {
      min: 10,
      max: 15
    })
  }
  ],
  fill: {
    gradient: {
      enabled: true,
      opacityFrom: 0.6,
      opacityTo: 0.8,
    }
  },
  legend: {
    position: 'top',
    horizontalAlign: 'left'
  },
  xaxis: {
    type: 'datetime'
  },
  grid: {
    row: {
      colors: ['transparent', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.2
    },
    borderColor: '#f1f3fa'
  },
  responsive: [{
    breakpoint: 600,
    options: {
      chart: {
        toolbar: {
          show: false
        }
      }
    }
  }]
}

var chart = new ApexCharts(
  document.querySelector("#stacked-area"),
  options
);

chart.render();

/*
  // this function will generate output in this format
  // data = [
      [timestamp, 23],
      [timestamp, 33],
      [timestamp, 12]
      ...
  ]
  */
function generateDayWiseTimeSeries(baseval, count, yrange) {
  var i = 0;
  var series = [];
  while (i < count) {
    var x = baseval;
    var y = Math.floor(Math.random() * (yrange.max - yrange.min + 1)) + yrange.min;

    series.push([x, y]);
    baseval += 86400000;
    i++;
  }
  return series;
}


//
// IRREGULAR TIMESERIES
//

var ts1 = 1388534400000;
var ts2 = 1388620800000;
var ts3 = 1389052800000;

var dataSet = [[], [], []];

for (var i = 0; i < 12; i++) {
  ts1 = ts1 + 86400000;
  var innerArr = [ts1, dataSeries[2][i].value];
  dataSet[0].push(innerArr)
}
for (var i = 0; i < 18; i++) {
  ts2 = ts2 + 86400000;
  var innerArr = [ts2, dataSeries[1][i].value];
  dataSet[1].push(innerArr)
}
for (var i = 0; i < 12; i++) {
  ts3 = ts3 + 86400000;
  var innerArr = [ts3, dataSeries[0][i].value];
  dataSet[2].push(innerArr)
}

var options = {
  chart: {
    type: 'area',
    stacked: false,
    height: 380,
    zoom: {
      enabled: false
    },
  },
  plotOptions: {
    line: {
      curve: 'smooth',
    }
  },
  dataLabels: {
    enabled: false
  },
  series: [{
    name: 'PRODUCT A',
    data: dataSet[0]
  }, {
    name: 'PRODUCT B',
    data: dataSet[1]
  }, {
    name: 'PRODUCT C',
    data: dataSet[2]
  }],
  colors: ["#39afd1", "#fa5c7c", "#727cf5"],
  markers: {
    size: 0,
    style: 'full',
  },
  stroke: {
    width: 3
  },
  fill: {
    gradient: {
      enabled: true,
      shadeIntensity: 1,
      inverseColors: false,
      opacityFrom: 0.45,
      opacityTo: 0.05,
      stops: [20, 100, 100, 100]
    },
  },
  yaxis: {
    //floating: true,
    labels: {
      style: {
        color: '#8e8da4',
      },
      offsetX: 0,
      formatter: function (val) {
        return (val / 1000000).toFixed(0);
      },
    },
    axisBorder: {
      show: false,
    },
    axisTicks: {
      show: false
    }
  },
  xaxis: {
    type: 'datetime',
    tickAmount: 8,
    labels: {
      formatter: function (val) {
        return moment(new Date(val)).format("DD MMM YYYY")
      }
    }
  },
  title: {
    text: 'Irregular Data in Time Series',
    align: 'left',
    offsetX: 0
  },
  tooltip: {
    shared: true,
    y: {
      formatter: function (val) {
        return (val / 1000000).toFixed(0) + " points"
      }
    }
  },
  legend: {
    position: 'top',
    horizontalAlign: 'center',
    offsetX: -10
  },
  grid: {
    row: {
      colors: ['transparent', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.2
    },
    borderColor: '#f1f3fa'
  },
  responsive: [{
    breakpoint: 600,
    options: {
      chart: {
        toolbar: {
          show: false
        }
      }
    }
  }]
}

var chart = new ApexCharts(
  document.querySelector("#area-timeSeries"),
  options
);

chart.render();


//
// AREA CHART WITH NULL VALUES
//

var options = {
  chart: {
    height: 380,
    type: 'area',
    animations: {
      enabled: false
    },
    zoom: {
      enabled: false
    },
  },
  dataLabels: {
    enabled: false
  },
  stroke: {
    curve: 'straight'
  },
  colors: ["#6c757d"],
  series: [{
    name: 'Network',
    data: [{
      x: 'Dec 23 2017',
      y: null
    },
    {
      x: 'Dec 24 2017',
      y: 44
    },
    {
      x: 'Dec 25 2017',
      y: 31
    },
    {
      x: 'Dec 26 2017',
      y: 38
    },
    {
      x: 'Dec 27 2017',
      y: null
    },
    {
      x: 'Dec 28 2017',
      y: 32
    },
    {
      x: 'Dec 29 2017',
      y: 55
    },
    {
      x: 'Dec 30 2017',
      y: 51
    },
    {
      x: 'Dec 31 2017',
      y: 67
    },
    {
      x: 'Jan 01 2018',
      y: 22
    },
    {
      x: 'Jan 02 2018',
      y: 34
    },
    {
      x: 'Jan 03 2018',
      y: null
    },
    {
      x: 'Jan 04 2018',
      y: null
    },
    {
      x: 'Jan 05 2018',
      y: 11
    },
    {
      x: 'Jan 06 2018',
      y: 4
    },
    {
      x: 'Jan 07 2018',
      y: 15,
    },
    {
      x: 'Jan 08 2018',
      y: null
    },
    {
      x: 'Jan 09 2018',
      y: 9
    },
    {
      x: 'Jan 10 2018',
      y: 34
    },
    {
      x: 'Jan 11 2018',
      y: null
    },
    {
      x: 'Jan 12 2018',
      y: null
    },
    {
      x: 'Jan 13 2018',
      y: 13
    },
    {
      x: 'Jan 14 2018',
      y: null
    }
    ],
  }],
  fill: {
    opacity: 0.8,
    gradient: {
      enabled: false
    },
    pattern: {
      enabled: true,
      style: ['verticalLines', 'horizontalLines'],
      width: 5,
      depth: 6
    },
  },
  markers: {
    size: 5,
    hover: {
      size: 9
    }
  },
  title: {
    text: 'Network Monitoring',
  },
  tooltip: {
    intersect: true,
    shared: false
  },
  theme: {
    palette: 'palette1'
  },
  xaxis: {
    type: 'datetime',
  },
  yaxis: {
    title: {
      text: 'Bytes Received'
    }
  },
  grid: {
    row: {
      colors: ['transparent', 'transparent'], // takes an array which will be repeated on columns
      opacity: 0.2
    },
    borderColor: '#f1f3fa'
  },
  responsive: [{
    breakpoint: 600,
    options: {
      chart: {
        toolbar: {
          show: false
        }
      }
    }
  }]
}

var chart = new ApexCharts(
  document.querySelector("#area-chart-nullvalues"),
  options
);

chart.render();