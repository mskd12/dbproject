<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order Page</title>
</head>
<body>

<%String UserData = request.getAttribute("UserData").toString();
String ArrayUser[] = UserData.split(" ");%>

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
String ItemData = request.getAttribute("ItemData").toString();
String ItemDataArray[] = ItemData.split("//");

String SellerData = request.getAttribute("SellerData").toString();
String SellerDataArray[] = SellerData.split(" ");
%>

<div style="float:left">
<h3>please select your cuisine and order</h3>
<form action="FOS" method ="post">
  <input type="checkbox" name="1" value="abc"> North Indian<br>
  <input type="checkbox" name="2" value="abc" > Chinese<br>
  <input type="checkbox" name="3" value="abc"> South Indian<br>
  <input type="checkbox" name="4" value="abc"> Veg<br>
  <input type="checkbox" name="5" value="abc"> Non Veg<br>
  <input type = "hidden" name = "UserId" value = <% out.println(ArrayUser[0]); %>>
  <input type = "hidden" name = "SellerId" value = <% out.println(SellerDataArray[0]); %>>
  <input type="hidden" name="from" value = "4">
  <input type="submit" value="Submit">
</form>
</div>


<div style="float:right;padding-right:10%;">
<h3> Items of <%out.println(SellerDataArray[1]); %></h3>
<div style="width:100%;background:#F9EECF;border:1px dotted black;text-align:center;">
<form action="FOS">
<TABLE BORDER="1" CELLPADDING="3" CELLSPACING="1">
	<TR>
        <TH>Name</TH>
        <TH>Cost</TH>
        <TH>Expected time</TH>
        <TH>veg or non veg</TH>
        <TH>cuisine</TH>
    </TR>

<%
for(String s : ItemDataArray)
{
	if(s.equals("")) continue;
	String ItemArray[] = s.split("@");
	System.out.println(s);
	System.out.println(ItemArray[0]);
	System.out.println(ItemArray[1]);
	System.out.println(ItemArray[2]);
	System.out.println(ItemArray[3]);
	
%>
    <TR>
        <TD><%=ItemArray[4] %></TD>
        <TD><%=ItemArray[2] %></TD>
        <TD><%=ItemArray[3] %></TD>
        <TD><%=ItemArray[5] %></TD>
        <TD><%=ItemArray[6] %></TD>
        <TD><select name="Quantity" >
  <option value="1">1</option>
  <option value="2">2</option>
  <option value="3">3</option>
  <option value="4">4</option></select>
  <input type="checkbox" name = "itemid" value = <%out.println(ItemArray[0]); %>>
</TD>
    </TR>



</a>
</div>

<%}%>
</TABLE>
<input type="submit" name="submitvalue" value="login">

</form>
</div>


</body>
</html>