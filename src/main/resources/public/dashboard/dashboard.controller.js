DashboardController.$inject = ['UserService', '$rootScope', '$scope', '$http'];
function DashboardController(UserService, $rootScope, $scope, $http) {
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
		});
	}

	function loadLeagueID(){
		UserService.GetLeagueIDFromUser($rootScope.globals.currentUser.id).then(function(data){
			$rootScope.tempLeagueID = data;
		});
	}

	$scope.FTJController = function() {
		$scope.list1 = 'Select Team 1';
		$scope.list2 = 'Select Team 2';
		//$scope.selectedTeam = null;
		$scope.teams = [];
		$scope.players1 = [];
		$scope.players2 = [];
		$scope.roster1 = null;
		$scope.roster2 = null;
		$scope.players =[];
		$http({
			method: 'GET',
			url: './rest/LeagueTeams?LeagueID=' + $rootScope.tempLeagueID
		}).success(function (result) {
			$scope.teams = result;
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
			}).success(function (result){
				$scope.players = result;
				//players
				if ((($scope.players[0].ftjPoints/($scope.players[0].ftjPoints + 
						$scope.players[1].ftjPoints))>0.45) &&
						($scope.players[0].ftjPoints/($scope.players[0].ftjPoints + 
								$scope.players[1].ftjPoints))<0.55)

				{
					$scope.FTJ = "Yes, this is a fair trade.";
				} else {
					$scope.FTJ = "No, this is not a fair trade.";
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

	$scope.DraftBuddyController = function() {
		$scope.sortType     = 'OverallRank';
		$scope.sortReverse  = false;
		$scope.players = []
		$scope.notClicked = true;
		$scope.buttonClicked = false;
		$scope.startDraft = function (){
			$scope.players = []
			$http({
				method: 'GET',
				url: './rest/DraftBuddy'
			}).success(function (result) {
				$scope.players = result;
			});
			$scope.buttonClicked = true;
			$scope.notClicked = false;
		}

		$scope.endDraft = function (){
			$scope.searchedPlayers = [];
			$scope.selectedPlayers = []; 
			$scope.players = [];
			$scope.buttonClicked = false;
			$scope.notClicked = true;
		}

		//$scope.players.length = 0;

		$scope.setSelected= function(){

			console.log('selecting: ' + $scope.index);
			//$scope.selected = "red";
			$scope.searchPlayer = "";
		}


		$scope.searchedPlayers = [];

		$scope.select = function(){
			console.log("Searching for " + $scope.searchPlayer);
		
			if ($.inArray($scope.searchPlayer, $scope.selectedPlayers) > -1){
        		// var index = $scope.selectedPlayers.indexOf($scope.searchedPlayers);
        		// console.log("found it");
 				// $scope.selectedPlayers.splice(index, 1); 
        	} else {
           	 	$scope.searchedPlayers.push($scope.searchPlayer);
           	 	console.log("selected: " + $scope.searchPlayer);
           	 }
           	  $scope.searchPlayer = "";
		}

        $scope.selIdx= -1;
        $scope.selectedPlayers = [];

        $scope.selUser=function(player,idx){
        	if ($.inArray(player, $scope.selectedPlayers) > -1){
        		var index = $scope.selectedPlayers.indexOf(player);
 				 $scope.selectedPlayers.splice(index, 1); 
 				 var index2 = $scope.searchedPlayers.indexOf(player.Name);
 				 $scope.searchedPlayers.splice(index2, 1);
        	} else {
           	 	$scope.selectedPlayers.push(player);
           	 	$scope.searchedPlayers.push(player.Name);
           	 	console.log("selected: " + player);
           	 }
           	 $scope.inputField = "";
        }

        $scope.userSelected =  function(playerName){
		    return $.inArray(playerName, $scope.searchedPlayers) > -1;
		}


	}

	$scope.SRController = function(){
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
			url: './rest/SmartRank?LeagueID=' + $rootScope.tempLeagueID + '&Week=17'
		}).then(function (result){
			$scope.smartrankings = result.data;
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

	$scope.AwardsController = function(){
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
			url: './rest/Awards?LeagueID=' + $rootScope.tempLeagueID + '&Week=1'
		}).then(function (result){
			$scope.awards = result.data;
			//console.log($scope.awards);
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

	$scope.CPController = function(){
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
			url: './rest/CompositeRank?LeagueID=' + $rootScope.tempLeagueID + '&Week=17'
		}).then(function (result){
			$scope.comprankings = result.data;
			//buildCpChart(result.data);
			//console.log($scope.comprankings);
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

	$scope.INSULTController = function() {
		$scope.list = 'Select Team';
		$scope.teams = [];
		$scope.outputInsult =null;
		$http({
			method: 'GET',
			url: './rest/LeagueTeams?LeagueID=' + $rootScope.tempLeagueID
		}).success(function (result) {
			$scope.teams = result;
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

	}
	$scope.WaiverController = function(){
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

	}

	$scope.PollsController = function() {
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

		$http({
			method: 'GET',
			url: './rest/LeagueTeams?LeagueID=' + $rootScope.tempLeagueID
		}).success(function (result) {
			$scope.teams = result;
		});

		$scope.submitPoll = function(week, ranks){
			console.log("in submit poll");
			console.log($scope.pRanks);
			$http({
				method: 'POST',
				url: './rest/InsertPoll',
				data: $scope.pRanks
			}).success(function (result) {
				$scope.teams = result;
			});
		}
		$scope.selectedRanks = [];
		$scope.addToSelectedRanks = function(rank){
			$scope.selectedRanks.push(rank);
		}
		$scope.pollFilter = function(text){
			for (var i = 0; i < $scope.selectedRanks.length; i++) {
	            if (text.indexOf($scope.selectedRanks[i]) !== -1) {
	                return false;
	            }
	        }
	        return true;
		}
		

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