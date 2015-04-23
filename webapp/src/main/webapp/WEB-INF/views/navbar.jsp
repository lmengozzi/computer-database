<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="dashboard">Computer Database</a>
        <div class="pull-right">
            <a class="btn btn-danger logout" id="logout" href="logout"><spring:message
                    code="application.logout"/></a>
        </div>
    </div>
</header>