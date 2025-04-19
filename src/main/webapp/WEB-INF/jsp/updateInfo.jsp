<!DOCTYPE html>
<html>
<head>
    <title>UpdateInfo</title>
    <style>
        .error {
            color: red;
            font-weight: bold;
            display: block;
        }
    </style>
</head>
<body>
<a href="<c:url value="/lecture/list" />">Return to lecture list</a><br/>
<h2>User Information</h2>
username: ${lectureUser.username}<br/>
password: ${fn:substringAfter(lectureUser.password, '{noop}')}<br/>
full name: ${lectureUser.fullName}<br/>
email: ${lectureUser.email}<br/>
phone: ${lectureUser.phone}<br/>
user type:
<c:forEach items="${lectureUser.roles}" var="role" varStatus="status">
    <c:if test="${!status.first}">, </c:if>
    ${role.role}
</c:forEach>
<br/><br/>
<h2>Update</h2>
<form:form method="POST" modelAttribute="updateInfo">
    <form:label path="password">Password</form:label><br/>
    <form:errors path="password" cssClass="error" />
    <form:input type="password" path="password"/><br/><br/>

    <form:label path="fullName">Full Name</form:label><br/>
    <form:input type="text" path="fullName" autocomplete="off" autofill="false"/><br/><br/>

    <form:label path="email">Email</form:label><br/>
    <form:input type="text" path="email" autocomplete="off" autofill="false"/><br/><br/>

    <form:label path="phone">Phone Number</form:label><br/>
    <form:input type="text" path="phone" autocomplete="off" autofill="false"/><br/><br/>
    <input type="submit" value="Update"/>
</form:form>
</body>
</html>
