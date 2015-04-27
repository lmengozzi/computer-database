<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ attribute name="page" required="true"
              type="java.lang.Integer" description="Sets the number of pages." %>
<%@ attribute name="pageSize" required="true"
              type="java.lang.Integer"
              description="Sets the number of results per page." %>
<%@ attribute name="paginationStart" required="true"
              type="java.lang.Integer"
              description="Sets at which page the pagination starts." %>
<%@ attribute name="paginationEnd" required="true"
              type="java.lang.Integer"
              description="Sets at which page the pagination finishes." %>
<c:choose>
    <c:when test="${page > 1}">
        <c:set var="lower" value="${page - 1}"/>
    </c:when>
    <c:otherwise>
        <c:set var="lower" value="1"/>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${page < totalPageNumber}">
        <c:set var="higher" value="${page + 1}"/>
    </c:when>
    <c:otherwise>
        <c:set var="higher" value="${totalPageNumber}"/>
    </c:otherwise>
</c:choose>
<div class="btn-group btn-group-sm pull-right" role="group">
    <form action="" method="GET">
        <input type="hidden" name="page" value="${page}"/>
        <input type="hidden" name="search" value="${search}"/>
        <input type="hidden" name="orderBy" value="${orderBy}"/>
        <input type="hidden" name="asc" value="${asc}"/>
        <button type="submit" name="pageSize" class="btn btn-default"
                value="10">10
        </button>
        <button type="submit" name="pageSize" class="btn btn-default"
                value="50">50
        </button>
        <button type="submit" name="pageSize" class="btn btn-default"
                value="100">100
        </button>
    </form>
</div>
<div class="container text-center">
    <ul class="pagination">
        <li><a
                href="
<mylib:link
target="dashboard"
resultsPerPage="${pageSize}"
search="${search}"
page="${lower}"
orderBy="${orderBy}"
ascendent="${asc}"
/>
"
                aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
        </a></li>
        <c:forEach var="i" begin="${paginationStart}"
                   end="${paginationEnd}">
            <li><a
                    href="
<mylib:link
target="dashboard"
resultsPerPage="${pageSize}"
search="${search}"
page="${i}"
orderBy="${orderBy}"
ascendent="${asc}"
/>
">${i}
            </a></li>
        </c:forEach>
        <li><a
                href="
<mylib:link
target="dashboard"
resultsPerPage="${pageSize}"
search="${search}"
page="${higher}"
orderBy="${orderBy}"
ascendent="${asc}"
/>
"
                aria-label="Next"> <span aria-hidden="true">&raquo;</span>
        </a></li>
    </ul>
</div>