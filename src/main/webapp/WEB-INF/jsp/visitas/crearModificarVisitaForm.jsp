<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>
<body>

<script>
    $(function () {
        $("#birthDate").datepicker({ dateFormat: 'yy/mm/dd'});
    });    
</script>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
    <c:choose>
        <c:when test="${pet['new']}">
            <c:set var="method" value="post"/>
        </c:when>
        <c:otherwise>
            <c:set var="method" value="put"/>
        </c:otherwise>
    </c:choose>
 <center><table width="60%">
  <tr>
  <td>
    <h2>
        <c:if test="${pet['new']}">Registar </c:if>
        Visita
    </h2>

    <form:form modelAttribute="pet" method="${method}"
               class="form-horizontal">
        <div class="control-group" id="owner">
            <label class="control-label">Paciente </label>

            <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
        </div>
        <petclinic:inputField label="Motivo" name="name"/>        
        <petclinic:inputField label="Fecha" name="birthDate"/>
        <div class="control-group">
            <label class="control-label">Area que visita</label>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <form:select path="type" items="${types}" size="5"/>
        </div>
        <div >
            <c:choose>
                <c:when test="${pet['new']}">
                    <center><button type="submit" class="btn btn-info">Registrar</button><br><br></center>
                </c:when>
                <c:otherwise>
                   <center> <button type="submit" class="btn btn-info">Editar</button></center>
                </c:otherwise>
            </c:choose>
        </div>
    </form:form>
    <c:if test="${!pet['new']}">
    </c:if>
 </td>
 </tr>
 </table></center><br>
    <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>

</html>
