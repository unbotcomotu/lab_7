package com.example.webapphr1_2023.Daos;


import com.example.webapphr1_2023.Beans.Department;
import com.example.webapphr1_2023.Beans.Employee;
import com.example.webapphr1_2023.Beans.Location;

import java.sql.*;
import java.util.ArrayList;

public class DepartmentDao extends DaoBase {

    public ArrayList<Department> lista() {

        ArrayList<Department> list = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select * from departments d left join employees m on d.manager_id = m.employee_id left join locations l on d.location_id = l.location_id order by d.department_id")) {

            while (rs.next()) {
                Department department = new Department();
                department.setDepartmentId(rs.getInt(1));
                department.setDepartmentName(rs.getString(2));
                Employee manager = null;
                if(rs.getInt("m.employee_id") != 0){
                    manager = new Employee();
                    manager.setEmployeeId(rs.getInt("m.employee_id"));
                    manager.setFirstName(rs.getString("m.first_name"));
                    manager.setLastName(rs.getString("m.last_name"));
                    department.setManager(manager);
                }
                Location location = null;
                if(rs.getInt("l.location_id") != 0){
                    location = new Location();
                    location.setLocationId(rs.getInt("l.location_id"));
                    location.setCity(rs.getString("l.city"));
                    department.setLocation(location);
                }
                list.add(department);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return list;
    }

    public void crearDepartment(Department department){
        String sql = "insert into departments (department_id, department_name, manager_id, location_id) VALUES (?,?,?,?)";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,department.getDepartmentId());
            pstmt.setString(2,department.getDepartmentName());
            if(department.getManager().getEmployeeId() == 0){
                pstmt.setNull(3,Types.INTEGER);
            }else{
                pstmt.setInt(3,department.getManager().getEmployeeId());
            }
            if(department.getLocation().getLocationId() == 0){
                pstmt.setNull(4,Types.INTEGER);
            }else{
                pstmt.setInt(4,department.getLocation().getLocationId());
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Department obtenerDepartmentPorId(int departmentId){
        Department department = new Department();
        String sql = "select * from departments d left join employees m on d.manager_id = m.employee_id left join locations l on d.location_id = l.location_id where d.department_id=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,departmentId);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    department.setDepartmentId(rs.getInt("d.department_id"));
                    department.setDepartmentName(rs.getString("d.department_name"));
                    Employee manager = null;
                    if(rs.getInt("m.employee_id") != 0){
                        manager = new Employee();
                        manager.setEmployeeId(rs.getInt("m.employee_id"));
                        manager.setFirstName(rs.getString("m.first_name"));
                        manager.setLastName(rs.getString("m.last_name"));
                        department.setManager(manager);
                    }
                    Location location = null;
                    if(rs.getInt("l.location_id") != 0){
                        location = new Location();
                        location.setLocationId(rs.getInt("l.location_id"));
                        location.setCity(rs.getString("l.city"));
                        department.setLocation(location);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return department;
    }

    public void editarDepartment(Department department){
        String sql = "update departments set department_name=?, manager_id=?, location_id=? where department_id=?";
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,department.getDepartmentName());
            if(department.getManager().getEmployeeId() == 0){
                pstmt.setNull(2,Types.INTEGER);
            }else{
                pstmt.setInt(2,department.getManager().getEmployeeId());
            }
            if(department.getLocation().getLocationId() == 0){
                pstmt.setNull(3,Types.INTEGER);
            }else{
                pstmt.setInt(3,department.getLocation().getLocationId());
            }
            pstmt.setInt(4,department.getDepartmentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}