ReportBugController.$inject = ['$rootScope', '$http', 'UserService'];
function ReportBugController($rootScope, $http, UserService) {
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

