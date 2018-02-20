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
        vm.search =search;
        vm.mobileValidations ;
        vm.isRowSelected ;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mobileValidation.id !== null) {
            	
            	    	 MobileValidation.update(vm.mobileValidation[i], onSaveSuccess, onSaveError);
            	  
            	
            } else {
            	for (var i=0;i<vm.mobileValidations.length;i++)
            	{
            	    if (vm.mobileValidations[i].isselected === true) 
            	    {
                		if(vm.mobileValidation.colorcode==='G3y'){
                  			 vm.mobileValidation.colorcode = 'red';
                  			 vm.mobileValidation.countCategory1 =  vm.mobileValidation.countCategory1+1;
                  		 }
                  		 if(vm.mobileValidation.colorcode==='Active'){
                  			 vm.mobileValidation.colorcode = 'green';
                  			 vm.mobileValidation.countCategory2 =  vm.mobileValidation.countCategory2+1;
                  		 }
                  		 if(vm.mobileValidation.colorcode==='L1'){
                  			 vm.mobileValidation.colorcode = 'white';
                  			 vm.mobileValidation.countCategory3 =  vm.mobileValidation.countCategory3+1;
                  		 }
                  		 if(vm.mobileValidation.colorcode==='0-1'){
                  			 vm.mobileValidation.colorcode = 'yellow';
                  			 vm.mobileValidation.countCategory4 =  vm.mobileValidation.countCategory4+1;
                  		 }
                  		 if(vm.mobileValidation.colorcode==='1-2'){
                  			 vm.mobileValidation.colorcode = 'blue';
                  			 vm.mobileValidation.countCategory5 =  vm.mobileValidation.countCategory5+1;
                  		 }
                  		
            	    	 MobileValidation.save(vm.mobileValidations[i], onSaveSuccess, onSaveError);
            	    }
            	}
               
            }
        }
 
        init();
        function init(){
        	
        	MobileValidation.getSystemByName(vm.vm.mobileValidation,onSuccess,onError);
        	 function onSuccess(data){
        		if(data.user != null){
        			vm.scancaf.user=data.user
        			 var array =  data.boxassign.split(",");
                 	 
     	           	if(array[0].indexOf('E1A') > -1){
     	           		vm.scancaf.category1 = array[0];
     	           	}
     	           	if(array[1].indexOf('EC1') > -1){
     	           		vm.scancaf.category2 = array[1];
     	           	}
     	           	if(array[2].indexOf('EC2')  > -1){
     	           	 vm.scancaf.category3= array[2]; 
     	           	}
     	           	if(array[3].indexOf('EC3')  > -1){
     	           	  vm.scancaf.category4  =  array[3];
     	           	}
     	           	if(array[4].indexOf('EDA')  > -1){
     	           	 vm.scancaf.category5 = array[4];
     	           	}
    				}
        		MobileValidation.getDetailsByName(vm.scancaf,onSuccessName);
        			function onSuccessName(result){
        				if(result != null){
        					vm.scancaf.sourcebox = result.sourcebox;
        				 vm.scancaf.countCategory1 = result.countCategory1;
        	        	   vm.scancaf.countCategory2 = result.countCategory2;
        	        	   vm.scancaf.countCategory3 = result.countCategory3;
        	        	   vm.scancaf.countCategory4 = result.countCategory4;
        	        	   vm.scancaf.countCategory5 = result.countCategory5;
        		           vm.scancaf.category1 = result.category1;
        		           vm.scancaf.category2 = result.category2;
        		           vm.scancaf.category3 = result.category3;
        		           vm.scancaf.category4 = result.category4;
        		           vm.scancaf.category5 = result.category5;
        				}
        			}
        				
        		}
        				
}
        
        function search (){
        	MobileValidation.getByMobileNum(vm.mobileValidation, onSaveSuccessmob, onSaveErrormob);
        	function onSaveSuccessmob(result){
        		vm.mobileValidations = result;
        		

        	}
        	function onSaveErrormob(){
        		
        	}
        }
        
    

        function onSaveSuccess (result) {
          /*  $scope.$emit('cafvalidityV2App:mobileValidationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;*/
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
