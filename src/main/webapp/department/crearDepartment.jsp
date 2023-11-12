<%@page import="java.util.ArrayList" %>
<%@page import="com.example.webapphr1_2023.Beans.Employee" %>
<%@page import="com.example.webapphr1_2023.Beans.Location" %>
<%@page import="com.example.webapphr1_2023.Beans.Department" %>
<jsp:useBean id="locationList" type="ArrayList<Location>" scope="request" />
<jsp:useBean id="managerList" type="ArrayList<Employee>" scope="request" />
<jsp:useBean id="ultimoDepartment" type="Department" scope="request" />
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="../includes/bootstrap_header.jsp"/>
        <title>Nuevo Department</title>
    </head>
    <body>
        <div class='container'>
            <div class="row justify-content-center">
                <form method="post" action="DepartmentServlet?action=crear" class="col-md-6 col-lg-6">
                    <h1 class='mb-3'>Nuevo Department</h1>
                    <hr>
                    <div class="mb-3">
                        <label for="department_id">Department ID</label>
                        <input type="hidden" class="form-control form-control-sm" name="department_id" id="department_id" value="<%=ultimoDepartment.getDepartmentId()+10%>">
                    </div>
                    <div class="mb-3">
                        <label for="department_name">Department Name</label>
                        <input type="text" class="form-control form-control-sm" name="department_name" id="department_name">
                    </div>
                    <div class="mb-3">
                        <label for="manager_id">Manager</label>
                        <select name="manager_id" class="form-select" id="manager_id">
                            <option value="0">-- Sin manager --</option>
                            <% for(Employee manager: managerList){ %>
                            <option value="<%=manager.getEmployeeId()%>"> <%=manager.getFullName()%> </option>
                            <% } %>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="location_id">Department</label>
                        <select name="location_id" class="form-select" id="location_id">
                            <option value="0">-- Sin location --</option>
                            <% for(Location location: locationList){ %>
                            <option value="<%=location.getLocationId()%>"> <%=location.getCity()%> </option>
                            <% } %>
                        </select>
                    </div>
                    <a href="<%= request.getContextPath()%>/DepartmentServlet" class="btn btn-danger">Cancelar</a>
                    <input type="submit" value="Guardar" class="btn btn-primary"/>
                </form>
            </div>
        </div>
        <jsp:include page="../includes/bootstrap_footer.jsp"/>
    </body>
</html>
