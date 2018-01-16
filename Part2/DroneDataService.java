/*
Jorge Castillo
*/
package com.dronerecon.ws;

        import javax.servlet.*;
        import javax.servlet.http.*;
        import java.io.*;
        import java.util.*;
        import java.net.URL;


public class DroneDataService extends HttpServlet{


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
	

        // ##############################
        // 1. Get params passed in.
		String area_id = request.getParameter("area_id");
		
        int iTileX = Integer.parseInt(request.getParameter("tilex"));
        int iTileY = Integer.parseInt(request.getParameter("tiley"));

        int iTotalCols = Integer.parseInt(request.getParameter("totalcols"));
        int iTotalRows = Integer.parseInt(request.getParameter("totalrows"));
        String r = request.getParameter("r");
        String g  = request.getParameter("g");
		
        // Get the following parameters from the request object and put them into strings:
        // area_id
        // tilex
        // tiley
        // totalcols
        // totalrows
        // ##############################



        // ##############################
        // 2. Default value of beginning direction.
			String sDirection = "right";
        // Set a string called sDirection to "right".
        // ##############################
  String sServiceReturnJson = "";

    	try {

            // Call weather API.
            URL url = new URL("http://127.0.0.1:8080/dronereconportal/PortalDBService?area_id="
                    +area_id+"&tilex="+iTileX+"&tiley="+iTileY+"&r="+r+"&g="+g+"");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                    sServiceReturnJson += strTemp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("An error occurred in callDroneService() in DBManager: " + ex.toString());
        }


        // ##############################
        // 3. Calculate next drone move.
		 // Determine next tile to move to.
        if(iTileY % 2 == 0) {

            // Moving right currently.
            if(iTileX == iTotalCols - 1){
                iTileY++;
                sDirection = "left";
            }
            else{
                iTileX++;
                sDirection = "right";
            }
        }
        else{

            // Moving left currently.
            if(iTileX == 0){
                iTileY++;
                sDirection = "right";
            }
            else{
                iTileX--;
                sDirection = "left";
            }
        }

        if(iTileY == iTotalRows){
            sDirection = "stop";
        }

        // Response with confirmation of data saved.
        out.println(
            "{\"area_id\":\"" +area_id +  "\"" +
                ",\"nextTileX\":\"" + iTileX + "\"" +
                ",\"nextTileY\":\"" + iTileY + "\"" +
                ",\"direction\":\"" + sDirection + "\"" +
            "}");

    }
}

