(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('MobilevalidationDetailController', MobilevalidationDetailController);

    MobilevalidationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Mobilevalidation'];

    function MobilevalidationDetailController($scope, $rootScope, $stateParams, previousState, entity, Mobilevalidation) {
        var vm = this;

        vm.mobilevalidation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:mobilevalidationUpdate', function(event, result) {
            vm.mobilevalidation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
