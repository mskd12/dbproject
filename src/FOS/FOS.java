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
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("in do post");
		String num=request.getParameter("from");
		if(num.equals("2")){
			String lor=request.getParameter("submitvalue");
			if(lor.equals("login")){
				String UserId= request.getParameter("Username");
		        String PassWd= request.getParameter("Password");
		        String sor= request.getParameter("SellerOrUser");
		        boolean retVal=AuthenticateUser(UserId,PassWd,sor);
		        if(retVal&&sor.equals("Seller")){response.sendRedirect("/FOS/Temp.jsp?name=Raccha");}
		        else if(retVal&&sor.equals("User")){toUser("2", request, response);}
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
	
	void toUser(String uid, HttpServletRequest request, HttpServletResponse response)
	{
		String q1 = "Select * from seller";
		ResultSet rs;
		//uid = uid + " deepak";
		try {
			 rs = st.executeQuery(q1);
			 String SellerData ="";
			 while(rs.next())
				{	String id = rs.getString(1);
				 	String name = rs.getString(2);
				 	String address = rs.getString(3);
				 	SellerData = SellerData + name + " " + address + "//";
				}
			 request.setAttribute("data", SellerData);
			 request.setAttribute("id",uid);	
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