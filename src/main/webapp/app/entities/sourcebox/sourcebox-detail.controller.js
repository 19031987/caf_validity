(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SourceboxDetailController', SourceboxDetailController);

    SourceboxDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sourcebox'];

    function SourceboxDetailController($scope, $rootScope, $stateParams, previousState, entity, Sourcebox) {
        var vm = this;

        vm.sourcebox = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:sourceboxUpdate', function(event, result) {
            vm.sourcebox = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
