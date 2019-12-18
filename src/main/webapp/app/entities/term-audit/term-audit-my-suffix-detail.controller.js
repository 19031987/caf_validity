(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('TermAuditMySuffixDetailController', TermAuditMySuffixDetailController);

    TermAuditMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TermAudit'];

    function TermAuditMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, TermAudit) {
        var vm = this;

        vm.termAudit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('termAuditAoApp:termAuditUpdate', function(event, result) {
            vm.termAudit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
