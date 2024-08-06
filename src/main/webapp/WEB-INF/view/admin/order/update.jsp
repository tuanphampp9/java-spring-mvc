<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib
uri="http://www.springframework.org/tags/form" prefix="form"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <title>Update product - Tuấn Phạm IT</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script
      src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
      crossorigin="anonymous"
    ></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {
        const productFile = $("#productFile");
        const currentImg = "${newProduct.image}";
        if (currentImg) {
          $("#productPreview").attr("src", "/images/product/" + currentImg);
          $("#productPreview").css({ display: "block" });
        }
        productFile.change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#productPreview").attr("src", imgURL);
          $("#productPreview").css({ display: "block" });
        });
      });
    </script>
  </head>

  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">Products</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
              <li class="breadcrumb-item active">Product</li>
            </ol>
            <div class="container mt-5">
              <h1>Update a order</h1>
              <hr />
              <div class="d-flex gap-3 align-content-center">
                <p>Order id = ${newOrder.id}</p>
                <p>
                  Price =
                  <fmt:formatNumber
                    value="${newOrder.totalPrice}"
                    type="number"
                  />
                  đ
                </p>
              </div>
              <form:form
                method="post"
                action="/admin/order/update"
                modelAttribute="newOrder"
              >
                <input
                  type="hidden"
                  name="${_csrf.parameterName}"
                  value="${_csrf.token}"
                />
                <form:input
                  id="id"
                  aria-describedby="id"
                  type="hidden"
                  path="id"
                />
                <div class="d-flex gap-3">
                  <div>
                    <label for="receiverName" class="form-label"
                      >ReceiverName:</label
                    >
                    <form:input
                      type="text"
                      id="receiverName"
                      aria-describedby="receiverName"
                      disabled="true"
                      path="receiverName"
                    />
                  </div>
                  <div class="d-flex gap-2 align-items-center">
                    <label for="status" class="form-label">Status:</label>
                    <form:select
                      class="form-select"
                      aria-label="Default select example"
                      name="status"
                      path="status"
                    >
                      <form:option value="Pending">Pending</form:option>
                      <form:option value="Shipping">Shipping</form:option>
                      <form:option value="Complete">Complete</form:option>
                      <form:option value="Cancel">Cancel</form:option>
                    </form:select>
                  </div>
                </div>
                <button type="submit" class="btn btn-warning">Update</button>
              </form:form>
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
    <script src="/js/scripts.js"></script>
  </body>
</html>
