var myApp = angular.module('myApp',[]);
myApp.controller('FTJController', function($scope, $http) {
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
});

//Draft Buddy Controller
myApp.controller('DraftBuddyController', function($scope, $http) {
	$scope.players =[];
	$scope.player = "Insult here";
	$http({
		method: 'GET',
		url: './rest/DraftBuddy'
	}).success(function (result) {
		$scope.players = result;
		console.log('success');
	});


	$scope.searchPlayer = function(){
		
		$scope.playerName = "";
		var i = 0;
		angular.forEach($scope.players, function(filterObj , filterKey){
			console.log('Searching for player:' + $scope.playerName);
			i++;
		});

	}

});





