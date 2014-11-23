package FOS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.security.Principal;

/**
 * Servlet implementation class FOS
 */
public class FOS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	Connection conn1 =null;
	Statement st =null;
	public void init() throws ServletException {
      //Open the connection here
	
	String dbURL2 = "jdbc:postgresql://10.5.0.45/cs387";
    String user = "sgondala";
    String pass = "x";

    try {
		Class.forName("org.postgresql.Driver");
	
		conn1 = DriverManager.getConnection(dbURL2, user, pass);
		st = conn1.createStatement();
		System.out.println("init"+conn1);
    	} catch (Exception e) {
		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }

    public void destroy() {
     //Close the connection here
    	try{
    		conn1.close();
    		System.out.println("close");
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in do get");
		String num = request.getParameter("from");
		System.out.println("num : " + num);
		if(request.getParameter("from").equals("3"))
		{
			//System.out.println(request.getParameter("SellerData"));
			String UserData = request.getParameter("UserData");
			String UserDataArray[] = UserData.split(" "); 
			//System.out.println(s);
			String SellerData = request.getParameter("SellerData");
			String SellerDataArray[] = SellerData.split(" "); 
			String q1 = "Select * from menu natural join item where sid = '" + SellerDataArray[0] + "'";
			try {
				String ItemData = "";
				ResultSet rs = st.executeQuery(q1);
				while(rs.next())
				{
					ItemData += rs.getString(1) + "@" + rs.getString(2) + "@"  + rs.getString(3) + "@" +
							rs.getString(4) + "@" + rs.getString(5) + "@"  + rs.getString(6) + "@" +
							rs.getString(7);
					ItemData += "//";
					//System.out.println();
				}
				//request.setAttribute("SellerData", SellerDataNew);
				request.setAttribute("ItemData",ItemData);
				request.setAttribute("SellerData",SellerData);
				request.setAttribute("UserData",UserData);
				//request.setAttribute("SellerCuisine",SellerCuisine);
				//System.out.println(ItemData);
				RequestDispatcher reqDispatcher = getServletConfig().getServletContext().
						getRequestDispatcher("/UserOrder.jsp");
				try {
					reqDispatcher.forward(request,response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in do post");
		String num=request.getParameter("from");
		System.out.println("num" + num);
		if(num.equals("2")){
			String lor=request.getParameter("submitvalue");
			if(lor.equals("login")){
				String UserId= request.getParameter("Username");
		        String PassWd= request.getParameter("Password");
		        String sor= request.getParameter("SellerOrUser");
		        boolean retVal=AuthenticateUser(UserId,PassWd,sor);
		        System.out.println(UserId + " " +  PassWd + " " + sor + " " + retVal);
		        if(retVal&&sor.equals("Seller")){response.sendRedirect("/FOS/Temp.jsp?name=Raccha");}
		        else if(retVal&&sor.equals("User")){toUser(UserId, request, response, null);}
		        else {response.sendRedirect("/FOS/Temp.jsp?name=Ettindhi!!");}
		    }
			else if(lor.equals("signup")){
				String UserId= request.getParameter("Username");
		        String PassWd= request.getParameter("Password");
		        String Address= request.getParameter("Address");
		        String Name= request.getParameter("Name");
		        String sor= request.getParameter("SellerOrUser");
		      //  System.out.println("*****");
		      //  System.out.println(Name);
		      //  System.out.println("*****");
		        String sql="";
		        if(sor.equals("Seller")){sql="insert into seller values(?,?,?,?)";}
		        else if(sor.equals("User")){sql="insert into users values(?,?,?,?)";}
		        try{
		        	PreparedStatement pStmt=conn1.prepareStatement(sql);
		        	pStmt.setString(1,UserId);
		        	pStmt.setString(2,Name);
		        	pStmt.setString(3,Address);
		        	pStmt.setString(4,PassWd);
		        	pStmt.executeUpdate();
		        	response.sendRedirect("/FOS/Temp.jsp?name=Raccha");
		        }
		        catch(SQLException e){
		        	String ErrorState=e.getSQLState();
		        	System.out.println("Caught SQLException " + e.getErrorCode() + "/" + e.getSQLState() + " " +   
                            e.getMessage() ) ;
		        	if(ErrorState.equals("23505")){response.sendRedirect("/FOS/Temp.jsp?name=SelectAnotherUserId");}
		        	if(ErrorState.equals("23514")){response.sendRedirect("/FOS/Temp.jsp?name=Password Length Must be>=4");}
		        }
			}
		}
		else if(num.equals("3"))
		{
			String SelectedCuisine = "";
			String uid= request.getParameter("uid");
			for(Integer i=1; i<=3; i++)
			{
				System.out.println(i);
				String temp = request.getParameter(i.toString());
				System.out.println(temp);
				if(temp!=null && temp.equals("abc"))
				{
					SelectedCuisine = SelectedCuisine + getCuisineName(i.toString()) + " ";
				}
			}
			System.out.println("SelectedCuisine : " +  SelectedCuisine);
			if(SelectedCuisine.equals("")) SelectedCuisine = null;
			toUser(uid, request, response, SelectedCuisine);
		}
		else if(num.equals("4"))
		{
			
		}
	}
	
	boolean AuthenticateUser(String UserName, String PassWd, String sor){
		String sql="";
		boolean ret=false;
		if(sor.equals("Seller")){
        	sql="Select * from seller where sid='"+UserName+"' and password='"+PassWd+"'";
        }
        else if(sor.equals("User")){
        	sql="Select * from users where uid='"+UserName+"' and password='"+PassWd+"'";
        }
        try{
        	ResultSet rs;
        	rs = st.executeQuery(sql);
        	String found="";
        	if(conn1!=null){
        		while(rs.next()){
        			found=rs.getString("name");
        		}
        		rs.close();
        		if(found.equals("")){
        			ret=false;
        		}
        		else{
        			ret=true;
        		}
        	}
        }
        catch(SQLException ex){
	    	ex.printStackTrace();
	        System.out.println("HERE IS THE ERROR2");
	    }
        return ret;
	}

	String getCuisine(String sid){
		String Cuisine="";
		String qCuisine = "select distinct cuisine from menu natural join item where sid ='"+sid+"'";
		try {
			ResultSet rs1 = st.executeQuery(qCuisine);
			
			while(rs1.next()){
				Cuisine = Cuisine+getCuisineName(rs1.getString(1))+" ";
			}
			rs1.close();
			//System.out.println(Cuisine);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Cuisine;
		
	}
	
	String getCuisineName(String n){
		String deepu="";
		if(n.equals("1")){
			deepu =  "North-Indian";
		}
		if(n.equals("2")){
			deepu =  "Chinese";
		}
		if(n.equals("3")){
			deepu =  "South-Indian";
		}
		return deepu;
	}
	
	void toUser(String uid, HttpServletRequest request, HttpServletResponse response, String SelectedCuisines)
	{
		String q1 = "Select * from seller";
		String qname = "select * from users where uid = '"+uid+"'";
		String q2 = "select distinct cuisine from item";
		ResultSet rs;
		String Uname="";
		String Uwallet = "";
		String UserData="";
		//uid = uid + " deepak";
		try {
			 /*rs = st.executeQuery(q2);
			 rs.next();
			 String AllCuisine = "";
			 while(rs.next())
			 {
				 AllCuisine = AllCuisine + rs.getString(1);
			 }
			 System.out.println(AllCuisine);
			 */
			 rs = st.executeQuery(qname);
			 rs.next();
			 Uname = rs.getString(2);
			 Uwallet = rs.getString(5);
			 UserData = uid + " " + Uname + " " + Uwallet;
			 rs = st.executeQuery(q1);
			 String SellerData ="";
			 while(rs.next())
				{
				 	String id = rs.getString(1);
				 	String name = rs.getString(2);
				 	String address = rs.getString(3);
				 	SellerData = SellerData + id + " " + name + " " + address +  "//";
				 	//AllSellerCuisine = AllSellerCuisine + SellerCuisine + "//";
				}
			 rs.close();
			 String SellerCuisine = "";//getCuisine(id);//" " + SellerCuisine +
			 String SellerDataNew = "";
			 for(String s:SellerData.split("//"))
			 {
				 if(SelectedCuisines == null)
				 {
					 System.out.println("Nothing Selected");
					 SellerCuisine = getCuisine(s.split(" ")[0]);
					 //System.out.println(SellerCuisine + s.split(" ")[0]);
					 SellerDataNew = SellerDataNew + s + " " + SellerCuisine + "//"; 
				 }
				 else
				 {
					 System.out.println("Selected Only");
					 SellerCuisine = getCuisine(s.split(" ")[0]);
					 boolean IsThere = false;
					 for(String sel:SellerCuisine.split(" "))
					 {
						 System.out.println(sel);
						 if(SelectedCuisines.contains(sel)) IsThere = true;
					 }
					 System.out.println("IsThere" + IsThere);
					 if(IsThere) SellerDataNew = SellerDataNew + s + " " + SellerCuisine + "//";
				 }
			 }
			 //request.setAttribute("CuisineData", AllCuisine);
			 request.setAttribute("SellerData", SellerDataNew);
			 request.setAttribute("UserData",UserData);
			 //request.setAttribute("SellerCuisine",SellerCuisine);
				RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/User.jsp");
				try {
					reqDispatcher.forward(request,response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		//System.out.println("linkin");
		
		
		
		System.out.println("to user");
	}
}