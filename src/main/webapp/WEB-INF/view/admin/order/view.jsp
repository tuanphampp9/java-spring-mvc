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
    <title>Detail order - Tuấn Phạm IT</title>
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
                <a href="/admin/product">Order</a>
              </li>
              <li class="breadcrumb-item active">View detail</li>
            </ol>
            <div class="container mt-5">
              <div class="d-flex justify-content-between align-items-center">
                <h2>Order detail with id =${order.id}</h2>
              </div>
              <hr />
              <table class="table table-bordered">
                <thead>
                  <tr>
                    <th scope="col">Sản phẩm</th>
                    <th scope="col">Tên</th>
                    <th scope="col">Giá</th>
                    <th scope="col">Số lượng</th>
                    <th scope="col">Thành tiền</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listOrderDetails}" var="orderDetail">
                    <tr>
                      <td>
                        <img
                          style="
                            width: 100px;
                            height: 100px;
                            border-radius: 50%;
                            overflow: hidden;
                          "
                          src="/images/product/${orderDetail.product.image}"
                        />
                      </td>
                      <td>
                        <a href="/product/${orderDetail.product.id}"
                          >${orderDetail.product.name}</a
                        >
                      </td>
                      <td>
                        <fmt:formatNumber
                          value="${orderDetail.price}"
                          type="number"
                        />
                        đ
                      </td>
                      <td>${orderDetail.quantity}</td>
                      <td>
                        <fmt:formatNumber
                          value="${orderDetail.price * orderDetail.quantity}"
                          type="number"
                        />
                        đ
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              <a href="/admin/order" class="btn btn-success mt-2">Back</a>
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
