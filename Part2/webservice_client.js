function callWS(sQueryString, cb){
  console.log("callWS() called");
  $.ajax({

    type: "GET",
    url: "http://127.0.0.1:8080/dronerecon/DroneDataService" + sQueryString,
    dataType: "json",
    success: function(resp){
      console.log("REST service response received: " + JSON.stringify(resp));
      cb(resp);
    },
    error: function(e){
      console.log("REST service failed:" + JSON.stringify(e));
    }
  });
}