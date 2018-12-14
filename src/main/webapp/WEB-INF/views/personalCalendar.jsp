<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Respets :: 캘린더</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon" href="resources/images/logo-sm.png" />

<!-- third party css -->
<link
	href="resources/dist/assets/css/vendor/jquery-jvectormap-1.2.2.css"
	rel="stylesheet" type="text/css" />
<link href="resources/dist/assets/css/vendor/fullcalendar.min.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />

</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
<body data-layout="topnav">

	<!-- Begin page -->
	<div class="wrapper">

		<!-- ============================================================== -->
		<!-- Start Page Content here -->
		<!-- ============================================================== -->

		<div class="content-page">
			<div class="content">

				<!-- Topbar Start -->
				<%@ include file="topbar-landing.jsp"%>
				<!-- end Topbar -->

				<div class="topnav">
					<div class="container-fluid">
						<nav class="navbar navbar-dark navbar-expand-lg topnav-menu">

							<div class="collapse navbar-collapse" id="topnav-menu-content">
								<ul class="navbar-nav">
									<li class="nav-item"><a href="./recentMyBookingList" class="nav-link">
											<i class="mdi mdi-clipboard-check mr-1"></i>최근 예약 목록
									</a></li>
									<li class="nav-item"><a href="./personalAllBookingList" class="nav-link">
											<i class="mdi mdi-clipboard-text mr-1"></i>전체 예약 목록
									</a></li>
									<li class="nav-item"><a href="./likeBusinessList" class="nav-link">
											<i class="mdi mdi-heart mr-1"></i>즐겨찾기 목록
									</a></li>
									<li class="nav-item"><a href="./petList" class="nav-link">
											<i class="mdi mdi-paw mr-1"></i>나의 반려동물 정보
									</a></li>
									<li class="nav-item"><a href="./myInfo" class="nav-link">
											<i class="mdi mdi-account-circle mr-1"></i>나의 회원 정보
									</a></li>
								</ul>
							</div>
						</nav>
					</div>
				</div>


				<!-- Start Content-->
				<div class="container-fluid">

					<!-- start page title -->
					<div class="row">
						<div class="col-12">
							<div class="page-title-box">
								<div class="page-title-right">
									<form class="form-inline"></form>
								</div>
								<h4 class="page-title">내 반려동물 달력</h4>
							</div>
						</div>
					</div>
					<!-- end page title -->

					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-body">

									<!-- <h4 class="header-title">캘린더</h4> -->

									<div class="col-12">
										<div id="calendar"></div>
									</div> <!-- col-12 end -->
								</div>
								<!-- end card-body-->
								<div class="modal fade show" id="event-modal" tabindex="-1"
									style="display: none; padding-right: 16px;">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header pr-4 pl-4 border-bottom-0 d-block">
												<button type="button" class="close" data-dismiss="modal"
													aria-hidden="true">×</button>
												<h4 class="modal-title">일정 확인</h4>
											</div>
											<div class="modal-body pt-3 pr-4 pl-4">
												<form>
													<label>Change event name</label>
													<div class="input-group m-b-15">
														<input class="form-control" type="text"
															value="See John Deo"><span
															class="input-group-append"><button type="submit"
																class="btn btn-success btn-md  ">
																<i class="fa fa-check"></i> Save
															</button></span>
													</div>
												</form>
											</div>
											<div class="text-right pb-4 pr-4">
												<button type="button" class="btn btn-light "
													data-dismiss="modal">Close</button>
												
											</div>
										</div>
										<!-- end modal-content-->
									</div>
									<!-- end modal dialog-->
								</div>
							</div>
							<!-- end card-->
						</div>
						<!-- end col-->
					</div>
					<!-- end row -->
				</div>
				<!-- container -->

			</div>
			<!-- content -->

			<!-- Footer Start -->
			<footer class="footer footer-alt"> Copyright Respets Corp. All rights reserved. </footer>
			<!-- end Footer -->

		</div>

		<!-- ============================================================== -->
		<!-- End Page content -->
		<!-- ============================================================== -->


	</div>
	<!-- END wrapper -->


	<!-- App js -->
	<script src="/resources/dist/assets/js/app.min.js"></script>

	<!-- third party js -->
	<script src="resources/dist/assets/js/vendor/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="resources/dist/assets/js/vendor/jquery-jvectormap-world-mill-en.js"></script>
	<script src="/resources/dist/assets/js/vendor/jquery-ui.min.js"></script>
	<script src="/resources/dist/assets/js/vendor/fullcalendar-per.min.js"></script>
	<!-- third party js ends -->

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
! function (e) {
    "use strict";
    var t = function () {
        this.$body = e("body"), this.$modal = e("#event-modal"),
            this.$event = "#external-events div.external-event",
            this.$calendar = e("#calendar"), this.$saveCategoryBtn = e(".save-category"),
            this.$categoryForm = e("#add-category form"), this.$extEvents = e("#external-events"),
            this.$calendarObj = null
    };
    t.prototype.onDrop = function (t, n) {
            var a = t.data("eventObject"),
                l = t.attr("data-class"),
                i = e.extend({}, a);
            i.start = n, l && (i.className = [l]),
                this.$calendar.fullCalendar("renderEvent", i, !0),
                e("#drop-remove").is(":checked") && t.remove()
        }, t.prototype.onEventClick = function (t, n, a) {
        	
        	var jsonData = ${e};
        	console.log(jsonData);
        	for(var i=0; i<jsonData.length; i++) {
        		console.log(jsonData[i].bk_no);
        		console.log(t.start);
        		console.log(t.start._i)
        	
            var l = this,
                i = e("<form></form>");
            i.append("<label>"+t.title+"</label>"),
                i.append("<p><br/> 업체명: " + t.bus_name 
                		+ "<br/><br/> 예약 시간: " + t.start._i 
                		+ "<br/><br/> 업체 주소: " + t.bus_addr + " " + t.bus_addr2 + "</p>"),
                l.$modal.modal({
                    backdrop: "static"
                }), l.$modal.find(".delete-event").show().end().find(".save-event").hide().end().find(".modal-body").empty().prepend(i).end().
            find(".delete-event").unbind("click").click(function () {
                l.$calendarObj.fullCalendar("removeEvents", function (e) {
                        return e._id == t._id
                    }),
                    l.$modal.modal("hide")
            }), l.$modal.find("form").on("submit", function () {
                return t.title = i.find("input[type=text]").val(),
                    l.$calendarObj.fullCalendar("updateEvent", t), l.$modal.modal("hide"), !1
            })
        }
        },
        t.prototype.onSelect = function (t, n, a) {
            var l = this;
            l.$modal.modal({
                backdrop: "static"
            });
            var i = e("<form></form>");
        
                l.$modal.find(".delete-event").hide().end().find(".save-event").show().end().find(".modal-body")
                .empty().prepend(i).end().find(".save-event").unbind("click").click(function () {
                    i.submit()
                }), l.$modal.find("form").on("submit", function () {
                    var e = i.find("input[name='title']")
                        .val(),
                        a = (i.find("input[name='beginning']")
                            .val(), i.find("input[name='ending']")
                            .val(), i.find("select[name='category'] option:checked").val());
                    return null !== e && 0 != e.length ? (l.$calendarObj.fullCalendar("renderEvent", {
                            title: e,
                            start: t,
                            end: n,
                            allDay: !1,
                            className: a
                        }, !0),
                        l.$modal.modal("hide")) : alert("You have to give a title to your event"), !1
                }),
                l.$calendarObj.fullCalendar("unselect")
        }, t.prototype.enableDrag = function () {
            e(this.$event).each(function () {
                var t = {
                    title: e.trim(e(this).text())
                };
                e(this).data("eventObject", t),
                    e(this).draggable({
                        zIndex: 999,
                        revert: !0,
                        revertDuration: 0
                    })
            })
        }, t.prototype.init = function () {
            this.enableDrag();
            var data = ${e}; //json으로 받아 온 데이터
            var t = new Date,
                n = (
                    t.getDate(), t.getMonth(), t.getFullYear(),
                    new Date(e.now())),
                a = data,
                l = this;
            l.$calendarObj = l.$calendar.fullCalendar({
                    slotDuration: "00:15:00",
                    minTime: "08:00:00",
                    maxTime: "19:00:00",
                    defaultView: "month",
                    handleWindowResize: !0,
                    height: e(window).height() - 200,
                    header: {
                        left: "prev,next today",
                        center: "title",
                        right: "month,agendaWeek,agendaDay"
                    },
                    events: a,
                    editable: !0,
                    droppable: !0,
                    eventLimit: !0,
                    selectable: !0,
                    drop: function (t) {
                        l.onDrop(e(this), t)
                    },
                    select: function (e, t, n) {
                        l.onSelect(e, t, n)
                    },
                    eventClick: function (e, t, n) {
                        l.onEventClick(e, t, n)
                    }
                }),
                this.$saveCategoryBtn.on("click", function () {
                    var e = l.$categoryForm.find("input[name='category-name']").val(),
                        t = l.$categoryForm.find("select[name='category-color']").val();
                    null !== e && 0 != e.length && (l.$extEvents.append('<div class="external-event bg-' + t + '" data-class="bg-' + t + '" style="position: relative;"><i class="mdi mdi-checkbox-blank-circle mr-2 vertical-middle"></i>' + e + "</div>"),
                        l.enableDrag())
                })
        }, e.CalendarApp = new t, e.CalendarApp.Constructor = t
}(window.jQuery),
function (e) {
    "use strict";
    e.CalendarApp.init()
}(window.jQuery);
</script>

	<script src="resources/dist/assets/js/pages/demo.widgets.js"></script>
	<script src="resources/dist/assets/js/pages/demo.dashboard.js"></script>
	<!-- end demo js-->

</body>
</html>
