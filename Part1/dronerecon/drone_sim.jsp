<html>
  <head>
    
      <%
		int iCols = Integer.parseInt (request.getParameter("colcount"));
		int iRows = Integer.parseInt (request.getParameter("rowcount"));
        // convert those incoming strings to ints. Integer.parseInt ()
      %>

    <style>
      body {
        font-family: "Arial", arial, ans-serif;
      }
    </style>

    <script src="js/jquery.min.js"></script>
    <script src="js/webservice_client.js"></script>
    <script src="js/dronesim.js"></script>

    <script>

      setTiles(<%=iCols%>, <%=iRows%>);

    </script>

  </head>

  <!--<body onload="changeColor();">-->
  <body>

    <br />
    <h2 style="text-align:center;background-color:gray;color:white">DRONE RECON</h2>
    Area ID: <span id="area_id" type="text"><%=request.getParameter("area_id")%></span>
    <br />
    <button onclick="beginDroneSim();">Begin Drone Sim</button>
    <button onclick="stopDroneSim();">Stop Drone Sim</button>

    <br /><br />

    <table style="width:100%;border-style:solid;
      text-align:center;vertical-align:middle;font-weight:bold;font-size:large">

      <%
		for(int y = 0; y < iRows; y++)
		{
          %>
          <tr>
          <%
		 for(int x = 0; x < iCols; x++)
		 {
            %>
            
              <td id="<%="x" + x + "-y" + y%>" style="background-color:white;height:100">
                
                <div id="<%="div-x" + x + "-y" + y%>" style="width: 100px; height: 1px; position: relative;">
                </div>
                <%="X:" + x + ",Y:" + y%>
              </td>

            <%
          }

          %>
          </tr>
          <%
        }
        %>
    </table>
  </body>
</html>