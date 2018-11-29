/**
 * Theme: Hyper - Responsive Bootstrap 4 Admin Dashboard
 * Author: Coderthemes
 * Module/App: Dashboard
 */

! function ($) {
    "use strict";

    var Dashboard = function () {
        this.$body = $("body"),
        this.charts = []
    };

    Dashboard.prototype.respChart = function(selector,type,data, options) {
        var draw = Chart.controllers.line.prototype.draw;
        Chart.controllers.line.prototype.draw = function () {
            draw.apply(this, arguments);
            var ctx = this.chart.chart.ctx;
            var _stroke = ctx.stroke;
            ctx.stroke = function () {
                ctx.save();
                ctx.shadowColor = 'rgba(0,0,0,0.01)';
                ctx.shadowBlur = 20;
                ctx.shadowOffsetX = 0;
                ctx.shadowOffsetY = 5;
                _stroke.apply(this, arguments);
                ctx.restore();
            }
        };

        var draw2 = Chart.controllers.doughnut.prototype.draw;
        Chart.controllers.doughnut = Chart.controllers.doughnut.extend({
            draw: function () {
                draw2.apply(this, arguments);
                var ctx = this.chart.chart.ctx;
                var _fill = ctx.fill;
                ctx.fill = function () {
                    ctx.save();
                    ctx.shadowColor = 'rgba(0,0,0,0.03)';
                    ctx.shadowBlur = 4;
                    ctx.shadowOffsetX = 0;
                    ctx.shadowOffsetY = 3;
                    _fill.apply(this, arguments)
                    ctx.restore();
                }
            }
        });

        var draw3 = Chart.controllers.bar.prototype.draw;
        Chart.controllers.bar = Chart.controllers.bar.extend({
            draw: function () {
                draw3.apply(this, arguments);
                var ctx = this.chart.chart.ctx;
                var _fill = ctx.fill;
                ctx.fill = function () {
                    ctx.save();
                    ctx.shadowColor = 'rgba(0,0,0,0.01)';
                    ctx.shadowBlur = 20;
                    ctx.shadowOffsetX = 4;
                    ctx.shadowOffsetY = 5;
                    _fill.apply(this, arguments)
                    ctx.restore();
                }
            }
        });

        // get selector by context
        var ctx = selector.get(0).getContext("2d");
        // pointing parent container to make chart js inherit its width
        var container = $(selector).parent();

        // this function produce the responsive Chart JS
        function generateChart(){
            // make chart width fit with its container
            var ww = selector.attr('width', $(container).width() );
            var chart;
            switch(type){
                case 'Line':
                    chart = new Chart(ctx, {type: 'line', data: data, options: options});
                    break;
                case 'Doughnut':
                    chart = new Chart(ctx, {type: 'doughnut', data: data, options: options});
                    break;
                case 'Pie':
                    chart = new Chart(ctx, {type: 'pie', data: data, options: options});
                    break;
                case 'Bar':
                    chart = new Chart(ctx, {type: 'bar', data: data, options: options});
                    break;
                case 'Radar':
                    chart = new Chart(ctx, {type: 'radar', data: data, options: options});
                    break;
                case 'PolarArea':
                    chart = new Chart(ctx, {data: data, type: 'polarArea', options: options});
                    break;
            }
            return chart;
        };
        // run function - render chart at first load
        return generateChart();
    },
    // init various charts and returns
    Dashboard.prototype.initCharts = function() {
        var charts = [];
        if ($('#revenue-chart').length > 0) {
            var lineChart = {
                labels: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
                datasets: [{
                    label: "Current Week",
                    backgroundColor: 'transparent', //rgba(114, 124, 245, 0.4)
                    borderColor: '#727cf5',
                    data: [32, 42, 42, 62, 52, 75, 62]
                }, {
                    label: "Previous Week",
                    fill: true,
                    backgroundColor: 'transparent',
                    borderColor: "#0acf97",
                    data: [42, 58, 66, 93, 82, 105, 92]
                }]
            };

            var lineOpts = {
                maintainAspectRatio: false,
                legend: {
                    display: false
                },
                tooltips: {
                    intersect: false
                },
                hover: {
                    intersect: true
                },
                plugins: {
                    filler: {
                        propagate: false
                    }
                },
                scales: {
                    xAxes: [{
                        reverse: true,
                        gridLines: {
                            color: "rgba(0,0,0,0.05)"
                        }
                    }],
                    yAxes: [{
                        ticks: {
                            stepSize: 20
                        },
                        display: true,
                        borderDash: [5, 5],
                        gridLines: {
                            color: "rgba(0,0,0,0)",
                            fontColor: '#fff'
                        }
                    }]
                }
            };
            charts.push(this.respChart($("#revenue-chart"), 'Line', lineChart, lineOpts));
        }

        //barchart
        if ($('#high-performing-product').length > 0) {
            // create gradient
            var ctx = document.getElementById('high-performing-product').getContext("2d");
            var gradientStroke = ctx.createLinearGradient(0, 500, 0, 150);
            gradientStroke.addColorStop(0, "#fa5c7c");
            gradientStroke.addColorStop(1, "#727cf5");

            var barChart = {
                // labels: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"],
                labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                datasets: [
                    {
                        label: "Sales Analytics",
                        backgroundColor: gradientStroke,
                        borderColor: gradientStroke,
                        hoverBackgroundColor: gradientStroke,
                        hoverBorderColor: gradientStroke,
                        data: [65, 59, 80, 81, 56, 89, 40, 32, 65, 59, 80, 81]
                    },
                    {
                        label: "Dollar Rate",
                        backgroundColor: "#e3eaef",
                        borderColor: "#e3eaef",
                        hoverBackgroundColor: "#e3eaef",
                        hoverBorderColor: "#e3eaef",
                        data: [89, 40, 32, 65, 59, 80, 81, 56, 89, 40, 65, 59]
                    }
                ]
            };
            var barOpts = {
                maintainAspectRatio: false,
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        gridLines: {
                            display: false
                        },
                        stacked: false,
                        ticks: {
                            stepSize: 20
                        }
                    }],
                    xAxes: [{
                        barPercentage: 0.7,
                        categoryPercentage: 0.5,
                        stacked: false,
                        gridLines: {
                            color: "rgba(0,0,0,0.01)"
                        }
                    }]
                }
            };

            charts.push(this.respChart($("#high-performing-product"), 'Bar', barChart, barOpts));
        }

        if ($('#average-sales').length > 0) {
            //donut chart
            var donutChart = {
                labels: [
                    "Direct",
                    "Affilliate",
                    "Sponsored",
                    "E-mail"
                ],
                datasets: [
                    {
                        data: [300, 135, 48, 154],
                        backgroundColor: [
                            "#727cf5",
                            "#fa5c7c",
                            "#0acf97",
                            "#ebeff2"
                        ],
                        borderColor: "transparent",
                        borderWidth: "3",
                    }]
            };
            var donutOpts = {
                maintainAspectRatio: false,
                cutoutPercentage: 60,
                legend: {
                    display: false
                }
            };
            charts.push(this.respChart($("#average-sales"), 'Doughnut', donutChart, donutOpts));
        }
        return charts;
    },
    // inits the map
    Dashboard.prototype.initMaps = function() {
        //various examples
        if ($('#world-map-markers').length > 0) {
            $('#world-map-markers').vectorMap({
                map: 'world_mill_en',
                normalizeFunction: 'polynomial',
                hoverOpacity: 0.7,
                hoverColor: false,
                regionStyle: {
                    initial: {
                        fill: '#e3eaef'
                    }
                },
                markerStyle: {
                    initial: {
                        r: 9,
                        'fill': '#727cf5',
                        'fill-opacity': 0.9,
                        'stroke': '#fff',
                        'stroke-width': 7,
                        'stroke-opacity': 0.4
                    },

                    hover: {
                        'stroke': '#fff',
                        'fill-opacity': 1,
                        'stroke-width': 1.5
                    }
                },
                backgroundColor: 'transparent',
                markers: [{
                    latLng: [40.71, -74.00],
                    name: 'New York'
                }, {
                    latLng: [37.77, -122.41],
                    name: 'San Francisco'
                }, {
                    latLng: [-33.86, 151.20],
                    name: 'Sydney'
                }, {
                    latLng: [1.3, 103.8],
                    name: 'Singapore'
                }],
                zoomOnScroll: false
            });
        }
    },
    //initializing various components and plugins
    Dashboard.prototype.init = function () {
        var $this = this;
        // font
        Chart.defaults.global.defaultFontFamily = '-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,Oxygen-Sans,Ubuntu,Cantarell,"Helvetica Neue",sans-serif';        
        
        //default date range picker
        $('#dash-daterange').daterangepicker({
            singleDatePicker: true
        });

        // init charts
        $this.charts = this.initCharts();

        //init maps
        this.initMaps();

        // enable resizing matter
        $(window).on('resize', function(e) {
            $.each($this.charts, function( index, chart ) {
                try {
                    chart.destroy();
                }
                catch(err) {
                }
            });
            $this.charts = $this.initCharts();
        });
    },

    //init flotchart
    $.Dashboard = new Dashboard, $.Dashboard.Constructor = Dashboard
}(window.jQuery),

    //initializing Dashboard
    function ($) {
        "use strict";
        $.Dashboard.init()
    }(window.jQuery);