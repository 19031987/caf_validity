(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('ScancafDetailController', ScancafDetailController);

    ScancafDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Scancaf'];

    function ScancafDetailController($scope, $rootScope, $stateParams, previousState, entity, Scancaf) {
        var vm = this;

        vm.scancaf = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:scancafUpdate', function(event, result) {
            vm.scancaf = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
