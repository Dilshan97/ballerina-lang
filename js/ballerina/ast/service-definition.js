/**
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
define(['lodash', './node'], function (_, ASTNode) {

    var ServiceDefinition = function () {
        this._basePath = "/";
        this._resourceDefinitions = [];
        this._variableDeclarations = [];
        this._connectionDeclarations = [];
    };

    ServiceDefinition.prototype = Object.create(ASTNode.prototype);
    ServiceDefinition.prototype.constructor = ServiceDefinition;

    ServiceDefinition.prototype.setBasePath = function (basePath) {
        if (!_.isNil(basePath)) {
            this._basePath = basePath;
        }
    };

    ServiceDefinition.prototype.getBasePath = function () {
        return this._basePath;
    };

    ServiceDefinition.prototype.setResourceDefinitions = function (resourceDefinitions) {
        if (!_.isNil(resourceDefinitions)) {
            this._resourceDefinitions = resourceDefinitions;
        }
    };

    ServiceDefinition.prototype.getResourceDefinitions = function () {
        return this._resourceDefinitions;
    };

    ServiceDefinition.prototype.setVariableDeclarations = function (variableDeclarations) {
        if (!_.isNil(variableDeclarations)) {
            this._variableDeclarations = variableDeclarations;
        }
    };

    ServiceDefinition.prototype.getVariableDeclarations = function () {
        return this._variableDeclarations;
    };

    ServiceDefinition.prototype.setConnectionDeclarations = function (connectionDeclarations) {
        if (!_.isNil(connectionDeclarations)) {
            this._connectionDeclarations = connectionDeclarations;
        }
    };

    ServiceDefinition.prototype.getConnectionDeclarations = function () {
        return this._connectionDeclarations;
    };

});