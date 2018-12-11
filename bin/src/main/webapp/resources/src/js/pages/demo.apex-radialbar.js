/**
 * Theme: Hyper - Responsive Bootstrap 4 Admin Dashboard
 * Author: Coderthemes
 * Module/App: Apex RadialBar Charts
 */

//
// BASIC RADIALBAR CHART
//

var options = {
    chart: {
        height: 320,
        type: 'radialBar',
    },
    plotOptions: {
        radialBar: {
            hollow: {
                size: '70%',
            }
        },
    },
    colors: ["#39afd1"],
    series: [70],
    labels: ['CRICKET'],

}

var chart = new ApexCharts(
    document.querySelector("#basic-radialbar"),
    options
);

chart.render();


//
// MULTIPLE RADIALBARS
//

var options = {
    chart: {
        height: 320,
        type: 'radialBar',
    },
    plotOptions: {
        circle: {
            dataLabels: {
                showOn: 'hover'
            }
        }
    },
    colors: ["#6c757d", "#ffbc00", "#727cf5", "#0acf97"],
    series: [44, 55, 67, 83],
    labels: ['Apples', 'Oranges', 'Bananas', 'Berries'],
    responsive: [{
        breakpoint: 380,
        options: {
          chart: {
            height: 260,
          }
        }
    }]
    
}

var chart = new ApexCharts(
    document.querySelector("#multiple-radialbar"),
    options
);

chart.render();


//
// CIRCLE CHART - CUSTOM ANGLE
//

var options = {
    chart: {
        height: 380,
        width: 380,
        type: 'radialBar',
    },
    plotOptions: {
        radialBar: {
            offsetY: -30,
            startAngle: 0,
            endAngle: 270,
            hollow: {
                margin: 5,
                size: '30%',
                background: 'transparent',
                image: undefined,
            },
            dataLabels: {
                name: {
                    show: false,
                    
                },
                value: {
                    show: false,
                }
            }
        }
    },
    colors: ['#0acf97', '#727cf5', '#fa5c7c', '#ffbc00'],
    series: [76,67,61,90],
    labels: ['Vimeo', 'Messenger', 'Facebook', 'LinkedIn'],
    legend: {
        show: true,
        floating: true,
        fontSize: '13px',
        position: 'left',
        verticalAlign: 'top',
        textAnchor: 'end',
        labels: {
            useSeriesColors: true,
        },
        markers: {
            size: 0
        },
        formatter: function(seriesName, opts) {
            return seriesName + ":  " + opts.globals.series[opts.seriesIndex]
        },
        itemMargin: {
            vertical: 8,
        },
        containerMargin: {
            left: 180,
            top: 8
        }
    },
    responsive: [{
        breakpoint: 380,
        options: {
          chart: {
            height: 240,
            width: 240,
          },
          legend: {
              show: false
          }
        }
    }]
}

var chart = new ApexCharts(
    document.querySelector("#circle-angle-radial"),
    options
);

chart.render();


//
// CIRCLE CHART WITH IMAGE
//

var options = {
    chart: {
        height: 380,
        type: 'radialBar',
    },
    fill: {
        type: 'image',
        image: {
            src: ['assets/images/small/small-2.jpg'],
        }
    },
    plotOptions: {
        radialBar: {
            hollow: {
                size: '70%',
            }
        },
    },
    series: [70],
    stroke: {
      lineCap: 'round'
    },
    labels: ['Volatility'],
    responsive: [{
        breakpoint: 380,
        options: {
          chart: {
            height: 280
          }
        }
    }]
}

var chart = new ApexCharts(
    document.querySelector("#image-radial"),
    options
);

chart.render();


//
// STROKED CIRCULAR GUAGE
//

var options = {
    chart: {
        height: 380,
        type: 'radialBar',
    },
    plotOptions: {
        radialBar: {
            startAngle: -135,
            endAngle: 135,
            dataLabels: {
                name: {
                    fontSize: '16px',
                    color: undefined,
                    offsetY: 120
                },
                value: {
                    offsetY: 76,
                    fontSize: '22px',
                    color: undefined,
                    formatter: function (val) {
                        return val + "%";
                    }
                }
            }
        }
    },
    fill: {
        gradient: {
            enabled: true,
            shade: 'dark',
            shadeIntensity: 0.15,
            inverseColors: false,
            opacityFrom: 1,
            opacityTo: 1,
            stops: [0, 50, 65, 91]
        },
    },
    stroke: {
        dashArray: 4
    },
    colors: ["#727cf5"],
    series: [67],
    labels: ['Median Ratio'],
    responsive: [{
        breakpoint: 380,
        options: {
          chart: {
            height: 280
          }
        }
    }]
}

var chart = new ApexCharts(
    document.querySelector("#stroked-guage-radial"),
    options
);

chart.render();

// window.setInterval(function () {
//     chart.updateSeries([Math.floor(Math.random() * (100 - 1 + 1)) + 1])
// }, 2000)
