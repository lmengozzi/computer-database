<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="page" required="true"
	type="java.lang.Integer"%>
<%@ attribute name="pageSize" required="true"
	type="java.lang.Integer"%>
<%@ attribute name="paginationStart" required="true"
	type="java.lang.Integer"%>
<%@ attribute name="paginationFinish" required="true"
	type="java.lang.Integer"%>
<div class="btn-group btn-group-sm pull-right" role="group">
	<form action="" method="GET">
		<input type="hidden" name="page" value="${page}" />
		<button type="submit" name="pageSize" class="btn btn-default"
			value="10">10</button>
		<button type="submit" name="pageSize" class="btn btn-default"
			value="50">50</button>
		<button type="submit" name="pageSize" class="btn btn-default"
			value="100">100</button>
	</form>
</div>
<div class="container text-center">
	<ul class="pagination">
		<li><a
			href="
<c:url value="">
	<c:param name="pageSize" value="${pageSize}" />
	<c:if test="${page > 1}">
		<c:param name="pagepageNumber" value="${page - 1}" />
	</c:if>
</c:url>
"
			aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
		</a></li>
		<c:forEach var="i" begin="${paginationStart}"
			end="${paginationFinish}">
			<li><a
				href="
<c:url value="">
	<c:param name="pageSize" value="${pageSize}" />
	<c:param name="pageNumber" value="${i}" />
</c:url>">${i}
			</a></li>
		</c:forEach>
		<li><a
			href="
<c:url value="">
<c:param name="pageSize" value="${pageSize}" />
<c:if test="${page < pageCount}">
<c:param name="pageNumber" value="${page + 1}" />
</c:if>
</c:url>"
			aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</div>