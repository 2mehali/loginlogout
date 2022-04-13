package com.assignmentPhase2;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
	private DBConnection dbConnection = null;
    public DashboardServlet() {
        super();
       }

    @Override
    public void init() throws ServletException {
    	InputStream inStream = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
    	Properties props = new Properties();
    	try {
			props.load(inStream);
			
			String url = props.getProperty("url");
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			dbConnection = new DBConnection(url, user, password);
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
    }
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		out.print("<html> <body>");
		
		if (session != null) {
			if(session.getAttribute("username") != null) {
				String username = (String) session.getAttribute("username");
				boolean isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
				if(isLoggedIn) {
					response.getWriter().print("Welcome, "+ username +"<br>");
					response.getWriter().print("<a href='logout'> Logout </a> <br>");
					
					//eproducts display
					
					Connection connection = dbConnection.getConnection();
					if(connection!=null) {
						try {
							Statement statement = connection.createStatement();
								String query = "SELECT * FROM eproduct";
						ResultSet resultSet = 	statement.executeQuery(query);
							while(resultSet.next()) {
								out.print(resultSet.getInt("ID") +" "+ resultSet.getString("name")  +" "+
											resultSet.getFloat("price")  +" "+ resultSet.getDate("date_added") +"<br>" );
							}
							resultSet.close();
							statement.close();
							dbConnection.closeConnection();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}else {
				response.sendRedirect("login");
			}
			
		}else {
			response.sendRedirect("login");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
