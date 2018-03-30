(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('MobileValidationDialogController', MobileValidationDialogController);

    MobileValidationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MobileValidation','$cookies','$cookieStore'];

    function MobileValidationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MobileValidation,$cookie,$cookieStore) {
        var vm = this;
        $scope.lastcompletedbox = $cookieStore.get('completed');
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
                  		 if( vm.mobileValidation.countCategory5===1600){
                 			alert('Category1 Box is completed please ask for another one') ;
                 			 vm.mobileValidation.boxstatus='category5-completed';
                 				 vm.mobileValidation.countCatergory5 = 0;
                 				vm.mobileValidation.catergory5 = mobileValidation.findCategory5(onSucess);
               				function onSuccess(result){
               				vm.mobileValidation.catergory5 = result;
               				}
               			  vm.mobileValidation.catergory5= 'EDA'+increment_alphanumeric_str(vm.mobileValidation.catergory5.substring(3, 8));
                 		 }

                 		 if( vm.mobileValidation.countCategory1===800){
                 			 alert('Category2 Box is completed please ask for another one') ;
                 			 vm.mobileValidation.countCatergory1 = 0;
              				vm.mobileValidation.category1 = mobileValidation.findCategory1(onSucess);
            				function onSuccess(result){
            				vm.mobileValidation.category1 = result;
            				}
            			  vm.mobileValidation.category1= 'E1A'+increment_alphanumeric_str(vm.mobileValidation.category1.substring(3, 8));
                 			if( vm.mobileValidation.boxstatus!=null){
                 				 vm.mobileValidation.boxstatus=' ,category1-completed'
                 			}else{
                 				 vm.mobileValidation.boxstatus='category1-completed'
                 			}
                 		 }
                 		 if( vm.mobileValidation.countCategory2===300){
                 	 alert('Category2 Box is completed please ask for another one') ;
                 	vm.mobileValidation.countCatergory2 = 0;
      				vm.mobileValidation.category2 = mobileValidation.findCategory2(onSucess);
    				function onSuccess(result){
    				vm.mobileValidation.category2 = result;
    				}
    			  vm.mobileValidation.category2= 'EC1'+increment_alphanumeric_str(vm.mobileValidation.category1.substring(3, 8));
                 	 if( vm.mobileValidation.boxstatus!=null){
          				 vm.mobileValidation.boxstatus=' ,category2-completed'
          			}else{
          				 vm.mobileValidation.boxstatus='category2-completed'
          			}
                 		 }

                 		 if( vm.mobileValidation.countCategory3===300){
                 	 alert('Category3 Box is completed please ask for another one') ;
                 	vm.mobileValidation.countCatergory3 = 0;
      				vm.mobileValidation.category3 = mobileValidation.findCategory3(onSucess);
    				function onSuccess(result){
    				vm.mobileValidation.category3 = result;
    				}
    			  vm.mobileValidation.category3= 'EC2'+increment_alphanumeric_str(vm.mobileValidation.category3.substring(3, 8));
                 	 if( vm.mobileValidation.boxstatus!=null){
          				 vm.mobileValidation.boxstatus=' ,category3-completed'
          			}else{
          				 vm.mobileValidation.boxstatus='category3-completed'
          			}
                 		 }
                 		 if( vm.mobileValidation.countCategory4===300){
                 		 alert('Category4 Box is completed please ask for another one') ;
                 		vm.mobileValidation.countCatergory4 = 0;
          				vm.mobileValidation.catergory4 = mobileValidation.findCategory4(onSucess);
        				function onSuccess(result){
        				vm.mobileValidation.catergory4 = result;
        				}
        			  vm.mobileValidation.catergory4= 'EC3'+increment_alphanumeric_str(vm.mobileValidation.catergory4.substring(3, 8));
         		        	 if( vm.mobileValidation.boxstatus!=null){
         		 				 vm.mobileValidation.boxstatus=' ,category4-completed'
         		 			}else{
         		 				 vm.mobileValidation.boxstatus='category4-completed'
         		 			}
                 		 }
                 		 if( vm.mobileValidation.countCategory1===50||  vm.mobileValidation.countCategory1===100|| vm.mobileValidation.countCategory1===150||
                 				  vm.mobileValidation.countCategory1===200||  vm.mobileValidation.countCategory1===250|| vm.mobileValidation.countCategory1===300||
                 				  vm.mobileValidation.countCategory1===350|| vm.mobileValidation.countCategory1===400||
                 				  vm.mobileValidation.countCategory1===450||  vm.mobileValidation.countCategory1===500|| vm.mobileValidation.countCategory1===550||
                 				  vm.mobileValidation.countCategory1===600|| vm.mobileValidation.countCategory1===650||
                 				  vm.mobileValidation.countCategory1===700||  vm.mobileValidation.countCategory1===750){
                  			alert('For Category1 : Lot is completed') ;
                  			vm.mobileValidation.category1 = mobileValidation.findCategory1(onSucess);
              				function onSuccess(result){
              				vm.mobileValidation.category1 = result;
              				}
              			  vm.mobileValidation.category1= 'E1A'+increment_alphanumeric_str(vm.mobileValidation.category1.substring(3, 8));
                  		 }

                 		 if( vm.mobileValidation.countCategory5===50||  vm.mobileValidation.countCategory5===100|| vm.mobileValidation.countCategory5===150||
                 				  vm.mobileValidation.countCategory5===200||  vm.mobileValidation.countCategory5===250|| vm.mobileValidation.countCategory5===300||
                 				  vm.mobileValidation.countCategory5===350|| vm.mobileValidation.countCategory5===400||
                 				  vm.mobileValidation.countCategory5===450||  vm.mobileValidation.countCategory5===500|| vm.mobileValidation.countCategory5===550||
                 				  vm.mobileValidation.countCategory5===600|| vm.mobileValidation.countCategory5===650||
                 				  vm.mobileValidation.countCategory5===700||  vm.mobileValidation.countCategory5===750|| vm.mobileValidation.countCategory5===800||
                 				  vm.mobileValidation.countCategory5===850|| vm.mobileValidation.countCategory5===900||
                 				  vm.mobileValidation.countCategory5===950||  vm.mobileValidation.countCategory5===1000|| vm.mobileValidation.countCategory5===1050||
                 				  vm.mobileValidation.countCategory5===1100|| vm.mobileValidation.countCategory5===1150||
                 				  vm.mobileValidation.countCategory5===1200||  vm.mobileValidation.countCategory5===1250|| vm.mobileValidation.countCategory5===1300||
                 				  vm.mobileValidation.countCategory5===1350|| vm.mobileValidation.countCategory5===1400||
                 				  vm.mobileValidation.countCategory5===1450||  vm.mobileValidation.countCategory5===1500
                 				 ||  vm.mobileValidation.countCategory5===1550){
                 			 alert('For Category5 : Lot is completed') ;
                 			vm.mobileValidation.catergory5 = mobileValidation.findCategory5(onSucess);
              				function onSuccess(result){
              				vm.mobileValidation.catergory5 = result;
              				}
              			  vm.mobileValidation.catergory5= 'EDA'+increment_alphanumeric_str(vm.mobileValidation.catergory5.substring(3, 8));
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

            	    	 MobileValidation.save(vm.mobileValidation, onSaveSuccess, onSaveError);
            	    }
            	}

            }
        }

        init();
        function init(){

        	MobileValidation.getSystemByName(vm.mobileValidation,onSuccess,onError);
        	 function onSuccess(data){
        		if(data.user != null){
        			vm.mobileValidation.user=data.user;
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
     	           	  vm.mobileValidation.catergory4  =  array[3];
     	           	}
     	           	if(array[4].indexOf('EDA')  > -1){
     	           	 vm.mobileValidation.catergory5 = array[4];
     	           	}
    				}
        		MobileValidation.getDetailsByName(vm.mobileValidation,onSuccessName);
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
        		           vm.mobileValidation.catergory4 = result.catergory4;
        		           vm.mobileValidation.catergory5 = result.catergory5;
        				}
        			}



        		}
        	 function onError(result){
     			alert(result)
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
			 vm.mobileValidation.mobilenumber ='';
     		vm.mobileValidation.remobilenumber='';

      		  if(vm.mobileValidation.countCategory5===1600){

      			$cookieStore.put('completed', vm.mobileValidation.category1);
      				vm.mobileValidation.countCategory5= 0;
      			  vm.mobileValidation.catergory5= 'EDA'+increment_alphanumeric_str(vm.mobileValidation.catergory5.substring(3, 8));
      		      $scope.lastcompletedbox = $cookieStore.get('completed')
      		  }

      		  if(vm.mobileValidation.countCategory1===800){
      			  $cookieStore.put('completed', vm.mobileValidation.category2);
      			  vm.mobileValidation.countCategory1= 0;
      			   mobileValidation.findCategory1(onSuccessCategory1);

                   function onSuccessCategory1(result){
                     vm.mobileValidation.category1 = result.category1;
                     vm.mobileValidation.category1= 'E1A'+increment_alphanumeric_str(vm.mobileValidation.category1.substring(3, 8));
                      }

      			  $scope.lastcompletedbox = $cookieStore.get('completed')
  		       }
      		  if(vm.mobileValidation.countCategory2===300){
      			$cookieStore.put('completed', vm.mobileValidation.category2);
    			  vm.mobileValidation.countCategory2= 0;
    			  mobileValidation.findCategory2(onSuccessCategory2);

                    function onSuccessCategory2(result){
                       vm.mobileValidation.category2 = result.category2;
                       vm.mobileValidation.category2= 'EC2'+increment_alphanumeric_str(vm.mobileValidation.category2.substring(3, 8));
                      }

    			  $scope.lastcompletedbox = $cookieStore.get('completed')
      		  }
      		  if(vm.mobileValidation.countCategory3===300){
      			$cookieStore.put('completed', vm.mobileValidation.category3);
  			  vm.mobileValidation.countCategory3= 0;
  			  mobileValidation.findCategory4(onSuccessCategory3);

              function onSuccessCategory3(result){
               vm.mobileValidation.category3 = result.category3;
                 vm.mobileValidation.category3 = 'EC2'+increment_alphanumeric_str(vm.mobileValidation.category3.substring(3, 8));
                }
  			  }

      		    if(vm.mobileValidation.countCategory4===300){
      			$cookieStore.put('completed', vm.mobileValidation.catergory4);
  			  vm.mobileValidation.countCategory4= 0;
  			  vm.mobileValidation.catergory4= 'EC3'+increment_alphanumeric_str(vm.mobileValidation.catergory4.substring(4, 8));
  			  $scope.lastcompletedbox = $cookieStore.get('completed')
      		  }

      		  if(vm.mobileValidation.countCategory5===800){
      			  $cookieStore.put('completed', vm.mobileValidation.catergory5);
      			  vm.mobileValidation.countCategory5= 0;
      			   mobileValidation.findCategory5(onSuccessCategory1);

                   function onSuccessCategory5(result){
                     vm.mobileValidation.catergory5 = result.category5;
                     vm.mobileValidation.catergory5= 'E1A'+increment_alphanumeric_str(vm.mobileValidation.catergory5.substring(3, 8));
                      }

      			  $scope.lastcompletedbox = $cookieStore.get('completed')
  		       }
           }

