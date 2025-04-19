<!DOCTYPE html>
<html>
<head>
    <title>Group project</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Lecture ${lectureId}</h2>
<h2>Lecture title: <c:out value="${lecture.title}"/></h2>
<security:authorize access="hasRole('ADMIN') or
                          principal.username=='${lecture.userName}'">
    [<a href="<c:url value="/lecture/edit/${lecture.id}" />">Edit</a>]
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/lecture/delete/${lecture.id}" />">Delete</a>]
</security:authorize>
<br/><br/>
<h2>Download links of lecture notes:</h2><br/>
<c:if test="${empty lecture.attachments}">
    There are no lecture notes
</c:if>
<c:if test="${!empty lecture.attachments}">
    <c:forEach items="${lecture.attachments}" var="attachment" varStatus="status">
        <c:if test="${!status.first}"><br/></c:if>
        <a href="<c:url value="/lecture/${lectureId}/attachment/${attachment.id}" />">
            <c:out value="${attachment.name}"/></a>
        <security:authorize access="hasRole('ADMIN')">
            [<a href="<c:url value="/lecture/${lectureId}/delete/${attachment.id}" />">Delete</a>]
        </security:authorize>
    </c:forEach><br/><br/>
</c:if>
<h2>Comments:</h2>
<c:if test="${empty lecture.comments}">
    <i>There are no comments.</i><br/>
</c:if>
<c:if test="${!empty lecture.comments}">
    <c:forEach items="${lecture.comments}" var="comment" varStatus="status">
        <c:if test="${!status.first}"><br/></c:if>
        <c:out value="${comment.userName}"/>: <c:out value="${comment.comment}"/>
        <security:authorize access="hasRole('ADMIN')">
            [<a href="<c:url value="/lecture/${lectureId}/deleteComment/${comment.id}" />">Delete</a>]
        </security:authorize>
    </c:forEach><br/><br/>
</c:if>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="commentForm">
    <textarea name="comment" cols="50" rows="5"/></textarea><br/><br/>
    <input type="submit" value="Send"/>
</form:form>
<br/>
<a href="<c:url value="/lecture" />">Return to lecture list</a>
</body>
</html>