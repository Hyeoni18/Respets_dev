"use strict";

const gulp = require("gulp"),
    newer = require("gulp-newer"),
    imagemin = require("gulp-imagemin"),
    sass = require("gulp-sass"),
    sourcemaps = require("gulp-sourcemaps"),
    autoprefixer = require("gulp-autoprefixer"),
    cleanCSS = require('gulp-clean-css'),
    rename = require("gulp-rename"),
    concat = require("gulp-concat"),
    uglify = require("gulp-uglify"),
    gutil = require("gulp-util"),
    lodash = require("lodash"),
    gulpSequence = require("gulp-sequence"),
    browsersync = require("browser-sync"),
    fileinclude = require('gulp-file-include');

const folder = {
    src: "src/", // source files
    dist: "dist/", // build files
    dist_assets: "dist/assets/" //build assets files
};

/*
Copy assets/vendors from their node_module package to scss & js folder
Read More: https://florian.ec/articles/frontend-dependencies-npm/
*/

gulp.task("copy-assets", function () {
    var assets = {
        js: [
            "./node_modules/jquery/dist/jquery.js",
            "./node_modules/bootstrap/dist/js/bootstrap.bundle.js",
            "./node_modules/moment/moment.js",
            "./node_modules/metismenu/dist/metisMenu.js",
            "./node_modules/jquery-slimscroll/jquery.slimscroll.js",
            "./node_modules/daterangepicker/daterangepicker.js",
            "./node_modules/jquery-toast-plugin/dist/jquery.toast.min.js",
            "./node_modules/select2/dist/js/select2.min.js",
            "./node_modules/jquery-mask-plugin/dist/jquery.mask.min.js",
            "./node_modules/twitter-bootstrap-wizard/jquery.bootstrap.wizard.min.js",
            "./node_modules/bootstrap-timepicker/js/bootstrap-timepicker.min.js",
            "./node_modules/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.js",
            "./node_modules/bootstrap-maxlength/bootstrap-maxlength.min.js"
        ],
        scss: [
            "./node_modules/daterangepicker/daterangepicker.css",
            "./node_modules/jquery-toast-plugin/dist/jquery.toast.min.css",
            "./node_modules/select2/dist/css/select2.min.css",
            "./node_modules/bootstrap-timepicker/css/bootstrap-timepicker.min.css",
            "./node_modules/bootstrap-touchspin/dist/jquery.bootstrap-touchspin.min.css"
        ],
    };

    var third_party_assets = {
        js: [
            "./node_modules/chart.js/dist/Chart.bundle.min.js",
            "./node_modules/d3/dist/d3.min.js",
            "./node_modules/britecharts/dist/bundled/britecharts.min.js",
            "./node_modules/datatables.net/js/jquery.dataTables.js",
            "./node_modules/datatables.net-bs4/js/dataTables.bootstrap4.js",
            "./node_modules/datatables.net-responsive/js/dataTables.responsive.min.js",
            "./node_modules/datatables.net-responsive-bs4/js/responsive.bootstrap4.min.js",
            "./node_modules/datatables.net-buttons/js/dataTables.buttons.min.js",
            "./node_modules/datatables.net-buttons-bs4/js/buttons.bootstrap4.min.js",
            "./node_modules/datatables.net-buttons/js/buttons.html5.min.js",
            "./node_modules/datatables.net-buttons/js/buttons.flash.min.js",
            "./node_modules/datatables.net-buttons/js/buttons.print.min.js",
            "./node_modules/datatables.net-keytable/js/dataTables.keyTable.min.js",
            "./node_modules/datatables.net-select/js/dataTables.select.min.js",
            "./node_modules/jquery-datatables-checkboxes/js/dataTables.checkboxes.min.js",
            "./node_modules/jquery-ui/jquery-ui.min.js",
            "./node_modules/fullcalendar/dist/fullcalendar.min.js",
            "./node_modules/gmaps/gmaps.min.js",
            "./node_modules/admin-resources/jquery.vectormap/jquery-jvectormap-1.2.2.min.js",
            "./node_modules/admin-resources/jquery.vectormap/maps/jquery-jvectormap-world-mill-en.js",
            "./node_modules/admin-resources/jquery.vectormap/maps/jquery-jvectormap-us-merc-en.js",
            "./node_modules/admin-resources/jquery.vectormap/maps/jquery-jvectormap-au-mill-en.js",
            "./node_modules/admin-resources/jquery.vectormap/maps/jquery-jvectormap-us-il-chicago-mill-en.js",
            "./node_modules/admin-resources/jquery.vectormap/maps/jquery-jvectormap-in-mill-en.js",
            "./node_modules/dragula/dist/dragula.min.js",
            "./node_modules/dropzone/dist/min/dropzone.min.js",
            "./node_modules/apexcharts/dist/apexcharts.min.js",
            "./node_modules/summernote/dist/summernote-bs4.min.js",
            "./node_modules/simplemde/dist/simplemde.min.js"
        ],
        css: [
            "./node_modules/admin-resources/jquery.vectormap/jquery-jvectormap-1.2.2.css",
            "./node_modules/britecharts/dist/css/britecharts.min.css",
            "./node_modules/datatables.net-bs4/css/dataTables.bootstrap4.css",
            "./node_modules/datatables.net-responsive-bs4/css/responsive.bootstrap4.css",
            "./node_modules/datatables.net-buttons-bs4/css/buttons.bootstrap4.css",
            "./node_modules/datatables.net-select-bs4/css/select.bootstrap4.css",
            "./node_modules/fullcalendar/dist/fullcalendar.min.css",
            "./node_modules/@mdi/font/css/materialdesignicons.min.css",
            "./node_modules/summernote/dist/summernote-bs4.css",
            "./node_modules/simplemde/dist/simplemde.min.css"
        ],
        font: [
            "./node_modules/@mdi/font/css/materialdesignicons.min.css"
        ]
    };

    //copying third party assets
    lodash(third_party_assets).forEach(function (assets, type) {
        if (type == "css") {
            // gulp.src(assets).pipe(gulp.dest(folder.src + "css/vendor"));
            gulp.src(assets).pipe(gulp.dest(folder.dist_assets + "css/vendor"));
        }
        else {
            // gulp.src(assets).pipe(gulp.dest(folder.src + "js/vendor"));
            gulp.src(assets).pipe(gulp.dest(folder.dist_assets + "js/vendor"));
        }
    });

    //copying required assets
    lodash(assets).forEach(function (assets, type) {
        if (type == "scss") {
            gulp
                .src(assets)
                .pipe(
                    rename({
                        // rename aaa.css to _aaa.scss
                        prefix: "_",
                        extname: ".scss"
                    })
                )
                .pipe(gulp.dest(folder.src + "scss/vendor"));
        } else {
            gulp.src(assets).pipe(gulp.dest(folder.src + "js/vendor"));
        }
    });
});

