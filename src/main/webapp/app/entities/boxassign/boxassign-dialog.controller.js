(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('BoxassignDialogController', BoxassignDialogController);

    BoxassignDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Boxassign'];

    function BoxassignDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Boxassign) {
        var vm = this;

        vm.boxassign = entity;
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
            if (vm.boxassign.id !== null) {
                Boxassign.update(vm.boxassign, onSaveSuccess, onSaveError);
            } else {
                Boxassign.save(vm.boxassign, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cafvalidityV2App:boxassignUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
        $scope.users = Boxassign.users();
        
        $scope.catChange = function(){
        	
        	Boxassign.getCatLatest(vm.boxassign,onSave);
        	
        	 function onSave(result){
           		 var boxassign = result;
           		 
           	if(vm.boxassign.churntype==='category_1'){
           		vm.boxassign.boxassign = 'E1A'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8)); 
           	}
           	if(vm.boxassign.churntype==='category_2'){
           		vm.boxassign.boxassign = 'EC1'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8)); 
           	}
           	if(vm.boxassign.churntype==='category_3'){
           		vm.boxassign.boxassign = 'EC2'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8)); 
           	}
           	if(vm.boxassign.churntype==='category_4'){
           		vm.boxassign.boxassign = 'EC3'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8)); 
           	}
           	if(vm.boxassign.churntype==='category_5'){
           		vm.boxassign.boxassign = 'EDA'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8)); 
           	}
           	
         }
        	
        }
        
        function increment_alphanumeric_str(str){
            var numeric = str.match(/\d+$/)[0];
            var prefix = str.split(numeric)[0];

            function increment_string_num(str){
                var inc = String(parseInt(str)+1);
                return str.slice(0, str.length-inc.length)+inc;
            }

            return prefix+increment_string_num(numeric);
        }

       
    }
})();
