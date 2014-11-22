<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ABCD</title>
</head>
<body>
<%String DataUser = request.getAttribute("UserData").toString();
String ArrayUser[] = DataUser.split(" ");%>
<div style="width:100%;height = 50px;background:#F9EECF;border:1px dotted black;">
<center>
<a href="Wallet.jsp"><div style="width:20%;height = 50px;background:#F9EECF;border:1px dotted black;float:left;">wallet</div></a>
<a href="editdetails"><div style="width:20%;height = 50px;background:#F9EECF;border:1px dotted black;float:left;">edit details</div></a>
<a href="svag"><div style="width:20%;height = 50px;background:#F9EECF;border:1px dotted black;float:left;">history</div></a>
<a href="Home.jsp"><div style="width:20%;height = 50px;background:#F9EECF;border:1px dotted black;float:left;">Logout</div></a>
</center>
</div>
<h1>welcome <%out.println(ArrayUser[1]); %></h1>
<h2>your wallet contains <%out.println(ArrayUser[2]); %></h2>
<%
String DataSell = request.getAttribute("SellerData").toString();
String ArraySell[] = DataSell.split("//");
//out.println(s.split("//")[0] + " dinesh " + s.split(" ")[1]);
%>
<div style="float:left">
<h3>please select your cuisine</h3>
<form action="FOS" method ="post">
  <input type="checkbox" name="1" value="abc"> North Indian<br>
  <input type="checkbox" name="2" value="abc" > Chinese<br>
  <input type="checkbox" name="3" value="abc"> South Indian<br>
  <input type="hidden" name="uid" value=<%out.println(ArrayUser[0]); %>> 
  <input type="hidden" name="from" value = "3">
  <input type="submit" value="Submit">
</form>

</div>
<div style="float:right;padding-right:20%;">
<%
for(String s : ArraySell)
{
%>	

<div style="width:100%;background:#F9EECF;border:1px dotted black;text-align:center;">
<a href = "/FOS/FOS?SellerData=<%out.println(s); %>" >
<%out.println(s); %>
</a>
</div>

	<br>
	<br>
<%}%>
</div>
</body>
</html>