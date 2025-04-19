<!DOCTYPE html>
<html>
<head>
    <title>Group project</title>
</head>
<body>
<a href="<c:url value="/lecture/list" />">Return to lecture list</a>

<h2>Create a lecture</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="lectureForm">
    <form:label path="title">Lecture title</form:label><br/>
    <form:input type="text" path="title" autocomplete="off" autofill="false"/><br/><br/>
    <b>Attachments</b><br/>
    <input type="file" name="attachments" multiple="multiple"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>
</body>
</html>