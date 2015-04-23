<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="../../css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="../../css/main.css" rel="stylesheet" media="screen">
    <script src="../../js/jquery.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/i18n.js"></script>
    <script type="text/javascript">
        var message_validation_wrong_name_format = "<spring:message code='validation.wrong.name.format'/>";
        var message_validation_right_date_format = "<spring:message code='validation.right.date.format'/>";
        var message_validation_wrong_date_format = "<spring:message code='validation.wrong.date.format'/>";
    </script>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<c:if test="${show && showSuccess}">
    <div class="col-xs-8 col-xs-offset-2 alert alert-info" role="alert">
        <c:forEach items="${message}" var="m">
            <p>${m}</p>
        </c:forEach>
    </div>
</c:if>
<c:if test="${show && !showSuccess}">
    <div class="col-xs-8 col-xs-offset-2 alert alert-danger" role="alert">
        <c:forEach items="${message}" var="m">
            <p>${m}</p>
        </c:forEach>
    </div>
</c:if>
<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1><spring:message code="addComputer.add_computer"/></h1>
                <form:form action="addComputer" method="POST" commandName="computerDTO">
                    <fieldset>
                        <div class="form-group">
                            <form:label path="name" for="computerName"><spring:message
                                    code="computer_name"/></form:label>
                            <form:input path="name"
                                        type="text" class="form-control" id="computerName"
                                        name="computerName" placeholder="${computerName}"/>
                            <span id="computerNameMessage"></span>
                        </div>
                        <div class="form-group">
                            <form:label path="introduced" for="introduced"><spring:message
                                    code="introduced_date"/></form:label>
                            <form:input path="introduced"
                                        type="date" class="form-control" id="introduced"
                                        name="introduced" placeholder="${introducedDate}"/>
                            <span id="introducedMessage"></span>
                        </div>
                        <div class="form-group">
                            <form:label path="discontinued" for="discontinued"><spring:message
                                    code="discontinued_date"/></form:label>
                            <form:input path="discontinued"
                                        type="date" class="form-control" id="discontinued"
                                        name="discontinued" placeholder="${discontinuedDate}"/>
                            <span id="discontinuedMessage"></span>
                        </div>
                        <div class="form-group">
                            <form:label path="companyDTO.id" for="companyDTO"><spring:message code="company"/></form:label>
                            <form:select path="companyDTO.id"
                                         class="form-control" id="companyDTO" name="companyDTO">
                                <option value="0">--</option>
                                <c:forEach items="${companies}" var="company">
                                    <option value="${company.id}">${company.name}</option>
                                </c:forEach>
                            </form:select>
                            <span id="companyMessage"></span>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" value="<spring:message code="addComputer.add"/>" class="btn btn-primary">
                        <spring:message code="application.or"/>
                        <a href="dashboard" class="btn btn-default"><spring:message code="application.cancel"/></a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>
</body>
</html>