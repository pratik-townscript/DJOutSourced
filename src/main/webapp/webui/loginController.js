var mainApp = angular.module('mainApp');

mainApp.controller('loginController', ['$rootScope', '$http', '$location', '$route', 'AppService', LoginController]);
		
function LoginController($rootScope, $http, $location, $route, AppService){
			var self = this;
			self.error = false;
			self.errorMsg = null;
			self.credentials = {};
			
			var authenticate = function(user , callback){
				
				AppService.testMethod();
				
				$http.post('api/loginUser', user)
					 .then(
					   function(response){
						 	console.log("response is " + response);
						 	console.log("json response is " + JSON.stringify(response));
						 	console.log("response code is " + response.data.code);
						 	
						 	if(response.data.code == 200){
						 		console.log("correct credentials passed");
						 		self.error = false;
						 		console.log("current path is " + $location.path);
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
						   console.log("error response is " + response);
						   console.log("error json response is " + JSON.stringify(response));
						   console.log("error somewhere");
						   self.error = true;
						   self.errorMsg = "Error trying to log in. Please try Again";
					   });
				
			};
			
			self.login = function(){
				console.log("Log in Code Invoked");
				console.log("username is " + self.credentials.username);
				console.log("password is " + self.credentials.password);
				
				authenticate(self.credentials, function(authenticated){
					console.log("callback on authentication");
				});
			};
		};