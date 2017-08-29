'use strict';
var mainApp = angular.module('mainApp');

mainApp
  .service('AppService' , ['$cookieStore',  
   function($cookieStore){
	var service = this;
	
	service.setCredentials = function(username){
		console.log("Setting credentials for username " + username);
		$cookieStore.put('currentUser' ,username);
	};
	
	service.clearCredentials = function(){
		console.log("Clearing Credentials..");
		$cookieStore.remove('currentUser');
	};
	
	service.testMethod = function(){
		console.log("calling test method 1");
	};
}]);