/**
 * Theme: Hyper - Responsive Bootstrap 4 Admin Dashboard
 * Author: Coderthemes
 * Module/App: Apex Bar Charts
 */

 //
 // BASIC BAR CHART
 //

 var options = {
    chart: {
        height: 380,
        type: 'bar',
        toolbar: {
            show: false
        }
    },
    plotOptions: {
        bar: {
            horizontal: true,
        }
    },
    dataLabels: {
        enabled: false
    },
    series: [{
        data: [400, 430, 448, 470, 540, 580, 690, 1100, 1200, 1380]
    }],
    colors: ["#39afd1"],
    xaxis: {
        categories: ['South Korea', 'Canada', 'United Kingdom', 'Netherlands', 'Italy', 'France', 'Japan', 'United States', 'China', 'Germany'],
    },
    states: {
        hover: {
            filter: 'none'
        }
    },
    grid: {
        borderColor: '#f1f3fa'
    }
}

var chart = new ApexCharts(
    document.querySelector("#basic-bar"),
    options
);

chart.render();


//
// GROUPED BAR CHART
//

var options = {
    chart: {
        height: 380,
        type: 'bar',
        toolbar: {
            show: false
        }
    },
    plotOptions: {
        bar: {
            horizontal: true,
            dataLabels: {
                position: 'top',
        },
    }  
    },
    dataLabels: {
        enabled: true,
        offsetX: -6,
        style: {
            fontSize: '12px',
            colors: ['#fff']
        }
    },
    colors: ["#fa5c7c", "#6c757d"],
    stroke: {
        show: true,
        width: 1,
        colors: ['#fff']
    },
    series: [{
        name: 'Series 1',
        data: [44, 55, 41, 64, 22, 43, 21]
    },{
        name: 'Series 2',
        data: [53, 32, 33, 52, 13, 44, 32]
    }],
    xaxis: {
        categories: [2001, 2002, 2003, 2004, 2005, 2006, 2007],
    },
    states: {
        hover: {
            filter: 'none'
        }
    },
    grid: {
        borderColor: '#f1f3fa'
    }
}

var chart = new ApexCharts(
    document.querySelector("#grouped-bar"),
    options
);

chart.render();


//
// STACKED BAR CHART
//

var options = {
    chart: {
        height: 380,
        type: 'bar',
        stacked: true,
        toolbar: {
            show: false
        }
    },
    plotOptions: {
        bar: {
            horizontal: true,
        },
        
    },
    stroke: {
        show: false
    },
    series: [{
        name: 'Marine Sprite',
        data: [44, 55, 41, 37, 22, 43, 21]
    },{
        name: 'Striking Calf',
        data: [53, 32, 33, 52, 13, 43, 32]
    },{
        name: 'Tank Picture',
        data: [12, 17, 11, 9, 15, 11, 20]
    },{
        name: 'Bucket Slope',
        data: [9, 7, 5, 8, 6, 9, 4]
    },{
        name: 'Reborn Kid',
        data: [25, 12, 19, 32, 25, 24, 10]
    }],
    xaxis: {
        categories: [2008, 2009, 2010, 2011, 2012, 2013, 2014],
        labels: {
            formatter: function(val) {
                return val + "K"
            }
        }
    },
    yaxis: {
        title: {
            text: undefined
        },
        
    },
    colors: ["#727cf5", "#0acf97", "#fa5c7c","#6c757d", "#39afd1"],
    tooltip: {
        y: {
            formatter: function(val) {
            return val + "K"
        }
        }
    },
    fill: {
        opacity: 1
    },
    states: {
        hover: {
            filter: 'none'
        }
    },
    legend: {
        position: 'top',
        horizontalAlign: 'center'
    },
    grid: {
        borderColor: '#f1f3fa'
    }
}

var chart = new ApexCharts(
    document.querySelector("#stacked-bar"),
    options
);

chart.render();


//
// 100% STACKED BAR CHART
//

var options = {
    chart: {
        height: 380,
        type: 'bar',
        stacked: true,
        stackType: '100%',
        toolbar: {
            show: false
        }
    },
    plotOptions: {
        bar: {
            horizontal: true,
        },
    },
    stroke: {
        width: 1,
        colors: ['#fff']
    },
    series: [{
        name: 'Marine Sprite',
        data: [44, 55, 41, 37, 22, 43, 21]
    },{
        name: 'Striking Calf',
        data: [53, 32, 33, 52, 13, 43, 32]
    },{
        name: 'Tank Picture',
        data: [12, 17, 11, 9, 15, 11, 20]
    },{
        name: 'Bucket Slope',
        data: [9, 7, 5, 8, 6, 9, 4]
    },{
        name: 'Reborn Kid',
        data: [25, 12, 19, 32, 25, 24, 10]
    }],
    xaxis: {
        categories: [2008, 2009, 2010, 2011, 2012, 2013, 2014],
    },
    colors: ["#ffbc00", "#39afd1", "#6c757d","#e3eaef", "#727cf5"],
    tooltip: {
            y: {
                formatter: function(val) {
                return val + "K"
            }
        }
    },
    fill: {
        opacity: 1
        
    },
    states: {
        hover: {
            filter: 'none'
        }
    },
    legend: {
        position: 'top',
        horizontalAlign: 'center'
    },
    grid: {
        borderColor: '#f1f3fa'
    }
}

