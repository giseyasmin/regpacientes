<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/resources/images/medi.jpg" var="banner"/>
<img src="${banner}" style="height:190px;width:855px;" class="image"/>

<div class="navbar" style="width: 100%;">
    <div class="navbar-inner">
        <ul class="nav">
            <li style="width: 100px;"><a href="<spring:url value="/" htmlEscape="true" />"><i class=""></i>
                Inicio</a></li>
            <li style="width: 130px;"><a href="<spring:url value="/pacientes.html" htmlEscape="true" />"><i
                    class=""></i>Pacientes</a></li>                
            <li style="width: 130px;"><a href="<spring:url value="/pacientes/nuevo.html" htmlEscape="true" />"><i
                    class=""></i>Registrar</a></li>
             <!-- li style="width: 130px;"><a href="<spring:url value="/pacientes/integrantes.html" htmlEscape="true" />"><i
                    class=""></i>Integrantes</a></li-->                   
            
        </ul>
    </div>
</div>
	
