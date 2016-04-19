DashboardController.$inject = ['UserService', '$rootScope'];
function DashboardController(UserService, $rootScope) {
	var vm = this;

	vm.user = null;
	vm.allUsers = [];

	initController();

	function initController() {
		loadCurrentUser();
		loadLeagueID();
	}

	function loadCurrentUser() {
		UserService.GetByEmail($rootScope.globals.currentUser.email)
		.then(function (user) {
			vm.user = user.data;
			$rootScope.$broadcast('user-loaded');
		});
	}

	function loadLeagueID(){
		//console.log("in loadLeague");
		$rootScope.$on('user-loaded', function(){
			UserService.GetLeagueIDFromUser($rootScope.globals.currentUser.id).then(function(data){
				$rootScope.tempLeagueID = data;
				$rootScope.$broadcast('leagueID-loaded');
			});
		});
	}

}

NavController.$inject = ['AuthenticationService', '$scope', '$location', '$rootScope'];
function NavController(AuthenticationService, $scope, $location, $rootScope) {
	$scope.logoutUser = function() {
		AuthenticationService.ClearCredentials();
		$location.path('/login');
	}
	$scope.editUser = function() {
		$location.path('/account');
	}
}

FTJController.$inject = ['$scope', '$http', '$rootScope'];
function FTJController($scope, $http, $rootScope) {
	$scope.list1 = 'Select Team 1';
	$scope.list2 = 'Select Team 2';
	//$scope.selectedTeam = null;
	$scope.teams = [];
	$scope.players1 = [];
	$scope.players2 = [];
	$scope.roster1 = null;
	$scope.roster2 = null;
	$scope.$on('leagueID-loaded', function() {
		//console.log("leagueIDloaded in FTJController")
		$http({
			method: 'GET',
			url: './rest/LeagueTeams?LeagueID=' + $rootScope.tempLeagueID
		}).success(function (result) {
			$scope.teams = result;
		});
	});
	//console.log("in controller");
	$scope.getRoster1 = function(selectedTeam){
		//console.log("in getRoster with teamID = " + selectedTeam.teamID);
		$http({
			method: 'GET',
			url: './rest/Roster?LeagueID=1682132&TeamID=' +selectedTeam.teamID + '&Week=1&Year=2015'
		}).then(function (result){
			$scope.roster1 = result.data;
		});

	}
	//duplicating for now, should change to use the same method for both rosters
	$scope.getRoster2 = function(selectedTeam){
		//console.log("in getRoster with teamID = " + selectedTeam.teamID);
		$http({
			method: 'GET',
			url: './rest/Roster?LeagueID=1682132&TeamID=' +selectedTeam.teamID + '&Week=1&Year=2015'
		}).then(function (result){
			$scope.roster2 = result.data;
		});
	}

	$scope.comparePlayers = function(){
		$scope.FTJ = "Loading...";
		$http({
			method: 'GET',
			url: './rest/FTJ?PlayerID1=' + $scope.players1 + '&PlayerID2=' + $scope.players2
		}).then(function (result){
			if (result.data){
				$scope.FTJ = "Hell yea";
			} else {
				$scope.FTJ = "f no";
			}
		});
	};
	$scope.addID1 = function(s){
		$scope.players1 = s;
		////console.log($scope.players1);

	};
	$scope.addID2 = function(s){
		$scope.players2 = s;
		//console.log($scope.players2);
	};
}


DraftBuddyController.$inject = ['$scope', '$http', '$rootScope', 'UserService', '$timeout'];
function DraftBuddyController($scope, $http, $rootScope, UserService, $timeout) {
	$scope.sortType = 'Rank'
	
	if ($scope.players == null){
		$http({
			method: 'GET',
			url: './rest/DraftBuddy'
		}).success(function (result) {
			$scope.players = result;
		});
	}

	$scope.players = [];

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
	$scope.$on('leagueID-loaded', function() {
		$http({
			method: 'GET',
			url: './rest/SmartRank?LeagueID=' + $rootScope.tempLeagueID + '&Week=17'
		}).then(function (result){
			$scope.smartrankings = result.data;
		});
	});

	$scope.updateSmartRankings = function(week){       
		$http({
			method: 'GET',
			url: './rest/SmartRank?LeagueID=' + $rootScope.tempLeagueID + '&Week=' + week.week
		}).then(function (result){
			$scope.smartrankings = result.data;
		});
	};
	$scope.generateSrChart = function(week){
		$("#srButton").addClass("active");
		var srChart1 = {};
		srChart1.type = "LineChart";
		$http({
			method: 'GET',
			url: './rest/SmartRankChart?LeagueID=' + $rootScope.tempLeagueID + '&Week=' + week.week
		}).then(function (result){
			//console.log(result.data);
			srChart1.data = result.data;
			srChart1.options = {
				chartArea: {
					width: '70%',
					height: '80%',
					left: '5%'
				},
				height: '1200px',
				legend: {
					position: 'right'
				},
				hAxis: {
					format: 'short',
					title: 'Week'
				}, 
				vAxis: {
					direction: -1,
					ticks: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
					title: 'SmartRank'
				}
				
			}
			$scope.srChart = srChart1;
			$("#srButton").removeClass("active");
		});

	}
}