var chart = new ApexCharts(
    document.querySelector("#full-stacked-bar"),
    options
);

chart.render();


//
// BAR WITH NEGATIVE VALUES
//

var options = {
    chart: {
        height: 380,
        type: 'bar',
        stacked: true,
        toolbar: {
            show: false
        }
    },
    colors: ['#fa5c7c','#0acf97'],
    plotOptions: {
        bar: {
            horizontal: true,
            barHeight: '80%',
            
        },
    },
    dataLabels: {
        enabled: false
    },
    stroke: {
        width: 1,
        colors: ["#fff"]
    },
    series: [{
        name: 'Males',
        data: [0.4, 0.65, 0.76, 0.88, 1.5, 2.1, 2.9, 3.8, 3.9, 4.2, 4, 4.3, 4.1, 4.2, 4.5, 3.9, 3.5, 3]
    },
    {
        name: 'Females',
        data: [-0.8, -1.05, -1.06, -1.18, -1.4, -2.2, -2.85, -3.7, -3.96, -4.22, -4.3, -4.4, -4.1, -4, -4.1, -3.4, -3.1, -2.8]
    }],
    grid: {
        xaxis: {
            showLines: false
        }
    },
    yaxis: {
        min: -5,
        max: 5,
        title: {
           // text: 'Age',
        },
    },
    tooltip: {
        shared: false,
        x: {
            formatter: function(val) {
                return val
            }
        },
        y: {
            formatter: function(val) {
                return Math.abs(val) + "%"
            }
        }
    },
    xaxis: {
      categories: ['85+', '80-84', '75-79', '70-74', '65-69', '60-64', '55-59', '50-54', '45-49', '40-44', '35-39', '30-34', '25-29', '20-24', '15-19', '10-14', '5-9', '0-4'],
      title: {
          text: 'Percent'
      },
      labels: {
        formatter: function(val) {
          return Math.abs(Math.round(val)) + "%"
        }
      }
    },
    grid: {
        borderColor: '#f1f3fa'
    }
}

var chart = new ApexCharts(
    document.querySelector("#negative-bar"),
    options
);

chart.render();


//
// PATTERNED BAR CHART
//

var options = {
    chart: {
        height: 380,
        type: 'bar',
        stacked: true,
        toolbar: {
            show: false
        },
        shadow: {
            enabled: true,
            blur: 1,
            opacity: 0.5
        }
    },
    plotOptions: {
        bar: {
            horizontal: true,
            barHeight: '60%',
            
        },
    },
    dataLabels: {
        enabled: false
    },
    stroke: {
        width: 2,
    },
    series: [{
        name: 'Marine Sprite',
        data: [44, 55, 41, 37, 22, 43, 21]
    },{
        name: 'Striking Calf',
        data: [53, 32, 33, 52, 13, 43, 32]
    },{
        name: 'Tank Picture',
        data: [12, 17, 11, 9, 15, 11, 20]
    },{
        name: 'Bucket Slope',
        data: [9, 7, 5, 8, 6, 9, 4]
    }],
    xaxis: {
        categories: [2008, 2009, 2010, 2011, 2012, 2013, 2014],
       
    },
    yaxis: {
        title: {
            text: undefined
        },
    },
    tooltip: {
        shared: false,
        y: {
            formatter: function(val) {
                return val + "K"
            }
        }
    },
    colors: ["#727cf5", "#0acf97", "#fa5c7c","#39afd1"],
    fill: {
        type: 'pattern',
        opacity: 1,
        pattern: {
            style: ['circles', 'slantedLines', 'verticalLines', 'horizontalLines'], // string or array of strings
          
        }
    },
    states: {
        hover: {
            filter: 'none'
        }
    },
    legend: {
        position: 'right',
    },
    grid: {
        borderColor: '#f1f3fa'
    },
    responsive: [{
        breakpoint: 600,
        options: {
          legend: {
            show: false
          },
        }
    }]
}

var chart = new ApexCharts(
    document.querySelector("#pattern-bar"),
    options
);

chart.render();



//
// BAR WITH IMAGE FILL
//

var labels = Array.apply(null, {length: 39}).map(function(el, index){
    return index + 1;
});

var options = {
    chart: {
        height: 450,
        type: 'bar',
        toolbar: {
            show: false
        },
        animations: {
            enabled: false
        }
    },
    plotOptions: {
        bar: {
            horizontal: true,
            barHeight: '100%',
            
        },
    },
    dataLabels: {
        enabled: false,
    },
    stroke: {
        colors: ["#fff"],
        width: 0.2
    },
    series: [{
        name: 'coins',
        data: [2, 4, 3, 4, 3, 5, 5, 6.5, 6, 5, 4, 5, 8, 7, 7, 8, 8, 10, 9, 9, 12, 12, 11, 12, 13, 14, 16, 14, 15, 17, 19, 21]
    }],
    labels: labels,
    yaxis: {
        axisBorder: {
            show: false
        },
        axisTicks: {
            show: false
        },
        labels: {
            show: false
        },
        title: {
            text: 'Weight',
        },
    },
    grid: {
        position: 'back',
        borderColor: '#f1f3fa'
    },
    fill: {
        type: 'image',
        opacity: 0.87,
        image: {
            src: ['assets/images/small/small-4.jpg'],
            width: 466, 
            height: 406
        }
    },
}

var chart = new ApexCharts(
    document.querySelector("#image-fill-bar"),
    options
);

chart.render();