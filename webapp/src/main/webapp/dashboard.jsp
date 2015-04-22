<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags"%>
<!--jsp:useBean id="computerModel" class="com.excilys.computerdatabase.webapp.computer.view.ComputerBackingBean" /-->
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${computerCount} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" method="GET"
						class="form-inline">
						<input type="hidden" name="pageSize"
							value="${pageSize}" /> <input type="hidden" name="page"
							value="${page}" /> <input type="hidden" name="orderBy"
							value="${orderBy}" /> <input type="hidden" name="asc"
							value="${asc}" /> <input type="search" id="searchbox"
							name="search" class="form-control"
							placeholder="<spring:message code="dashboard.search_placeholder"/>"
							value="${search}" /> <input type="submit"
							id="searchsubmit"
							value="<spring:message code="dashboard.filter_by_name"/>"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
							code="dashboard.add_computer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="dashboard.edit" /></a>
					<a class="btn btn-danger" id="logout" href="logout"><spring:message
							code="application.logout" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<!-- Table Headers -->
						<th>Computer name</th>
						<th>Introduced date</th>
						<th>Discontinued date</th>
						<th>Company</th>

					</tr>
				</thead>
				<!-- List Computers -->
				<tbody>
					<c:forEach items="${items}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a
								href="
									<c:url value="editComputer">
									<c:param name="id" value="${computer.id}"/>
									</c:url>
									">
									${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.companyDTO.name}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<footer class="navbar-fixed-bottom">
			<mylib:page page="${page}" pageSize="${pageSize}"
							  paginationStart="${paginationStart}" paginationEnd="${paginationEnd}"/>
		</footer>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>