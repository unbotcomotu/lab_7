package com.example.webapphr1_2023.Controllers;

import com.example.webapphr1_2023.Beans.Employee;
import com.example.webapphr1_2023.Beans.Location;
import com.example.webapphr1_2023.Daos.CountriesDao;
import com.example.webapphr1_2023.Daos.LocationDao;
import com.mysql.cj.PreparedQuery;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LocationServlet", urlPatterns = {"/LocationServlet"})
public class LocationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view;
        LocationDao lD=new LocationDao();
        CountriesDao cD=new CountriesDao();
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        switch (action) {
            case "lista":
                request.setAttribute("locationList",lD.locationList());
                request.setAttribute("alert",request.getParameter("alert"));
                request.getRequestDispatcher("location/list.jsp").forward(request, response);
                break;
            case "agregar":
                request.setAttribute("countriesList",cD.countriesList());
                request.getRequestDispatcher("location/locationCreate.jsp").forward(request, response);
                break;
            case "editar":
                break;
            case "borrar":
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        LocationDao lD=new LocationDao();
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        switch (action){
            case "agregar":
                String streetAddress=request.getParameter("street_address");
                String postalCode=request.getParameter("postal_code");
                String city=request.getParameter("city");
                String stateProvince=request.getParameter("state_province");
                String countryID=request.getParameter("country_id");
                if(streetAddress!=null&&postalCode!=null&&city!=null&&stateProvince!=null&&countryID!=null){
                    if(streetAddress.length()>40||postalCode.length()>12||city.length()>30||stateProvince.length()>25){
                        response.sendRedirect("LocationServlet?alert=2");
                    }else{
                        lD.createLocation(streetAddress,postalCode,city,stateProvince,countryID);
                        response.sendRedirect("LocationServlet");
                    }
                }else{
                    response.sendRedirect("LocationServlet?alert=1");
                }
                break;
        }
    }
}