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
                req.setAttribute("managerList",employeeDao.listarEmpleados());
                req.setAttribute("locationList",locationDao.locationList());
                req.setAttribute("department",departmentDao.obtenerDepartmentPorId(Integer.parseInt(req.getParameter("id"))));
                req.getRequestDispatcher("department/editarDepartment.jsp").forward(req,resp);
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
                    resp.sendRedirect(req.getContextPath()+"/DepartmentServlet");
                }else{
                    req.setAttribute("managerList",employeeDao.listarEmpleados());
                    req.setAttribute("locationList",locationDao.locationList());
                    req.setAttribute("ultimoDepartment",departmentDao.lista().get(departmentDao.lista().size()-1));
                    req.getRequestDispatcher("department/crearDepartment.jsp").forward(req,resp);
                }
                break;
            case "editar":
                Department department2 = new Department();
                department2.setDepartmentId(Integer.parseInt(req.getParameter("department_id")));
                boolean isAllValid2 = true;

                if(req.getParameter("department_name").length() > 30 || req.getParameter("department_name").isEmpty()){
                    isAllValid2 = false;
                }
                department2.setDepartmentName(req.getParameter("department_name"));
                Employee manager2 = new Employee();
                manager2.setEmployeeId(Integer.parseInt(req.getParameter("manager_id")));
                department2.setManager(manager2);
                Location location2 = new Location();
                location2.setLocationId(Integer.parseInt(req.getParameter("location_id")));
                department2.setLocation(location2);
                if(isAllValid2){
                    departmentDao.editarDepartment(department2);
                    resp.sendRedirect(req.getContextPath()+"/DepartmentServlet");
                }else{
                    req.setAttribute("managerList",employeeDao.listarEmpleados());
                    req.setAttribute("locationList",locationDao.locationList());
                    req.setAttribute("department",departmentDao.obtenerDepartmentPorId(Integer.parseInt(req.getParameter("department_id"))));
                    req.getRequestDispatcher("department/editarDepartment.jsp").forward(req,resp);
                }
                break;
            case "borrar":
                departmentDao.borrarDepartment(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect(req.getContextPath()+"/DepartmentServlet");
                break;
        }
    }
}
