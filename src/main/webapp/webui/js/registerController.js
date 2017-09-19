var mainApp = angular.module('mainApp');

mainApp.controller('registerController', ['$rootScope', '$http' , '$location', 'AppService', RegisterController]);

function RegisterController($rootScope , $http, $location, AppService){
	var self = this;
	self.error = false;
	self.errorMsg = null;
	
	self.credentials = {};
	
	self.registerUser = function(){
		self.error = false;
		self.errorMsg = null;
		
		if(!self.credentials.username
			|| !self.credentials.password 
			|| !self.credentials.passwordConfirm)
		{
			self.error = true;
			self.errorMsg = "Please don't keep any field blank";
		}
		else if(self.credentials.username.length > 40)
		{
			self.error = true;
			self.errorMsg = "Username length exceed max value 40";
		}	
		else if(self.credentials.password !== self.credentials.passwordConfirm)
		{
			self.error = true;
			self.errorMsg = "Passwords are not same";
		}
		else if(self.credentials.password.length > 40)
		{
			self.error = true;
			self.errorMsg = "Password length exceed max value 40";
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
				 	
				 	if(response.data.code == 200){
				 		self.error = false;
				 		self.errorMsg = null;
				 		
				 		AppService.setCredentials(response.data.result.user.username);
				 		
				 		$location.path('/home');
				 	}
				 	else{
				 		self.error = true;
				 		console.log("Error on Registering. Response data is " + response.data.result);
				 		self.errorMsg = response.data.result;
				 	}
				 	
				},function(response){
					 self.error = true;
					 self.errorMsg = "Error trying to Register. Please try Again";
				});
	};
}