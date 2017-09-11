//Empty square brackets mins this module does not depends on any other module.
//var mainApp = angular.module('mainApp', ["ngRoute"]);

var mainApp = angular.module('mainApp');

//Work registered in the config method will be performed when the application is loading.
mainApp.config(function($routeProvider){
	
	$routeProvider
		.when('/', {
			templateUrl : 'webui/views/login.html',
			controller  : 'loginController',
			controllerAs: 'lc'	
		})
		.when('/login',{
			templateUrl : 'webui/views/login.html',
			controller  : 'loginController',
			controllerAs: 'lc'
		})
		.when('/home',{
			templateUrl : 'webui/views/home.html',
			controller  : 'homeController',
			controllerAs: 'hc'
		})
		.when('/register',{
			templateUrl : 'webui/views/register.html',
			controller  : 'registerController',
			controllerAs: 'rc'
		})
		.otherwise({ redirectTo: '/login'});
});