package com.example.webapphr1_2023.Daos;

import com.example.webapphr1_2023.Beans.Country;
import com.example.webapphr1_2023.Beans.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountriesDao extends DaoBase{
    public ArrayList<Country> countriesList(){
        ArrayList<Country>countriesList=new ArrayList<>();
        try (ResultSet rs = getConnection().createStatement().executeQuery("select country_id,country_name from countries")) {
            while (rs.next()) {
                Country c=new Country();
                c.setCountryId(rs.getString(1));
                c.setCountryName(rs.getString(2));
                countriesList.add(c);
            }return countriesList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

