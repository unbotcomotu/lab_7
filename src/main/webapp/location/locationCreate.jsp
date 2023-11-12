<%@ page import="com.example.webapphr1_2023.Beans.Country" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: unbotcomotu
  Date: 11/12/2023
  Time: 11:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%ArrayList<Country>countriesList=(ArrayList<Country>) request.getAttribute("countriesList");
String alert=(String) request.getAttribute("alert");%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/bootstrap_header.jsp"/>
    <title>Nueva dirección</title>
</head>
<body>
<div class='container'>
    <div class="row justify-content-center">
        <form method="POST" action="LocationServlet?action=agregar" class="col-md-6 col-lg-6">
            <h1 class='mb-3'>Nueva dirección</h1>
            <hr>
            <div class="mb-3">
                <label for="street_address">Dirección de calle</label>
                <input type="text" class="form-control form-control-sm" name="street_address" id="street_address">
            </div>
            <div class="mb-3">
                <label for="postal_code">Código postal</label>
                <input type="text" class="form-control form-control-sm" name="postal_code" id="postal_code">
            </div>
            <div class="mb-3">
                <label for="city">Ciudad</label>
                <input type="text" class="form-control form-control-sm" name="city" id="city">
            </div>
            <div class="mb-3">
                <label for="state_province">Provincia</label>
                <input type="text" class="form-control form-control-sm" name="state_province" id="state_province">
            </div>
            <div class="mb-3">
                <label for="country_id">País</label>
                <select name="country_id" class="form-select" id="country_id">
                    <% for(Country c: countriesList){ %>
                    <option value="<%=c.getCountryId()%>"> <%=c.getCountryName()%> </option>
                    <% } %>
                </select>
            </div>
            <a href="<%= request.getContextPath()%>/LocationServlet" class="btn btn-danger">Cancelar</a>
            <input type="submit" value="Guardar" class="btn btn-primary"/>
            <%if(alert!=null){
                if(alert.equals("1")){%>
            <a style="color: red">Todos los campos tienen que estar llenos.</a>
            <%}else if(alert.equals("2")){%>
            <a style="color: red">No sobrepase la cantidad límite de caracteres por campo.</a>
            <%}}%>
        </form>
    </div>
</div>
<jsp:include page="../includes/bootstrap_footer.jsp"/>
</body>
</html>