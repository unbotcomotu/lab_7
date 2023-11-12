<%@ page import="com.example.webapphr1_2023.Beans.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean type="java.util.ArrayList<com.example.webapphr1_2023.Beans.Department>" scope="request" id="departmentList"/>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/bootstrap_header.jsp"/>
    <title>Listar Departments</title>
</head>
<body>
<div class="container">

    <h1 class="mb-3">Lista de Departments en hr</h1>
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%= request.getContextPath()%>">Home</a></li>
            <li class="breadcrumb-item active">Departments</li>
        </ol>
    </nav>
    <a class="btn btn-primary mb-3" href="<%=request.getContextPath()%>/DepartmentServlet?action=crear">Crear Department</a>
    <table class="table">
        <tr>
            <th>Dep ID</th>
            <th>Dep Name</th>
            <th>Dep Manager</th>
            <th>Dep Location</th>
            <th></th>
            <th></th>
        </tr>
        <%
            for (Department dep : departmentList) {
        %>
        <tr>
            <td><%=dep.getDepartmentId()%>
            </td>
            <td><%=dep.getDepartmentName()%>
            </td>
            <%if(dep.getManager() != null){%>
            <td><%=dep.getManager().getEmployeeId() == 0 ? "-- Sin manager --" : dep.getManager().getFullName()%>
            </td>
            <%}else{%>
            <td><%="-- Sin manager --"%>
            </td>
            <%}%>
            <%if(dep.getLocation() != null){%>
            <td><%=dep.getLocation().getLocationId() == 0 ? "-- Sin location --" : dep.getLocation().getCity()%>
            </td>
            <%}else{%>
            <td><%="-- Sin location --"%>
            </td>
            <%}%>
            <td>
                <a class="btn btn-primary"
                   href="<%=request.getContextPath()%>/DepartmentServlet?action=editar&id=<%=dep.getDepartmentId()%>">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <form method="post" action="DepartmentServlet?action=borrar">
                    <button type="submit" style="border: 0; background: none; color: inherit">
                        <a class="btn btn-danger">
                            <i class="bi bi-trash3"></i>
                        </a>
                        <input type="hidden" name="id" value="<%=dep.getDepartmentId()%>">
                    </button>
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


