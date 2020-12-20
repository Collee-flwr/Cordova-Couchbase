

exports.setup = function() {

    couchbase.createNewDatabase("userlogs",
                           function(response){ document.querySelector('#message').innerHTML =response;},
                           function(error){console.log(error);});



    //establish outputs
    this.output('dataOut');
};

exports.initialize = function() {


profile = {
                "name": "user"

          };


//couchbase.insertDocument("userlogs",profile,function(response){document.querySelector('#message').innerHTML =response;},function(error){console.log(error);});


couchbase.query("userlogs",
                      function(response){document.querySelector('#message').innerHTML =response;}
                    );


this.send('dataOut');
};

