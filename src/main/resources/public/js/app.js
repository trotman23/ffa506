(function () {
	'use strict';

	var app = angular
	.module('app', ['ngRoute', 'ngCookies', 'googlechart'])
	.config(config)
	.run(run);
	//rss factory
	app.factory('FeedService',['$http',function($http){
		return {
			parseFeed : function(url){
				return $http.jsonp('//ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=50&callback=JSON_CALLBACK&q=' + encodeURIComponent(url));
			}
		}
	}]);
	config.$inject = ['$routeProvider', '$locationProvider'];
	function config($routeProvider, $locationProvider) {
		$routeProvider
		.when('/', {
			controller: 'DashboardController',
			templateUrl: 'dashboard/dashboard.view.html/',
			controllerAs: 'vm'
		})
		.when('/login', {
			controller: 'LoginController',
			templateUrl: 'login/login.view.html',
			controllerAs: 'vm'
		})

		.when('/register', {
			controller: 'RegisterController',
			templateUrl: 'register/register.view.html',
			controllerAs: 'vm'
		})
		.when('/fantasy-news', {
			controller: 'FantasyNewsController',
			templateUrl: 'fantasy-news/fantasy-news.view.html',
			controllerAs: 'vm'
		})
		.when('/top-performers', {
			controller: 'TopPerformersController',
			templateUrl: 'top-performers/top-performers.view.html',
			controllerAs: 'vm'
		})
		.when('/help', {
			controller: 'HelpController',
			templateUrl: 'help/help.view.html',
			controllerAs: 'vm'
		})
		.when('/request-feature', {
			controller: 'RequestFeatureController',
			templateUrl: 'request-feature/request-feature.view.html',
			controllerAs: 'vm'
		})
		.when('/report-bug', {
			controller: 'ReportBugController',
			templateUrl: 'report-bug/report-bug.view.html',
			controllerAs: 'vm'
		})
		.otherwise({ redirectTo: '/login' });
	}

	run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
	function run($rootScope, $location, $cookieStore, $http) {
		// keep user logged in after page refresh
		$rootScope.globals = $cookieStore.get('globals') || {};
		if ($rootScope.globals.currentUser) {
			$http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
		}

		$rootScope.$on('$locationChangeStart', function (event, next, current) {
			// redirect to login page if not logged in and trying to access a restricted page
			var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
			var loggedIn = $rootScope.globals.currentUser;
			if (restrictedPage && !loggedIn) {
				$location.path('/login');
			}
		});
	}
})();
