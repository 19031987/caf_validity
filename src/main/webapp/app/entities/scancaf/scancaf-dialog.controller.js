(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('ScancafDialogController', ScancafDialogController);

    ScancafDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Scancaf','$cookies','$cookieStore'];

    function ScancafDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Scancaf,$cookies,$cookieStore) {
        var vm = this;
        $scope.lastcompletedbox = $cookieStore.get('completed');
        $scope.lastsourcebox = $cookieStore.get('sourcecompleted')
        vm.scancaf = entity;
        vm.cafbarcodecheck = null;
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
            if (vm.scancaf.id !== null) {
                Scancaf.update(vm.scancaf, onSaveSuccess, onSaveError);
            } else {
                Scancaf.save(vm.scancaf, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cafvalidityV2App:scancafUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.userdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
       
        //watchCollection
        $scope.$watch('vm.scancaf.sourcebox', function(){
        	if(vm.scancaf.sourcebox.length ===8){
        		Scancaf.getSource(vm.scancaf.sourcebox,onSaveSuccess);
        	}
        	 function onSaveSuccess (result) {
        		

           	  Scancaf.getBoxCount(vm.scancaf,onSaveSuccess1);
           
           function onSaveSuccess1 (result) {
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
	           
	           if(vm.scancaf.countCategory1===300){
         			 
          			$cookieStore.put('completed', vm.scancaf.category1);
          				vm.scancaf.countCategory1= 0;
          			  vm.scancaf.category1= 'EDA'+increment_alphanumeric_str(vm.scancaf.category1.substring(3, 8));
          		      $scope.lastcompletedbox = $cookieStore.get('completed')

          			  
          		  }
          		  
          		  if(vm.scancaf.countCategory2===300){
          			  $cookieStore.put('completed', vm.scancaf.category2);
          			  vm.scancaf.countCategory2= 0;
          			  vm.scancaf.category2= 'E1A'+increment_alphanumeric_str(vm.scancaf.category2.substring(3, 8));
          			  $scope.lastcompletedbox = $cookieStore.get('completed')
      		       }
          		  if(vm.scancaf.countCategory3===300){
          			$cookieStore.put('completed', vm.scancaf.category3);
        			  vm.scancaf.countCategory3= 0;
        			  vm.scancaf.category3= 'EC1'+increment_alphanumeric_str(vm.scancaf.category3.substring(3, 8));
        			  $scope.lastcompletedbox = $cookieStore.get('completed')
          		  }
          		  if(vm.scancaf.countCategory4===300){
          			$cookieStore.put('completed', vm.scancaf.category4);
      			  vm.scancaf.countCategory4= 0;
      			  vm.scancaf.category4= 'EC2'+increment_alphanumeric_str(vm.scancaf.category4.substring(3, 8));
      			  $scope.lastcompletedbox = $cookieStore.get('completed')}
          		  if(vm.scancaf.countCategory5===300){
          			$cookieStore.put('completed', vm.scancaf.category5);
      			  vm.scancaf.countCategory5= 0;
      			  vm.scancaf.category5= 'EC3'+increment_alphanumeric_str(vm.scancaf.category5.substring(3, 8));
      			  $scope.lastcompletedbox = $cookieStore.get('completed')
          		  }
	           }
        		 
        	 }
        });
        
        $scope.$watch('vm.scancaf.cafbarcode', function(){
        	if(vm.scancaf.cafbarcode.length ===13){
        		 vm.scancaf.colorcode ='';
        		 vm.scancaf.mobilenumber='';
        		  Scancaf.getCaf(vm.scancaf,onSaveSuccess);
        		
        	}
        	 function onSaveSuccess (result) {
               //  $scope.$emit('cafvalidityV2App:scancafUpdate', result);
                 //$uibModalInstance.close(result);
        		 vm.scancaf.centralbarcode =vm.scancaf.cafbarcode;
        		 vm.scancaf.caftype = result.caftype;
        		 if(vm.scancaf.caftype==='G3y'){
        			 vm.scancaf.colorcode = 'red';
        			 vm.scancaf.countCategory1 =  vm.scancaf.countCategory1+1;
        		 }
        		 if(vm.scancaf.caftype==='Active'){
        			 vm.scancaf.colorcode = 'green';
        			 vm.scancaf.countCategory2 =  vm.scancaf.countCategory2+1;
        		 }
        		 if(vm.scancaf.caftype==='L1'){
        			 vm.scancaf.colorcode = 'white';
        			 vm.scancaf.countCategory3 =  vm.scancaf.countCategory3+1;
        		 }
        		 if(vm.scancaf.caftype==='0-1'){
        			 vm.scancaf.colorcode = 'yellow';
        			 vm.scancaf.countCategory4 =  vm.scancaf.countCategory4+1;
        		 }
        		 if(vm.scancaf.caftype==='1-2'){
        			 vm.scancaf.colorcode = 'blue';
        			 vm.scancaf.countCategory5 =  vm.scancaf.countCategory5+1;
        		 }
        		
        		 
        		 vm.scancaf.mobilenumber = result.mobilenumber;
        		 vm.scancaf.cafbarcode = result.cafbarcode;
        		 
        		
        		
        		 
        		 if(vm.scancaf.countCategory1===300){
        			alert('Category1 Box is completed please ask for another one') ;
        			vm.scancaf.boxstatus='category1-completed'
        		 }
        		 
        		 if(vm.scancaf.countCategory2===300){
        	alert('Category2 Box is completed please ask for another one') ; 
        			if(vm.scancaf.boxstatus!=null){
        				vm.scancaf.boxstatus=' ,category2-completed'
        			}else{
        				vm.scancaf.boxstatus='category2-completed'
        			}
        		 }
        		 if(vm.scancaf.countCategory3===300){
        	 alert('Category3 Box is completed please ask for another one') ;
        	 if(vm.scancaf.boxstatus!=null){
 				vm.scancaf.boxstatus=' ,category3-completed'
 			}else{
 				vm.scancaf.boxstatus='category3-completed'
 			}
        		 }
        		 if(vm.scancaf.countCategory4===300){
        	 alert('Category4 Box is completed please ask for another one') ;
        	 if(vm.scancaf.boxstatus!=null){
 				vm.scancaf.boxstatus=' ,category4-completed'
 			}else{
 				vm.scancaf.boxstatus='category4-completed'
 			}
        		 }
        		 if(vm.scancaf.countCategory5===300){
        		 alert('Category5 Box is completed please ask for another one') ;
		        	 if(vm.scancaf.boxstatus!=null){
		 				vm.scancaf.boxstatus=' ,category5-completed'
		 			}else{
		 				vm.scancaf.boxstatus='category5-completed'
		 			}
        		 }
        		 if(vm.scancaf.countCategory1===50|| vm.scancaf.countCategory1===100||vm.scancaf.countCategory1===150||
        				 vm.scancaf.countCategory1===200|| vm.scancaf.countCategory1===250){
         			alert('For Category1 : Lot is completed') ;
         		 }
         		 
        		 if(vm.scancaf.countCategory2===50|| vm.scancaf.countCategory2===100||vm.scancaf.countCategory2===150||
        				 vm.scancaf.countCategory2===200|| vm.scancaf.countCategory2===250){
        			 alert('For Category2 : Lot is completed') ;
         		 }
        		 if(vm.scancaf.countCategory3===50|| vm.scancaf.countCategory3===100||vm.scancaf.countCategory3===150||
        				 vm.scancaf.countCategory3===200|| vm.scancaf.countCategory3===250){
         			alert('For Category3 : Lot is completed') ;	 
         		 }
        		 if(vm.scancaf.countCategory4===50|| vm.scancaf.countCategory4===100||vm.scancaf.countCategory4===150||
        				 vm.scancaf.countCategory4===200|| vm.scancaf.countCategory4===250){
         			alert('For Category4 : Lot is completed') ;
        		 }
        		 if(vm.scancaf.countCategory5===50|| vm.scancaf.countCategory5===100||vm.scancaf.countCategory5===150||
        				 vm.scancaf.countCategory5===200|| vm.scancaf.countCategory5===250){
         			alert('For Category5 : Lot is completed') ;
        		 }
        		 // save functionality is being called
        		 Scancaf.save(vm.scancaf,onSaveSuccessFinal);
        		 
        		 function onSaveSuccessFinal() {
        			 vm.scancaf.cafbarcode ='';
           		  if(vm.scancaf.countCategory1===300){
           			 
           			$cookieStore.put('completed', vm.scancaf.category1);
           				vm.scancaf.countCategory1= 0;
           			  vm.scancaf.category1= 'EDA'+increment_alphanumeric_str(vm.scancaf.category1.substring(3, 8));
           		      $scope.lastcompletedbox = $cookieStore.get('completed')

           			  
           		  }
           		  
           		  if(vm.scancaf.countCategory2===300){
           			  $cookieStore.put('completed', vm.scancaf.category2);
           			  vm.scancaf.countCategory2= 0;
           			  vm.scancaf.category2= 'E1A'+increment_alphanumeric_str(vm.scancaf.category2.substring(3, 8));
           			  $scope.lastcompletedbox = $cookieStore.get('completed')
       		       }
           		  if(vm.scancaf.countCategory3===300){
           			$cookieStore.put('completed', vm.scancaf.category3);
         			  vm.scancaf.countCategory3= 0;
         			  vm.scancaf.category3= 'EC1'+increment_alphanumeric_str(vm.scancaf.category3.substring(3, 8));
         			  $scope.lastcompletedbox = $cookieStore.get('completed')
           		  }
           		  if(vm.scancaf.countCategory4===300){
           			$cookieStore.put('completed', vm.scancaf.category4);
       			  vm.scancaf.countCategory4= 0;
       			  vm.scancaf.category4= 'EC2'+increment_alphanumeric_str(vm.scancaf.category4.substring(3, 8));
       			  $scope.lastcompletedbox = $cookieStore.get('completed')}
           		  if(vm.scancaf.countCategory5===300){
           			$cookieStore.put('completed', vm.scancaf.category5);
       			  vm.scancaf.countCategory5= 0;
       			  vm.scancaf.category5= 'EC3'+increment_alphanumeric_str(vm.scancaf.category5.substring(3, 8));
       			  $scope.lastcompletedbox = $cookieStore.get('completed')
           		  }
                }

        		 
             }

        	         });
        
        $scope.cat1 = function(){
        	
            if(vm.scancaf.category1.length===12){
            	  Scancaf.getBoxCount(vm.scancaf,onSaveSuccess);
            }
            function onSaveSuccess (result) {
            vm.scancaf.countCategory1 = result.countCategory1;
            }
            }
        
  $scope.validate1 = function(){
        	
            if(vm.scancaf.category1.length===8){
            	  if(vm.scancaf.category1.substring(0,3)!='EDA'){
            		 alert('Assigned Box in Category 1 is not correct !!! Please Check') 
            	  }
            }
            if(vm.scancaf.category2.length===8){
          	  if(vm.scancaf.category2.substring(0,3)!='E1A'){
          		 alert('Assigned Box in Category 2 is not correct !!! Please Check') 
          	  }
          }
            if(vm.scancaf.category3.length===8){
          	  if(vm.scancaf.category3.substring(0,3)!='EC1'){
          		 alert('Assigned Box in Category 3 is not correct !!! Please Check') 
          	  }
          }
            if(vm.scancaf.category4.length===8){
          	  if(vm.scancaf.category4.substring(0,3)!='EC2'){
          		 alert('Assigned Box in Category 4 is not correct !!! Please Check') 
          	  }
          }
            if(vm.scancaf.category5.length===8){
          	  if(vm.scancaf.category5.substring(0,3)!='EC3'){
          		 alert('Assigned Box in Category 5 is not correct !!! Please Check') 
          	  }
          }

            function onSaveSuccess (result) {
            vm.scancaf.countCategory1 = result.countCategory1;
            }
            }
        	
        $scope.custom = true;
        $scope.toggleCustom = function() {
            $scope.custom = $scope.custom === false ? true: false;
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
        $scope.sourceBoxComplete = function() {
        	
        	$cookieStore.put('sourcecompleted', vm.scancaf.sourcebox);
    		  $scope.lastsourcebox = $cookieStore.get('sourcecompleted')
        	
        };
        
        $scope.cafcheck = function(){
        	if(vm.scancaf.boxstatus.length ===13){
        		  Scancaf.getBox(vm.scancaf,successCaf,errorcaf);
        		 function successCaf (result) {
        			 vm.scancaf = result;
        			 vm.scancaf.boxstatus ='SECOND_LEVEL';
        			 Scancaf.update(vm.scancaf, updatesucess); 
        			 function updatesucess (result) {
        				 vm.scancaf.boxstatus ='';
        			 }
        		 }
        		 function errorcaf (result) {
        			 vm.scancaf= result;
        		 }
        	}
        }
        
        
        $scope.BoxComplete = function() {
        	
        	if (vm.scancaf.sourceboxstaus.indexOf('EDA') > -1)
        	{
        	  vm.scancaf.caftype ='category_1'
        	}
        	if (vm.scancaf.sourceboxstaus.indexOf('E1A') > -1)
        	{
        	  vm.scancaf.caftype ='category_2'
        	}
        	if (vm.scancaf.sourceboxstaus.indexOf('EC1') > -1)
        	{
        	  vm.scancaf.caftype ='category_3'
        	}
        	if (vm.scancaf.sourceboxstaus.indexOf('EC2') > -1)
        	{
        	  vm.scancaf.caftype ='category_4'
        	}
        	if (vm.scancaf.sourceboxstaus.indexOf('EC3') > -1)
        	{
        	  vm.scancaf.caftype ='category_5'
        	}
        	
        	 Scancaf.getOutBox(vm.scancaf,getoutbox,geterrorbox);
        	 
        	 function getoutbox (result) {
        		 
    			 alert(result.boxstatus);
    			
    		 }
    		 function geterrorbox (result) {
    			 alert(result.boxstatus);
    		 }
        	
        };
        
        
        // end of scope
    }
    
    
    
    
   
   
})();
