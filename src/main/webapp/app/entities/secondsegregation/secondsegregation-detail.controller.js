(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SecondsegregationDetailController', SecondsegregationDetailController);

    SecondsegregationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Secondsegregation'];

    function SecondsegregationDetailController($scope, $rootScope, $stateParams, previousState, entity, Secondsegregation) {
        var vm = this;

        vm.secondsegregation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cafvalidityV2App:secondsegregationUpdate', function(event, result) {
            vm.secondsegregation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
