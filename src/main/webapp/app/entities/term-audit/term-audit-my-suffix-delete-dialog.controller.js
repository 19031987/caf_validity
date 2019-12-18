(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('TermAuditMySuffixDeleteController',TermAuditMySuffixDeleteController);

    TermAuditMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'TermAudit'];

    function TermAuditMySuffixDeleteController($uibModalInstance, entity, TermAudit) {
        var vm = this;

        vm.termAudit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TermAudit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
