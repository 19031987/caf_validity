(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SuspendedusersDeleteController',SuspendedusersDeleteController);

    SuspendedusersDeleteController.$inject = ['$uibModalInstance', 'entity', 'Suspendedusers'];

    function SuspendedusersDeleteController($uibModalInstance, entity, Suspendedusers) {
        var vm = this;

        vm.suspendedusers = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Suspendedusers.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
