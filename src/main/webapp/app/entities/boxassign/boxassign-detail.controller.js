(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('BoxassignDetailController', BoxassignDetailController);

    BoxassignDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Boxassign'];

    function BoxassignDetailController($scope, $rootScope, $stateParams, previousState, entity, Boxassign) {
        var vm = this;

        vm.boxassign = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:boxassignUpdate', function(event, result) {
            vm.boxassign = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
