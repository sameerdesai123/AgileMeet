<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.agilemeet.admin.model.DashBoardData, java.util.List, java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard</title>
	<!-- Add css imports -->
	<jsp:include page="/views/partials/cssImports.jsp"></jsp:include>
  <!-- Add Custom CSS imports -->
  <link rel="stylesheet" href="<%= request.getContextPath() %>/views/assests/Custom/css/dash.css">
</head>

<body>

	<% 
		String p = request.getContextPath() + "/";
		if(session.getAttribute("admin-name") == null || session.getAttribute("role") == null){
			response.sendRedirect(p);
		}
		
	%>
	<jsp:include page="/admin/partials/nav.jsp"></jsp:include>
	
	<% 
    	request.setAttribute("currentTask", session.getAttribute("currentTask"));
		DashBoardData data = (DashBoardData) session.getAttribute("dashData");
		request.setAttribute("data", data);
		List<Integer> graphData = (List<Integer>) session.getAttribute("graphData");
	%>
	
  <div class="container-fluid my-5 py-2">

    <!-- Section: Block Content -->
    <section>
      <!-- Grid row -->
      <div class="row">

        <!-- Grid column -->
        <div class="col-md-6 col-lg-3 mb-4">

          <!-- Card -->
          <div class="card primary-color white-text">
            <div class="card-body d-flex justify-content-between align-items-center">
              <div>
                <p class="h2-responsive font-weight-bold mt-n2 mb-0">${ data.getMeetingCount() }</p>
                <p class="mb-0">Meetings</p>
              </div>
              <div>
                <i class="fas fa-shopping-bag fa-4x text-black-40"></i>
              </div>
            </div>
            <a class="card-footer footer-hover small text-center white-text border-0 p-2" href="<%= request.getContextPath() %>/admin/meetings">More info<i
                class="fas fa-arrow-circle-right pl-2"></i></a>
          </div>
          <!-- Card -->

        </div>
        <!-- Grid column -->

        <!-- Grid column -->
        <div class="col-md-6 col-lg-3 mb-4">

          <!-- Card -->
          <div class="card warning-color white-text">
            <div class="card-body d-flex justify-content-between align-items-center">
              <div>
                <p class="h2-responsive font-weight-bold mt-n2 mb-0">${ data.getActionCount() }</p>
                <p class="mb-0">Actions</p>
              </div>
              <div>
                <i class="fas fa-chart-bar fa-4x text-black-40"></i>
              </div>
            </div>
            <a class="card-footer footer-hover small text-center white-text border-0 p-2" href="<%= request.getContextPath() %>/admin/action-items">More info<i
                class="fas fa-arrow-circle-right pl-2"></i></a>
          </div>
          <!-- Card -->
        </div>
        <!-- Grid column -->

        <!-- Grid column -->
        <div class="col-md-6 col-lg-3 mb-4">

          <!-- Card -->
          <div class="card light-blue lighten-1 white-text">
            <div class="card-body d-flex justify-content-between align-items-center">
              <div>
                <p class="h2-responsive font-weight-bold mt-n2 mb-0">${ data.getUserCount() }</p>
                <p class="mb-0">User Registrations</p>
              </div>
              <div>
                <i class="fas fa-user-plus fa-4x text-black-40"></i>
              </div>
            </div>
            <a class="card-footer footer-hover small text-center white-text border-0 p-2" href="<%= request.getContextPath() %>/admin/users">More info<i
                class="fas fa-arrow-circle-right pl-2"></i></a>
          </div>
          <!-- Card -->

        </div>
        <!-- Grid column -->

        <!-- Grid column -->
        <div class="col-md-6 col-lg-3 mb-4">

          <!-- Card -->
          <div class="card red accent-2 white-text">
            <div class="card-body d-flex justify-content-between align-items-center">
              <div>
                <p class="h2-responsive font-weight-bold mt-n2 mb-0">${ data.getUniqueAttendees() }</p>
                <p class="mb-0">Unique Attendees</p>
              </div>
              <div>
                <i class="fas fa-chart-pie fa-4x text-black-40"></i>
              </div>
            </div>
            <a class="card-footer footer-hover small text-center white-text border-0 p-2" href="<%= request.getContextPath() %>/admin/attendees">More info<i
                class="fas fa-arrow-circle-right pl-2"></i></a>
          </div>
          <!-- Card -->

        </div>
        <!-- Grid column -->

      </div>
      <!-- Grid row -->

    </section>
    <!-- Section: Block Content -->

  </div>

  <div class="container-fluid my-5 row">

    <div class="w-100" style="padding-left: 2em">
      <!-- Section -->
      <section>
      	<div class="heading row">
      		<div class="col">
				<h3 class="font-weight-bold text-center dark-grey-text pb-2">Activity Monitor</h3>
			    <p class="sub-heading text-center">Below Graph shows number of operations on meeting and meeting points per day</p>
			</div>
			<div class="col">
			  <h3 class="font-weight-bold text-center dark-grey-text pb-2">Notification Panel</h3>
        	  <p class="text-center">This task is notified to every user on login</p>
        </div>
      	</div>
		      
        <div class="row">
          <!-- Grid column -->
          <div class="col-12 mb-4">

            <div class="card">
              <div class="card-body p-0">
                <!-- Chart -->
                <div class="view view-cascade gradient-card-header blue-gradient p-4 rounded row">

                  <div class="view col">
                    <canvas id="lineChart" height="175"></canvas>
                  </div>
                  <div class="view col">
                  	<div class="card text-white">
			            <div class="card-body p-0">
			              <!-- Chart -->
			              <div class="view view-cascade gradient-card-header blue-gradient p-4 rounded">
			                <div class="form">
			                  <h3>Current Task</h3>
			                  <p>${ currentTask }</p>
			                  <form action="<%= request.getContextPath() %>/change-task" method="post">
			                    <label for="taskUpdate">Update Task</label>
			                    <textarea class="form-control focusable textarea" name="taskUpdate" cols="20" rows="8"></textarea>
			                    <button type="submit" class="form-control btn btn-primary rounded">Update</button>
			                  </form>
                		</div>
              		</div>
                  </div>

                </div>
              </div>
            </div>

          </div>
          <!-- Grid column -->

        </div>

      </section>
      <!-- Section -->
    </div>

	<!-- Add script imports-->
    <jsp:include page="/views/partials/jsImports.jsp"></jsp:include>
    <script>
	const graphArray = [
		<%
			for(int i=0;i<graphData.size();i++) {			
		%>
			"<%= graphData.get(i) %>"
		<%
			if(i != graphData.size() -1) {
		%>
			,
		<%
			}
		}
		%>
	];

	</script>
    <script src="<%= request.getContextPath() %>/views/assests/Custom/js/admin-dash-graph.js"></script>
</body>

</html>