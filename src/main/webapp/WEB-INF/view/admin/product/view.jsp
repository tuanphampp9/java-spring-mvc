<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib
uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="Tuấn Phạm IT - Dự án laptopshop" />
    <meta name="author" content="Tuấn Phạm IT" />
    <title>Detail product - Tuấn Phạm IT</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script
      src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
      crossorigin="anonymous"
    ></script>
  </head>

  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">Detail product</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
              <li class="breadcrumb-item">
                <a href="/admin/product">Product</a>
              </li>
              <li class="breadcrumb-item active">View detail</li>
            </ol>
            <div class="container mt-5">
              <div class="d-flex justify-content-between align-items-center">
                <h2>Product detail with id =${product.id}</h2>
              </div>
              <hr />
              <div class="card">
                <img
                  src="/images/product/${product.image}"
                  class="img-thumbnail"
                  alt="${product.name}"
                />
                <div class="card-header">Product Information</div>
                <ul class="list-group list-group-flush">
                  <li class="list-group-item">ID: ${product.id}</li>
                  <li class="list-group-item">Name: ${product.name}</li>
                  <li class="list-group-item">Role: ${product.price}</li>
                </ul>
              </div>
              <a href="/admin/product" class="btn btn-success mt-2">Back</a>
            </div>
          </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
      </div>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      crossorigin="anonymous"
    ></script>
    <script src="js/scripts.js"></script>
  </body>
</html>
