'use strict';
var mainApp = angular.module('mainApp');

mainApp
  .service('AppService' , ['$cookieStore',  
   function($cookieStore){
	var service = this;
	
	service.setCredentials = function(user){
		$cookieStore.put('currentUser' ,user);
	};
	
	service.clearCredentials = function(){
		console.log("Clearing Credentials..");
		$cookieStore.remove('currentUser');
	};
	
}]);