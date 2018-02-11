(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('MobileValidationDialogController', MobileValidationDialogController);

    MobileValidationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MobileValidation'];

    function MobileValidationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MobileValidation) {
        var vm = this;

        vm.mobileValidation = entity;
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
            if (vm.mobileValidation.id !== null) {
                MobileValidation.update(vm.mobileValidation, onSaveSuccess, onSaveError);
            } else {
                MobileValidation.save(vm.mobileValidation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cafvalidityV2App:mobileValidationUpdate', result);
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
