// Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/http;
import ballerina/io;
import ballerina/mime;

public function main(string... args) {
    endpoint http:Client clientEP {
        url: args[0],
        secureSocket: {
            keyStore: {
                path: "${ballerina.home}/bre/security/ballerinaKeystore.p12",
                password: "ballerina"
            },
            trustStore: {
                path: "${ballerina.home}/bre/security/ballerinaTruststore.p12",
                password: "ballerina"
            },
            ciphers: ["TLS_RSA_WITH_AES_256_CBC_SHA"]
        }
    };
    http:Request req = new;
    var resp = clientEP->get("/echo/");
    match resp {
        error err => io:println(err.message);
        http:Response response => {
            match (response.getTextPayload()) {
                error payloadError => io:println(payloadError.message);
                string res => io:println(res);
            }
        }
    }
}
