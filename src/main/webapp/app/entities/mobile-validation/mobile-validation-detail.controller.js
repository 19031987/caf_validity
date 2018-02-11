(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('MobileValidationDetailController', MobileValidationDetailController);

    MobileValidationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MobileValidation'];

    function MobileValidationDetailController($scope, $rootScope, $stateParams, previousState, entity, MobileValidation) {
        var vm = this;

        vm.mobileValidation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:mobileValidationUpdate', function(event, result) {
            vm.mobileValidation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
