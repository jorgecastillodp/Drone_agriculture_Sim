var bContinueDroneSim = false;

var iUpdateIntervalMillis = 1000;

var nIntervId;

var sAreaID;
var iCurX;
var iCurY;
var sRGB;

var iCols;
var iRows;


function setTiles(iColsNew, iRowsNew){
  
      iCols = iColsNew;
      iRows = iRowsNew;
}

function beginDroneSim(){
  
  bContinueDroneSim = true;
  sAreaID = $( "#area_id").val();
  iCurX = 0;
  iCurY = 0;
  sCurrentDroneDiv = "div-x" + iCurX + "-y" + iCurY

  // Set drone in first tile.
  moveDrone(iCurX, iCurY, "right");

  // Begin sim traversal of grid.
  nIntervId = setInterval(getNextTile, iUpdateIntervalMillis);
}

function stopDroneSim(){
  bContinueDroneSim = false;
  clearInterval(nIntervId);
  iCurX = 0;
  iCurY = 0;
}

function getNextTile(){

  if(bContinueDroneSim){

    // Generate RGB
    var r = "" + Math.floor((Math.random() * 256));
    var g = "" + Math.floor((Math.random() * 256));
    var b = "" + 0;

    sRGB = r + "," + g + "," + b;

    // Call web service to get results for next tile.
    callWS("?area_id=" + sAreaID + "&tilex=" + iCurX + "&tiley=" + iCurY + 
      "&r=" + r + "&g=" + g + "&b=" + b +
      "&totalcols=" + iCols + "&totalrows=" + iRows, updateTile);

  }
}

function updateTile(oResponse){

  // Remove pic from this tile.
  $( "#div-x" + iCurX + "-y" + iCurY).remove();

  // Then change text of tile to reflect RGB color values.
  $( "#x" + iCurX + "-y" + iCurY).html(
    iCurX + ":" + iCurY + "<br />(" + sRGB + ")"
  );

  // Next change actual style of tile to reflect RGB color.
  $( "#x" + iCurX + "-y" + iCurY).css( "background-color", "rgb(" + sRGB + ")" );

  // Set next current tile.
  iCurX = oResponse.nextTileX;
  iCurY = oResponse.nextTileY;

  if(oResponse.direction == "stop"){
    stopDroneSim();
  }
  else{
    moveDrone(iCurX, iCurY, oResponse.direction);
  }
}


function moveDrone(x, y, sDirection){

    sCssFlipImage = "";

    if(sDirection == "right"){
      sCssFlipImage = "transform: scale(-1,1);";  
    }

    // Change next tile to drone pic.
    $( "#x" + x + "-y" + y).html(
      "<div id='div-x" + x + "-y" + y + "' style='height:100%;width:100%;position: relative;'>" +
        "<img src='images/drone2.jpg' style='position: absolute; top: 0; left: 0; z-index: 2; width:100%;height:100%;" +
          sCssFlipImage + "'/>" +
      "</div>"
    );
}
