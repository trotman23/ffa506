DashboardController.$inject = ['UserService', '$rootScope'];
function DashboardController(UserService, $rootScope) {
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
}

FTJController.$inject = ['$scope', '$http'];
function FTJController($scope, $http) {
	$scope.list1 = 'Select Team 1';
	$scope.list2 = 'Select Team 2';
	//$scope.selectedTeam = null;
	$scope.teams = [];
	$scope.players1 = [];
	$scope.players2 = [];
	$scope.roster1 = null;
	$scope.roster2 = null;
	$http({
		method: 'GET',
		url: './rest/LeagueTeams?LeagueID=1682132'
	}).success(function (result) {
		$scope.teams = result;
	});
	console.log("in controller");
	$scope.getRoster1 = function(selectedTeam){
		console.log("in getRoster with teamID = " + selectedTeam.teamID);
		$http({
			method: 'GET',
			url: './rest/Roster?LeagueID=1682132&TeamID=' +selectedTeam.teamID + '&Week=1&Year=2015'
		}).then(function (result){
			$scope.roster1 = result.data;
		});

	}
	//duplicating for now, should change to use the same method for both rosters
	$scope.getRoster2 = function(selectedTeam){
		console.log("in getRoster with teamID = " + selectedTeam.teamID);
		$http({
			method: 'GET',
			url: './rest/Roster?LeagueID=1682132&TeamID=' +selectedTeam.teamID + '&Week=1&Year=2015'
		}).then(function (result){
			$scope.roster2 = result.data;
		});
	}

	$scope.comparePlayers = function(){
		console.log("testingsss");
		console.log($scope.players1);
		console.log($scope.players2);
		console.log('call: ./rest/FTJ?PlayerID1=' + $scope.players1 + '&PlayerID2=' + $scope.players1);
		console.log('Is $scope.players1 ' + ($scope.players1) + ' > $scope.players2 ' + $scope.players1 + ' ?');
		console.log('comparison');
		console.log($scope.players1 > $scope.players2);
		$http({
			method: 'GET',
			url: './rest/FTJ?PlayerID1=' + $scope.players1 + '&PlayerID2=' + $scope.players2
		}).then(function (result){
			console.log('result.data');
			console.log(result.data);
			if (result.data){
				$scope.FTJ = "Hell yea";
			} else {
				$scope.FTJ = "f no";
			}
		});
	};
	$scope.addID1 = function(s){
		$scope.players1 = s;
		console.log($scope.players1);

	};
	$scope.addID2 = function(s){
		$scope.players2 = s;
		console.log($scope.players2);
	};
}

//Draft Buddy Controller
DraftBuddyController.$inject = ['$scope', '$http', '$rootScope', 'UserService'];
function DraftBuddyController($scope, $http, $rootScope, UserService) {
	$scope.sortType = 'Name'
		$scope.players =[];
	$scope.player = "Insult here";
	$http({
		method: 'GET',
		url: './rest/DraftBuddy'
	}).success(function (result) {
		$scope.players = result;
		console.log(result);
		console.log('success');
	});

}

//smart ranking controller
SRController.$inject = ['$scope', '$http', '$rootScope', 'UserService'];
function SRController($scope, $http, $rootScope, UserService){
	$scope.weeks = [{"week": 1},
	                {"week": 2},
	                {"week": 3},
	                {"week": 4},
	                {"week": 5},
	                {"week": 6},
	                {"week": 7},
	                {"week": 8},
	                {"week": 9},
	                {"week": 10},
	                {"week": 11},
	                {"week": 12},
	                {"week": 13},
	                {"week": 14},
	                {"week": 15},
	                {"week": 16},
	                {"week": 17}];
	console.log($rootScope.globals.currentUser.id);
	UserService.GetLeagueIDFromUser($rootScope.globals.currentUser.id).then(function(data){
		$scope.tempLeagueID = data;
	});
	$scope.$watch('tempLeagueID', function (cast) {
		// When $scope.tempLeagueID  has data, then run these functions
		if (angular.isDefined(cast)) {          
			console.log("$scope.tempLeagueID has data");
			console.log("$scope.tempLeagueID" + $scope.tempLeagueID);
			$http({
				method: 'GET',
				url: './rest/SmartRank?LeagueID=' + $scope.tempLeagueID + '&Week=1'
			}).then(function (result){
				$scope.smartrankings = result.data;
				console.log("$scope.smartrankings: " + $scope.smartrankings);
			});
		}
	});

	$scope.updateSmartRankings = function(week){
		UserService.GetLeagueIDFromUser($rootScope.globals.currentUser.id).then(function(data){
			$scope.tempLeagueID = data;
		});
		console.log(week);
		$scope.$watch('tempLeagueID', function (cast) {
			// When $scope.tempLeagueID  has data, then run these functions
			if (angular.isDefined(cast)) {          
				console.log("$scope.tempLeagueID has data");
				console.log("$scope.tempLeagueID" + $scope.tempLeagueID);
				$http({
					method: 'GET',
					url: './rest/SmartRank?LeagueID=' + $scope.tempLeagueID + '&Week=' + week.week
				}).then(function (result){
					$scope.smartrankings = result.data;
					console.log("$scope.smartrankings: " + $scope.smartrankings);
				});
			}
		});
	};
}


