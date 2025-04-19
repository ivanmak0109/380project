<!DOCTYPE html>
<html>
<head>
  <title>Group project</title>
  <style>
    .error {
      color: red;
      font-weight: bold;
      display: block;
    }
  </style>
</head>
<body>
<a href="<c:url value="/lecture/list" />">Return to lecture list</a>

<h2>Add new user</h2>
<form:form method="POST" modelAttribute="lectureUser">
  <form:label path="username">Username</form:label><br/>
  <form:errors path="username" cssClass="error" />
  <form:input type="text" path="username" autocomplete="off" autofill="false"/><br/><br/>

  <form:label path="password">Password</form:label><br/>
  <form:errors path="password" cssClass="error" />
  <form:input type="text" path="password"/><br/><br/>

  <form:label path="fullName">Full Name</form:label><br/>
  <form:input type="text" path="fullName" autocomplete="off" autofill="false"/><br/><br/>

  <form:label path="email">Email</form:label><br/>
  <form:input type="text" path="email" autocomplete="off" autofill="false"/><br/><br/>

  <form:label path="phone">Phone Number</form:label><br/>
  <form:input type="text" path="phone" autocomplete="off" autofill="false"/><br/><br/>

  <form:label path="roles">Roles</form:label><br/>
  <form:errors path="roles" cssClass="error" />
  <form:checkbox path="roles" value="ROLE_USER"/>ROLE_USER
  <security:authorize access="isAuthenticated()">
  <security:authorize access="hasRole('ADMIN') or
                          principal.username=='${entry.userName}'">
  <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN
  </security:authorize>
  </security:authorize>
  <br/><br/>
  <input type="submit" value="Add User"/>
</form:form>

</body>
</html>