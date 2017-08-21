<%--
  Created by IntelliJ IDEA.
  User: Delwar
  Date: 8/14/2017
  Time: 11:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <link href="/resources/css/style2.css" rel="stylesheet">
</head>
<body background="${pageContext.request.contextPath}/resources/images/background2.jpg">

<h2>Below is Our Selection of Coffee</h2>

<table border="1" align="center">
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Quantity</th>
        <th>Price</th>
    </tr>

    <%--JSTL tag. Reads for each item in my list--%>
    <c:forEach items="${iList}" var="item">

        <tr>
                <%--Gets the values for each column--%>
            <td>${item.name}</td>
            <td>${item.description}</td>
            <td>${item.quantity}</td>
            <td>${item.price}</td>
        </tr>

    </c:forEach>
</table>

</body>
</html>
