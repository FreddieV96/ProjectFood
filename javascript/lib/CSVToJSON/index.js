//Import
var FileReader = require('filereader')
var File = require('file-api').File

/**
 * Convert a CSV file to JSON structure.
 * @param {string} file The path of the file to read from CSV to JSON.
 * @param {function} callBack The function to run once the file has been read.
 */
module.exports = function(file, callBack) {

    var reader = new FileReader();
    var file = new File({path : file, type: "text/plain"})
    var result = [];
    reader.readAsText(file, 'latin1');
    reader.onload = function() {
        if(reader.result.includes('"')) {
            console.error('CSV File contains " please delete for parser to work.');
        }
        var listOfRecords = reader.result.split("\n");
        var attributeNames = listOfRecords[0].split(";")
        for(var i = 1; i < listOfRecords.length; i++) {
            var record = listOfRecords[i];
            var recordAttributes = record.split(";");
            var obj = "{";
            for(var j = 0; j < recordAttributes.length; j++) {
                var attribute = recordAttributes[j];
                obj += '"' + attributeNames[j] + '" : "' + attribute + '"';
                if(j < recordAttributes.length - 1) {
                    obj += ","
                }
            }
            obj += "}";
            obj = obj.replace(/(\r\n|\n|\r)/gm,"");
            result.push(JSON.parse(obj));
        }
        
    callBack(result);
    }
}