'use strict';
var mainApp = angular.module('mainApp');

mainApp.service('WebSocketService' , ['$rootScope', '$http', 'SongService',
   function($rootScope , $http, SongService){
	var service = this;
	
	service.init = function(){
	    // WebSocket Initialization
		console.log("initing the websocket service");
		
	    var taskSocket = new WebSocket("ws://localhost:8080/DJOutSourced2/getRealSongList");
	 
	    taskSocket.onopen = function()
        {
	    	console.log("Open event handler invoked");
        };
        
	    taskSocket.onmessage = function(message) {
	    	
	    	var tempData = JSON.parse(message.data);
	    	console.log("currnt id is " + $rootScope.currentPlayingSong.id);
	    	for(var i = 0 ; i < tempData.length; i++)
	    	{
	    		var obj = tempData[i];
	    		if($rootScope.currentPlayingSong.id == obj.id){
	    			console.log("ids matched " + $rootScope.currentPlayingSong.id);
	    			tempData.splice(i,1);
	    		}
	    	}
	    	$rootScope.allSongsList = tempData;
	    	SongService.getUserUpvotedSongsId($rootScope.currUser.id);
	    	console.log("data after is " + JSON.stringify($rootScope.allSongsList));
	    	$rootScope.$apply();        
	    };

	    taskSocket.onclose = function() {
	    	console.log("web socket closing");
	        /*$scope.message = {
	            type: "danger",
	            short: "Socket error",
	            long: "An error occured with the WebSocket."
	        };
	        $scope.$apply();    */
	    }
	}
}]);