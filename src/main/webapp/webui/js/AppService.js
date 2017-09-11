'use strict';
var mainApp = angular.module('mainApp');

mainApp
  .service('AppService' , ['$cookieStore',  
   function($cookieStore){
	var service = this;
	
	service.setCredentials = function(user){
		console.log("Setting credentials for username " + JSON.stringify(user));
		$cookieStore.put('currentUser' ,user);
	};
	
	service.clearCredentials = function(){
		console.log("Clearing Credentials..");
		$cookieStore.remove('currentUser');
	};
	
}]);