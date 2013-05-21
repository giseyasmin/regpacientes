<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html lang="en">

<jsp:include page="fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="fragments/bodyHeader.jsp"/>
   
    <spring:url value="/resources/images/image.jpg" htmlEscape="true" var="petsImage"/>
   	
    <center>
    <h2>Registro de Pacientes</h2>
    <img src="${petsImage}"/></center><br>

    <jsp:include page="fragments/footer.jsp"/>

</div>
</body>

</html>