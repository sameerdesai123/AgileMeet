<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Agile Meetings</title>
  <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
    integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous">
  </script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/jquery.validate.min.js"></script>
  <link defer rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
    integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
  <script src="https://kit.fontawesome.com/a81368914c.js"></script>
  <link href='https://fonts.googleapis.com/css?family=Simonetta' rel='stylesheet'>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/views/css/login-styles.css">
  <style>
    body {
      font-family: 'Simonetta';
      font-size: 22px;
    }
  </style>
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
  <script>
    const inputs = document.querySelectorAll(".input");

    function addcl() {
      let parent = this.parentNode.parentNode;
      parent.classList.add("focus");
    }

    function remcl() {
      let parent = this.parentNode.parentNode;
      if (this.value == "") {
        parent.classList.remove("focus");
      }
    }


    inputs.forEach(input => {
      input.addEventListener("focus", addcl);
      input.addEventListener("blur", remcl);
    });
    
    function doValidate(){
    	var validator = $('.validatedForm').validate({
    		rules : {
    			password : {
    				minlength : 5
    			},
    			password_confirm : {
    				minlength : 5,
    				equalTo : "#password"
    			}
    		}
    	});

    	if (validator.form()) {
            alert('Sucess');
        }
    }
    
  </script>
</head>

<!-- Main Login Form -->

<body>
	<div class="wave-container">
		<svg viewBox="0 0 500 500" preserveAspectRatio="xMinYMin meet"> 
         	<path d="M0, 100 C150, 200 350, 0 500, 100 L500, 00 L0, 0 Z" 
            	style="stroke:none; fill:dodgerblue;"> 
        	 </path> 
    	</svg>
	</div>
  <div class="container d-flex justify-content-center">
    <div class="login-content">
      <form action="<%=request.getContextPath()%>/register" method="POST" class="validatedForm shadow p-3 mb-5 bg-white rounded">
        <h2>Register</h2>
        <div class="input-div one">
          <div class="i">
            <i class="fas fa-user"></i>
          </div>
          <div class="div">
            <h5>Name</h5>
            <input type="text" name="name" class="input">
          </div>
        </div>
        <div class="input-div one">
          <div class="i">
            <i class="fas fa-user"></i>
          </div>
          <div class="div">
            <h5>Email</h5>
            <input type="email" name="email" class="input">
          </div>
        </div>
        <div class="input-div pass">
          <div class="i">
            <i class="fas fa-lock"></i>
          </div>
          <div class="div">
            <h5>Password</h5>
            <input type="password" minlength="6" id="password" name="password" class="input">
          </div>
        </div>
        <div class="input-div pass">
          <div class="i">
            <i class="fas fa-lock"></i>
          </div>
          <div class="div">
            <h5>Confirm Password</h5>
            <input type="password" minlength="6" name="password_confirm" class="input">
          </div>
        </div>
        <input type="submit" class="btn register-btn" value="Login">
        <a href="<%=request.getContextPath()%>/" class="text-center">Already a member? Login Here</a>
      </form>
    </div>
  </div>
</body>

</html>