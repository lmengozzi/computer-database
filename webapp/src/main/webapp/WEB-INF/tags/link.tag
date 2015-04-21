<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="target" required="true"
              type="java.lang.String" description="Target of the link." %>
<%@ attribute name="resultsPerPage" required="false"
              type="java.lang.Integer" description="Number of results per page."%>
<%@ attribute name="page" required="false"
              type="java.lang.Integer" description="Current page"%>
<%@ attribute name="search" required="false"
              type="java.lang.String" description="Search string."%>
<%@ attribute name="orderBy" required="false"
              type="java.lang.String" description="Attribute selected for the ordering."%>
<%@ attribute name="ascendent" required="false"
              type="java.lang.String" description="True if the order is ascendant, false otherwise."%>

<c:url value="${target}">
    <c:param name="resultsPerPage" value="${resultsPerPage}"/>
    <c:param name="page" value="${page}" />
    <c:param name="search" value="${search}"/>
    <c:param name="orderBy" value="${orderBy}"/>
    <c:param name="asc" value="${ascendent}" />
</c:url>