<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.agilemeet.admin.model.Users, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User View</title>
<!-- Add css imports -->
	<jsp:include page="/views/partials/cssImports.jsp"></jsp:include>
  <!-- Add Custom CSS imports -->
  <!-- MDBootstrap Datatables  -->
	<link href="<%= request.getContextPath() %>/views/assests/MDB/css/addons/datatables.min.css" rel="stylesheet">
	 <style>
  	body{
  margin:0px 0px;
  padding:0px px;
}
svg {
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
<body>
<% 
		String p = request.getContextPath() + "/";
		if(session.getAttribute("admin-name") == null || session.getAttribute("role") == null){
			response.sendRedirect(p);
		}
	%>
	<%
		Users user = (Users) session.getAttribute("user");
		request.setAttribute("user", user);
	%>
	<jsp:include page="/admin/partials/nav.jsp"></jsp:include>
	<div class="wave-container">
		<svg viewBox="0 0 500 500" preserveAspectRatio="xMinYMin meet"> 
         	<path d="M0, 100 C150, 200 350, 0 500, 100 L500, 00 L0, 0 Z" 
            	style="stroke:none; fill:dodgerblue;"> 
        	 </path> 
    	</svg>
	</div>
<div class="container p-5">
	<div class="p-5 shadow bg-white">
		<div class="row"> 
			<div class="col col-lg-9">
				<h3><b>User Info</b></h3>
			</div>
			<div class="col col-lg-3">
				<form action="<%= request.getContextPath() %>/admin/users/delete-user" method="POST">
					<input name="id" value="${ user.getId() }" hidden/>
					<input type="submit" class="btn btn-danger rounded shadow" value="DELETE USER"/>
				</form>
			</div>
		</div>
		
		<hr>
		<br>
		<form action="<%= request.getContextPath() %>/admin/users/edit" method="POST">
			<div class="row form-group">
				<label for="ID" class="col col-lg-2">ID</label>
				<input class="form-control col" type="text" name="ID" value="${ user.getId() }" disabled/>
			</div>
			<div class="row form-group">
				<label for="name" class="col col-lg-2">Name</label>
				<input class="form-control col" type="text" name="name" value="${ user.getName() }" />
			</div>
			<div class="row form-group">
				<label for="email" class="col col-lg-2">Email</label>
				<input class="form-control col" type="email" name="email" value="${ user.getEmail() }" />
			</div>
			<div class="row form-group">
				<label for="password" class="col col-lg-2">Password</label>
				<input class="form-control col" type="text" name="password" value="${ user.getPass() }" />
			</div>
			<div class="row form-group">
				<div class="w-80">
					<input class="btn btn-primary text-center rounded" value="UPDATE" type="submit"/>
				</div>
				<div class="w-20">
					<a href="<%= request.getContextPath()%>/admin/users.jsp"><input class="btn rounded" value="CANCEL" type="button"/></a>
				</div>
			</div>
		</form>
	</div>
</div>

<!-- Add script imports-->
  <jsp:include page="/views/partials/jsImports.jsp"></jsp:include>
</body>
</html>