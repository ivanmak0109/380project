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

<h2>Edit Lecture #${lecture.id}</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="lectureForm">
    <form:label path="title">Lecture title</form:label><br/>
    <form:input type="text" path="title" autocomplete="off" autofill="false"/><br/><br/>
    <b>Add more attachments</b><br />
    <input type="file" name="attachments" multiple="multiple"/><br/><br/>
    <input type="submit" value="Save"/><br/><br/>
</form:form>
<a href="<c:url value="/lecture" />">Return to lecture list</a>
</body>
</html>