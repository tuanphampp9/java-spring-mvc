<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@taglib
uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Đăng ký - Laptopshop</title>

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
      rel="stylesheet"
    />
    <link href="/css/styles.css" rel="stylesheet" />
    <!-- Icon Font Stylesheet -->
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
      rel="stylesheet"
    />

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet" />
    <link
      href="/client/lib/owlcarousel/assets/owl.carousel.min.css"
      rel="stylesheet"
    />

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet" />
  </head>

  <body>
    <!-- Spinner Start -->
    <div
      id="spinner"
      class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center"
    >
      <div class="spinner-grow text-primary" role="status"></div>
    </div>

    <div id="layoutAuthentication">
      <div id="layoutAuthentication_content">
        <main>
          <div class="container">
            <div class="row justify-content-center">
              <div class="col-lg-7">
                <div class="card shadow-lg border-0 rounded-lg mt-5">
                  <div class="card-header">
                    <h3 class="text-center font-weight-light my-4">
                      Create Account
                    </h3>
                  </div>
                  <div class="card-body">
                    <form:form
                      action="/register"
                      method="post"
                      modelAttribute="registerUser"
                    >
                    <c:set var="errorFirstName">
                      <form:errors path="firstName" cssClass="invalid-feedback" />
                    </c:set>
                    <c:set var="errorPassword">
                      <form:errors path="password" cssClass="invalid-feedback" />
                    </c:set>
                    <c:set var="errorConfirmPassword">
                      <form:errors path="confirmPassword" cssClass="invalid-feedback" />
                    </c:set>
                    <c:set var="errorEmail">
                      <form:errors path="email" cssClass="invalid-feedback" />
                    </c:set>
                      <div class="row mb-3">
                        <div class="col-md-6">
                          <div class="form-floating mb-3 mb-md-0">
                            <form:input
                              class="form-control ${empty errorFirstName ? '' : 'is-invalid'}"
                              id="inputFirstName"
                              type="text"
                              placeholder="Enter your first name"
                              path="firstName"
                            />
                            <label for="inputFirstName">First name</label>
                            ${errorFirstName}
                          </div>
                        </div>
                        <div class="col-md-6">
                          <div class="form-floating">
                            <form:input
                              class="form-control"
                              id="inputLastName"
                              type="text"
                              placeholder="Enter your last name"
                              path="lastName"
                            />
                            <label for="inputLastName">Last name</label>
                          </div>
                        </div>
                      </div>
                      <div class="form-floating mb-3">
                        <form:input
                          class="form-control ${empty errorEmail ? '' : 'is-invalid'}"
                          id="inputEmail"
                          type="email"
                          placeholder="name@example.com"
                          path="email"
                        />
                        <label for="inputEmail">Email address</label>
                        ${errorEmail}
                      </div>
                      <div class="row mb-3">
                        <div class="col-md-6">
                          <div class="form-floating mb-3 mb-md-0">
                            <form:input
                              class="form-control ${empty errorPassword ? '' : 'is-invalid'}"
                              id="inputPassword"
                              type="password"
                              placeholder="Create a password"
                              path="password"
                            />
                            <label for="inputPassword">Password</label>
                            ${errorPassword}
                          </div>
                        </div>
                        <div class="col-md-6">
                          <div class="form-floating mb-3 mb-md-0">
                            <form:input
                              class="form-control ${empty errorConfirmPassword ? '' : 'is-invalid'}"
                              id="inputPasswordConfirm"
                              type="password"
                              placeholder="Confirm password"
                              path="confirmPassword"
                            />
                            <label for="inputPasswordConfirm"
                              >Confirm Password</label
                            >
                            ${errorConfirmPassword}
                          </div>
                        </div>
                      </div>
                      <div class="mt-4 mb-0">
                        <div class="d-grid">
                          <button class="btn btn-primary" type="submit"
                            >Create Account</
                          >
                        </div>
                      </div>
                    </form:form>
                  </div>
                  <div class="card-footer text-center py-3">
                    <div class="small">
                      <a href="/login">Have an account? Go to login</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
      <div id="layoutAuthentication_footer">
        <footer class="py-4 bg-light mt-auto">
          <div class="container-fluid px-4">
            <div
              class="d-flex align-items-center justify-content-between small"
            >
              <div class="text-muted">Copyright &copy; Your Website 2023</div>
              <div>
                <a href="#">Privacy Policy</a>
                &middot;
                <a href="#">Terms &amp; Conditions</a>
              </div>
            </div>
          </div>
        </footer>
      </div>
    </div>
    <!-- Back to Top -->
    <a
      href="#"
      class="btn btn-primary border-3 border-primary rounded-circle back-to-top"
      ><i class="fa fa-arrow-up"></i
    ></a>

    <!-- JavaScript Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/client/lib/easing/easing.min.js"></script>
    <script src="/client/lib/waypoints/waypoints.min.js"></script>
    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

    <!-- Template Javascript -->
    <script src="/client/js/main.js"></script>
  </body>
</html>
