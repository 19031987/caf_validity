(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SourceboxDialogController', SourceboxDialogController);

    SourceboxDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sourcebox'];

    function SourceboxDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sourcebox) {
        var vm = this;

        vm.sourcebox = entity;
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
            if (vm.sourcebox.id !== null) {
                Sourcebox.update(vm.sourcebox, onSaveSuccess, onSaveError);
            } else {
                Sourcebox.save(vm.sourcebox, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cafvalidityV2App:sourceboxUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createddate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
