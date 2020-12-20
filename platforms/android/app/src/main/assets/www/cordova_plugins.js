cordova.define('cordova/plugin_list', function(require, exports, module) {
  module.exports = [
    {
      "id": "cordova-plugin-couchbase.Couchbase",
      "file": "plugins/cordova-plugin-couchbase/www/Couchbase.js",
      "pluginId": "cordova-plugin-couchbase",
      "clobbers": [
        "couchbase"
      ]
    }
  ];
  module.exports.metadata = {
    "cordova-plugin-whitelist": "1.3.4",
    "cordova-plugin-couchbase": "0.0.1"
  };
});