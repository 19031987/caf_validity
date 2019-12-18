(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('MobilevalidationDeleteController',MobilevalidationDeleteController);

    MobilevalidationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mobilevalidation'];

    function MobilevalidationDeleteController($uibModalInstance, entity, Mobilevalidation) {
        var vm = this;

        vm.mobilevalidation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mobilevalidation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
