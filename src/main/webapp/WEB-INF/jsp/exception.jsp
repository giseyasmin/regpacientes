<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<jsp:include page="fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="fragments/bodyHeader.jsp"/>
    <spring:url value="/resources/images/image.jpg" var="petsImage"/>
    <img src="${petsImage}"/>
<center><table>
<tr>
<td>
    <h2>Algo salio mal.</h2>

    <p>${exception.message}</p>

    <!-- Exception: ${exception.message}.
		  	<c:forEach items="${exception.stackTrace}" var="stackTrace"> 
				${stackTrace} 
			</c:forEach>
	  	-->

</td></tr></table></center>
    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>

</html>