// image processing
gulp.task("imageMin", function () {
    var out = folder.dist_assets + "images";
    return gulp
        .src(folder.src + "images/**/*")
        .pipe(newer(out))
        .pipe(imagemin())
        .pipe(gulp.dest(out));
});

// copy fonts
// copy fonts from src folder to dist folder
gulp.task("fonts", function () {
    var out = folder.dist_assets + "fonts/";

    return gulp.src([folder.src + "fonts/**/*"]).pipe(gulp.dest(out));
});

// copy html
// copy html files from src folder to dist folder, also copy favicons
gulp.task("html", function () {
    var out = folder.dist;

    return gulp
        .src([
            folder.src + "html/*.html",
            folder.src + "html/*.ico", // favicons
            folder.src + "html/*.png"
        ])
        .pipe(fileinclude({
            prefix: '@@',
            basepath: '@file',
            indent: true
        }))
        .pipe(gulp.dest(out));
});

// compile & minify sass
gulp.task("css", function () {
    gulp
        .src([folder.src + "/scss/icons.scss"])
        .pipe(sourcemaps.init())
        .pipe(sass()) // scss to css
        .pipe(
            autoprefixer({
                browsers: ["last 2 versions"]
            })
        )
        .pipe(gulp.dest(folder.dist_assets + "css/"))
        .pipe(cleanCSS())
        .pipe(
            rename({
                // rename app.css to icons.min.css
                suffix: ".min"
            })
        )
        .pipe(sourcemaps.write("./")) // source maps for icons.min.css
        .pipe(gulp.dest(folder.dist_assets + "css/"));

    return gulp
        .src([folder.src + "/scss/app.scss"])
        .pipe(sourcemaps.init())
        .pipe(sass()) // scss to css
        .pipe(
            autoprefixer({
                browsers: ["last 2 versions"]
            })
        )
        .pipe(gulp.dest(folder.dist_assets + "css/"))
        .pipe(cleanCSS())
        .pipe(
            rename({
                // rename app.css to app.min.css
                suffix: ".min"
            })
        )
        .pipe(sourcemaps.write("./")) // source maps for app.min.css
        .pipe(gulp.dest(folder.dist_assets + "css/"));
});

