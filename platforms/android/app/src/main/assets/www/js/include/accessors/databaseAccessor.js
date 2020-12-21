

exports.setup = function() {

    couchbase.createNewDatabase("userlogs", function(response){ document.querySelector('#message').innerHTML =response;},function(error){console.log(error);});
    //establish outputs
    this.output('dataOut');

};

exports.initialize = function() {

       profile = {
                   "name": "user"
                 };


    var button = document.getElementById("save_button");
    button.onclick = function(){
        var user_name = document.getElementById("user_name").value;
        document.querySelector('#message').innerHTML = user_name;
    }

    //couchbase.insertDocument("userlogs",profile,function(response){document.querySelector('#message').innerHTML =response;},function(error){console.log(error);});

    //couchbase.query("userlogs",function(response){document.querySelector('#message').innerHTML =response;});

    this.send('dataOut');
};

