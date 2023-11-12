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
        String sql="insert into locations (location_id,street_address,postal_code,city,state_province,country_id) values (?,?,?,?,?,?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1,getLastLocationID()+100);
            pstmt.setString(2,streetAddress);
            pstmt.setString(3,postalCode);
            pstmt.setString(4,city);
            pstmt.setString(5,stateProvince);
            pstmt.setString(6,countryID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editLocation(String streetAddress, String postalCode, String city, String stateProvince,String countryID,int locationID){
        String sql="update locations set street_address=?,postal_code=?,city=?,state_province=?,country_id=? where location_id=?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1,streetAddress);
            pstmt.setString(2,postalCode);
            pstmt.setString(3,city);
            pstmt.setString(4,stateProvince);
            pstmt.setString(5,countryID);
            pstmt.setInt(6,locationID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLocation(int locationID){
        String sql="update departments set location_id=null where location_id=?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1,locationID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sql="delete from locations where location_id=?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1,locationID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastLocationID(){
        try (ResultSet rs = getConnection().createStatement().executeQuery("select location_id from locations order by location_id desc limit 1")) {
            if(rs.next()){
                return rs.getInt(1);
            }else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
