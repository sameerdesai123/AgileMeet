<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.agilemeet.model.Meeting, com.agilemeet.model.Attender, java.util.List, java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>Agile Meet</title>
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
	%>
	<%
		Meeting meeting = null;
		try{
			meeting = (Meeting) session.getAttribute("meeting");
			if(meeting == null) response.sendRedirect(p + "home");
		}catch(Exception e){
			out.println(e.getMessage());
		}
	%>
	
	<div class="bg">
		<svg viewBox="0 0 500 500" preserveAspectRatio="xMinYMin meet"> 
         	<path d="M0, 100 C150, 200 350, 0 500, 100 L500, 00 L0, 0 Z" 
            	style="stroke:none; fill:dodgerblue;"> 
        	 </path> 
    	</svg>
   	</div>
   	
	
	<!-- Add Navbar import -->
   	<jsp:include page="/views/partials/nav-logout.jsp"></jsp:include>
	
  <div class="m-5">
    <div class="container">
      <form action="<%= request.getContextPath() %>/save-meeting" method="post">
        <div class="main-meeting-details" style="padding-top: 2em;">
          <div class="row">
            <h2>Meeting Details</h2>
          </div>
          <input type="hidden" name="id" value="" />
          <div class="form-group">
            <label for="title">Title</label>
            <input type="text" name="title" class="form-control input" value="<%= meeting.getTitle()%>">
          </div>
          <div class="form-group row">
            <div class="col-lg-6 col-md-6">
              <label for="dateStart">Date Start</label>
              <input name="dateStart" value="<%= meeting.getDateStart().substring(0, 10) +  " " + meeting.getDateStart().substring(11, 16) %>" class="form-control" disabled />
            </div>
            <div class="col-lg-6 col-md-6">
              <label for="dateEnd">Date End</label>
              <input name="dateEnd" value="<%= meeting.getDateEnd().substring(0, 10) +  " " + meeting.getDateEnd().substring(11, 16) %>" class="form-control" disabled />
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
						<%
							List<Attender> a = meeting.getAttendees();
							request.setAttribute("attendees", a);
            		  		List<String> options = new ArrayList<>();
            		  		for(Attender i: a){
            		  			options.add(i.getName());
            		  		}
            		  		int indexOfAttendee = 2;
            		  		request.setAttribute("mpOptions", options);
						%>
							
        <div class="team-details">
          <div class="row">
            <h2>Team</h2>
          </div>
          <div class="table-view">
            <table class="table table table-hover table-striped table-bordered">
              <thead>
                <tr>
                  <th scope="row">#</th>
                  <th>Name</th>
                  <th>Email</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td scope="row">1</td>
                  <td><%= meeting.getOrganizerName() %></td>
                  <td><%= meeting.getOrganizerEmail() %></td>
                </tr>
                <!-- Insert JSTL code to print all attendees -->
                <c:forEach items="${attendees}" var="item">
							<tr>
								<td>${ indexOfAttendee }</td>
								<td>${ item.getName() }</td>
								<td>${ item.getEmail() }</td>
							</tr>
							<% indexOfAttendee += 1; %>
				</c:forEach>
              </tbody>
            </table>
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
              <tr class="meetingPoint">
                <td>
                  <input type="checkbox" name="delete" class="checkbox form-control" />
                </td>
                <td colspan="4">
                  <input type="text" name="meetingPointTasks" class="form-control" required />
                </td>
                <td class="justify-contents-center">
                	<select class="actionItem form-control" name="actionItem">
                  		<option value="NO">NO</option>
                  		<option value="YES">YES</option>
                 	</select>
                </td>
                <td>
                  <select class="assignee form-control" name="assignee">
                  	<option>Not Assigned</option>
                  	<c:forEach items="${mpOptions }" var="option">
                  		<option value="${option }">${option }</option>
                  	</c:forEach>
                  </select>
                </td>
              </tr>
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
  	<script src="<%= request.getContextPath() %>/views/assests/Custom/js/meetingPointsTable.js"></script>
</body>

</html>