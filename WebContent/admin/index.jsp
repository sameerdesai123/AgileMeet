<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Login</title>
  <!-- Add CSS imports -->
	<jsp:include page="/views/partials/cssImports.jsp"></jsp:include>
  <!-- Add Custom CSS imports -->
  <link rel="stylesheet" href="<%= request.getContextPath() %>/views/assests/Custom/css/landing.css">
</head>
<body>
	
	  <div class="container my-5 px-0">
    <!--Section: Content-->
    <section class="p-5 my-md-5 text-center" style="background-size: cover; background-position: center center;">
      <form class="my-5 mx-md-5" action="<%= request.getContextPath() %>/admin/login" method="POST">
        <div class="row">
          <div class="col-md-6 mx-auto">
            <!-- Material form login -->
            <div class="card">
              <!--Card content-->
              <div class="card-body">
                <!-- Form -->
                <form class="text-center" style="color: #757575;" action="#!">
                  <h3 class="font-weight-bold my-4 pb-2 text-center dark-grey-text"><b>ADMIN LOGIN</b></h3>
                  <div class="form-group">
                    <input type="text" autocomplete="name" class="form-control" name="uname" placeholder="Username"
                      required>
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control" placeholder="Password" name="pass" required
                      minlength="6">
                  </div>
                  <div class="text-center">
                    <button type="submit" class="btn btn-outline-primary btn-rounded my-4 waves-effect">Login</button>
                  </div>
                </form>
                <!-- Form -->
              </div>
            </div>
            <!-- Material form login -->
          </div>
        </div>
      </form>
    </section>
    <!--Section: Content-->
  </div>
	
	<!-- Add script imports-->
  	<jsp:include page="/views/partials/jsImports.jsp"></jsp:include>
</body>
</html>