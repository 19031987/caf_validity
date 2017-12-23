(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('ScancafDeleteController',ScancafDeleteController);

    ScancafDeleteController.$inject = ['$uibModalInstance', 'entity', 'Scancaf'];

    function ScancafDeleteController($uibModalInstance, entity, Scancaf) {
        var vm = this;

        vm.scancaf = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Scancaf.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
