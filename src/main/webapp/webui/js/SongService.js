'use strict';
var mainApp = angular.module('mainApp');

mainApp.service('SongService' , ['$rootScope', '$http',
	function($rootScope, $http){
	var service = this;
	
	//Temparory hack
	$rootScope.mainSongDir = "res/songFolder/";
	
	service.getAllSongsList = function(){
		$http.get("api/getAllSongs")
			 .then(function(response){
				 console.log("successful in getting songs");
				 if(response.data.code == 200){
					 $rootScope.allSongsList = response.data.result.songList;
					 $rootScope.allSongsList.sort(function(a , b){
						 return parseInt(b.upVote) - parseInt(a.upVote);
					 });
					 //console.log("sorted song list is " + JSON.stringify($rootScope.allSongsList));
					 var isAlreadyPlaying = false;
					 for(var i = 0 ; i < $rootScope.allSongsList.length ; i++)
					 {
						 var obj = $rootScope.allSongsList[i];
						 if(obj.currentSong == true)
						 {
							 console.log("will be playing only the retr");
							 $rootScope.currentPlayingSong = obj;
							 $rootScope.allSongsList.splice(i,1);  
							 isAlreadyPlaying = true;
						 }
					 }
					 if(!isAlreadyPlaying){
						 console.log("not already played. setting boolean as play");
						 $rootScope.currentPlayingSong = $rootScope.allSongsList.shift();
						 service.setSongAsPlaying();
					 }
					 console.log("top song is " + JSON.stringify($rootScope.currentPlayingSong));
				 }
			 },function(response){
				 console.log("error getting songs");
			 });
	};
	
	service.setSongAsPlaying = function(){
		
		var fd = new FormData();
		fd.append("songId" , $rootScope.currentPlayingSong.id);
		
		$http.post("api/setSongAsPlaying" , fd , {
			transformRequest : angular.identity, headers : { 'Content-Type' : undefined}
		}).then(function(response){
			if(response.data.code == 200){
				console.log("Successfully set the song as playing");
			}
		},function(response){
			console.log("Error reseting upvote for song");
		});
	};
	
	service.getUserUpvotedSongsId  = function(userId){
		
		console.log("user id is " + userId);
		
		$http.get("api/getUserUpvotedSongs" , {params:{userId:userId}}).then(function(response){
		    	console.log("successful in retrieving list") ;		    	
		    	if(response.data.code == 200){
		    		console.log("response is " + JSON.stringify(response.data.result));
		    		$rootScope.userSongUpVoteMap = response.data.result;
		    	}
		     }, function(response){
		    	 console.log("error getting user upvoted song list");
		    	 console.log("error is " + JSON.stringify(response));
		     });
	};
	
	service.changeSong = function(){

		var fd = new FormData();
		fd.append("songId" , $rootScope.currentPlayingSong.id);
		
		$http.post("api/resetSongUpVotes" , fd , {
			transformRequest : angular.identity, headers : { 'Content-Type' : undefined}
		}).then(function(response){
			if(response.data.code == 200){
				console.log("Successfully reset the upvote count");
				service.getAllSongsList();
			}
		},function(response){
			console.log("Error reseting upvote for song");
		});
	};
	
}]);