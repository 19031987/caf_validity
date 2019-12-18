(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('RejectedReasonTypeMySuffixDeleteController',RejectedReasonTypeMySuffixDeleteController);

    RejectedReasonTypeMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'RejectedReasonType'];

    function RejectedReasonTypeMySuffixDeleteController($uibModalInstance, entity, RejectedReasonType) {
        var vm = this;

        vm.rejectedReasonType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RejectedReasonType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
