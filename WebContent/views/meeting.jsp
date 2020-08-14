<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.agilemeet.model.Meeting, com.agilemeet.model.Attender,com.agilemeet.model.MeetingPoints, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- Import CSS -->
  <jsp:include page="/views/partials/cssImports.jsp"></jsp:include>
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
		if(session.getAttribute("user") == null){
			response.sendRedirect(p);
		}
		if(session.getAttribute("meeting") == null){
			response.sendRedirect(p + "/views/home.jsp");
		}
	%>
	<%
		Meeting meeting = null;
		try{
			meeting = (Meeting) session.getAttribute("meeting");
			if(meeting == null) response.sendRedirect(p + "/views/home.jsp");
		}catch(Exception e){
			out.println(e.getMessage());
		}
	%>
	<%
						List<Attender> a = null;
            		  	List<String> options = null;
            		  	List<MeetingPoints> mpList = null;
            		  	Map<Integer,String> mapAttendee = new HashMap<>();
            		  	int indexOfAttendee = 2;
							try{
								mpList = (List<MeetingPoints>) session.getAttribute("meetingPoints");
								request.setAttribute("mpList", mpList);
								a = meeting.getAttendees();
								request.setAttribute("attendees", a);
	            		  		options = new ArrayList<>();
	            		  		for(Attender i: a){
	            		  			mapAttendee.put(i.getId(), i.getName());
	            		  			options.add(i.getName());
	            		  		}
	            		  		session.setAttribute("mpList", mpList);
	            		  		request.setAttribute("mpOptions", options);
	            		  		request.setAttribute("mapAttendee", mapAttendee);
							}catch(Exception e){
								out.println(e.getMessage());
							}
	%>
	
	<!-- Add Navbar import -->
	<div class="wave-container">
		<svg viewBox="0 0 500 500" preserveAspectRatio="xMinYMin meet"> 
         	<path d="M0, 100 C150, 200 350, 0 500, 100 L500, 00 L0, 0 Z" 
            	style="stroke:none; fill:dodgerblue;"> 
        	 </path> 
    	</svg>
	</div> 
	<jsp:include page="/views/partials/nav-logout.jsp"></jsp:include>
  <div class="main">
    <div class="form container">
    	<div class="row" style="padding-top: 2em;">
            <h2 class="col col-lg-8">Meeting </h2>
		  	<div class="col float-right">
		  		<form action="<%= request.getContextPath() %>/delete-meeting" method="POST">
		  			<input class="btn btn-danger rounded" type="submit" value="DELETE">
		  		</form>
		 	</div>
    	</div>
      <form action="<%= request.getContextPath() %>/edit-meeting" method="post">
        <div class="main-meeting-details" style="padding-top: 2em;">
          
          <input type="hidden" name="id" value="" />
          <div class="form-group">
            <label for="title">Title</label>
            <input type="text" name="title" class="form-control" value="<%= meeting.getTitle()%>">
          </div>
          <div class="form-group row">
            <div class="col-lg-6 col-md-6">
              <label for="dateStart">Date Start</label>
              <input name="dateStart" value="<%= meeting.getDateStart() %>" class="form-control" disabled />
            </div>
            <div class="col-lg-6 col-md-6">
              <label for="dateEnd">Date End</label>
              <input name="dateEnd" value="<%= meeting.getDateEnd() %>" class="form-control" disabled />
            </div>
          </div>
          <div class="form-group">
            <label for="description">Description</label>
            <textarea name="description" rows="2" class="form-control"><%= meeting.getDescription() %></textarea>
          </div>
          <div class="form-group row">
            <div class="col-lg-6 col-md-6">
              <label for="location">Location</label>
              <input name="location" value="<%= meeting.getLocation() %>" class="form-control" disabled />
            </div>
            <div class="col-lg-6 col-md-6">
              <label for="teamSize">Team Size</label>
              <input name="teamSize" value="<%= meeting.getTeamSize() %>" class="form-control" disabled />
            </div>
          </div>
        </div>

        <hr>

        <div class="meeting-points">
          <div class="row">
            <h2>Meeting Points</h2>
          </div>
          <div class="row">
          	<div class="col float-left">
          		<button type="button" class="btn btn-sm btn-success rounded addRow">
              	ADD NEW&nbsp;<i class="fas fa-plus"></i></button>
          	</div>
          	<div class="col">
          		<button type="button" class="btn float-right btn-sm btn-danger rounded deleteRow">DELETE SELECTED</button>	
          	</div>
          </div>
          <div class="row"><p></p></div>
          <table class="meetingPointsTable text-center table table-hover table-bordered" width="100%">
            <thead class="black white-text">
              <tr>
                <th class="th-sm">
                  Select Record
                </th>
                <th class="th-sm" colspan="4">Meeting Point</th>
                <th class="th-sm">Action Item</th>
                <th class="th-sm">Assignee</th>
              </tr>
            </thead>
            <tbody class="meetingTableBody">
              <c:forEach items="${ mpList }" var="mp">
              	<tr class="meetingPoint">
	                <td>
	                  <input type="checkbox" name="delete" class="checkbox form-control" />
	                  <input type="text" name="meetingPointId" hidden class="form-control" value="${ mp.getId() }" required />
	                </td>
	                <td colspan="4">
	                  <input type="text" name="meetingPointTasks" class="form-control" value="${ mp.getTask() }" required />
	                </td>
	                <td class="justify-contents-center">
	                	<select class="actionItem form-control" name="actionItem">
	                		
	                		<c:choose>
	                			<c:when test='${ mp.getAssignee() == 1 }'>
	                				<option value="NO" selected>NO</option>
	                  				<option value="YES">YES</option>
	                			</c:when>
	                			<c:otherwise>
	                				<option value="NO">NO</option>
	                  				<option value="YES" selected>YES</option>
	                			</c:otherwise>
	                		</c:choose>
	                 	</select>
	                </td>
	                <td>
	                  <select class="assignee form-control" name="assignee">
	                  	<c:choose>
	                  		<c:when test="${ mp.getAssignee() == 1 }">
	                  			<option value="Not Assigned" selected>Not Assigned</option>
	                  		</c:when>
	                  		<c:when test="${ mp.getAssignee() != 1 }">
	                  			<option value="${ mapAttendee.get(mp.getAssignee()) }" selected>${ mapAttendee.get(mp.getAssignee()) }</option>
	                  		</c:when>
	                  		<c:otherwise></c:otherwise>
	                  	</c:choose>
	                  	<c:forEach items="${mpOptions }" var="option">
	                  		<option value="${option }">${option }</option>
	                  	</c:forEach>
	                  </select>
	                </td>
	              </tr>
              </c:forEach>
            </tbody>
          </table>

        </div>
        <div class="form-action">
          <div class="row form-group">
            <div class="col-sm-6">
              <div class="float-left">
                <a href="<%= request.getContextPath() %>/views/home.jsp" type="button" class="btn btn-danger">CANCEL</a>
              </div>
            </div>
            <div class="col">
              <div class="float-right">
                <button type="submit" class="btn btn-primary waves-effect">SAVE</button>
              </div>
            </div>
          </div>
        </div>
    </div>
    </form>
  </div>
  </div>

  	  <!-- Import JS -->
	<jsp:include page="/views/partials/jsImports.jsp"></jsp:include>
	<script>
	const optionsArray = [
		<%
			for(int i=0;i<options.size();i++) {			
		%>
			"<%= options.get(i) %>"
		<%
			if(i != options.size() -1) {
		%>
			,
		<%
			}
		}
		%>
	];

	</script>
  	<script src="/virtusa-agile-meet/views/assests/Custom/js/meetingPointsTable.js"></script>
</body>
</html>