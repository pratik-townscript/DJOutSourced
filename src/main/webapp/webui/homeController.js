var mainApp = angular.module('mainApp');

mainApp.controller('homeController', ['$location' ,'$rootScope', '$http', '$cookieStore', 'AppService', HomeController]);

function HomeController($location, $rootScope, $http, $cookieStore, AppService){
	console.log("Home Controller called");
	console.log("user is " + $cookieStore.get('currentUser'));
	
	var self = this;
	
	var myUser = $cookieStore.get('currentUser');
	$rootScope.currUser = null;
	if(myUser)
	{
		//self.currUser = myUser;
		$rootScope.currUser = myUser;
	}
	else
	{
		console.log("setting null currUser");
		$rootScope.currUser = null;
	}
	
	self.logout = function(){
		$rootScope.currUser = null;
		AppService.clearCredentials();
		$location.path('/');
	};
	
	
};