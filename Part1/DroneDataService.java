package com.dronerecon.ws;

        import javax.servlet.*;
        import javax.servlet.http.*;
        import java.io.*;
        import java.util.*;
        import java.security.SecureRandom;

/*
 @author Jorge Castillo
 */
public class DroneDataService extends HttpServlet{


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();

        // ##############################
        // 1. Get params passed in.

        // Get the following parameters from the request object and put them into strings:
        // area_id
        String sAreaid = request.getParameter("area_id");
        // tilex
        int iTileX = Integer.parseInt(request.getParameter("tilex"));
        // tiley
        int iTileY = Integer.parseInt(request.getParameter("tiley"));
        // totalcols
        int iTotalCols = Integer.parseInt (request.getParameter("totalcols"));
        // totalrows
        int iTotalRows = Integer.parseInt(request.getParameter("totalrows"));
        // ##############################
		
		//NEW CODE HERE


        // ##############################
        // 2. Default value of beginning direction.

        // Set a string called sDirection to "right".
        String sDirection = "right";
        // ##############################



        // ##############################
        // 3. Calculate next drone move.

        // Determine next tile to move to.
        // Base this on current x and y.
        // Change sDirection if necessary.
        // Drone must serpentine from top left of grid back and forth down.
        // If rows are done, change sDirection to "stop".
        // ##############################

        if(iTileY % 2 == 0) // % means that divides both values and it gives you the remainder.
        {
            // on even row moving right.
            if(iTileX == iTotalCols - 1)
            {
                // at the end of row, so increase y by 1.
                iTileY++;
                sDirection = "left";
            }
            else
            {
                iTileX++;
                sDirection = "right";
            }
        }
        else
            {
                // on odd row moving left.
                if(iTileX == 0)
                {
                    // At the end of row, so increse Y by 1.
                    iTileY++;
                    sDirection = "right";
                }
                else
                {
                    iTileX--;
                    sDirection = "left";
                }
            }
        
        // check if on eow/y beyond grid.
         if(iTileY == iTotalRows)
         {
             sDirection = "stop";
         }
        
        
        
        // ##############################
        // 4. Format & Return JSON string to caller.

        /* Return via out.println() a JSON string like this:
        {"area_id":"[area id from above]", "nextTileX":"[next tile x]", "nextTileY":"[next tile y]", "direction":"[direction string from above]"}
        */
        
        out.println("{" +
                 "\"area_id\":\"" + sAreaid + "\"," +
                 "\"nextTileX\":\"" + iTileX + "\"," +
                 "\"nextTileY\":\"" + iTileY + "\"," +
                 "\"direction\":\"" + sDirection + "\"," +
                "}");
        // ##############################

    }
}
