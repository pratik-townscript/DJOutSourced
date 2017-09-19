'use strict';
var mainApp = angular.module('mainApp');

mainApp.filter('trusted', ['$sce', function ($sce) {
    return function(url) {
        return $sce.trustAsResourceUrl(url);
    };
}]);