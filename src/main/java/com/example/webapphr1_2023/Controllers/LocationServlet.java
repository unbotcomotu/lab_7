package com.example.webapphr1_2023.Controllers;

import com.example.webapphr1_2023.Beans.Employee;
import com.example.webapphr1_2023.Beans.Location;
import com.example.webapphr1_2023.Daos.CountriesDao;
import com.example.webapphr1_2023.Daos.LocationDao;
import com.mysql.cj.PreparedQuery;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
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
                request.setAttribute("locationList", lD.locationList());
                request.setAttribute("alert", request.getParameter("alert"));
                request.getRequestDispatcher("location/list.jsp").forward(request, response);
                break;
            case "agregar":
                request.setAttribute("countriesList", cD.countriesList());
                request.setAttribute("alert", request.getParameter("alert"));
                request.getRequestDispatcher("location/locationCreate.jsp").forward(request, response);
                break;
            case "editar":
                request.setAttribute("countriesList", cD.countriesList());
                request.setAttribute("getLocation", lD.getLocation(Integer.parseInt(request.getParameter("location_id"))));
                request.setAttribute("alert", request.getParameter("alert"));
                request.getRequestDispatcher("location/locationEdit.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocationDao lD=new LocationDao();
        int locationID;
        String streetAddress;
        String postalCode;
        String city;
        String stateProvince;
        String countryID;
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        switch (action){
            case "agregar":
                streetAddress=request.getParameter("street_address");
                postalCode=request.getParameter("postal_code");
                city=request.getParameter("city");
                stateProvince=request.getParameter("state_province");
                countryID=request.getParameter("country_id");
                if(streetAddress!=""&&postalCode!=""&&city!=""&&stateProvince!=""&&countryID!=""){
                    if(streetAddress.length()>40||postalCode.length()>12||city.length()>30||stateProvince.length()>25){
                        response.sendRedirect("LocationServlet?action=agregar&alert=2");
                    }else{
                        lD.createLocation(streetAddress,postalCode,city,stateProvince,countryID);
                        response.sendRedirect("LocationServlet?alert=1");
                    }
                }else{
                    response.sendRedirect("LocationServlet?action=agregar&alert=1");
                }
                break;
            case "editar":
                locationID=Integer.parseInt(request.getParameter("location_id"));
                streetAddress=request.getParameter("street_address");
                postalCode=request.getParameter("postal_code");
                city=request.getParameter("city");
                stateProvince=request.getParameter("state_province");
                countryID=request.getParameter("country_id");
                if(streetAddress!=""&&postalCode!=""&&city!=""&&stateProvince!=""&&countryID!=""){
                    if(streetAddress.length()>40||postalCode.length()>12||city.length()>30||stateProvince.length()>25){
                        response.sendRedirect("LocationServlet?action=editar&alert=2&location_id="+locationID);
                    }else{
                        lD.editLocation(streetAddress,postalCode,city,stateProvince,countryID,locationID);
                        response.sendRedirect("LocationServlet?alert=2");
                    }
                }else{
                    response.sendRedirect("LocationServlet?action=editar&alert=1&location_id="+locationID);
                }
                break;
            case "borrar":
                locationID=Integer.parseInt(request.getParameter("location_id"));
                lD.deleteLocation(locationID);
                response.sendRedirect("LocationServlet?alert=3");
                break;
        }
    }
}