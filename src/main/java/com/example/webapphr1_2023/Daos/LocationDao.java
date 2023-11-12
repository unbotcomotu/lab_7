package com.example.webapphr1_2023.Daos;

import com.example.webapphr1_2023.Beans.Department;
import com.example.webapphr1_2023.Beans.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class LocationDao extends DaoBase{
    public ArrayList<Location> locationList(){
        ArrayList<Location>locationList=new ArrayList<>();
        try (ResultSet rs = getConnection().createStatement().executeQuery("select l.location_id,l.street_address,l.postal_code,l.city,l.state_province,c.country_name from locations l inner join countries c on l.country_id=c.country_id")) {
            while (rs.next()) {
                Location l=new Location();
                l.setLocationId(rs.getInt(1));
                l.setStreetAddress(rs.getString(2));
                l.setPostalCode(rs.getString(3));
                l.setCity(rs.getString(4));
                l.setStateProvince(rs.getString(5));
                l.getCountry().setCountryName(rs.getString(6));
                locationList.add(l);
            }return locationList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Location getLocation(int locationID){
        Location l=new Location();
        String sql="select l.location_id,l.street_address,l.postal_code,l.city,l.state_province,c.country_name from locations l inner join countries c on l.country_id=c.country_id where l.location_id=?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1,locationID);
            try(ResultSet rs=pstmt.executeQuery()){
                if (rs.next()) {
                    l.setLocationId(rs.getInt(1));
                    l.setStreetAddress(rs.getString(2));
                    l.setPostalCode(rs.getString(3));
                    l.setCity(rs.getString(4));
                    l.setStateProvince(rs.getString(5));
                    l.getCountry().setCountryName(rs.getString(6));
                }return l;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createLocation(String streetAddress, String postalCode, String city, String stateProvince,String countryID){
        String sql="insert into locations (street_address,postal_code,city,state_province,country_id) values (?,?,?,?,?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1,streetAddress);
            pstmt.setString(2,postalCode);
            pstmt.setString(3,city);
            pstmt.setString(4,stateProvince);
            pstmt.setString(5,countryID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
