/**
 * Theme: Hyper - Responsive Bootstrap 4 Admin Dashboard
 * Author: Coderthemes
 * Module/App: Widgets
 */


Apex.grid = {
    padding: {
        right: 0,
        left: 0
    }
}

Apex.dataLabels = {
    enabled: false
}

var randomizeArray = function (arg) {
    var array = arg.slice();
    var currentIndex = array.length, temporaryValue, randomIndex;

    while (0 !== currentIndex) {

        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;

        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }

    return array;
}


$(document).ready(function () {
    "use strict";
    // data for the sparklines that appear below header area
    var sparklineData = [47, 45, 54, 38, 56, 24, 65, 31, 37, 39, 62, 51, 35, 41, 35, 27, 93, 53, 61, 27, 54, 43, 19, 46];

    // the default colorPalette for this dashboard
    var colorPalette = ['#00D8B6', '#008FFB', '#FEB019', '#FF4560', '#775DD0']

    var salesspark = {
        chart: {
            type: 'area',
            height: 180,
            sparkline: {
                enabled: true
            },
        },
        stroke: {
            width: 2,
            curve: 'straight'
        },
        fill: {
            opacity: 0.2,
        },
        series: [{
            name: 'Hyper Sales ',
            data: randomizeArray(sparklineData)
        }],
        yaxis: {
            min: 0
        },
        colors: ['#DCE6EC'],
        title: {
            text: '$424,652',
            offsetX: 20,
            offsetY: 20,
            style: {
                fontSize: '24px'
            }
        },
        subtitle: {
            text: 'Sales',
            offsetX: 20,
            offsetY: 55,
            style: {
                fontSize: '14px'
            }
        }
    }
    new ApexCharts(document.querySelector("#sales-spark"), salesspark).render();

    var profitspark = {
        chart: {
            type: 'bar',
            height: 180,
            sparkline: {
                enabled: true
            },
        },
        stroke: {
            width: 0,
            curve: 'straight'
        },
        fill: {
            opacity: 0.5,
        },
        series: [{
            name: 'Net Profits ',
            data: randomizeArray(sparklineData)
        }],
        xaxis: {
            crosshairs: {
                width: 1
            },
        },
        yaxis: {
            min: 0
        },
        colors: ['#0acf97'],
        title: {
            text: '$135,965',
            offsetX: 20,
            offsetY: 20,
            style: {
                fontSize: '24px'
            }
        },
        subtitle: {
            text: 'Profits',
            offsetX: 20,
            offsetY: 55,
            style: {
                fontSize: '14px'
            }
        }
    }

    new ApexCharts(document.querySelector("#profit-spark"), profitspark).render();

    // Other Sparkline


    var spark1 = {
        chart: {
            type: 'line',
            height: 100,
            sparkline: {
                enabled: true
            },
        },
        series: [{
            data: [25, 66, 41, 59, 25, 44, 12, 36, 9, 21]
        }],
        stroke: {
            width: 4,
            curve: 'smooth'
        },
        markers: {
            size: 0
        },
        colors: ['#734CEA']
    }

    var spark2 = {
        chart: {
            type: 'bar',
            height: 100,
            sparkline: {
                enabled: true
            },
        },
        series: [{
            data: [12, 14, 2, 47, 32, 44, 14, 55, 41, 69]
        }],
        stroke: {
            width: 3,
            curve: 'smooth'
        },
        markers: {
            size: 0
        },
        colors: ['#34bfa3']
    }

    var spark3 = {
        chart: {
            type: 'line',
            height: 100,
            sparkline: {
                enabled: true
            },
        },
        series: [{
            data: [47, 45, 74, 32, 56, 31, 44, 33, 45, 19]
        }],
        stroke: {
            width: 4,
            curve: 'smooth'
        },
        markers: {
            size: 0
        },
        colors: ['#f4516c']
    }

    var spark4 = {
        chart: {
            type: 'bar',
            height: 100,
            sparkline: {
                enabled: true
            },
        },
        plotOptions: {
            bar: {
                horizontal: false,
                endingShape: 'rounded',
                columnWidth: '55%',
            },
        },
        series: [{
            data: [15, 75, 47, 65, 14, 32, 19, 54, 44, 61]
        }],
        stroke: {
            width: 3,
            curve: 'smooth'
        },
        markers: {
            size: 0
        },
        colors: ['#00c5dc']
    }

    new ApexCharts(document.querySelector("#spark1"), spark1).render();
    new ApexCharts(document.querySelector("#spark2"), spark2).render();
    new ApexCharts(document.querySelector("#spark3"), spark3).render();
    new ApexCharts(document.querySelector("#spark4"), spark4).render();
});