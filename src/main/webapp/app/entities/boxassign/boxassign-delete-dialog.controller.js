(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('BoxassignDeleteController',BoxassignDeleteController);

    BoxassignDeleteController.$inject = ['$uibModalInstance', 'entity', 'Boxassign'];

    function BoxassignDeleteController($uibModalInstance, entity, Boxassign) {
        var vm = this;

        vm.boxassign = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Boxassign.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
