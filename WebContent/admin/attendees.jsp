<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.agilemeet.admin.model.Attendees, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Attendees</title>
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
		List<Attendees> attendees = (List<Attendees>) session.getAttribute("attendees");
		request.setAttribute("attendees", attendees);
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
		<table id="attendeesTable" class="table table-striped table-hover table-bordered table-sm" cellspacing="0" width="100%">
			<thead >
				<tr>
					<th class="th-sm">Name</th>
					<th class="th-sm">Email</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ attendees }" var="m">
					<tr>
						<td>${ m.getName() }</td>
						<td>${ m.getEmail() }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<!-- Add script imports-->
  <jsp:include page="/views/partials/jsImports.jsp"></jsp:include>
  <!-- MDBootstrap Datatables  -->
<script type="text/javascript" src="<%= request.getContextPath() %>/views/assests/MDB/js/addons/datatables.min.js"></script>
<script>
$(document).ready(function () {
	  $('#attendeesTable').DataTable();
	  $('.dataTables_length').addClass('bs-select');
	});
</script>
</body>
</html>