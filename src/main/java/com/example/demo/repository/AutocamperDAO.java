package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AutocamperDAO implements CRUDRepository<AutocamperDTO>{
    private Connection conn;

    public AutocamperDAO(Connection conn){
        this.conn = conn;
    }


    public AutocamperDTO read(int id){
        AutocamperDTO objectToReturn = new AutocamperDTO();
        try {
            //Da autocamper info findes i både autocampers og autocampers_types, så inner joines de på brand og model,
            // da disse er FK i autocampers og PK i autocamper_types
            PreparedStatement getSingleAutocamper = conn.prepareStatement("select *\n" +
                    "from autocampers\n" +
                    "inner join autocamper_types\n" +
                    "on autocampers.autocamper_type_brand = autocamper_types.brand\n" +
                    "       and autocampers.autocamper_type_model = autocamper_types.model\n" +
                    "WHERE id = ?");
            getSingleAutocamper.setInt(1,id);
            ResultSet singleAutocamper = getSingleAutocamper.executeQuery();

            while(singleAutocamper.next()){
                objectToReturn.setBrand(singleAutocamper.getString("autocamper_type_brand"));
                objectToReturn.setModel(singleAutocamper.getString("autocamper_type_model"));
                objectToReturn.setYear(singleAutocamper.getInt("year"));
                objectToReturn.setPriceDay(singleAutocamper.getInt("price_day"));
                objectToReturn.setBeds(singleAutocamper.getInt("beds"));
                objectToReturn.setSeats(singleAutocamper.getInt("seats"));
                objectToReturn.setDescription(singleAutocamper.getString("description"));
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return objectToReturn;
    }

    @Override
    //TODO Atm. kan der ikke oprettes nye autocamper_types. Herudover kan der oprettes autocampere uden tilhørende type
    public void create(AutocamperDTO autocamperDTO) {
        try {
            PreparedStatement statementToInsert = conn.prepareStatement("INSERT INTO autocampers " + "(autocamper_type_brand, autocamper_type_model, year, price_day)" + " VALUES (?,?,?,?)");
            statementToInsert.setString(1, autocamperDTO.getBrand());
            statementToInsert.setString(2, autocamperDTO.getModel());
            statementToInsert.setInt(3, autocamperDTO.getYear());
            statementToInsert.setInt(4, autocamperDTO.getPriceDay());
            //Print for testing
            System.out.println(statementToInsert);
            statementToInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    //TODO atm bliver der ikke lavet noget med autocamper_types. Det er nok simpelt nok at fikse, men jeg er for træt til lige at fixe det
    public void update(AutocamperDTO autocamperDTO) {
        try{
            PreparedStatement statementToInsert= conn.prepareStatement("UPDATE autocampers SET " +
                    "autocamper_type_brand = ?, " +
                    "autocamper_type_model = ?, " +
                    "year = ?, " +
                    "price_day = ? " +
                    "WHERE id = ?");

            statementToInsert.setString(1, autocamperDTO.getBrand());
            statementToInsert.setString(2, autocamperDTO.getModel());
            statementToInsert.setInt(3, autocamperDTO.getYear());
            statementToInsert.setInt(4, autocamperDTO.getPriceDay());
            statementToInsert.setInt(5, autocamperDTO.getId());

            statementToInsert.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try{
            PreparedStatement statemenToExecute = conn.prepareStatement("DELETE FROM autocampers WHERE id = ?");
            statemenToExecute.setInt(1,id);
            statemenToExecute.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Collection<AutocamperDTO> readAll() {
        List<AutocamperDTO> autocamperList = new ArrayList<>();

        try{
            PreparedStatement statementToQuery = conn.prepareStatement("select *\n" +
                    "from autocampers\n" +
                    "natural join autocamper_types\n" +
                    "where autocampers.autocamper_type_brand = autocamper_types.brand and autocampers.autocamper_type_model = autocamper_types.model\n" +
                    "ORDER BY id\n");
            ResultSet rs = statementToQuery.executeQuery();

            while(rs.next()){
                AutocamperDTO autocamper = new AutocamperDTO();
                autocamper.setId(rs.getInt(1));
                autocamper.setBrand(rs.getString(2));
                autocamper.setModel(rs.getString(3));
                autocamper.setYear(rs.getInt(4));
                autocamper.setPriceDay(rs.getInt(5));
                autocamper.setBeds(rs.getInt(8));
                autocamper.setSeats(rs.getInt(9));
                autocamper.setDescription(rs.getString(10));

                autocamperList.add(autocamper);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return autocamperList;
    }

    public Map<Integer,AutocamperDTO> readAllAsMap() {
        Map<Integer, AutocamperDTO> autocamperMap = new TreeMap<>();

        try {
            PreparedStatement statementToQuery = conn.prepareStatement("select *\n" +
                    "from autocampers\n" +
                    "natural join autocamper_types\n" +
                    "where autocampers.autocamper_type_brand = autocamper_types.brand and autocampers.autocamper_type_model = autocamper_types.model\n" +
                    "ORDER BY id\n");
            ResultSet rs = statementToQuery.executeQuery();

            while(rs.next()) {
                AutocamperDTO autocamper = new AutocamperDTO();
                autocamper.setId(rs.getInt(1));
                autocamper.setBrand(rs.getString(2));
                autocamper.setModel(rs.getString(3));
                autocamper.setYear(rs.getInt(4));
                autocamper.setPriceDay(rs.getInt(5));
                autocamper.setBeds(rs.getInt(8));
                autocamper.setSeats(rs.getInt(9));
                autocamper.setDescription(rs.getString(10));

                autocamperMap.put(autocamper.getId(), autocamper);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return autocamperMap;
    }
}
