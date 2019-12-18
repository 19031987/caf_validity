(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('RejectedReasonsMySuffixDetailController', RejectedReasonsMySuffixDetailController);

    RejectedReasonsMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RejectedReasons', 'RejectedReasonType'];

    function RejectedReasonsMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, RejectedReasons, RejectedReasonType) {
        var vm = this;

        vm.rejectedReasons = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('termAuditAoApp:rejectedReasonsUpdate', function(event, result) {
            vm.rejectedReasons = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
