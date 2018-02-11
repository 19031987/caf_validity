(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SystemDetailController', SystemDetailController);

    SystemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'System'];

    function SystemDetailController($scope, $rootScope, $stateParams, previousState, entity, System) {
        var vm = this;

        vm.system = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:systemUpdate', function(event, result) {
            vm.system = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
