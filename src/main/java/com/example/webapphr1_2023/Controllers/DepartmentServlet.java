package com.example.webapphr1_2023.Controllers;

import com.example.webapphr1_2023.Beans.Department;
import com.example.webapphr1_2023.Beans.Employee;
import com.example.webapphr1_2023.Beans.Location;
import com.example.webapphr1_2023.Daos.DepartmentDao;
import com.example.webapphr1_2023.Daos.EmployeeDao;
import com.example.webapphr1_2023.Daos.JobDao;
import com.example.webapphr1_2023.Daos.LocationDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DepartmentServlet", urlPatterns = {"/DepartmentServlet"})
public class DepartmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "lista" : req.getParameter("action");

        RequestDispatcher view;
        DepartmentDao departmentDao = new DepartmentDao();
        EmployeeDao employeeDao = new EmployeeDao();
        LocationDao locationDao = new LocationDao();

        switch (action){
            case "lista":
                req.setAttribute("departmentList", departmentDao.lista());
                view = req.getRequestDispatcher("department/list.jsp");
                view.forward(req, resp);
                break;
            case "crear":
                req.setAttribute("managerList",employeeDao.listarEmpleados());
                req.setAttribute("locationList",locationDao.locationList());
                req.setAttribute("ultimoDepartment",departmentDao.lista().get(departmentDao.lista().size()-1));
                req.getRequestDispatcher("department/crearDepartment.jsp").forward(req,resp);
                break;
            case "editar":
                break;
            case "borrar":
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "lista" : req.getParameter("action");
        DepartmentDao departmentDao = new DepartmentDao();
        EmployeeDao employeeDao = new EmployeeDao();
        LocationDao locationDao = new LocationDao();
        switch (action){
            case "crear":
                Department department = new Department();
                department.setDepartmentId(Integer.parseInt(req.getParameter("department_id")));
                boolean isAllValid = true;

                if(req.getParameter("department_name").length() > 30 || req.getParameter("department_name").isEmpty()){
                    isAllValid = false;
                }
                department.setDepartmentName(req.getParameter("department_name"));
                Employee manager = new Employee();
                manager.setEmployeeId(Integer.parseInt(req.getParameter("manager_id")));
                department.setManager(manager);
                Location location = new Location();
                location.setLocationId(Integer.parseInt(req.getParameter("location_id")));
                department.setLocation(location);
                if(isAllValid){
                    departmentDao.crearDepartment(department);
                    resp.sendRedirect("DepartmentServlet");
                }else{
                    req.setAttribute("managerList",employeeDao.listarEmpleados());
                    req.setAttribute("locationList",locationDao.locationList());
                    req.setAttribute("ultimoDepartment",departmentDao.lista().get(departmentDao.lista().size()-1));
                    req.getRequestDispatcher("department/crearDepartment.jsp").forward(req,resp);
                }
                break;
            case "editar":

                break;
        }
    }
}