//awards controller
AwardsController.$inject = ['$scope', '$http', '$rootScope'];
function AwardsController($scope, $http, $rootScope){
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
	$scope.$on('leagueID-loaded', function() {
		$http({
			method: 'GET',
			url: './rest/Awards?LeagueID=' + $rootScope.tempLeagueID + '&Week=1'
		}).then(function (result){
			$scope.awards = result.data;
			//console.log($scope.awards);
		});
	});
	$scope.updateAwards = function(week){
		//console.log(week);
		$http({
			method: 'GET',
			url: './rest/Awards?LeagueID=' + $rootScope.tempLeagueID + '&Week=' + week.week
		}).then(function (result){
			$scope.awards = result.data;
		});
	};
}

CPController.$inject = ['$scope', '$http', '$rootScope'];
function CPController($scope, $http, $rootScope){
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
	//wait for the leagueID to be loaded before initializing data
	$scope.$on('leagueID-loaded', function() {
		$http({
			method: 'GET',
			url: './rest/CompositeRank?LeagueID=' + $rootScope.tempLeagueID + '&Week=17'
		}).then(function (result){
			$scope.comprankings = result.data;
			//buildCpChart(result.data);
			//console.log($scope.comprankings);
		});
	});

	$scope.updateCPRankings = function(week){
		//console.log(week);
		$http({
			method: 'GET',
			url: './rest/CompositeRank?LeagueID=' + $rootScope.tempLeagueID + '&Week=' + week.week
		}).then(function (result){
			$scope.comprankings = result.data;
			//buildCpChart(result.data);
			//console.log(result.data);
		});
	};

	function buildCpChart(json){

	}
}

INSULTController.$inject = ['$scope', '$http', '$rootScope'];
function INSULTController($scope, $http, $rootScope) {
	$scope.list = 'Select Team';
	$scope.teams = [];
	$scope.outputInsult =null;

	$scope.$on('leagueID-loaded', function() {
		$http({
			method: 'GET',
			url: './rest/LeagueTeams?LeagueID=' + $rootScope.tempLeagueID
		}).success(function (result) {
			$scope.teams = result;
		});
	});

	$scope.insult = function(selectedTeam){
		//console.log(selectedTeam.teamID);
		$http({
			method: 'GET',
			url: './rest/LeagueInsult?TeamID=' +selectedTeam.teamID 
		}).then(function (result) {
			//console.log('return from insult controller');
			$scope.outputInsult = result.data.key;
		});
	}

};


WaiverController.$inject= ['$scope', '$http'];
function WaiverController($scope, $http){
	$scope.positions =[{"position" : "QB"},
	                   {"position" : "RB"},
	                   {"position" : "WR"},
	                   {"position" : "TE"},
	                   {"position" : "K"},
	                   {"position" : "D/ST"}];
	$scope.players= null;

	$scope.getFreeAgents = function(selectedPos){
		$http({
			method: 'GET',
			url: './rest/WavierWireAid?Position=' + selectedPos.position 
		}).then(function (result) {
			//console.log(result.data);
			$scope.players=result.data;
		});
	}

};


PollsController.$inject = ['$scope', '$http', '$rootScope'];
function PollsController($scope, $http, $rootScope) {
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

	$scope.$on('leagueID-loaded', function() {
		$http({
			method: 'GET',
			url: './rest/LeagueTeams?LeagueID=' + $rootScope.tempLeagueID
		}).success(function (result) {
			$scope.teams = result;
		});
	});

	$scope.submitPoll = function(week, ranks){
		//console.log("in submit poll");
		$http({
			method: 'PUT',
			url: './rest/InsertPoll',
			data: ranks
		}).success(function (result) {
			$scope.teams = result;
		});
	}


	function checkSelected(val) {
		var ret = false;
		$(".pollSelect").each(function() {
			if ($(this).val() === val) {
				ret = true;
			}
		});
		return ret;
	}
	$scope.selectRank = function(el) {
		//console.log(el);
		$('.pollSelect option').each(function() {
			//console.log(el);
			var temp = checkSelected(el.rank);

			if (checkSelected(el.rank) && el.rank != "0") {
				var temp2 = $('.pollSelect option[value=' + el.rank + ']');
				$('.pollSelect option[value=' + el.rank + ']').attr('disabled', true);
			} else {
				var temp3 = $('.pollSelect option[value=' + el.rank + ']');
				$('.pollSelect option[value=' + el.rank + ']').removeAttr('disabled');
			}
		});
	}

}