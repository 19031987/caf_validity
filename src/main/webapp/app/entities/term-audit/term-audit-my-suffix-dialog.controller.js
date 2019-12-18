(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .controller('TermAuditMySuffixDialogController', TermAuditMySuffixDialogController);

    TermAuditMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TermAudit'];

    function TermAuditMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TermAudit) {
        var vm = this;
		var old ;
		var data;
        vm.termAudit = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        $scope.reason1 ="";
        $scope.reason2 = "";
        $scope.reason3 = "";
        $scope.reason4 = "";
        $scope.reason5 = "";
        $scope.reason6 = "";
        $scope.reason7 = "";
        $scope.reason8 = "";
        $scope.reason9 = "";
        $scope.reason10 = "";
        $scope.reason11 = "";
        
        
    	

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.termAudit.id !== null) {
				
            	vm.termAudit.rejectedreason +  $scope.reason1+", "+ $scope.reason2
            	+", "+ $scope.reason3
            	+", "+ $scope.reason4
            	+", "+ $scope.reason5
            	+", "+ $scope.reason6
            	+", "+ $scope.reason7
            	+", "+ $scope.reason8
            	+", "+ $scope.reason9
            	+", "+ $scope.reason10
            	+", "+ $scope.reason11
                TermAudit.update(vm.termAudit, onSaveSuccess, onSaveError);
				
				function onSaveSuccess (result) {
				//  $scope.$emit('termApp:termAuditUpdate', result);
				//  vm.termAudit.typeofdoc = old.typeofdoc;
            //$uibModalInstance.close(result);
			//vm.termAudit = new Object();
         	   
				
				 vm.termAudit.customernamestatus = true;
		            vm.termAudit.fathernamestatus= true;
		            vm.termAudit.doastatus= true;
		            vm.termAudit.aonamestatus= true;
		            vm.termAudit.identityproofstatus= true;
		            vm.termAudit.addressproofstatus= true;
		            vm.termAudit.photo=true;
		            vm.termAudit.checkcafstatus= true;
		            vm.termAudit.pointsalenamestatus=true;
		            vm.termAudit.pointsalecode=true;
		            vm.termAudit.pointsaleaddressstatus=true;

		            vm.termAudit.termno= null;
		            vm.termAudit.state= null;
		            vm.termAudit.connection= null;
		            vm.termAudit.connectiontype= null;
		            vm.termAudit.barcode= null;
		            vm.termAudit.nameofcustomer= null;
		            vm.termAudit.fathername= null;
		            vm.termAudit.doa= null;
		            vm.termAudit.aoname= null;
		            vm.termAudit.identityproof= null;
		            vm.termAudit.addressproof= null;
		            vm.termAudit.address= null;
		            vm.termAudit.caf= null;
		            vm.termAudit.activationsource= null;
		            vm.termAudit.vendor= null;
		            vm.termAudit.dob= null;
					vm.termAudit.mobilenumber=null;
	                
	                $scope.reason1 ="";
	                $scope.reason2 = "";
	                $scope.reason3 = "";
	                $scope.reason4 = "";
	                $scope.reason5 = "";
	                $scope.reason6 = "";
	                $scope.reason7 = "";
	                $scope.reason8 = "";
	                $scope.reason9 = "";
	                $scope.reason10 = "";
	                $scope.reason11 = "";
					
        }

        function onSaveError () {
           // vm.isSaving = false;
        	vm.termAudit.mobilenumber = null;
        	 $scope.reason1 ="";
             $scope.reason2 = "";
             $scope.reason3 = "";
             $scope.reason4 = "";
             $scope.reason5 = "";
             $scope.reason6 = "";
             $scope.reason7 = "";
             $scope.reason8 = "";
             $scope.reason9 = "";
             $scope.reason10 = "";
             $scope.reason11 = "";
        }
            } else {
				old = vm.termAudit
				
            	vm.termAudit.rejectedreason = 
            		$scope.reason1+", "+ $scope.reason2
            	+", "+ $scope.reason3
            	+", "+ $scope.reason4
            	+", "+ $scope.reason5
            	+", "+ $scope.reason6
            	+", "+ $scope.reason7
            	+", "+ $scope.reason8
            	+", "+ $scope.reason9
            	+", "+ $scope.reason10
            	+", "+ $scope.reason11
                TermAudit.save(vm.termAudit, onSaveSuccess, onSaveError);
            }
			function onSaveSuccess (result) {
				//  $scope.$emit('termApp:termAuditUpdate', result);
				//  vm.termAudit.typeofdoc = old.typeofdoc;
            //$uibModalInstance.close(result);
			//vm.termAudit = new Object();
         	   
				vm.termAudit.typeofdoc = old.typeofdoc;
				 vm.termAudit.customernamestatus = true;
		            vm.termAudit.fathernamestatus= true;
		            vm.termAudit.doastatus= true;
		            vm.termAudit.aonamestatus= true;
		            vm.termAudit.identityproofstatus= true;
		            vm.termAudit.addressproofstatus= true;
		            vm.termAudit.photo=true;
		            vm.termAudit.checkcafstatus= true;
		            vm.termAudit.pointsalenamestatus=true;
		            vm.termAudit.pointsalecode=true;
		            vm.termAudit.pointsaleaddressstatus=true;
		            vm.termAudit.mobilenumber= null;
		            vm.termAudit.termno= null;
		            vm.termAudit.state= null;
		            vm.termAudit.connection= null;
		            vm.termAudit.connectiontype= null;
		            vm.termAudit.barcode= null;
		            vm.termAudit.nameofcustomer= null;
		            vm.termAudit.fathername= null;
		            vm.termAudit.doa= null;
		            vm.termAudit.aoname= null;
		            vm.termAudit.identityproof= null;
		            vm.termAudit.addressproof= null;
		            vm.termAudit.address= null;
		            vm.termAudit.caf= null;
		            vm.termAudit.activationsource= null;
		            vm.termAudit.vendor= null;
		            vm.termAudit.dob= null;
	                
	                $scope.reason1 ="";
	                $scope.reason2 = "";
	                $scope.reason3 = "";
	                $scope.reason4 = "";
	                $scope.reason5 = "";
	                $scope.reason6 = "";
	                $scope.reason7 = "";
	                $scope.reason8 = "";
	                $scope.reason9 = "";
	                $scope.reason10 = "";
	                $scope.reason11 = "";
					
        }

        function onSaveError () {
           // vm.isSaving = false;
        	vm.termAudit.mobilenumber = null;
        	 $scope.reason1 ="";
             $scope.reason2 = "";
             $scope.reason3 = "";
             $scope.reason4 = "";
             $scope.reason5 = "";
             $scope.reason6 = "";
             $scope.reason7 = "";
             $scope.reason8 = "";
             $scope.reason9 = "";
             $scope.reason10 = "";
             $scope.reason11 = "";
        }
        }
        
        
        
        $scope.nameClick= function(){
                    	 TermAudit.getReasons('1', onSaveSuccess, onSaveError);
                    	 function onSaveSuccess (data) {
                        		$scope.nameReasonOptions =data;
                             	  
                        		
                               }
                    	 function onSaveError () {
                           console.log("raghu here");
                          }

                    	 
                    
                }
               
            
        $scope.fathernameClick= function(v){
            
                	 TermAudit.getReasons('2', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.availableOptions =data;
                         	  
                    		   console.log($scope.availableOptions);
                    		   
                    		   for(var i = 1; i < $scope.availableOptions.length; i++){
                    		   console.log($scope.availableOptions[i])
                    		   }
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
          
        $scope.doastatus= function(v){
          
                	 TermAudit.getReasons('3', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.doaOptions =data;
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
           
        $scope.aonamestatus= function(v){
                	 TermAudit.getReasons('4', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.aoOptions =data;
                         	  
                    		 
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
           
        $scope.identityproofstatus= function(v){
                	 TermAudit.getReasons('7', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.idProof =data;
                         	  
                    		 
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
           

        $scope.addressClick= function(v){
                	 TermAudit.getReasons('8', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.addressProof =data;
                         	  
                    		 
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
           
        
        $scope.checkcafstatus= function(v){
                	 TermAudit.getReasons('5', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.cafOptions =data;
                         	  
                    		 
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
            
        $scope.photoClick= function(v){
            
                	 TermAudit.getReasons('6', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.photoOptions =data;
                         	  
                    		 
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
        $scope.pointsaleaddressstatus= function(v){
                	 TermAudit.getReasons('9', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.retdesOptions =data;
                         	  
                    		 
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
          
        $scope.pointsalecode= function(v){
                	 TermAudit.getReasons('10', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.pointsalecodeOptions =data;
                         	  
                    		 
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
        $scope.pointsalenamestatus= function(v){
                	 TermAudit.getReasons('11', onSaveSuccess, onSaveError);
                	 function onSaveSuccess (data) {
                    		$scope.pointsalenameOptions =data;
                         	  
                    		 
                           }

                	 function onSaveError (result) {
                 		$scope.availableOptions =result;
                      	  
                 		   console.log($scope.availableOptions);
                        }
                           
                }
        

        vm.datePickerOpenStatus.auditdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        $scope.getData = function(){
        	vm.termAudit.mobilenumber =vm.termAudit.mobilenumber;
			 data = vm.termAudit;
       	 TermAudit.getData(vm.termAudit.mobilenumber , onSuccess, onSaveError);
             
       	   function onSuccess (result) {
			  
       		vm.termAudit =result;
			 // vm.isSaving = true;
			 vm.termAudit.typeofdoc = data.typeofdoc;
            vm.termAudit.customernamestatus = true;
            vm.termAudit.fathernamestatus= true;
            vm.termAudit.doastatus= true;
            vm.termAudit.aonamestatus= true;
            vm.termAudit.identityproofstatus= true;
            vm.termAudit.addressproofstatus= true;
            vm.termAudit.photo=true;
            vm.termAudit.checkcafstatus= true;
            vm.termAudit.pointsalenamestatus=true;
            vm.termAudit.pointsalecode=true;
            vm.termAudit.pointsaleaddressstatus=true;
            $scope.reason1 ="";
            $scope.reason2 = "";
            $scope.reason3 = "";
            $scope.reason4 = "";
            $scope.reason5 = "";
            $scope.reason6 = "";
            $scope.reason7 = "";
            $scope.reason8 = "";
            $scope.reason9 = "";
            $scope.reason10 = "";
            $scope.reason11 = "";
            
       		   
              }

              function onSaveError (result) {
            	  vm.termAudit.mobilenumber= null;
              }
       	   
       };
       
       $scope.findByMobNo = function(){
    	  
   	    TermAudit.findByTermNo(vm.termAudit.mobilenumber , onSuccess, onSaveError);
   	   
   	   function onSuccess (result) {
   		   
   		   if(vm.termAudit.state = result.state){
				
         		vm.termAudit =result;
         		vm.termAudit.customernamestatus = true;
               
                vm.termAudit.doastatus= true;
            
                vm.termAudit.identityproofstatus= true;
                vm.termAudit.addressproofstatus= true;
                vm.termAudit.photo=true;
                vm.isSaving = true;
                
                $scope.reason1 ="";
                $scope.reason2 = "";
                $scope.reason3 = "";
                $scope.reason4 = "";
                $scope.reason5 = "";
                $scope.reason6 = "";
                $scope.reason7 = "";
                $scope.reason8 = "";
                $scope.reason9 = "";
                $scope.reason10 = "";
                $scope.reason11 = "";

                }
   		else{
    		 alert('caf does not belong to state selected')  
    	   }
   	   }

                function onSaveError () {
                    //vm.isSaving = false;
                	vm.termAudit.mobilenumber = null;
                }
   	   
      };
      
      $scope.formatDate = function(date){
          var dateOut = new Date(date);
          return dateOut;
    };
      
      
    }
})();
