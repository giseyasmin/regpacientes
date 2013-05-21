<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>
  <center><table width="80%">
  <tr>
  <td>
    <h2>Listado de Pacientes</h2>
    
    <datatables:table id="pacientes" data="${selections}" cdn="true" row="paciente" theme="bootstrap2"
                      cssClass="table table-striped" paginate="false">
        
        <datatables:column title="Nombre" cssStyle="width: 150px;" display="html">
            <spring:url value="/pacientes/{pacienteId}.html" var="pacienteUrl">
                <spring:param name="pacienteId" value="${paciente.id}"/>
            </spring:url>
          		 <c:out value="${paciente.firstName} ${paciente.lastName}"/>
        </datatables:column>
       
        <datatables:column title="Direccion" property="address" cssStyle="width: 200px;"/>
        <datatables:column title="Departamento" property="city"/>
        <datatables:column title="Telefono" property="telephone"/>
        <datatables:column title="Detalles">
        	<a href="${fn:escapeXml(pacienteUrl)}">Ver</a>
        </datatables:column>        
    </datatables:table>    
   
</td>
</tr>
</table><center>
 <jsp:include page="../fragments/footer.jsp"/>
</div>
</body>

</html>
