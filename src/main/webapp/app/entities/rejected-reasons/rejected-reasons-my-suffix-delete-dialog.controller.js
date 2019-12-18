(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('RejectedReasonsMySuffixDeleteController',RejectedReasonsMySuffixDeleteController);

    RejectedReasonsMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'RejectedReasons'];

    function RejectedReasonsMySuffixDeleteController($uibModalInstance, entity, RejectedReasons) {
        var vm = this;

        vm.rejectedReasons = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RejectedReasons.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
