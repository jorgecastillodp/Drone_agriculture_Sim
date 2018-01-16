
/*
Jorge Castillo
*/
package com.dronerecon.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.ArrayList;

public class DBManager {

    public String DBLocation = "dronedata.sqlite"; 
    
    public String DBLocation = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\dronereconportal\\db\\dronedata.sqlite";

    // Create connection with DB.
  
    private Connection connect() {

        // SQLite connection string
        String url = "jdbc:sqlite:" + DBLocation;
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");

            // Connect to DB.
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }


    // Insert DB record into AreaGridTiles table.
    
    public void insertAreaGridTile(String sAreaID, int iX, int iY, int iR, int iG){

        Connection c = connect();
        Statement stmt = null;

        try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO AreaGridTiles (area_id,x,y,r,g,timestamp) " +
                    "VALUES ('" + sAreaID + "'," + iX + "," + iY + "," + iR + "," + iG + ",datetime());";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            e.printStackTrace();
        }
    }


    // Select DB records from AreaGridTiles table.
    
    public ArrayList<AreaGridTile> readAreaGridTiles(String sAreaID){

        Connection c = connect();
        Statement stmt = null;

        // Used to hold tiles retrieved from DB.
        ArrayList<AreaGridTile> lstTiles = new ArrayList<>();

        try {
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM AreaGridTiles WHERE area_id = '" + sAreaID + "';" );

            while ( rs.next() ) {
                sAreaID = rs.getString("area_id");
                int iX = rs.getInt("x");
                int iY = rs.getInt("y");
                int iR = rs.getInt("r");
                int iG = rs.getInt("g");
                String sTimestamp = rs.getString("timestamp");

                AreaGridTile oTile = new AreaGridTile();
                oTile.areaID = sAreaID;
                oTile.x = iX;
                oTile.y = iY;
                oTile.r = iR;
                oTile.g = iG;
                oTile.timestamp = sTimestamp;

                lstTiles.add(oTile);

            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return lstTiles;
    }

    //  testing in an IDE.
    public static void main(String[] args){

        DBManager oDBManager = new DBManager();
        oDBManager.insertAreaGridTile("abc123",10,10,243,109);
        System.out.println("Record inserted.");

        ArrayList<AreaGridTile> oTiles = oDBManager.readAreaGridTiles("23abc");

        for(AreaGridTile oTile: oTiles){
            System.out.println("tile: " + oTile.x + "," + oTile.y);
        }
    }
}