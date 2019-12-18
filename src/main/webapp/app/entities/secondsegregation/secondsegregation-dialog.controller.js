(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SecondsegregationDialogController', SecondsegregationDialogController);

    SecondsegregationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Secondsegregation'];

    function SecondsegregationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Secondsegregation) {
        var vm = this;

        vm.secondsegregation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });
        
    	init();
    	
    	function init() {
    		Secondsegregation.getLatestBox(vm.secondsegregation, successbox);
    		function successbox(result) {
    			vm.secondsegregation = result;
    			vm.secondsegregation.cafcode ='';
    		if( new Date().setHours(0,0,0,0) > new Date(result.userdate)){
				vm.secondsegregation.count =0;
			}
    		}
    	}

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.secondsegregation.id !== null) {
                Secondsegregation.update(vm.secondsegregation, onSaveSuccess, onSaveError);
            } else {
                Secondsegregation.save(vm.secondsegregation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
        	vm.secondsegregation.cafcode ="";
           // $scope.$emit('cafvalidityV2App:secondsegregationUpdate', result);
            //$uibModalInstance.close(result);
            //vm.isSaving = false;
        }

        function onSaveError () {
        	vm.secondsegregation.cafcode ="";
          //  vm.isSaving = false;
        }

        vm.datePickerOpenStatus.userdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        $scope.$watch(
    			'vm.secondsegregation.boxname', function() {
    				if (vm.secondsegregation.boxname!= null && vm.secondsegregation.boxname.length >= 8) {
    					Secondsegregation.getOutbox(vm.secondsegregation, successOutbox, errorBox);
    					function successOutbox(result) {
    						
    	    				vm.secondsegregation = result;
    	    				vm.secondsegregation.id = null;
    	    				vm.secondsegregation.cafcode='';
    					}
    					function errorBox(result){
    						
    						vm.secondsegregation=result.data;
    						vm.secondsegregation.cafcode='';
    	    				vm.secondsegregation.colorcode='';
    					}
    				}
    			
    			});
        
        $scope.$watch(
    			'vm.secondsegregation.cafcode', function() {
    		if (vm.secondsegregation.cafcode!= null && vm.secondsegregation.cafcode.length === 11) {
    			Secondsegregation.getBox(vm.secondsegregation, successCaf, errorcaf);
    			// noinspection JSAnnotator
    			function successCaf(result) {
    				vm.secondsegregation = result;
    				vm.secondsegregation.count = vm.secondsegregation.count+1;
    				if(vm.secondsegregation.segregatedcount===800){
    					alert("Box is completed"+ vm.secondsegregation.boxname+"Color : "+vm.secondsegregation.colorcode);
    					Secondsegregation.save(vm.secondsegregation, onSaveSuccess,onSaveError);
    					vm.secondsegregation = null;
    				}else{
    					Secondsegregation.save(vm.secondsegregation, onSaveSuccess,onSaveError);
    				}
    			}
    			// noinspection JSAnnotator
    			function errorcaf(result) {
    				vm.secondsegregation = result.data;
    				vm.secondsegregation.count = vm.secondsegregation.count+1;
    				Secondsegregation.save(vm.secondsegregation, onSaveSuccess,onSaveError);
    				
    			}
    		}
    	});

    }
})();
