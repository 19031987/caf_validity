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
        $scope.system = Boxassign.system();


        $scope.catChangeGetLatest = function(){

        	Boxassign.getCatLatestRes(vm.boxassign,onSave);

        	 function onSave(result){
               	 var array = result.boxassign.split(',');

                	if(array[0].indexOf('EKA') > -1){
                		vm.boxassign.boxassign = 'EKA'+increment_alphanumeric_str(array[0].substring(3, 8));
                	}
                	if(array[1].indexOf('EC1') > -1){
                		vm.boxassign.boxassign = vm.boxassign.boxassign+","+ 'EC1'+increment_alphanumeric_str(array[1].substring(3, 8));
                	}
                	if(array[2].indexOf('EC2')  > -1){
                		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'EC2'+increment_alphanumeric_str(array[2].substring(3, 8));
                	}
                	if(array[3].indexOf('EC3')  > -1){
                		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'EC3'+increment_alphanumeric_str(array[3].substring(3, 8));
                	}
                	if(array[4].indexOf('EDA')  > -1){
                		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'EDA'+increment_alphanumeric_str(array[4].substring(3, 8));
                	}
                	if(array[5].indexOf('ERV')  > -1){
                		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'ERV'+increment_alphanumeric_str(array[5].substring(3, 8));
                	}
                	if(array[6].indexOf('ENA')  > -1){
                		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'ENA'+increment_alphanumeric_str(array[6].substring(3, 8));
                	}
                	}
        	 }


        $scope.catChange = function(){

        	Boxassign.getCatLatest(onSave);

        	 function onSave(result){
           		 var boxassign = result;

           	if(vm.boxassign.churntype==='category_1'){
           		vm.boxassign.boxassign = 'EKA'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
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
           	if(vm.boxassign.churntype==='categoryRv'){
           		vm.boxassign.boxassign = 'ERV'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
           	if(vm.boxassign.churntype==='categoryNA'){
           		vm.boxassign.boxassign = 'ENA'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
           	vm.boxassign.boxassign = result.boxassign;

           	if(vm.boxassign.churntype===null){
           	 var array = result.boxassign.split(',');

           	if(array[0].indexOf('EKA') > -1){
           		vm.boxassign.boxassign = 'EKA'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
           	if(array[1].indexOf('EC1') > -1){
           		vm.boxassign.boxassign = vm.boxassign.boxassign+","+ 'EC1'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
           	if(array[2].indexOf('EC2')  > -1){
           		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'EC2'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
           	if(array[3].indexOf('EC3')  > -1){
           		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'EC3'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
           	if(array[4].indexOf('EDA')  > -1){
           		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'EDA'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
           	if(array[5].indexOf('ERV')  > -1){
           		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'ERV'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
           	if(array[6].indexOf('ENA')  > -1){
           		vm.boxassign.boxassign = vm.boxassign.boxassign+","+'ENA'+increment_alphanumeric_str(boxassign.boxassign.substring(3, 8));
           	}
}

         }

        };

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
