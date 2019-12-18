(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('RejectedReasonTypeMySuffixDetailController', RejectedReasonTypeMySuffixDetailController);

    RejectedReasonTypeMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RejectedReasonType', 'RejectedReasons'];

    function RejectedReasonTypeMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, RejectedReasonType, RejectedReasons) {
        var vm = this;

        vm.rejectedReasonType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('termAuditAoApp:rejectedReasonTypeUpdate', function(event, result) {
            vm.rejectedReasonType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
