arearesults
<html>
<form>
<table>

<%@ page import ="com.dronerecon.ws.AreaGrilTile"%>
<%@page import = "com.dronerecon.ws.DBManager"%>
<%@page import = "java.util.ArrayList" %>
<% DBManager oDBManager = new DBManager();
	oDBManager.DBLocation = System.getProperty("catalina.base") +
		"\\webapps\\dronereconportal\\db\\" + oDBManager.DBLocation;
		oDBManager.readAreaGridTiles(request.getParameter("area_id"))%>
		<%
		ArrayList<AreaGrilTile> obj = new ArrayList<>();
		
		int itR = 0;
		int xrHigh = 0;
		int yrHigh = 0;
        for(AreaGrilTile objTile : obj)
		{
			if(objTile.r > itR)
			{
				xrHigh = objTile.x;
				yrHigh = objTile.y;
			}
		}
			
		%>
		<table>
		<form>
		</html>