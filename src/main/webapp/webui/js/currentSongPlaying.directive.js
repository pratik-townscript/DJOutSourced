
var mainApp = angular.module('mainApp');

mainApp.directive('songAction', ['SongService' , CurrentSongDirective]);

function CurrentSongDirective(SongService){
	
	return {
		restrict: 'A',
	    link: function(scope, element, attrs, ctrl){
	    	element.bind('play', function(){
	    		console.log("The song started playing the action is hit");
	    	});
	    	
	    	element.bind('pause', function(){
	    		console.log("The song has paused");
	    	});
	    	
	    	element.bind('ended',function(){
	    		console.log("The song has ended");
	    		SongService.changeSong();
	    	});
	    }
	};
};