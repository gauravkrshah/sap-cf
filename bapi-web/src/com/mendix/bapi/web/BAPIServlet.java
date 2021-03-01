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
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoParameterField;
import com.sap.conn.jco.JCoParameterFieldIterator;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;

/**
 * Servlet implementation class BAPIServlet
 */
@WebServlet("/BAPIServlet")
public class BAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public BAPIServlet() {
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
			
			//get the XSUAA token
			
//			OAuth2ServiceConfiguration configuration = Environments.getCurrent().getXsuaaConfiguration();
//			String clientSecret = configuration.getClientSecret();
//			String clientid = configuration.getClientId();
//			String url = configuration.getUrl().toString();
//
//			XsuaaTokenFlows tokenFlows = new XsuaaTokenFlows(
//					new DefaultOAuth2TokenService(),
//					new XsuaaDefaultEndpoints(url), new ClientCredentials(clientid, clientSecret));
//			OAuth2TokenResponse tokenResponse = tokenFlows.clientCredentialsTokenFlow().execute();
//			
//			writeLine(response, "Access-Token: " + tokenResponse.getAccessToken());
//			writeLine(response, "Access-Token-Payload: " + tokenResponse.getDecodedAccessToken().getPayload());
//			writeLine(response, "Expired-At: " + tokenResponse.getExpiredAtDate());
			
			//SecurityContext.setToken(new XsuaaToken(tokenResponse.getAccessToken()));
			
			
			destination = JCoDestinationManager.getDestination("mx-cal");
			  // optional ping to check if destination is available
	        destination.ping();

	        // just print the attributes here
	        responseWriter.append("Attributes:: ").println(destination.getAttributes());
	        System.out.println("Attributes:");
	        System.out.println(destination.getAttributes());
	        JCoRepository repo = destination.getRepository();
	        JCoFunction rfcFunction = repo.getFunction("BAPI_BUPA_CENTRAL_GETDETAIL");
	        
	        //check on Function Template
	        JCoFunctionTemplate functionTemplate = rfcFunction.getFunctionTemplate();
	        System.out.println("TO JSON::: "+functionTemplate.getRequest().toJSON());
	        System.out.println("TO XML::: "+functionTemplate.getRequest().toXML());
	        System.out.println("rfcFunction.getExportParameterList().toJSON()::: "+rfcFunction.getExportParameterList().toJSON());
	        System.out.println("rfcFunction.getExportParameterList().toXML()::: "+rfcFunction.getExportParameterList().toXML());
	        
	        
	        rfcFunction.getImportParameterList().setValue("BUSINESSPARTNER", "9000000024");
	        rfcFunction.execute(destination);
	        System.out.println("rfcFunction.toXML():: "+rfcFunction.toXML());
	        
	       
	        
	        System.out.println("BAPI_BUPA_CENTRAL_GETDETAIL finished:");
	        responseWriter.println("BAPI_BUPA_CENTRAL_GETDETAIL finished:");
	        
	      
	        
	        JCoParameterList exportList =  rfcFunction.getExportParameterList();
	        
	        
	        String echoText = exportList.getString("CENTRALDATA");
	        String respText = exportList.getString("CENTRALDATAORGANIZATION");
	        System.out.println(" CENTRALDATA: "+echoText);
	        System.out.println(" CENTRALDATAORGANIZATION: "+respText);

	        
	        JCoParameterFieldIterator it = exportList.getParameterFieldIterator();
	        
	        response.setHeader("Content-type", "text/html");
	        responseWriter.println("<html><body>");
	        responseWriter.println("GetDetailedList execution finished:");
	        
	        while(it.hasNextField()) {
	        	JCoParameterField field = it.nextParameterField();
	        	if(field.isStructure()) {
	        		JCoStructure struct= field.getStructure();
	        		String searchTerm = struct.getString("SEARCHTERM1");
	        		responseWriter.println(" SEARCHTERM1 :::::::: "+searchTerm);
	        	}
	        	String fieldName = field.getName();
	        	Object value = field.getString();
	        	System.out.println(" Field Name : "+fieldName);
	  	        System.out.println(" Response: "+value);

		        responseWriter.println(" Field Name : "+fieldName);
		        responseWriter.println(" Response: "+value);
	  	       
	        }
	        
//	        String echoText = exportList.getString("ECHOTEXT");
//	        String respText = exportList.getString("RESPTEXT");
//	        System.out.println(" Echo: "+echoText);
//	        System.out.println(" Response: "+respText);
//	       
	       
	        responseWriter.println("</html></body>");
		} catch (JCoException e) {
			responseWriter.println(e.getMessage());
			e.printStackTrace(responseWriter);
		}

      
	}
	
	private void writeLine(HttpServletResponse response, String string) throws IOException {
		response.getWriter().append(string);
		response.getWriter().append("\n");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
