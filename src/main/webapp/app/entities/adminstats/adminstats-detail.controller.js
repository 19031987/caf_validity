(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('AdminstatsDetailController', AdminstatsDetailController);

    AdminstatsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Adminstats'];

    function AdminstatsDetailController($scope, $rootScope, $stateParams, previousState, entity, Adminstats) {
        var vm = this;

        vm.adminstats = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:adminstatsUpdate', function(event, result) {
            vm.adminstats = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
