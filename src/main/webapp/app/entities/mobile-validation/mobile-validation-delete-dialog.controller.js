(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('MobileValidationDeleteController',MobileValidationDeleteController);

    MobileValidationDeleteController.$inject = ['$uibModalInstance', 'entity', 'MobileValidation'];

    function MobileValidationDeleteController($uibModalInstance, entity, MobileValidation) {
        var vm = this;

        vm.mobileValidation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MobileValidation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