//awards controller
AwardsController.$inject = ['$scope', '$http'];
function AwardsController($scope, $http){
	$scope.weeks = [{"week": 1},
	                {"week": 2},
	                {"week": 3},
	                {"week": 4},
	                {"week": 5},
	                {"week": 6},
	                {"week": 7},
	                {"week": 8},
	                {"week": 9},
	                {"week": 10},
	                {"week": 11},
	                {"week": 12},
	                {"week": 13},
	                {"week": 14},
	                {"week": 15},
	                {"week": 16},
	                {"week": 17}];

	$http({
		method: 'GET',
		url: './rest/Awards?LeagueID=1682132&Week=1'
	}).then(function (result){
		$scope.awards = result.data;
		console.log($scope.awards);
	});
	$scope.updateAwards = function(week){
		console.log(week);
		$http({
			method: 'GET',
			url: './rest/Awards?LeagueID=1682132&Week=' + week.week
		}).then(function (result){
			$scope.awards = result.data;
		});
	};
}

CPController.$inject = ['$scope', '$http'];
function CPController($scope, $http){
	$scope.weeks = [{"week": 1},
	                {"week": 2},
	                {"week": 3},
	                {"week": 4},
	                {"week": 5},
	                {"week": 6},
	                {"week": 7},
	                {"week": 8},
	                {"week": 9},
	                {"week": 10},
	                {"week": 11},
	                {"week": 12},
	                {"week": 13},
	                {"week": 14},
	                {"week": 15},
	                {"week": 16},
	                {"week": 17}];
	$http({
		method: 'GET',
		url: './rest/CompositeRank?LeagueID=1682132&Week=17'
	}).then(function (result){
		$scope.comprankings = result.data;
		console.log($scope.comprankings);
	});
	$scope.updateCPRankings = function(week){
		console.log(week);
		$http({
			method: 'GET',
			url: './rest/CompositeRank?LeagueID=1682132&Week=' + week.week
		}).then(function (result){
			$scope.comprankings = result.data;
			console.log(result.data);
		});
	};
}

INSULTController.$inject = ['$scope', '$http'];
function INSULTController($scope, $http) {
	$scope.list = 'Select Team';
	$scope.teams = [];
	$scope.outputInsult =null;


	$http({
		method: 'GET',
		url: './rest/LeagueTeams?LeagueID=1682132'
	}).success(function (result) {
		$scope.teams = result;
	});

	$scope.insult = function(selectedTeam){
		console.log(selectedTeam.teamID);
		$http({
			method: 'GET',
			url: './rest/LeagueInsult?TeamID=' +selectedTeam.teamID 
		}).then(function (result) {
			console.log('return from insult controller');
			$scope.outputInsult = result.data.key;
		});
	}

}


jQuery(function () {
	jQuery('#list a:last').tab('show');
});

myApp.controller('PollsController', function($scope, $http){
	$scope.weeks = [{"week": 1},
	                {"week": 2},
	                {"week": 3},
	                {"week": 4},
	                {"week": 5},
	                {"week": 6},
	                {"week": 7},
	                {"week": 8},
	                {"week": 9},
	                {"week": 10},
	                {"week": 11},
	                {"week": 12},
	                {"week": 13},
	                {"week": 14},
	                {"week": 15},
	                {"week": 16},
	                {"week": 17}];
	$scope.ranks = [{"rank": 1},
	                {"rank": 2},
	                {"rank": 3},
	                {"rank": 4},
	                {"rank": 5},
	                {"rank": 6},
	                {"rank": 7},
	                {"rank": 8},
	                {"rank": 9},
	                {"rank": 10},
	                {"rank": 11},
	                {"rank": 12}];
	$scope.pRanks = [];
	$http({
		method: 'GET',
		url: './rest/LeagueTeams?LeagueID=1682132'
	}).success(function (result) {
		$scope.teams = result;
	});
	
	$scope.submitPoll = function(week, ranks){
		console.log("in submit poll");
		$http({
			method: 'PUT',
			url: './rest/InsertPoll',
			data: ranks
		}).success(function (result) {
			$scope.teams = result;
		});
	}
	
	$scope.updateWeeks = function(week){
		var temp = $scope.weeks.indexOf(week);
		console.log(temp);
	}
});

