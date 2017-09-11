var mainApp = angular.module('mainApp');

mainApp.controller('registerController', ['$rootScope', '$http' , '$location', 'AppService', RegisterController]);

function RegisterController($rootScope , $http, $location, AppService){
	
	console.log("From the registration controller");
	
	var self = this;
	self.error = false;
	self.errorMsg = null;
	
	self.credentials = {};
	
	self.registerUser = function(){
		console.log("register method invoked");
		self.error = false;
		self.errorMsg = null;
		
		if(self.credentials.password !== self.credentials.passwordConfirm)
		{
			self.error = true;
			self.errorMsg = "Passwords are not same";
		}
		else
		{
			self.error = false;
			self.errorMsg = null;
			self.register(self.credentials);
		}
	};
	
	self.register = function(user){
		
		$http.post('api/registerUser' , user)
			.then(
				function(response){
				 	console.log("response is " + response);
				 	console.log("json response is " + JSON.stringify(response));
				 	console.log("response code is " + response.data.code);
				 	
				 	if(response.data.code == 200){
				 		self.error = false;
				 		self.errorMsg = null;
				 		console.log("everythings look good");
				 		
				 		console.log("the username is from login is " + response.data.result.user.username);
				 		AppService.setCredentials(response.data.result.user.username);
				 		
				 		$location.path('/home');
				 		
				 	}
				 	else{
				 		self.error = true;
				 		console.log("Response data is " + response.data.result);
				 		self.errorMsg = response.data.result;
				 	}
				 	
				},function(response){
					 console.log("error somewhere");
					 self.error = true;
					 self.errorMsg = "Error trying to Register. Please try Again";
				});
	};
}