function jsonFormat() {

    var json j1 = `{"name" : "ballerina"}`;
    var string s1 = json.get(j1, "$.name");
    //assert(s1 == "ballerina");

    json.set(j1, "$.name", "wso2");
    //assert(j1 == `{"name" : "wso2"}`);

    var json j2 = `{"fullName": {"firstName" : "Alice" , "lastName": "Bob"}}`;
    json.remove(j2, "$.fullName.lastName");
    //assert(j2 == `{"fullName": {"firstName" : "Alice"}}`);

    var json j3 = `{"letters" : ["a", "b", "c", "d"]}`;
    json.remove(j3, "$.letters[3]");
    //assert (j3 == `{"letters" : ["a", "b", "c"]}`);
}