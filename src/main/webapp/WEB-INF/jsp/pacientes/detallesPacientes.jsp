<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
 <center><table width="60%">
  <tr>
  <td>
    <h2>Datos Paciente</h2>

    <table class="table table-striped" style="width:600px;">
        <tr>
            <th>Nombre</th>
            <td><b><c:out value="${paciente.firstName} ${paciente.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Direccion</th>
            <td><c:out value="${paciente.address}"/></td>
        </tr>
        <tr>
            <th>Departamento</th>
            <td><c:out value="${paciente.city}"/></td>
        </tr>
        <tr>
            <th>Telefono</th>
            <td><c:out value="${paciente.telephone}"/></td>
        </tr>
         <tr>
            <td> 
            	<spring:url value="{pacienteId}/modificar.html" var="editUrl">
                    <spring:param name="pacienteId" value="${paciente.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(editUrl)}" class="btn btn-info">Editar Datos</a></td>
            <td>
            	<spring:url value="{pacienteId}/visitas/nuevo.html" var="addUrl">
                    <spring:param name="pacienteId" value="${paciente.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(addUrl)}"  class="btn btn-info">Registrar Visita</a></td>
                
        </tr>
    </table>

    <h2>Visitas</h2>

    <c:forEach var="pet" items="${paciente.pets}">
        <table class="table" style="width:600px;">
            <tr>
                <td valign="top" style="width: 300px;">
                    
                        <b>Motivo:</b>
                        <c:out value="${pet.name}"/><br>
                        <b>Fecha:</b>
                        <joda:format value="${pet.birthDate}" pattern="dd-MM-yyyy"/><br>
                        <b>Area Visitada:</b>
                       <c:out value="${pet.type.name}"/>
                    
                </td>                
                <td>
                    <table class="table-condensed">
                        <thead>
                        <tr>
                                                
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><joda:format value="${visit.date}" pattern="dd-MM-yyyy"/></td>
                                <td><c:out value="${visit.description}"/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td> 
                            	<spring:url value="/pacientes/{pacienteId}/visitas/{petId}/modificar" var="petUrl">
			                        <spring:param name="pacienteId" value="${paciente.id}"/>
			                        <spring:param name="petId" value="${pet.id}"/>
			                    </spring:url>
			                    <a href="${fn:escapeXml(petUrl)}">Modificar</a>
			                </td>
                            <!--td>
			                    <spring:url value="/pacientes/{pacienteId}/visitas/{petId}/visits/new" var="visitUrl">
			                        <spring:param name="pacienteId" value="${paciente.id}"/>
			                        <spring:param name="petId" value="${pet.id}"/>
			                    </spring:url>
			                    <!-- a href="${fn:escapeXml(visitUrl)}">Registrar Visita</a-->
                           
                       	</tr>
                    </table>
                </td>
            </tr>
        </table>
    </c:forEach>
</td>
</tr>
</table></center>
    <jsp:include page="../fragments/footer.jsp"/>

</div>

</body>

</html>
