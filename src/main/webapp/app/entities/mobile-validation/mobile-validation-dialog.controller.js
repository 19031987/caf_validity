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
                  		 if( vm.mobileValidation.countCategory1===1600){
                 			alert('Category1 Box is completed please ask for another one') ;
                 			 vm.mobileValidation.boxstatus='category1-completed'
                 		 }
                 		 
                 		 if( vm.mobileValidation.countCategory2===800){
                 	alert('Category2 Box is completed please ask for another one') ; 
                 			if( vm.mobileValidation.boxstatus!=null){
                 				 vm.mobileValidation.boxstatus=' ,category2-completed'
                 			}else{
                 				 vm.mobileValidation.boxstatus='category2-completed'
                 			}
                 		 }
                 		 if( vm.mobileValidation.countCategory3===300){
                 	 alert('Category3 Box is completed please ask for another one') ;
                 	 if( vm.mobileValidation.boxstatus!=null){
          				 vm.mobileValidation.boxstatus=' ,category3-completed'
          			}else{
          				 vm.mobileValidation.boxstatus='category3-completed'
          			}
                 		 }
                 		 if( vm.mobileValidation.countCategory4===300){
                 	 alert('Category4 Box is completed please ask for another one') ;
                 	 if( vm.mobileValidation.boxstatus!=null){
          				 vm.mobileValidation.boxstatus=' ,category4-completed'
          			}else{
          				 vm.mobileValidation.boxstatus='category4-completed'
          			}
                 		 }
                 		 if( vm.mobileValidation.countCategory5===300){
                 		 alert('Category5 Box is completed please ask for another one') ;
         		        	 if( vm.mobileValidation.boxstatus!=null){
         		 				 vm.mobileValidation.boxstatus=' ,category5-completed'
         		 			}else{
         		 				 vm.mobileValidation.boxstatus='category5-completed'
         		 			}
                 		 }
                 		 if( vm.mobileValidation.countCategory1===50||  vm.mobileValidation.countCategory1===100|| vm.mobileValidation.countCategory1===150||
                 				  vm.mobileValidation.countCategory1===200||  vm.mobileValidation.countCategory1===250|| vm.mobileValidation.countCategory1===300|| 
                 				  vm.mobileValidation.countCategory1===350|| vm.mobileValidation.countCategory1===400||
                 				  vm.mobileValidation.countCategory1===450||  vm.mobileValidation.countCategory1===500|| vm.mobileValidation.countCategory1===550|| 
                 				  vm.mobileValidation.countCategory1===600|| vm.mobileValidation.countCategory1===650||
                 				  vm.mobileValidation.countCategory1===700||  vm.mobileValidation.countCategory1===750){
                  			alert('For Category1 : Lot is completed') ;
                  		 }
                  		 
                 		 if( vm.mobileValidation.countCategory1===50||  vm.mobileValidation.countCategory1===100|| vm.mobileValidation.countCategory1===150||
                 				  vm.mobileValidation.countCategory1===200||  vm.mobileValidation.countCategory1===250|| vm.mobileValidation.countCategory1===300|| 
                 				  vm.mobileValidation.countCategory1===350|| vm.mobileValidation.countCategory1===400||
                 				  vm.mobileValidation.countCategory1===450||  vm.mobileValidation.countCategory1===500|| vm.mobileValidation.countCategory1===550|| 
                 				  vm.mobileValidation.countCategory1===600|| vm.mobileValidation.countCategory1===650||
                 				  vm.mobileValidation.countCategory1===700||  vm.mobileValidation.countCategory1===750|| vm.mobileValidation.countCategory1===800|| 
                 				  vm.mobileValidation.countCategory1===850|| vm.mobileValidation.countCategory1===900||
                 				  vm.mobileValidation.countCategory1===950||  vm.mobileValidation.countCategory1===1000|| vm.mobileValidation.countCategory1===1050|| 
                 				  vm.mobileValidation.countCategory1===1100|| vm.mobileValidation.countCategory1===1150||
                 				  vm.mobileValidation.countCategory1===1200||  vm.mobileValidation.countCategory1===1250|| vm.mobileValidation.countCategory1===1300|| 
                 				  vm.mobileValidation.countCategory1===1350|| vm.mobileValidation.countCategory1===1400||
                 				  vm.mobileValidation.countCategory1===1450||  vm.mobileValidation.countCategory1===1500
                 				 ||  vm.mobileValidation.countCategory1===1550){
                 			 alert('For Category2 : Lot is completed') ;
                  		 }
                 		 if( vm.mobileValidation.countCategory3===50||  vm.mobileValidation.countCategory3===100|| vm.mobileValidation.countCategory3===150||
                 				  vm.mobileValidation.countCategory3===200||  vm.mobileValidation.countCategory3===250){
                  			alert('For Category3 : Lot is completed') ;	 
                  		 }
                 		 if( vm.mobileValidation.countCategory4===50||  vm.mobileValidation.countCategory4===100|| vm.mobileValidation.countCategory4===150||
                 				  vm.mobileValidation.countCategory4===200||  vm.mobileValidation.countCategory4===250){
                  			alert('For Category4 : Lot is completed') ;
                 		 }
                 		 if( vm.mobileValidation.countCategory5===50||  vm.mobileValidation.countCategory5===100|| vm.mobileValidation.countCategory5===150||
                 				  vm.mobileValidation.countCategory5===200||  vm.mobileValidation.countCategory5===250){
                  			alert('For Category5 : Lot is completed') ;
                 		 }
                  		
            	    	 MobileValidation.save(vm.mobileValidations[i], onSaveSuccess, onSaveError);
            	    }
            	}
               
            }
        }
 
        init();
        function init(){
        	
        	MobileValidation.getSystemByName(vm.mobileValidation,onSuccess,onError);
        	 function onSuccess(data){
        		if(data.user != null){
        			vm.mobileValidation.user=data.user
        			 var array =  data.boxassign.split(",");
                 	 
     	           	if(array[0].indexOf('E1A') > -1){
     	           		vm.mobileValidation.category1 = array[0];
     	           	}
     	           	if(array[1].indexOf('EC1') > -1){
     	           		vm.mobileValidation.category2 = array[1];
     	           	}
     	           	if(array[2].indexOf('EC2')  > -1){
     	           	 vm.mobileValidation.category3= array[2]; 
     	           	}
     	           	if(array[3].indexOf('EC3')  > -1){
     	           	  vm.mobileValidation.category4  =  array[3];
     	           	}
     	           	if(array[4].indexOf('EDA')  > -1){
     	           	 vm.mobileValidation.category5 = array[4];
     	           	}
    				}
        		MobileValidation.getDetailsByName(vm.mobilevalidation,onSuccessName);
        			function onSuccessName(result){
        				if(result != null){
        					vm.mobileValidation.sourcebox = result.sourcebox;
        					vm.mobileValidation.countCategory1 = result.countCategory1;
        	        	   vm.mobileValidation.countCategory2 = result.countCategory2;
        	        	   vm.mobileValidation.countCategory3 = result.countCategory3;
        	        	   vm.mobileValidation.countCategory4 = result.countCategory4;
        	        	   vm.mobileValidation.countCategory5 = result.countCategory5;
        		           vm.mobileValidation.category1 = result.category1;
        		           vm.mobileValidation.category2 = result.category2;
        		           vm.mobileValidation.category3 = result.category3;
        		           vm.mobileValidation.category4 = result.category4;
        		           vm.mobileValidation.category5 = result.category5;
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
