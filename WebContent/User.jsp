<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ABCD</title>
</head>
<body>
<%= request.getAttribute("id")%>
<%
String dataSell = request.getAttribute("data").toString();
String arraysell[] = dataSell.split("//");
//out.println(s.split("//")[0] + " dinesh " + s.split(" ")[1]);
%>
<%
for(String s : arraysell)
{
%>
<a href = "/FOS/FOS" >
<%out.println(s); %>
</a>
	<br>
<%}%>

<a href="/FOS/User.jsp">abc</a>
</body>
</html>