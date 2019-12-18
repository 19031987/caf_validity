(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('RejectedReasonsMySuffixDialogController', RejectedReasonsMySuffixDialogController);

    RejectedReasonsMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RejectedReasons', 'RejectedReasonType'];

    function RejectedReasonsMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RejectedReasons, RejectedReasonType) {
        var vm = this;

        vm.rejectedReasons = entity;
        vm.clear = clear;
        vm.save = save;
        vm.rejectedreasontypes = RejectedReasonType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rejectedReasons.id !== null) {
                RejectedReasons.update(vm.rejectedReasons, onSaveSuccess, onSaveError);
            } else {
                RejectedReasons.save(vm.rejectedReasons, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('termAuditAoApp:rejectedReasonsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
