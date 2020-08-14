<!--Navbar-->
<nav class="navbar navbar-expand-lg navbar-light white">

  <div class="container-fluid p-2">

    <a class="navbar-brand" href="/virtusa-agile-meet/" target="_self">
      <img src="<%= request.getContextPath() %>/views/assests/Custom/img/agile.png" height="30" alt="Agile logo">
    </a>

    <!-- Collapse button -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#LogoutNav"
      aria-controls="LogoutNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Links -->
    <div class="collapse navbar-collapse" id="LogoutNav">

      <!-- Left -->
      <ul class="navbar-nav mr-auto">
        <li class="nav-item">
          <a class="nav-link waves-effect" href="<%= request.getContextPath() %>/admin/users" target="_self">User Management</a>
        </li>
        <li class="nav-item">
          <a class="nav-link waves-effect" href="<%= request.getContextPath() %>/admin/dashboard.jsp" target="_self">Dashboard</a>
        </li>
      </ul>

      <!-- Right -->
      <ul class="navbar-nav nav-flex-icons">
        <li class="nav-item">
          <form class="form-inline my-2 my-lg-0 ml-auto" method="GET" action="<%= request.getContextPath() %>/admin/logout">
            <button class="btn btn-primary btn-md my-2 my-sm-0 ml-3" type="submit">LOGOUT</button>
          </form>
        </li>
      </ul>

    </div>

  </div>

</nav>
<!--/.Navbar-->