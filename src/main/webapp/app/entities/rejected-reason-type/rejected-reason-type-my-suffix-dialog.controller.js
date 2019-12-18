(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('RejectedReasonTypeMySuffixDialogController', RejectedReasonTypeMySuffixDialogController);

    RejectedReasonTypeMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RejectedReasonType', 'RejectedReasons'];

    function RejectedReasonTypeMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RejectedReasonType, RejectedReasons) {
        var vm = this;

        vm.rejectedReasonType = entity;
        vm.clear = clear;
        vm.save = save;
        vm.rejectedreasons = RejectedReasons.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rejectedReasonType.id !== null) {
                RejectedReasonType.update(vm.rejectedReasonType, onSaveSuccess, onSaveError);
            } else {
                RejectedReasonType.save(vm.rejectedReasonType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('termAuditAoApp:rejectedReasonTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
