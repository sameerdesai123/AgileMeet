package com.agilemeet.web;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemeet.model.Meeting;
import com.agilemeet.utils.ICSFileDecode;

@WebServlet("/new-meeting")
public class NewMeeting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file ; 
	private Logger logger = LoggerFactory.getLogger(NewMeeting.class);
	
    public NewMeeting() {
        super();
    }
    
    public void init( ){
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload"); 
     }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isMultipart = ServletFileUpload.isMultipartContent(request);
	    response.setContentType("text/html");
	    RequestDispatcher errorPage = request.getRequestDispatcher("/error.jsp");
	    RequestDispatcher newMeetPage = request.getRequestDispatcher("/views/NewMeeting.jsp");
	      if( !isMultipart ) {
	         errorPage.forward(request, response);
	    	 return;
	      }
	  
	      DiskFileItemFactory factory = new DiskFileItemFactory();
	      // maximum size that will be stored in memory
	      factory.setSizeThreshold(maxMemSize);
	      // Location to save data that is larger than maxMemSize.
	      factory.setRepository(new File("c:\\temp"));
	      // Create a new file upload handler
	      ServletFileUpload upload = new ServletFileUpload(factory);
	      // maximum file size to be uploaded.
	      upload.setSizeMax( maxFileSize );

	      try { 
	         List<FileItem> fileItems = upload.parseRequest(new ServletRequestContext(request));
	         Iterator<FileItem> i = fileItems.iterator();
	         while ( i.hasNext () ) {
	            FileItem fi = (FileItem)i.next();
	            if ( !fi.isFormField () ) {
	            	String fileName = fi.getName();
	               // Write the file
	               if( fileName.lastIndexOf("\\") >= 0 ) {
	                  file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
	               } else {
	                  file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
	               }
	               fi.write( file ) ;
	               Meeting meeting = ICSFileDecode.parseICS(file.getAbsolutePath());
	               file.delete();
	               try{
	            	   HttpSession session = request.getSession();
	            	   session.setAttribute("meeting", meeting);
	            	   logger.info("FILE PARSED " + meeting.getTitle());
	            	   newMeetPage.forward(request, response);
	               }catch(Exception e){
	            	   logger.error("FAILED TO SET MEETING IN SESSION " + e.getCause());
	               }
	            }
	         }
	         } catch(Exception ex) {
	            logger.error("ADDING NEW MEETING " + ex.getCause());
	            response.sendRedirect(request.getContextPath() + "/");
	         }
	}
}