// js
gulp.task("javascript", function () {
    var out = folder.dist_assets + "js/";

    //copying demo pages related assets
    var app_pages_assets = {
        js: [
            folder.src + "js/pages/demo.dashboard.js",
            folder.src + "js/pages/demo.britechart.js",
            folder.src + "js/pages/demo.calendar.js",
            folder.src + "js/pages/demo.chartjs.js",
            folder.src + "js/pages/demo.datatable-init.js",
            folder.src + "js/pages/demo.form-advanced.js",
            folder.src + "js/pages/demo.form-validation.js",
            folder.src + "js/pages/demo.form-wizard.js",
            folder.src + "js/pages/demo.google-maps.js",
            folder.src + "js/pages/demo.vector-maps.js",
            folder.src + "js/pages/demo.profile.js",
            folder.src + "js/pages/demo.toastr.js",
            folder.src + "js/pages/demo.project-detail.js",
            folder.src + "js/pages/demo.apex-line.js",
            folder.src + "js/pages/demo.apex-area.js",
            folder.src + "js/pages/demo.apex-column.js",
            folder.src + "js/pages/demo.apex-bar.js",
            folder.src + "js/pages/demo.apex-mixed.js",
            folder.src + "js/pages/demo.apex-bubble.js",
            folder.src + "js/pages/demo.apex-candlestick.js",
            folder.src + "js/pages/demo.apex-scatter.js",
            folder.src + "js/pages/demo.apex-pie.js",
            folder.src + "js/pages/demo.apex-radialbar.js",
            folder.src + "js/pages/demo.apex-heatmap.js",
            folder.src + "js/pages/demo.apex-sparklines.js",
            folder.src + "js/pages/demo.summernote.js",
            folder.src + "js/pages/demo.simplemde.js",
            folder.src + "js/pages/demo.products.js",
            folder.src + "js/pages/demo.customers.js",
            folder.src + "js/pages/demo.sellers.js",
            folder.src + "js/pages/demo.widgets.js"
        ]
    };

    lodash(app_pages_assets).forEach(function (assets, type) {
        gulp.src(assets)
            .pipe(uglify())
            .on("error", function (err) {
                gutil.log(gutil.colors.red("[Error]"), err.toString());
            })
            .pipe(gulp.dest(out + "pages"));
    });

    // optional components
    var components_assets = {
        js: [
            folder.src + "js/ui/component.dragula.js",
            folder.src + "js/ui/component.fileupload.js"
        ]
    };

    lodash(components_assets).forEach(function (assets, type) {
        gulp.src(assets)
            .pipe(uglify())
            .on("error", function (err) {
                gutil.log(gutil.colors.red("[Error]"), err.toString());
            })
            .pipe(gulp.dest(out + "ui"));
    });

    // It's important to keep files at this order
    // so that `app.min.js` can be executed properly
    return gulp
        .src([
            folder.src + "js/vendor/jquery.js",
            folder.src + "js/vendor/bootstrap.bundle.js",
            folder.src + "js/vendor/moment.js",
            folder.src + "js/vendor/jquery.slimscroll.js",
            folder.src + "js/vendor/daterangepicker.js",
            folder.src + "js/vendor/metisMenu.js",
            folder.src + "js/vendor/jquery.toast.min.js",
            folder.src + "js/vendor/select2.min.js",
            folder.src + "js/vendor/jquery.mask.min.js",
            folder.src + "js/vendor/jquery.bootstrap.wizard.min.js",
            folder.src + "js/vendor/bootstrap-timepicker.min.js",
            folder.src + "js/vendor/jquery.bootstrap-touchspin.min.js",
            folder.src + "js/vendor/bootstrap-maxlength.min.js",
            folder.src + "js/hyper.js"
        ])
        .pipe(sourcemaps.init())
        .pipe(concat("app.js"))
        .pipe(gulp.dest(out))
        .pipe(
            rename({
                // rename app.js to app.min.js
                suffix: ".min"
            })
        )
        .pipe(uglify())
        .on("error", function (err) {
            gutil.log(gutil.colors.red("[Error]"), err.toString());
        })
        .pipe(sourcemaps.write("./"))
        .pipe(gulp.dest(out));
});

// live browser loading
gulp.task("browserSync", function () {
    browsersync.init({
        server: {
            baseDir: folder.dist,
            middleware: [
                function (req, res, next) {
                    req.method = 'GET';
                    next();
                }
            ]
        }
    });
});

// watch all changes
gulp.task("watch", function () {
    gulp.watch(folder.src + "html/**", ["html", browsersync.reload]);
    gulp.watch(folder.src + "assets/images/**/*", [
        "imageMin",
        browsersync.reload
    ]);
    gulp.watch(folder.src + "assets/fonts/**/*", ["fonts", browsersync.reload]);
    gulp.watch(folder.src + "scss/**/*", ["css", browsersync.reload]);
    gulp.watch(folder.src + "js/**/*", ["javascript", browsersync.reload]);
});

// default task
gulp.task(
    "default",
    gulpSequence(
        "copy-assets",
        "html",
        "imageMin",
        "fonts",
        "css",
        "javascript",
        "browserSync",
        "watch"
    )
);

// build
gulp.task(
    "build",
    gulpSequence("copy-assets", "html", "imageMin", "fonts", "css", "javascript")
);

// doc
gulp.task("docs", function () {
    browsersync.init({
        server: {
            baseDir: "docs"
        }
    });
});
