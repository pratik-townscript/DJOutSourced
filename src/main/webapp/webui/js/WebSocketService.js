'use strict';
var mainApp = angular.module('mainApp');

mainApp.service('WebSocketService' , ['$rootScope', '$http', 'SongService',
   function($rootScope , $http, SongService){
	var service = this;
	
	service.init = function(){
		console.log("initing the websocket service");
		
	    var taskSocket = new WebSocket(service.getUrl());
	 
	    taskSocket.onopen = function()
        {
	    	console.log("Open event handler invoked");
        };
        
	    taskSocket.onmessage = function(message) {
	    	SongService.getAllSongsList();
	    	SongService.getUserUpvotedSongsId($rootScope.currUser.id);
	    	$rootScope.$apply();        
	    };

	    taskSocket.onclose = function() {
	    	console.log("web socket closing");
	    	SongService.getAllSongsList();
	    	SongService.getUserUpvotedSongsId($rootScope.currUser.id);
	    	$rootScope.$apply();
	    }
	    
	    taskSocket.onError = function(){
	    	console.log("There was some error connecting to server. Refreshing");
	    	SongService.getAllSongsList();
	    	SongService.getUserUpvotedSongsId($rootScope.currUser.id);
	    	$rootScope.$apply();
	    }
	}
	
	service.getUrl = function(){
		var url = window.location.href;
	    var n = url.indexOf("//");
	    var a = url.indexOf("/",n+2);
	    var b = url.indexOf("/",a+1);
	    var result = "ws:";
	    result = result + url.substring(n,b) + "/getRealSongList";
		return result;
	}
}]);