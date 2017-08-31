// Should use when it is need. Currently not needed
var mainApp = angular.module('mainApp');

mainApp.directive('songOnChange', function($parse){

	var validFormats = ['mp3'];
	return {
		restrict: 'A',
		require: 'ngModel',
		link: function(scope, element, attrs, ngModel){
			var md = $parse(attrs.ngModel);
			var mdSetter = md.assign;
			
			element.bind('change', function(){
				console.log("successfully calling on change");
				mdSetter(scope, element[0].files[0]);
				var fileSize =  element[0].files[0].size;
				console.log("file size is " + fileSize);
			});
		}
	};
});