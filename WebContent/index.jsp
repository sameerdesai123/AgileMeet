<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Agile Meet</title>
  <!-- Add css imports -->
	<jsp:include page="/views/partials/cssImports.jsp"></jsp:include>
  <!-- Add Custom CSS imports -->
  <link rel="stylesheet" href="<%= request.getContextPath() %>/views/assests/Custom/css/landing.css">
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
		String p = request.getContextPath() + "/views/home.jsp";
		if(session.getAttribute("user") != null){
			response.sendRedirect(p);
		}
		String message = (String) session.getAttribute("msg");
		String status = (String) session.getAttribute("status");
	%>
	
  <!-- Add Navbar import -->
  <div class="wave-container">
  	<svg viewBox="0 0 500 500" preserveAspectRatio="xMinYMin meet"> 
         <path d="M0, 50 C200, 100 250, 0 500, 100 L550, 00 L0, 0 Z" 
            style="stroke:none; fill:dodgerblue;"> 
         </path> 
    </svg>
  </div>
	<jsp:include page="/views/partials/nav-login.jsp"></jsp:include>
	
	<div role="alert" aria-live="assertive" aria-atomic="true" class="toast bg-info d-none" data-autohide="false" style="margin-left:10px;">
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
	
  <div class="overlay">
    <div class="flex-center">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-6">
            <h1 class="h1">
              AGILE MEET</h1>
            <br>
            <div>
              <p class="lead">
                Agile Meet is the place where you get to track your virtual meetings, draw up actionables, assign tasks,
                maintain teams, notify assignees and track progress the agile way....</p>
            </div>
            <br>
            <div class="row">
              <div class="col align-content-center">
                <button class="btn waves-effect btn-primary rounded" data-toggle="modal" data-target="#signupModal">SIGN
                  UP</button>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="image">
              <img src="<%= request.getContextPath() %>/views/assests/Custom/img/landing-side.jpg" class="img-fluid" alt="Image">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


  <!-- Add script imports-->
  <jsp:include page="/views/partials/jsImports.jsp"></jsp:include>
	
	<script>
		const status = "<%= status %>";
		<% 
		status = "";
		message = "";
		%>
	</script>
	
	<script>
		$(document).ready(function(){
			if(status == "DANGER"){ 
				$('.toast').addClass('bg-danger');
				$('.toast').removeClass('bg-info');
				$('.toast').removeClass('bg-success');
				$('.toast').removeClass('d-none');
				$('.toast').toast('show');	
				status = "";
			} 
			else if(status == "SUCCESS"){
				$('.toast').addClass('bg-success');
				$('.toast').removeClass('bg-info');
				$('.toast').removeClass('d-none');
				$('.toast').removeClass('bg-danger');
				$('.toast').toast('show');
				status = "";
			}
			else {
				$('.toast').addClass('bg-info');
				$('.toast').removeClass('bg-success');
				$('.toast').removeClass('bg-danger');	
				status = "";
			}
	  	});
	</script>
</body>

</html>