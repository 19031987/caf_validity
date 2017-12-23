(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SourceboxDeleteController',SourceboxDeleteController);

    SourceboxDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sourcebox'];

    function SourceboxDeleteController($uibModalInstance, entity, Sourcebox) {
        var vm = this;

        vm.sourcebox = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sourcebox.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
