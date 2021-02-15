

exports.setup = function() {

    couchbase.createNewDatabase("userlogs", function(response){ document.querySelector('#message').innerHTML =response;},function(error){console.log(error);});
    //establish outputs
    this.output('dataOut');

};

exports.initialize = function() {




    //var button = document.getElementById("save_button");
    //button.onclick = function(){
        //var user_name = document.getElementById("user_name").value;
        //document.querySelector('#message').innerHTML = user_name;

        profile = {
                    "name": "user",
                    "age": "27"
                };

        couchbase.insertDocument("userlogs",profile,function(response){document.querySelector('#message').innerHTML =response;},function(error){console.log(error);});
   // url = "http://192.168.1.209:8081/uploadDocument.php";
    //url = "http://192.168.1.209:8081/test.php";

    //couchbase.uploadDocuments(url,function(response){document.querySelector('#message').innerHTML =response;},function(error){console.log(error);});
    couchbase.getAllDocuments("userlogs",function(response){document.querySelector('#message').innerHTML =response;},function(error){console.log(error);});
    //}//





    this.send('dataOut');
};

