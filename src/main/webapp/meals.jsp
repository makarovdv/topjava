<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        .tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
        .tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;}
        .tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
        .tg .tg-4eph{background-color:#f9f9f9}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table class="tg">
    <tr>
        <th width="200">Time</th>
        <th width="250">Description</th>
        <th width="100">Calories</th>
        <th></th>
    </tr>
<c:forEach items="${meals}" var="meal">
    <c:if test="${meal.exceed}">
        <c:set var="trColor" value="red"></c:set>
    </c:if>
    <c:if test="${!meal.exceed}">
        <c:set var="trColor" value="#adff2f"></c:set>
    </c:if>
    <tr bgcolor='${trColor}'>
        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" var="dateTime"/>
        <td>${dateTime}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td><a href="<c:url value='/meals?remove=${meal.id}' />" >Remove</a></td>
    </tr>
</c:forEach>
</body>
</html>
