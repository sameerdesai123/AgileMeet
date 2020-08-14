<!--Navbar-->
<nav class="navbar navbar-expand-lg navbar-light white z-depth-2">

  <div class="container">

    <a class="navbar-brand" href="<%= request.getContextPath() %>" target="_self">
      <img src="<%= request.getContextPath() %>/views/assests/Custom/img/agile.png" height="30" alt="Agile logo">
    </a>

    <!-- Collapse button -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#LoginNav" aria-controls="LoginNav"
      aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Links -->
    <div class="collapse navbar-collapse" id="LoginNav">

      <!-- Left -->
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link waves-effect" href="<%= request.getContextPath() %>" target="_self">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link waves-effect" href="#" target="_blank">About</a>
        </li>
        <li class="nav-item">
          <a class="nav-link waves-effect" href="#" target="_blank">Contact</a>
        </li>
        <li class="nav-item">
          <a href="https://github.com/sameerdesai123" class="nav-link rounded waves-effect mr-2" target="_blank">
            <i class="fab fa-github"></i>
          </a>
        </li>
      </ul>

      <!-- Right -->
      <ul class="navbar-nav nav-flex-icons">
        <li class="nav-item">
          <button type="button" class="nav-link border border-light rounded waves-effect btn mr-2" data-toggle="modal"
            data-target="#loginModal">
            <i class="fas fa-sign-in-alt mr-1"></i>LOGIN
          </button>
          <!-- Central Modal Medium Info -->
          <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModal"
            aria-hidden="true">
            <div class="modal-dialog modal-full-height modal-right modal-notify modal-info" role="document">
              <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                  <p class="heading lead">AGILE MEET</p>

                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">X</span>
                  </button>
                </div>

                <!--Body-->
                <div class="modal-body">
                  <div class="text-center">
                    <i class="fas fa-sign-in-alt fa-4x mb-3 animated rotateIn"></i>
                    <p>
                      <strong>SIGNIN</strong>
                    </p>
                  </div>

                  <hr>

                  <form class="form" method="POST" action="<%=request.getContextPath()%>/login">
                    <div class="form-group mb-4">
                      <label class="form-label" for="email">Email</label>
                      <input class="form-control" type="email" name="email">
                    </div>
                    <div class="form-group mb-4">
                      <label class="form-label" for="password">Password</label>
                      <input class="form-control" type="password" name="password">
                    </div>
                    <p class="text-center">
                      <a href="/forgot-password">Forgot Password?</a>
                    </p>
                    <div class="modal-footer justify-content-center">
                    	<button type="submit" class="btn btn-primary waves-effect waves-light">LOGIN</button>
                  		<a type="button" class="btn btn-outline-primary waves-effect" data-dismiss="modal">Cancel</a>
                	</div>
                </div>
                </form>
              </div>
            </div>
          </div>
          <!-- Central Modal Medium Info -->
          <div class="modal fade" id="signupModal" tabindex="-1" role="dialog" aria-labelledby="signupModal"
            aria-hidden="true">
            <div class="modal-dialog modal-full-height modal-right modal-notify modal-info" role="document">
              <div class="modal-content">
                <!--Header-->
                <div class="modal-header">
                  <p class="heading lead">AGILE MEET</p>

                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">X</span>
                  </button>
                </div>

                <!--Body-->
                <div class="modal-body">
                  <div class="text-center">
                    <i class="fas fa-user-plus fa-4x mb-3 animated rotateIn"></i>
                    <p>
                      <strong>SIGNUP</strong>
                    </p>
                  </div>

                  <hr>

                  <form class="form" method="POST" action="<%=request.getContextPath()%>/register">
                    <div class="form-group mb-4">
                      <label class="form-label" for="name">Name</label>
                      <input class="form-control" type="text" name="name">
                    </div>
                    <div class="form-group mb-4">
                      <label class="form-label" for="email">Email</label>
                      <input class="form-control" type="email" name="email">
                    </div>
                    <div class="form-group mb-4">
                      <label class="form-label" for="password">Password</label>
                      <input class="form-control" type="password" name="password">
                    </div>
                    <div class="form-group mb-4">
                      <label class="form-label" for="cnfpassword">Confirm Password</label>
                      <input class="form-control" type="password" name="cnfpassword">
                    </div>
                    <p class="text-center">
                      <button type="button" class="border-light rounded waves-effect btn mr-2" data-toggle="modal"
                        data-target="#loginModal" data-dismiss="modal">Already a user? Sign In</button>
                    </p>
                    <div class="modal-footer justify-content-center">
                  		<button type="submit" class="btn btn-primary waves-effect waves-light">REGISTER</button>
                  		<a type="button" class="btn btn-outline-primary waves-effect" data-dismiss="modal">Cancel</a>
                	</div>
                </div>
                </form>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</nav>
<!--/.Navbar-->