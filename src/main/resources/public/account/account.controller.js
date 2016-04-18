(function () {
    'use strict';
    angular
        .module('app')
        .controller('AccountController', AccountController);
    

    AccountController.$inject = ['$location', '$rootScope', 'UserService', 'FlashService', 'AuthenticationService'];
    function AccountController($location, $rootScope, UserService, FlashService, AuthenticationService) {
        var vm = this;

        vm.edit = edit;
        vm.user = $rootScope.globals.currentUser;

        function edit() {
            vm.dataLoading = true;
            
            vm.user.name = vm.name
            vm.user.email = vm.email
            vm.user.password = vm.password
            
            UserService.Update(vm.user).then( function (response) {
            	console.log(response);
                if (response.status == "200") {
                	console.log(response.data);
                    AuthenticationService.ClearCredentials();
                    $location.path('/login');
                } else {
                    FlashService.Error(response.message);
                    vm.dataLoading = false;
                }
            });
        };
    }

})();
