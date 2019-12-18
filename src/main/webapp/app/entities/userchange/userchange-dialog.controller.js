(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('UserchangeDialogController', UserchangeDialogController);

    UserchangeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Userchange'];

    function UserchangeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Userchange) {
        var vm = this;

        vm.userchange = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        
        $scope.users = Userchange.users();
        
        function save () {
            vm.isSaving = true;
            if (vm.userchange.id !== null) {
                Userchange.update(vm.userchange, onSaveSuccess, onSaveError);
            } else {
                Userchange.save(vm.userchange, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cafvalidityV2App:userchangeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
