<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">
<style>
#ohmygod {
    background-color: #ccc;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius:6px;
    color: #fff;
    font-family: 'Oswald';
    font-size: 20px;
    text-decoration: none;
    cursor: pointer;
    border:none;
}

#ohmygod:hover {
    border: none;
    background:red;
    box-shadow: 0px 0px 1px #777;
}
</style>
<HTML>
   <HEAD>
      <TITLE>Food Panda</TITLE>
   </HEAD>
   <BODY>

LOGIN
<form action="FOS" method="post">
<input type="radio" name="SellerOrUser" value="Seller" checked>Seller
<input type="radio" name="SellerOrUser" value="User">User<br/>
Please enter Username and Password <br/>
<input type="text" name="Username"size="20px" >
<input type="password" name="Password" size="20px">
<input type="hidden" name="from" value = "2">
<input type="submit" name="submitvalue" value="login" id="ohmygod">
</form>

SIGN UP
<form action="FOS" method="post">
<input type="radio" name="SellerOrUser" value="Seller" checked>Seller
<input type="radio" name="SellerOrUser" value="User">User<br/>
Please enter Username and Password and Address <br/>

username:
<input type="text" name="Username"size="60px">
</br>
</br>
</br>
Address:

<input type="text" name="Address" size="60px">
</br>
</br>
</br>
Name:

<input type="text" name="Name" size="60px">
<br>
<br>
Password:

<input type="password" name="Password" size="60px">
</br>
</br>
</br>
<input type="hidden" name="from" value = "2">
<input type="submit" name="submitvalue" value="signup" id="ohmygod">
</form>

   </BODY>
</HTML>