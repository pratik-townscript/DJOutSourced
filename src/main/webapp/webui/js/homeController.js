const MAX_FILE_SIZE = 10048576;
const supportedFormat = ["mp3"];

var mainApp = angular.module('mainApp');

mainApp.controller('homeController', ['$location','$scope' ,'$rootScope', '$http', '$cookieStore', 'AppService','SongService', 'WebSocketService', HomeController]);

function HomeController($location, $scope, $rootScope, $http, $cookieStore, AppService, SongService, WebSocketService){
	var self = this;
	
	self.uploadSongFields = {};
	self.uploadSongError = false;
	self.uploadSongErrorMsg = null;
	self.allSongsList = {};
	self.uploadInProcess = false;
	
	var myUser = $cookieStore.get('currentUser');
	$rootScope.currUser = null;
	if(myUser)
	{
		$rootScope.currUser = myUser;
		SongService.getAllSongsList();
		SongService.getUserUpvotedSongsId($rootScope.currUser.id);
		WebSocketService.init();
	}
	else
	{
		console.log("setting null currUser");
		$rootScope.currUser = null;
	}
	
	self.likeSong = function(userId , songId){
		console.log("Increase the count with id " + songId + " by user " + userId);
		document.getElementById("glyUpVote_" + songId).style.display = 'none';
		var fd = new FormData();
		fd.append('userId' , userId);
		fd.append('songId', songId);

		$http
		 .post("api/upvoteSong" , fd, {
				transformRequest : angular.identity, headers : { 'Content-Type' : undefined}
		 })
		 .then(function(response){
			console.log("successful in upvoting");
		 }, function(response){
			console.log("Failed to upvote song. Error response is " + JSON.stringify(response));
		 });
	};
	
	self.logout = function(){
		$rootScope.currUser = null;
		AppService.clearCredentials();
		$location.path('/');
	};
	
	self.uploadSong = function(){
		
		var x = document.getElementById("uploadedFile");
		
		if(x.files.length == 0){
			self.uploadSongError = true;
			self.uploadSongErrorMsg = "Please attach audio file to upload";
		}
		else{
			var file = x.files[0];
			var fileName = file.name;
			var fileSize = file.size;
			var fileExtension = fileName.split('.').pop();
			
			if(fileSize > MAX_FILE_SIZE)
			{
				self.uploadSongError = true;
				self.uploadSongErrorMsg = "File Size exceeds " + (MAX_FILE_SIZE/1000000) + " MB";
			}
			else if(fileExtension.toLowerCase() != 'mp3'){
				self.uploadSongError = true;
				self.uploadSongErrorMsg = "Please upload mp3 version of file only";
			}
			else {
				
				var songTitle = null;
				
				var url = file.urn || file.name;
				ID3.loadTags(url , function(){
					var tags = ID3.getAllTags(url);
					songTitle = tags.title;
					
					if(self.uploadSongFields.uploadedSongTitle)
					{
						songTitle = self.uploadSongFields.uploadedSongTitle;
					}
					else if(!songTitle){
						songTitle = fileName.substring(0 , fileName.lastIndexOf('.'));
					}
					
					console.log("final song title is " + songTitle);
					
					if(!songTitle)
					{
						self.uploadSongError = true;
						self.uploadSongErrorMsg = "Please provide title of song";
					}
					else
					{
						console.log("Starting to upload song");
						self.uploadInProcess = true;
						
						document.getElementById("uploadSongConfirmButton").disabled = true;
						
						$('body').addClass('wait');
						
						var fd = new FormData();
						fd.append('file' , file);
						fd.append('songTitle', songTitle);
						fd.append('username',$rootScope.currUser.username);
						
						$http.post("api/uploadUserSong" , fd, {
							transformRequest : angular.identity, headers : { 'Content-Type' : undefined}
						})
						.then(function(response){
							self.uploadInProcess = false;
							
							if(response.data.code == 200){
								console.log("success in uploading songs");
								self.uploadSongError = false;
								self.uploadSongErrorMsg = null;
								
								$('body').removeClass('wait');
								$('#uploadSongModal').modal('hide');
							}
							else{
								console.log("error with response " + JSON.stringify(response));
								self.uploadSongError = true;
								self.uploadSongErrorMsg = response.data.result;
								 $('body').removeClass('wait');
							}
							document.getElementById("uploadSongConfirmButton").disabled = false;
						},function(response){
						 	console.log("Error in uploading songs. JSON response is " + JSON.stringify(response));
							self.uploadInProcess = false;
						 	self.uploadSongError = true;
						 	self.uploadSongErrorMsg = "Error occured while uploading Song. Please try again";
						 	document.getElementById("uploadSongConfirmButton").disabled = false;
						 	 $('body').removeClass('wait');
						});
					}
				 },{
			        tags: ["title"],
			        dataReader: ID3.FileAPIReader(file)
			      });
			}
		}
	};
	
	self.resetSongFields = function(){
		console.log("resetsong fields method invoked");
		self.uploadSongFields = {};
		self.uploadSongError = false;
		self.uploadSongErrorMsg = null;
		self.uploadInProcess = false;
		document.getElementById("uploadSongForm").reset();
	};
	
	self.changeSongs = function(){
		SongService.changeSong();
	}
};