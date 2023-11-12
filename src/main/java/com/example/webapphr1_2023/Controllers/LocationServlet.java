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
                request.setAttribute("alert",request.getParameter("alert"));
                request.getRequestDispatcher("location/locationCreate.jsp").forward(request, response);
                break;
            case "editar":
                request.setAttribute("countriesList",cD.countriesList());
                request.setAttribute("getLocation",lD.getLocation(Integer.parseInt(request.getParameter("location_id"))));
                request.setAttribute("alert",request.getParameter("alert"));
                request.getRequestDispatcher("location/locationEdit.jsp").forward(request, response);
                break;
            case "borrar":
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocationDao lD=new LocationDao();
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        switch (action){
            case "agregar":
                String streetAddress=request.getParameter("street_address");
                String postalCode=request.getParameter("postal_code");
                String city=request.getParameter("city");
                String stateProvince=request.getParameter("state_province");
                String countryID=request.getParameter("country_id");
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
                int locationID=Integer.parseInt(request.getParameter("location_id"));
                String streetAddress2=request.getParameter("street_address");
                String postalCode2=request.getParameter("postal_code");
                String city2=request.getParameter("city");
                String stateProvince2=request.getParameter("state_province");
                String countryID2=request.getParameter("country_id");
                if(streetAddress2!=""&&postalCode2!=""&&city2!=""&&stateProvince2!=""&&countryID2!=""){
                    if(streetAddress2.length()>40||postalCode2.length()>12||city2.length()>30||stateProvince2.length()>25){
                        response.sendRedirect("LocationServlet?action=editar&alert=2&id_location="+locationID);
                    }else{
                        lD.editLocation(streetAddress2,postalCode2,city2,stateProvince2,countryID2,locationID);
                        response.sendRedirect("LocationServlet?alert=2");
                    }
                }else{
                    response.sendRedirect("LocationServlet?action=editar&alert=1&id_location="+locationID);
                }
                break;
        }
    }
}