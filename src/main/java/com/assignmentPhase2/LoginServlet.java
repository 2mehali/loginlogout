package com.assignmentPhase2;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
 
    public LoginServlet() {
        super();
    
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if( (username!=null && username.equals("Snigdha")) &&
				(password!=null && password.equals("12345"))	) {
			
			HttpSession session = request.getSession();
			session.setAttribute("isLoggedIn", true);
			session.setAttribute("username", username);
			
			response.sendRedirect("dashboard");
		}
		response.getWriter().print("<html> <body>");
		response.getWriter().print("Invalid credentials please try again!!.");
		response.getWriter().append("<form action='login' method='POST'> ").append(("Username : <input type=\"text\" name=\"username\"/>")).append("Password : <input type=\"text\" name=\"password\"/>").append("<button type=\"submit\"> Login </button>").append("</form>").append("</body> </html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
