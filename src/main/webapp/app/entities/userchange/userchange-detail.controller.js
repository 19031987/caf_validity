(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('UserchangeDetailController', UserchangeDetailController);

    UserchangeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Userchange'];

    function UserchangeDetailController($scope, $rootScope, $stateParams, previousState, entity, Userchange) {
        var vm = this;

        vm.userchange = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:userchangeUpdate', function(event, result) {
            vm.userchange = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
