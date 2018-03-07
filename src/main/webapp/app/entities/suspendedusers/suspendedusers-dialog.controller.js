(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SuspendedusersDialogController', SuspendedusersDialogController);

    SuspendedusersDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Suspendedusers'];

    function SuspendedusersDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Suspendedusers) {
        var vm = this;

        vm.suspendedusers = entity;
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
            if (vm.suspendedusers.id !== null) {
                Suspendedusers.update(vm.suspendedusers, onSaveSuccess, onSaveError);
            } else {
            	vm.suspendedusers.count =vm.suspendedusers.count +1;
                Suspendedusers.save(vm.suspendedusers, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
           if(vm.suspendedusers.count==50||vm.suspendedusers.count==100||vm.suspendedusers.count==150||
        		   vm.suspendedusers.count==200||vm.suspendedusers.count==250||vm.suspendedusers.count==300){
        	   alert('Lot Completed')
           }
           if(vm.suspendedusers.count==300){
        	   alert('Box Completed');
        	   vm.suspendedusers.count =0;
    			  vm.scancaf.centralbarcode= 'SUS'+increment_alphanumeric_str(vm.scancaf.category1.substring(3, 8));
           }
           
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.userdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
