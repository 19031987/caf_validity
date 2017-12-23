(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SuspendedusersDetailController', SuspendedusersDetailController);

    SuspendedusersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Suspendedusers'];

    function SuspendedusersDetailController($scope, $rootScope, $stateParams, previousState, entity, Suspendedusers) {
        var vm = this;

        vm.suspendedusers = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:suspendedusersUpdate', function(event, result) {
            vm.suspendedusers = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