$scope.validate1 = function(){

            if(vm.mobileValidation.category1.length===8){
            	  if(vm.mobileValidation.category1.substring(0,3)!='EDA'){
            		 alert('Assigned Box in Category 1 is not correct !!! Please Check')
            	  }
            }
            if(vm.mobileValidation.category2.length===8){
          	  if(vm.mobileValidation.category2.substring(0,3)!='E1A'){
          		 alert('Assigned Box in Category 2 is not correct !!! Please Check')
          	  }
          }
            if(vm.mobileValidation.category3.length===8){
          	  if(vm.mobileValidation.category3.substring(0,3)!='EC1'){
          		 alert('Assigned Box in Category 3 is not correct !!! Please Check')
          	  }
          }
            if(vm.mobileValidation.catergory4.length===8){
          	  if(vm.mobileValidation.catergory4.substring(0,3)!='EC2'){
          		 alert('Assigned Box in Category 4 is not correct !!! Please Check')
          	  }
          }
            if(vm.mobileValidation.catergory5.length===8){
          	  if(vm.mobileValidation.catergory5.substring(0,3)!='EC3'){
          		 alert('Assigned Box in Category 5 is not correct !!! Please Check')
          	  }
          }
};

        function onSaveError () {
            vm.isSaving = false;
        }

       $scope.isSelected= function(mobileValidation) {
        vm.mobileValidation.colorCode = mobileValidation.colorCode;
    if(mobileValidation.isselected){
        if(vm.mobileValidation.colorCode==='G3y'){
 			 vm.mobileValidation.colorCode = 'red';
 			 vm.mobileValidation.countCategory5 =  vm.mobileValidation.countCategory5+1;
 		 }
 		 if(vm.mobileValidation.colorCode==='Active'){
 			 vm.mobileValidation.colorCode = 'green';
 			 vm.mobileValidation.countCategory1 =  vm.mobileValidation.countCategory1+1;
 		 }
 		 if(vm.mobileValidation.colorCode==='L1'){
 			 vm.mobileValidation.colorCode = 'white';
 			 vm.mobileValidation.countCategory2 =  vm.mobileValidation.countCategory2+1;
 		 }
 		 if(vm.mobileValidation.colorCode==='0-1'){
 			 vm.mobileValidation.colorCode = 'yellow';
 			 vm.mobileValidation.countCategory3 =  vm.mobileValidation.countCategory3+1;
 		 }
 		 if(vm.mobileValidation.colorCode==='1-2'){
 			 vm.mobileValidation.colorode = 'blue';
 			 vm.mobileValidation.countCategory4 =  vm.mobileValidation.countCategory4+1;
 		 }
    }
        };

        vm.datePickerOpenStatus.userDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        $scope.toggleCustom = function() {
            $scope.custom = $scope.custom === false ? true: false;
        };

        $scope.BoxComplete = function() {

        	if (vm.mobileValidation.sourceboxstaus.indexOf('EDA') > -1)
        	{
        	  vm.mobileValidation.caftype ='category_1'
        	}
        	if (vm.mobileValidation.sourceboxstaus.indexOf('E1A') > -1)
        	{
        	  vm.mobileValidation.caftype ='category_2'
        	}
        	if (vm.mobileValidation.sourceboxstaus.indexOf('EC1') > -1)
        	{
        	  vm.mobileValidation.caftype ='category_3'
        	}
        	if (vm.mobileValidation.sourceboxstaus.indexOf('EC2') > -1)
        	{
        	  vm.mobileValidation.caftype ='category_4'
        	}
        	if (vm.mobileValidation.sourceboxstaus.indexOf('EC3') > -1)
        	{
        	  vm.mobileValidation.caftype ='category_5'
        	}

        	 mobileValidation.getOutBox(vm.mobileValidation,getoutbox,geterrorbox);

        	 function getoutbox (result) {
        		 $scope.item = result;
    			// alert(result.boxstatus);

    			 var blob = new Blob([result.boxstatus], {
    		            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
    		        });

    		        saveAs(blob, "Report.xls");

    		 }
    		 function geterrorbox (result) {
    			 alert(result.boxstatus);
    		 }

        };


    $scope.cafcheck = function(){
            	if(vm.mobileValidation.mobilenumber.length ===10){
            		  MobileValidation.getBox(vm.mobileValidation,successCaf,errorcaf);
            		 function successCaf (result) {
            			 vm.mobileValidation = result;
            			 MobileValidation.update(vm.mobileValidation, updatesucess);
            			 function updatesucess (result) {
            				 vm.mobileValidation.mobilenumber ='';
            			 }
            		 }
            		 function errorcaf (result) {
            			 vm.mobileValidation= result;
            		 }
            	}
            }


    }
})();
