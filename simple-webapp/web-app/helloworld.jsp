<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
  <head>
    <title>Hello, Spring MVC!</title>
  </head>
  <body>Hello, <sec:authentication property="name" />!</body>
</html>
