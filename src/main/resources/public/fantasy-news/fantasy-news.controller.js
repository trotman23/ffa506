FantasyNewsController.$inject = ['$rootScope', '$http', 'UserService','FeedService'];
function FantasyNewsController($rootScope, $http, UserService, FeedService) {
	var vm = this;

	vm.user = null;
	vm.allUsers = [];
	vm.deleteUser = deleteUser;

	initController();

	function initController() {
		loadCurrentUser();

	}

	function loadCurrentUser() {
		UserService.GetByEmail($rootScope.globals.currentUser.email)
		.then(function (user) {
			vm.user = user.data;
		});
	}

	function deleteUser(id) {
		UserService.Delete(id)
		.then(function () {
			loadAllUsers();
		});
	}
	$rootScope.feedSrc = [];
	$rootScope.loadButtonText="Select Source";
	$rootScope.loadFeed=function(feedUrl, e){
		console.log("in load feed");
		console.log(feedUrl);
		console.log($rootScope.tempFeed);
		FeedService.parseFeed(feedUrl).then(function(res){
			$rootScope.loadButtonText=angular.element(e.target).text();
			$rootScope.feeds=res.data.responseData.feed.entries;
		});
	}
}
