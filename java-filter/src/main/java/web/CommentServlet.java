package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CommentServlet
 */
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public void service(
			HttpServletRequest request,
			HttpServletResponse response) 
					throws ServletException,
					IOException {
		System.out.println("service begin...");
		request.setCharacterEncoding("utf-8");
		response.setContentType(
				"text/html;charset=utf-8");
		PrintWriter out = 
				response.getWriter();
		
		String content = 
				request.getParameter("content");
		out.println("你的看法是:" + content);
		System.out.println("service end.");
	}

}




