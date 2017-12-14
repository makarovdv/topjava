<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3>
        <c:if test="${meal.id==null}"><spring:message code="meal.create"/></c:if>
        <c:if test="${meal.id!=null}"><spring:message code="meal.edit"/></c:if>
    </h3>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="<c:url value='/meals' />">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.date"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit" class="green">
            <img width="20" height="20" src="<c:url value='/resources/icons/ok.png' />" />
        </button>
        <button onclick="window.history.back()" type="button" class="red">
            <img width="20" height="20" src="<c:url value='/resources/icons/cancel.png' />" />
        </button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
