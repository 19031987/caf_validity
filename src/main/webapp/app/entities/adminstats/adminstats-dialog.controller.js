(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('AdminstatsDialogController', AdminstatsDialogController);

    AdminstatsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Adminstats'];

    function AdminstatsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Adminstats) {
        var vm = this;

        vm.adminstats = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.adminstats.id !== null) {
                Adminstats.update(vm.adminstats, onSaveSuccess, onSaveError);
            } else {
                Adminstats.save(vm.adminstats, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cafvalidityV2App:adminstatsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
