<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container"> 
    <jsp:include page="../fragments/bodyHeader.jsp"/>
   
    <c:choose>
        <c:when test="${paciente['new']}"><c:set var="method" value="post"/></c:when>
        <c:otherwise><c:set var="method" value="put"/></c:otherwise>
    </c:choose>
 <center><table width="50%">
  <tr>
  <td>
    <h2>
        <c:if test="${paciente['new']}">Nuevo </c:if> Paciente
    </h2>
    <form:form modelAttribute="paciente" method="${method}" class="form-horizontal" id="add-paciente-form">
        <petclinic:inputField label="Nombres" name="firstName"/>
        <petclinic:inputField label="Apellidos" name="lastName"/>
        <petclinic:inputField label="Direccion" name="address"/>
        <petclinic:inputField label="Departamento" name="city"/>
        <petclinic:inputField label="Telefono" name="telephone"/>

        <div >
            <c:choose>
                <c:when test="${paciente['new']}">
                   <center> <button type="submit" class="btn btn-info">Guardar</button></center>
                </c:when>
                <c:otherwise>
                    <center><button type="submit" class="btn btn-info">Actualizar</button></center>
                </c:otherwise>
            </c:choose>
        </div>
    </form:form>
</td>
</tr>
</table><br></center>
    
    <jsp:include page="../fragments/footer.jsp"/>
</div>

</body>

</html>
