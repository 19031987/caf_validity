(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('AdminstatsDeleteController',AdminstatsDeleteController);

    AdminstatsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Adminstats'];

    function AdminstatsDeleteController($uibModalInstance, entity, Adminstats) {
        var vm = this;

        vm.adminstats = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Adminstats.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
