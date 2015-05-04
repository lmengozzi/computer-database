<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<spring:message code="placeholder.computer_name" var="computerName" />
<spring:message code="placeholder.introduced_date" var="introducedDate" />
<spring:message code="placeholder.discontinued_date" var="discontinuedDate" />

<!DOCTYPE html>
<html>
<head>
  <title><spring:message code="application.page_title"/></title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
  <link href="css/font-awesome.css" rel="stylesheet" media="screen">
  <link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>

<%@ include file="/WEB-INF/views/navbar.jsp" %>

<section id="main">
  <div class="container">
    <div class="row">
      <div class="col-xs-8 col-xs-offset-2 box">
        <div class="label label-default pull-right">
          id: ${computerDTO.id}
        </div>
        <h1><spring:message code="editComputer.edit_computer"/></h1>
        <form:form action="editComputer" method="POST" commandName="computerDTO">
          <form:input path="id" type="hidden"/>
          <fieldset>
            <div class="form-group row">
              <form:label path="name" for="computerName"><spring:message code="computer_name"/></form:label>
              <form:input path="name"
                          type="text" class="form-control" id="computerName"
                          name="computerName" placeholder="${computerName}"/>
              <div id="computerNameMessageDiv" class="col-xs-8 col-xs-offset-2 alert alert-danger" role="alert">
                <span id="computerNameMessage">${nameErrorMessage}</span>
              </div>
            </div>
            <div class="form-group row">
              <form:label path="introduced" for="introduced"><spring:message code="introduced_date"/></form:label>
              <form:input path="introduced"
                          type="date" class="form-control" id="introduced"
                          name="introduced" placeholder="${introducedDate}"/>
              <div id="introducedMessageDiv" class="col-xs-8 col-xs-offset-2 alert alert-danger" role="alert">
                <span id="introducedMessage">${introducedErrorMessage}</span>
              </div>
            </div>
            <div class="form-group row">
              <form:label path="discontinued" for="discontinued"><spring:message code="discontinued_date"/></form:label>
              <form:input path="discontinued"
                          type="date" class="form-control" id="discontinued"
                          name="discontinued" placeholder="${discontinuedDate}"/>
              <div id="discontinuedMessageDiv" class="col-xs-8 col-xs-offset-2 alert alert-danger" role="alert">
                <span id="discontinuedMessage">${discontinuedErrorMessage}</span>
              </div>
            </div>
            <div class="form-group row">
              <form:label path="company.id" for="companyId"><spring:message code="company"/></form:label>
              <form:select path="company.id"
                           class="form-control" id="companyId" name="companyId">
                <option value="0">--</option>
                <c:forEach items="${companies}" var="company">
                  <c:choose>
                    <c:when test="${company.id == computerDTO.company.id}">
                      <option value="${company.id}" selected="selected">${company.name}</option>
                    </c:when>
                    <c:otherwise>
                      <option value="${company.id}">${company.name}</option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </form:select>
              <span id="companyMessage"></span>
            </div>
          </fieldset>
          <div class="actions pull-right">
            <input type="submit" value="<spring:message code="editComputer.edit"/>" class="btn btn-primary">
            <spring:message code="application.or"/>
            <a href="dashboard" class="btn btn-default"><spring:message code="application.cancel"/></a>
          </div>
        </form:form>
      </div>
    </div>
  </div>
</section>

<%@ include file="/WEB-INF/partials/js.jsp" %>
<%@ include file="/WEB-INF/partials/addVerification.jsp" %>

</body>
</html>