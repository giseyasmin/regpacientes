<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>

    <center><h2>Integrantes</h2>
<table style="width:80%">
<tr>
<td>
Karla
    <spring:url value="/pacientes.html" var="formUrl"/>
    <form:form modelAttribute="owner" action="${fn:escapeXml(formUrl)}" method="get" class="form-horizontal"
               id="search-owner-form">
        <!-- fieldset>
            <div class="control-group" id="lastName">
                <label class="control-label">Apellido </label>
                <form:input path="lastName" size="30" maxlength="80"/>
                <span class="help-inline"><form:errors path="*"/></span>
            </div>
            <div class="form-actions">
                <button type="submit">Buscar</button>
            </div>
        </fieldset-->
    </form:form>
</td></tr></table></center>
    
    <!-- a href='<spring:url value="/pacientes/new" htmlEscape="true"/>'>Add </a-->

    <jsp:include page="../fragments/footer.jsp"/>

</div>
</body>

</html>
