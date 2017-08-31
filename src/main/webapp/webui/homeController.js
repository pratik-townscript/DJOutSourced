const MAX_FILE_SIZE = 10048576;
const supportedFormat = ["mp3"];

var mainApp = angular.module('mainApp');

mainApp.controller('homeController', ['$location','$scope' ,'$rootScope', '$http', '$cookieStore', 'AppService', HomeController]);

function HomeController($location, $scope, $rootScope, $http, $cookieStore, AppService){
	console.log("Home Controller called");
	console.log("user is " + $cookieStore.get('currentUser'));
	
	var self = this;
	
	self.uploadSongFields = {};
	self.uploadSongError = false;
	self.uploadSongErrorMsg = null;
	
	var myUser = $cookieStore.get('currentUser');
	$rootScope.currUser = null;
	if(myUser)
	{
		$rootScope.currUser = myUser;
	}
	else
	{
		console.log("setting null currUser");
		$rootScope.currUser = null;
	}
	
	self.logout = function(){
		$rootScope.currUser = null;
		AppService.clearCredentials();
		$location.path('/');
	};
	
	self.uploadSong = function(){
		console.log("received upload song request");
		console.log("upload title is " + self.uploadSongFields.uploadedSongTitle);
		
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
			
			console.log("file name is " + fileName);
			console.log("file size is " + fileSize/1000000);
			
			if(fileSize > MAX_FILE_SIZE)
			{
				self.uploadSongError = true;
				self.uploadSongErrorMsg = "File Size exceeds " + (MAX_FILE_SIZE/1000000) + " MB";
			}
			else if(fileExtension.toLowerCase() != 'mp3'){
				console.log("not valid version");
				self.uploadSongError = true;
				self.uploadSongErrorMsg = "Please upload mp3 version of file only";
			}
			else {
				console.log("user that will be sent is " + $rootScope.currUser);
				
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
						var fd = new FormData();
						fd.append('file' , file);
						fd.append('songTitle', songTitle);
						fd.append('username',$rootScope.currUser);
						
						$http.post("api/uploadUserSong" , fd, {
							transformRequest : angular.identity, headers : { 'Content-Type' : undefined}
						})
						.then(function(response){
							console.log("success in uploading songs");
						},function(response){
							console.log("error in uploading songs");
						 	console.log("json response is " + JSON.stringify(response));
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
		document.getElementById("uploadSongForm").reset();
	};

};