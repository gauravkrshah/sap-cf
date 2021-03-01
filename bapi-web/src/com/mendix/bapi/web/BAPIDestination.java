package com.mendix.bapi.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;

/**
 * Servlet implementation class BAPIDestination
 */
@WebServlet("/BAPIDestination")
public class BAPIDestination extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BAPIDestination() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter responseWriter = response.getWriter();
		responseWriter.append("Served at: ").append(request.getContextPath());
		JCoDestination destination;
		try {
			destination = JCoDestinationManager.getDestination("mx-cal");
			 // optional ping to check if destination is available
	        destination.ping();

	        // just print the attributes here
	        System.out.println("Attributes:");
	        responseWriter.append("Attributes:: ").println(destination.getAttributes());
	        System.out.println(destination.getAttributes());
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			responseWriter.println(e.getMessage());
			e.printStackTrace(responseWriter);
		}

       
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
