<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>Hyper - Responsive Bootstrap 4 Admin Dashboard</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta
	content="A fully featured admin theme which can be used to build CRM, CMS, etc."
	name="description" />
<meta content="Coderthemes" name="author" />
<!-- App favicon -->
<link rel="shortcut icon"
	href="resources/dist/assets/images/favicon.ico">

<!-- third party css -->
<link href="resources/dist/assets/css/vendor/fullcalendar.min.css"
	rel="stylesheet" type="text/css" />
<!-- third party css end -->

<!-- App css -->
<link href="resources/dist/assets/css/icons.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/dist/assets/css/app.min.css" rel="stylesheet"
	type="text/css" />
</head>
<body>

	<div class="row">
		<div class="col-12">

			<div class="card">
				<div class="card-body">
					<div class="row">
						<div class="col-lg-9">
							<div id="calendar"></div>
						</div>

					</div>
					<!-- end row -->
				</div>
				<!-- end card body-->
			</div>
			<!-- end card -->

			<!-- Add New Event MODAL -->
			<div class="modal fade" id="event-modal" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header pr-4 pl-4 border-bottom-0 d-block">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title">Add New Event</h4>
						</div>
						<div class="modal-body pt-3 pr-4 pl-4"></div>
						<div class="text-right pb-4 pr-4">
							<button type="button" class="btn btn-light " data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-success save-event  ">Create
								event</button>
							<button type="button" class="btn btn-danger delete-event  "
								data-dismiss="modal">Delete</button>
						</div>
					</div>
					<!-- end modal-content-->
				</div>
				<!-- end modal dialog-->
			</div>
			<!-- end modal-->

			<!-- Modal Add Category -->
			<div class="modal fade" id="add-category" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header border-bottom-0 d-block">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title">Add a category</h4>
						</div>
						<div class="modal-body p-4">
							<form>
								<div class="form-group">
									<label class="control-label">Category Name</label> <input
										class="form-control form-white" placeholder="Enter name"
										type="text" name="category-name" />
								</div>
								<div class="form-group">
									<label class="control-label">Choose Category Color</label> <select
										class="form-control form-white"
										data-placeholder="Choose a color..." name="category-color">
										<option value="primary">Primary</option>
										<option value="success">Success</option>
										<option value="danger">Danger</option>
										<option value="info">Info</option>
										<option value="warning">Warning</option>
										<option value="dark">Dark</option>
									</select>
								</div>

							</form>

							<div class="text-right">
								<button type="button" class="btn btn-light "
									data-dismiss="modal">Close</button>
								<button type="button"
									class="btn btn-primary ml-1   save-category"
									data-dismiss="modal">Save</button>
							</div>

						</div>
						<!-- end modal-body-->
					</div>
					<!-- end modal-content-->
				</div>
				<!-- end modal dialog-->
			</div>
			<!-- end modal-->
		</div>
		<!-- end col-12 -->
	</div>
	<!-- end row -->
	<!-- /Right-bar -->


	<!-- App js -->
	<script src="resources/dist/assets/js/app.min.js"></script>

	<!-- third party js -->
	<script src="resources/dist/assets/js/vendor/jquery-ui.min.js"></script>
	<script src="resources/dist/assets/js/vendor/fullcalendar.min.js"></script>
	<!-- third party js ends -->

	<!-- demo app -->
	<script src="resources/dist/assets/js/pages/demo.calendar.js"></script>
	<!-- end demo js-->
	<input type="hidden" id="am" value="${am}"/>
	<input type="hidden" id="pm" value="${pm}"/>
</body>
</html>