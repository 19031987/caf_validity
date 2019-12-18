(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('UserchangeDeleteController',UserchangeDeleteController);

    UserchangeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Userchange'];

    function UserchangeDeleteController($uibModalInstance, entity, Userchange) {
        var vm = this;

        vm.userchange = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Userchange.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
