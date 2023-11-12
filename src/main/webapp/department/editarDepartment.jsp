<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.example.webapphr1_2023.Beans.Employee" %>
<%@page import="com.example.webapphr1_2023.Beans.Location" %>
<%@page import="com.example.webapphr1_2023.Beans.Department" %>
<jsp:useBean id="locationList" type="ArrayList<Location>" scope="request" />
<jsp:useBean id="managerList" type="ArrayList<Employee>" scope="request" />
<jsp:useBean id="department" type="Department" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="../includes/bootstrap_header.jsp"/>
        <title>Editar Department</title>
    </head>
    <body>
        <div class='container mb-4'>
            <div class="row justify-content-center">
                <h1 class='mb-3'>Editar Department</h1>
                <hr>
                <form method="post" action="DepartmentServlet?action=editar" class="col-md-6 col-lg-6">
                    <input type="hidden" name="department_id" value="<%= department.getDepartmentId()%>"/>
                    <div class="mb-3">
                        <label for="department_name">Department Name</label>
                        <input type="text" class="form-control form-control-sm" name="department_name" id="department_name"
                               value="<%= department.getDepartmentName() == null ? "" : department.getDepartmentName()%>">
                    </div>
                    <div class="mb-3">
                        <label for="manager_id">Manager</label>
                        <select name="manager_id" class="form-select" id="manager_id">
                            <option value="0">-- Sin manager --</option>
                            <% for(Employee manager: managerList){ %>
                            <option value="<%=manager.getEmployeeId()%>" <%=manager.getEmployeeId() == department.getManager().getEmployeeId() ? "selected":""%>  > <%=manager.getFullName()%> </option>
                            <% } %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="location_id">Location</label>
                        <select name="location_id" class="form-select" id="location_id">
                            <option value="0">-- Sin location --</option>
                            <% for(Location location: locationList){ %>
                            <option value="<%=location.getLocationId()%>" <%=location.getLocationId() == department.getLocation().getLocationId()?"selected":""%>   > <%=location.getCity()%> </option>
                            <% } %>
                        </select>
                    </div>
                    <a href="<%= request.getContextPath()%>/DepartmentServlet" class="btn btn-danger">Cancelar</a>
                    <input type="submit" value="Actualizar" class="btn btn-primary"/>
                </form>
            </div>
        </div>
        <jsp:include page="../includes/bootstrap_footer.jsp"/>
    </body>
</html>
