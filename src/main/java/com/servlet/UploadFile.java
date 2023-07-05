package com.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;

import jakarta.servlet.http.HttpSession;

@WebServlet("/upload")
@MultipartConfig
public class UploadFile extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   
		Part p = req.getPart("files");
		String filename = p.getSubmittedFileName();
		
		String remark = req.getParameter("remark");
		System.out.println(filename +" "+ remark);
		 
	HttpSession session=(HttpSession) req.getSession();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/upload_file","root", "root");
				PreparedStatement ps = conn.prepareStatement("insert into  file_details(file_name,remark) values(?,?)");
				ps.setString(1,filename);
				ps.setString(2, remark);
				int i=ps.executeUpdate();
				if(i==1)
				{
					String path = getServletContext().getRealPath("")+"Images";
					File file = new File(path);
					p.write(path+File.separator+filename);
				    session.setAttribute("msg", "upload success");
			        resp.sendRedirect("index.jsp");
				}
				else {
				      System.out.println("Error in Server");
					}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	}

	
}
