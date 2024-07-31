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
              <h1>Update a product</h1>
              <hr />
              <form:form
                method="post"
                action="/admin/product/update/${newProduct.id}"
                modelAttribute="newProduct"
                enctype="multipart/form-data"
              >
                <div class="d-flex gap-3">
                  <div class="mb-3 w-50">
                    <c:set var="errorName">
                      <form:errors path="name" cssClass="invalid-feedback" />
                    </c:set>
                    <label for="name" class="form-label">Name:</label>
                    <form:input
                      type="text"
                      class="form-control ${empty errorName ? '' : 'is-invalid'}"
                      id="name"
                      aria-describedby="name"
                      path="name"
                    />
                    ${errorName}
                  </div>
                  <div class="mb-3 w-50">
                    <c:set var="errorPrice">
                      <form:errors path="price" cssClass="invalid-feedback" />
                    </c:set>
                    <label for="price" class="form-label">Price:</label>
                    <form:input
                      type="number"
                      class="form-control ${empty errorPrice ? '' : 'is-invalid'}"
                      id="price"
                      path="price"
                    />
                    ${errorPrice}
                  </div>
                </div>
                <div class="mb-3">
                  <c:set var="errorDetailDesc">
                    <form:errors
                      path="detailDesc"
                      cssClass="invalid-feedback"
                    />
                  </c:set>
                  <label for="detailDesc" class="form-label"
                    >Detail description:</label
                  >
                  <form:textarea
                    type="text"
                    class="form-control ${empty errorDetailDesc ? '' : 'is-invalid'}"
                    id="detailDesc"
                    path="detailDesc"
                    rows="4"
                  ></form:textarea>
                  ${errorDetailDesc}
                </div>
                <div class="d-flex gap-3">
                  <div class="mb-3 w-50">
                    <c:set var="errorShortDesc">
                      <form:errors
                        path="shortDesc"
                        cssClass="invalid-feedback"
                      />
                    </c:set>
                    <label for="shortDesc" class="form-label"
                      >Short description:</label
                    >
                    <form:input
                      type="text"
                      class="form-control ${empty errorShortDesc ? '' : 'is-invalid'}"
                      id="shortDesc"
                      aria-describedby="shortDesc"
                      path="shortDesc"
                    />
                    ${errorShortDesc}
                  </div>
                  <div class="mb-3 w-50">
                    <c:set var="errorQuantity">
                      <form:errors
                        path="quantity"
                        cssClass="invalid-feedback"
                      />
                    </c:set>
                    <label for="quantity" class="form-label">Quantity:</label>
                    <form:input
                      type="number"
                      class="form-control ${empty errorQuantity ? '' : 'is-invalid'}"
                      id="quantity"
                      path="quantity"
                    />
                    ${errorQuantity}
                  </div>
                </div>
                <div class="d-flex gap-3">
                  <div class="mb-3 w-50">
                    <label for="factory" class="form-label">Factory:</label>
                    <form:select
                      class="form-select"
                      aria-label="Default select example"
                      path="factory"
                    >
                      <form:option value="Apple">Apple</form:option>
                      <form:option value="Asus">Asus</form:option>
                      <form:option value="Lenovo">Lenovo</form:option>
                      <form:option value="Dell">Dell</form:option>
                      <form:option value="LG">LG</form:option>
                      <form:option value="Acer">Acer</form:option>
                    </form:select>
                  </div>
                  <div class="mb-3 w-50">
                    <label for="target" class="form-label">Target:</label>
                    <form:select
                      class="form-select"
                      aria-label="Default select example"
                      path="target"
                    >
                      <form:option value="Gaming">Gaming</form:option>
                      <form:option value="Sinh viên - Văn phòng"
                        >Sinh viên - Văn phòng</form:option
                      >
                      <form:option value="Thiết kế đồ hoạ"
                        >Thiết kế đồ hoạ</form:option
                      >
                      <form:option value="Mỏng nhẹ">Mỏng nhẹ</form:option>
                      <form:option value="Doanh nhân">Doanh nhân</form:option>
                    </form:select>
                  </div>
                </div>
                <div class="mb-3 w-50">
                  <label for="productFile" class="form-label"
                    >Image Product:</label
                  >
                  <input
                    class="form-control"
                    type="file"
                    id="productFile"
                    name="productFile"
                    accept=".png, .jpg, .jpeg"
                  />
                </div>
                <div>
                  <img
                    src="https://via.placeholder.com/150"
                    alt="product preview"
                    id="productPreview"
                    style="max-height: 250px; display: none"
                  />
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
