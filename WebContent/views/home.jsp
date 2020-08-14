<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.agilemeet.model.Meeting, com.agilemeet.model.Attender, java.util.List, java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Give Title</title>
  <!-- Add css imports -->
	<jsp:include page="/views/partials/cssImports.jsp"></jsp:include>
  <!-- Add Custom CSS imports -->
  
   <style>
  	body{
  margin:0px 0px;
  padding:0px px;
}
.svg {
  display: inline-block;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
}
.wave-container {
  display: inline-block;
  position: absolute;
  width: 100%;
  vertical-align: middle;
  overflow: hidden;
  height:100%;
}


  </style>
</head>

<body class="bg">
	<%
		String p = request.getContextPath() + "/";
		if(session.getAttribute("user") == null){
			response.sendRedirect(p);
		}
		String message = (String) session.getAttribute("msg");
	%>

  <!-- Add Navbar import -->
	<jsp:include page="/views/partials/nav-logout.jsp"></jsp:include>
	<div class="wave-container">
		<svg viewBox="0 0 500 500" preserveAspectRatio="xMinYMin meet"> 
         	<path d="M0, 100 C150, 200 350, 0 500, 100 L500, 00 L0, 0 Z" 
            	style="stroke:none; fill:dodgerblue;"> 
        	 </path> 
    	</svg>
	</div> 
  <div class="main container pt-5">
  	
  	<div role="alert" aria-live="assertive" aria-atomic="true" class="toast bg-info" data-autohide="false">
	  <div class="toast-header">
	    <strong class="mr-auto">Notification</strong>
	    <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
	      <span aria-hidden="true">&times;</span>
	    </button>
	  </div>
	  <div class="toast-body">
	    <%= message %>
	  </div>
	</div>
  	
  	
    <div class="header">
      <div class="row">
        <div class="col-8">
          <h1>Meetings</h1>
        </div>
        <div class="col">
          <div class="btn btn-yellow rounded" data-toggle="modal" data-target="#uploadIcsModal">
            Add Meeting
          </div>
          <!-- Central Modal Medium Info -->
          <div class="modal fade" id="uploadIcsModal" tabindex="-1" role="dialog" aria-labelledby="uploadIcsModal"
            aria-hidden="true">
            <div class="modal-dialog modal-full-height modal-right modal-notify modal-info" role="document">
              <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                  <p class="heading lead">ADD NEW MEETING INFO</p>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">X</span>
                  </button>
                </div>
                <!--Body-->
                <div class="modal-body">
                  <div class="text-center">
                    <i class="fas fa-file-upload fa-4x mb-3 animated fadeIn"></i>
                    <p>
                      <strong>UPLOAD ICS FILE</strong>
                    </p>
                  </div>
                  <hr>
                  <form class="form" method="POST" action="<%=request.getContextPath()%>/new-meeting" enctype = "multipart/form-data">
                    <div class="input-group">
                      <div class="custom-file">
                        <input type="file" class="custom-file-input" name="icsFile">
    	  				<label class="custom-file-label" for="customFile">Choose calendar file</label>
                      </div>
                    </div>
                </div>
                <div class="modal-footer justify-content-center">
                  <button type="submit" class="btn btn-primary waves-effect waves-light">UPLOAD</button>
                  <a type="button" class="btn btn-outline-primary waves-effect" data-dismiss="modal">Cancel</a>
                </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <%
    	request.setAttribute("myMeetings", session.getAttribute("myMeetings"));
    %>
    <div class="banner pt-3">
      <div class="row">
      	<c:forEach items="${myMeetings}" var="meeting">
		   	<div class="col-lg-4 col-md-6 p-3">
          		<div class="card">
            	<!-- Card content -->
            	<i class="fas fa-people d-flex justify-content-center"></i>
            		<div class="card-body">
              		<!-- Title -->
              			<h4 class="card-title">Title : ${ meeting.getTitle() }</h4>
              		<!-- Text -->
              			<div class="col w-100 card-text">
	              			<span class="row">Start : ${ meeting.getDateStart() }</span>
	          			</div>
	          			<div class="col card-text">
	             			<span class="row">End : ${ meeting.getDateEnd() }</span>
	             			<span class="row">Team Size: ${ meeting.getTeamSize() }</span>
	          			</div>
              		<!-- Button -->
              		<a href="<%= request.getContextPath() %>/meeting/${ meeting.getId() }" class="btn btn-info form-control">VIEW</a>
            		</div>
          		</div>
          	</div>
          	<!-- Card -->
	  		</c:forEach>
      </div>
    </div>
  </div>

  <!-- Add script imports-->
  <jsp:include page="/views/partials/jsImports.jsp"></jsp:include>
  
  <script>
  	$(document).ready(function(){
    	$('.toast').toast('show');
  	});
  </script>
</body>

</html>