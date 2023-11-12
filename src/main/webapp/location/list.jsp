<%@ page import="com.example.webapphr1_2023.Beans.Location" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean type="java.util.ArrayList<com.example.webapphr1_2023.Beans.Location>" scope="request" id="locationList"/>
<%String alert=(String) request.getAttribute("alert");%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/bootstrap_header.jsp"/>
    <title>Listar Locations</title>
</head>
<body>
<div class='container'>

    <h1 class='mb-3'>Lista de Locations en hr</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%= request.getContextPath()%>">Home</a></li>
            <li class="breadcrumb-item active">Locations</li>
        </ol>
    </nav>
    <a class="btn btn-primary mb-3" href="<%=request.getContextPath()%>/LocationServlet?action=agregar">Crear dirección</a>
    <%if(alert!=null){
        if(alert.equals("1")){%>
    <a style="color: blue">Dirección creada exitosamente</a>
        <%}else if(alert.equals("2")){%>
    <a style="color: blue">Dirección editada exitosamente</a>
        <%}else if(alert.equals("3")){%>
    <a style="color: blue">Dirreción borrada exitosamente</a>
    <%}}%>
    <table class="table">
        <tr>
            <th>Loc ID</th>
            <th>Street Address</th>
            <th>Postal Code</th>
            <th>City</th>
            <th>State Province</th>
            <th>Country id</th>
            <th></th>
            <th></th>
        </tr>
        <%
            for (Location loc : locationList) {
        %>
        <tr>
            <td><%=loc.getLocationId()%>
            </td>
            <td><%=loc.getStreetAddress()%>
            </td>
            <td><%=loc.getPostalCode()%>
            </td>
            <td><%=loc.getCity()%>
            </td>
            <td><%=loc.getStateProvince()%>
            </td>
            <td><%=loc.getCountry().getCountryName()%>
            </td>
            <td>
                <a class="btn btn-primary"
                   href="<%=request.getContextPath()%>/LocationServlet?action=editar&location_id=<%=loc.getLocationId()%>">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <form method="post" action="<%=request.getContextPath()%>/LocationServlet?action=borrar" id="formBorrar">
                    <input type="hidden" name="location_id" value="<%=loc.getLocationId()%>">
                    <a class="btn btn-danger" onclick=enviarForm('formBorrar')>
                        <i class="bi bi-trash3"></i>
                    </a>
                    <script>
                        function enviarForm(formID){
                            document.getElementById(formID).submit();
                        }
                    </script>
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<jsp:include page="../includes/bootstrap_footer.jsp"/>
</body>
</html>


