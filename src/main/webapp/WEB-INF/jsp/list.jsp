<!DOCTYPE html>
<html>
<head>
  <title>Group project</title>
</head>
<body>

<security:authorize access="isAuthenticated()">
  <c:url var="logoutUrl" value="/logout"/>
  <form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form>
</security:authorize>

<security:authorize access="!isAuthenticated()">
  <a href="<c:url value="/login" />">Login</a>
  <a href="<c:url value="/user/create" />">Register</a>
</security:authorize>

<h2>Course name: COMP S380F</h2>

<security:authorize access="hasRole('ADMIN')">
  <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
<a href="<c:url value="/lecture/create" />">Create lecture</a><br/><br/>
</security:authorize>
Lecture list:<br/>
<c:choose>
  <c:when test="${fn:length(lectureDatabase) == 0}">
    <i>There are no lectures in the system.</i>
  </c:when>
  <c:otherwise>
    <c:forEach items="${lectureDatabase}" var="entry">
      Lecture ${entry.id}&nbsp;
      <a href="<c:url value="/lecture/view/${entry.id}" />">course material page</a>
      <security:authorize access="isAuthenticated()">
      <security:authorize access="hasRole('ADMIN') or
                          principal.username=='${entry.userName}'">
        [<a href="<c:url value="/lecture/edit/${entry.id}" />">Edit</a>]
      </security:authorize>
      </security:authorize>
      <security:authorize access="hasRole('ADMIN')">
        [<a href="<c:url value="/lecture/delete/${entry.id}" />">Delete</a>]
      </security:authorize>
      <br />
    </c:forEach>
  </c:otherwise>
</c:choose>
</body>
</html>