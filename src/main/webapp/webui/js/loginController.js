var mainApp = angular.module('mainApp');

mainApp.controller('loginController', ['$rootScope', '$http', '$location', '$route', 'AppService', LoginController]);
		
function LoginController($rootScope, $http, $location, $route, AppService){
			var self = this;
			self.error = false;
			self.errorMsg = null;
			self.credentials = {};
			
			var authenticate = function(user , callback){
				
				$http.post('api/loginUser', user)
					 .then(
					   function(response){

						   if(response.data.code == 200){
						 		self.error = false;
						 		AppService.setCredentials(response.data.result.user);
						 		
						 		$location.path('/home');
						 	}
						 	else{
						 		self.error = true;
						 		console.log("Error while authenticating.Response data is " + response.data.result);
						 		self.errorMsg = response.data.result;
						 	}
					   },function(response){
						   console.log("Error while login In. JSON response is" + JSON.stringify(response));
						   self.error = true;
						   self.errorMsg = "Error trying to log in. Please try Again";
					   });
				
			};
			
			self.login = function(){
				authenticate(self.credentials, function(authenticated){
					console.log("callback on authentication");
				});
			};
		};