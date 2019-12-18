(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SecondsegregationDeleteController',SecondsegregationDeleteController);

    SecondsegregationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Secondsegregation'];

    function SecondsegregationDeleteController($uibModalInstance, entity, Secondsegregation) {
        var vm = this;

        vm.secondsegregation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Secondsegregation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
