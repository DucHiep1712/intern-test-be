<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello Me!" %>
</h1>
<p/>
<a href="${pageContext.request.contextPath}/login">Hello Servlet</a>
</body>
</html>