(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('MobilevalidationDialogController', MobilevalidationDialogController);

    MobilevalidationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Mobilevalidation'];

    function MobilevalidationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Mobilevalidation) {
        var vm = this;

        vm.mobilevalidation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mobilevalidation.id !== null) {
                Mobilevalidation.update(vm.mobilevalidation, onSaveSuccess, onSaveError);
            } else {
                Mobilevalidation.save(vm.mobilevalidation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cafvalidityV2App:mobilevalidationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.userDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
