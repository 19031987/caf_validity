(function() {
	'use strict';

	angular.module('cafvalidityV2App').controller('ScancafDialogController',
			ScancafDialogController);

	ScancafDialogController.$inject = [ '$timeout', '$scope', '$stateParams',
			'$uibModalInstance', 'entity', 'Scancaf', '$cookies',
			'$cookieStore' ];

	function ScancafDialogController($timeout, $scope, $stateParams,
			$uibModalInstance, entity, Scancaf, $cookies, $cookieStore) {
		var vm = this;
		$scope.lastcompletedbox = $cookieStore.get('completed');
		$scope.lastsourcebox = $cookieStore.get('sourcecompleted');
		vm.scancaf = entity;
		vm.cafbarcodecheck = null;
		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.save = save;
		
		
		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$uibModalInstance.dismiss('cancel');
		}

		function save() {
			vm.isSaving = true;
			if (vm.scancaf.id !== null) {
				Scancaf.update(vm.scancaf, onSaveSuccess, onSaveError);
			} else {
				Scancaf.save(vm.scancaf, onSaveSuccess, onSaveError);
			}
		}

		function onSaveSuccess(result) {
			$scope.$emit('cafvalidityV2App:scancafUpdate', result);
			$uibModalInstance.close(result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		vm.datePickerOpenStatus.userdate = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}
		$scope.count=0
		function init() {

			Scancaf.getSystemByName(vm.scancaf, onSuccess, onError);
			function onSuccess(data) {
				if (data.user != null) {
					vm.scancaf.user = data.user;
					var array = data.boxassign.split(",");
					if (array[0].indexOf('E1A') > -1) {
						vm.scancaf.category1 = array[0];
					}
					if (array[1].indexOf('EC1') > -1) {
						vm.scancaf.category2 = array[1];
					}
					if (array[2].indexOf('EC2') > -1) {
						vm.scancaf.category3 = array[2];
					}
					if (array[3].indexOf('EC3') > -1) {
						vm.scancaf.category4 = array[3];
					}
					if (array[4].indexOf('EDA') > -1) {
						vm.scancaf.category5 = array[4];
					}
					if (array[5].indexOf('ERV') > -1) {
						vm.scancaf.categoryRv = array[5]
					}
					if (array[6].indexOf('ENA') > -1) {
						vm.scancaf.categoryNA = array[6];
					}
				}
				Scancaf.getDetailsByName(vm.scancaf, onSuccessName,error);
				function onSuccessName(result) {
					if (result != null) {
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
						vm.scancaf.categoryRv = result.categoryRv;
						vm.scancaf.categoryNA = result.categoryNA;
						vm.scancaf.countCategoryRv = result.countCategoryRv;
						vm.scancaf.countCategoryNA = result.countCategoryNA;
						if( new Date().setHours(0,0,0,0) > new Date(result.userdate)){
							vm.scancaf.category =0;
						}else{
							vm.scancaf.category =result.category;
						}
					}
					
				}
				function error(){
						vm.scancaf.category =0;
					}
			}
			function onError() {
				alert("You are not allowed to work on the system, Please check with your Team leader");
			}
		}

		init();

		// watchCollection
		$scope.sourceTrue
		$scope
				.$watch(
						'vm.scancaf.sourcebox',
						function() {
							if (vm.scancaf.sourcebox != null && vm.scancaf.sourcebox.length >= 9) {
								Scancaf.getSource(vm.scancaf.sourcebox,
										onSaveSuccess,onError);
							}
							function onSaveSuccess() {

								Scancaf.getBoxCount(vm.scancaf, onSaveSuccess1);
									$scope.sourceTrue = true;
								function onSaveSuccess1(result) {}

							}
							
							function onError(){
								$scope.sourceTrue = false;
							alert("Please Enter correct SourceBoxName");
							}
						});

		$scope
				.$watch(
						'vm.scancaf.cafbarcode',
						function() {
							if (vm.scancaf.cafbarcode!= null && vm.scancaf.cafbarcode.length === 12) {
								//vm.scancaf.colorcode = '';
								vm.scancaf.mobilenumber = '';
								Scancaf.getCaf(vm.scancaf, onSaveSuccess);
							}
							function onSaveSuccess(result) {
								// $scope.$emit('cafvalidityV2App:scancafUpdate', result);
								// $uibModalInstance.close(result);
								vm.scancaf.centralbarcode = vm.scancaf.cafbarcode;
								vm.scancaf.category = parseInt(vm.scancaf.category, 10) + 1
								vm.scancaf.caftype = result.caftype;
								
								if(vm.scancaf.caftype === null){
								vm.scancaf.caftype = "";
								}

								if (vm.scancaf.caftype.trim() === 'G3y' || vm.scancaf.caftype.trim() === "G3y") {
									vm.scancaf.colorcode = 'red';
									vm.scancaf.countCategory5 = vm.scancaf.countCategory5 + 1;
									if (vm.scancaf.countCategory5 === 50
											|| vm.scancaf.countCategory5 === 100
											|| vm.scancaf.countCategory5 === 150
											|| vm.scancaf.countCategory5 === 200
											|| vm.scancaf.countCategory5 === 250
											|| vm.scancaf.countCategory5 === 300
											|| vm.scancaf.countCategory5 === 350
											|| vm.scancaf.countCategory5 === 400
											|| vm.scancaf.countCategory5 === 450
											|| vm.scancaf.countCategory5 === 500
											|| vm.scancaf.countCategory5 === 550
											|| vm.scancaf.countCategory5 === 600
											|| vm.scancaf.countCategory5 === 650
											|| vm.scancaf.countCategory5 === 700
											|| vm.scancaf.countCategory5 === 750
											) {
										alert('For >3 years : Lot is completed, Lot number is:'+ vm.scancaf.countCategory5/50
												+" Box Name "+ vm.scancaf.category5+" Color "+ vm.scancaf.colorcode);
										alert('Please press ENTER');
										
									}
								}
								if (vm.scancaf.caftype.trim() === 'Active' || vm.scancaf.caftype.trim() === "Active") {
									vm.scancaf.colorcode = 'green';
									vm.scancaf.countCategory1 = vm.scancaf.countCategory1 + 1;
									if (vm.scancaf.countCategory1 === 50
											|| vm.scancaf.countCategory1 === 100
											|| vm.scancaf.countCategory1 === 150
											|| vm.scancaf.countCategory1 === 200
											|| vm.scancaf.countCategory1 === 250
											|| vm.scancaf.countCategory1 === 300
											|| vm.scancaf.countCategory1 === 350
											|| vm.scancaf.countCategory1 === 400
											|| vm.scancaf.countCategory1 === 450
											|| vm.scancaf.countCategory1 === 500
											|| vm.scancaf.countCategory1 === 550
											|| vm.scancaf.countCategory1 === 600
											|| vm.scancaf.countCategory1 === 650
											|| vm.scancaf.countCategory1 === 700
											|| vm.scancaf.countCategory1 === 750) {
										alert('For Active : Lot is completed, Lot number is:'+ vm.scancaf.countCategory1/50
												+" Box Name "+ vm.scancaf.category1+" Color "+ vm.scancaf.colorcode);
										alert('Please press ENTER');
									//	Scancaf.findCategory1(onSuccessCategory);
									}
								}
								if (vm.scancaf.caftype.trim() === 'L1' || vm.scancaf.caftype.trim() === "L1") {
									vm.scancaf.colorcode = 'blue';
									vm.scancaf.countCategory2 = vm.scancaf.countCategory2 + 1;
									if (vm.scancaf.countCategory2 === 50
											|| vm.scancaf.countCategory2 === 100
											|| vm.scancaf.countCategory2 === 150
											|| vm.scancaf.countCategory2 === 200
											|| vm.scancaf.countCategory2 === 250
											|| vm.scancaf.countCategory2 === 300
											|| vm.scancaf.countCategory2 === 350
											|| vm.scancaf.countCategory2 === 400
											|| vm.scancaf.countCategory2 === 450
											|| vm.scancaf.countCategory2 === 500
											|| vm.scancaf.countCategory2 === 550
											|| vm.scancaf.countCategory2 === 600
											|| vm.scancaf.countCategory2 === 650
											|| vm.scancaf.countCategory2 === 700
											|| vm.scancaf.countCategory2 === 750) {
										alert('For < 1 year : Lot is completed, Lot number is:'+ vm.scancaf.countCategory2/50
												+" Box Name "+ vm.scancaf.category2+" Color "+ vm.scancaf.colorcode);
										alert('Please press ENTER');
									}
								}
								if (vm.scancaf.caftype.trim() === '1-2y'||vm.scancaf.caftype.trim() === "1-2y") {
									vm.scancaf.colorcode = 'yellow';
									vm.scancaf.countCategory3 = vm.scancaf.countCategory3 + 1;
									if (vm.scancaf.countCategory3 === 50
											|| vm.scancaf.countCategory3 === 100
											|| vm.scancaf.countCategory3 === 150
											|| vm.scancaf.countCategory3 === 200
											|| vm.scancaf.countCategory3 === 250
											|| vm.scancaf.countCategory3 === 300
											|| vm.scancaf.countCategory3 === 350
											|| vm.scancaf.countCategory3 === 400
											|| vm.scancaf.countCategory3 === 450
											|| vm.scancaf.countCategory3 === 500
											|| vm.scancaf.countCategory3 === 550
											|| vm.scancaf.countCategory3 === 600
											|| vm.scancaf.countCategory3 === 650
											|| vm.scancaf.countCategory3 === 700
											|| vm.scancaf.countCategory3 === 750) {
										alert('For 1-2 years : Lot is completed, Lot number is:'+ vm.scancaf.countCategory3/50
												+" Box Name "+ vm.scancaf.category3+" Color "+ vm.scancaf.colorcode);
										alert('Please press ENTER');
									}
								}
								if (vm.scancaf.caftype.trim() === '2-3y' || vm.scancaf.caftype.trim() === "2-3y") {
									vm.scancaf.colorcode = 'white';
									vm.scancaf.countCategory4 = vm.scancaf.countCategory4 + 1;
									if (vm.scancaf.countCategory4 === 50
											|| vm.scancaf.countCategory4 === 100
											|| vm.scancaf.countCategory4 === 150
											|| vm.scancaf.countCategory4 === 200
											|| vm.scancaf.countCategory4 === 250
											|| vm.scancaf.countCategory4 === 300
											|| vm.scancaf.countCategory4 === 350
											|| vm.scancaf.countCategory4 === 400
											|| vm.scancaf.countCategory4 === 450
											|| vm.scancaf.countCategory4 === 500
											|| vm.scancaf.countCategory4 === 550
											|| vm.scancaf.countCategory4 === 600
											|| vm.scancaf.countCategory4 === 650
											|| vm.scancaf.countCategory4 === 700
											|| vm.scancaf.countCategory4 === 750) {
										alert('For 2-3 years : Lot is completed, Lot number is:'+ vm.scancaf.countCategory4/50
												+" Box Name "+ vm.scancaf.category4+" Color "+ vm.scancaf.colorcode);
										alert('Please press ENTER');
									}
								}
								if (vm.scancaf.caftype.trim() === 'RV' || vm.scancaf.caftype.trim() === "RV") {
									vm.scancaf.colorcode = 'orange';
									vm.scancaf.countCategoryRv = vm.scancaf.countCategoryRv + 1;
									if (vm.scancaf.countCategoryRv === 50
											|| vm.scancaf.countCategoryRv === 100
											|| vm.scancaf.countCategoryRv === 150
											|| vm.scancaf.countCategoryRv === 200
											|| vm.scancaf.countCategoryRv === 250
											|| vm.scancaf.countCategoryRv === 300
											|| vm.scancaf.countCategoryRv === 350
											|| vm.scancaf.countCategoryRv === 400
											|| vm.scancaf.countCategoryRv === 450
											|| vm.scancaf.countCategoryRv === 500
											|| vm.scancaf.countCategoryRv === 550
											|| vm.scancaf.countCategoryRv === 600
											|| vm.scancaf.countCategoryRv === 650
											|| vm.scancaf.countCategoryRv === 700
											|| vm.scancaf.countCategoryRv === 750) {
										alert('RV : Lot is completed, Lot number is:'+ vm.scancaf.countCategoryRv/50
												+" Box Name "+ vm.scancaf.categoryRv+" Color "+ vm.scancaf.colorcode);
										alert('Please press ENTER');
									}
								}
								if (vm.scancaf.caftype.trim() === 'NA' || vm.scancaf.caftype.trim() === "NA") {
									vm.scancaf.colorcode = 'pink';
									vm.scancaf.countCategoryNA = vm.scancaf.countCategoryNA + 1;
									vm.scancaf.centralbarcode = vm.scancaf.cafbarcode+"_NA"
								}
								vm.scancaf.mobilenumber = result.mobilenumber;
								//vm.scancaf.cafbarcode = result.cafbarcode;
								vm.scancaf.customername = result.customername;
								vm.scancaf.activationdate = result.activationdate;
								
								// save functionality is being called
								Scancaf.save(vm.scancaf, onSaveSuccessFinal,onScancafError);

								function onSaveSuccessFinal() {
									vm.scancaf.cafbarcode = '';
									if (vm.scancaf.countCategory5 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category5);
										$cookieStore.put('completed',
												vm.scancaf.category1);
										vm.scancaf.countCategory5 = 0;
										Scancaf.findCategory5(onSuccessCategory5);

										// noinspection JSAnnotator
										function onSuccessCategory5(result) {
											vm.scancaf.category5 = result.category5;
											vm.scancaf.category5 = 'EDA'
													+ increment_alphanumeric_str(vm.scancaf.category5
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
										$scope.lastcompletedbox = $cookieStore
												.get('completed')
										}
									}
									if (vm.scancaf.countCategory1 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category1);
										$cookieStore.put('completed',
												vm.scancaf.category1);
										vm.scancaf.countCategory1 = 0;
										Scancaf
												.findCategory1(onSuccessCategory1);

										// noinspection JSAnnotator
										function onSuccessCategory1(result) {
											vm.scancaf.category1 = result.category1;
											vm.scancaf.category1 = 'E1A'
													+ increment_alphanumeric_str(vm.scancaf.category1
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
											$scope.lastcompletedbox = $cookieStore
																				.get('completed');
										}
									}

									if (vm.scancaf.countCategory2 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category2);
										$cookieStore.put('completed',
												vm.scancaf.category2);
										vm.scancaf.countCategory2 = 0;
										Scancaf
												.findCategory2(onSuccessCategory2);

										// noinspection JSAnnotator
										function onSuccessCategory2(result) {
											vm.scancaf.category2 = result.category2;
											vm.scancaf.category2 = 'EC1'
													+ increment_alphanumeric_str(vm.scancaf.category2
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
											$scope.lastcompletedbox = $cookieStore
											.get('completed')
										}
									}
									if (vm.scancaf.countCategory3 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category3);
										$cookieStore.put('completed',
												vm.scancaf.category3);
										vm.scancaf.countCategory3 = 0;
										Scancaf
												.findCategory3(onSuccessCategory3);

										// noinspection JSAnnotator
										function onSuccessCategory3(result) {
											vm.scancaf.category3 = result.category3;
											vm.scancaf.category3 = 'EC2'
													+ increment_alphanumeric_str(vm.scancaf.category3
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
											$scope.lastcompletedbox = $cookieStore
											.get('completed')
										}
									}
									if (vm.scancaf.countCategory4 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category4);
										$cookieStore.put('completed',
												vm.scancaf.category4);
										vm.scancaf.countCategory4 = 0;
										Scancaf
												.findCategory4(onSuccessCategory4);

										// noinspection JSAnnotator
										function onSuccessCategory4(result) {
											vm.scancaf.category4 = result.category4;
											vm.scancaf.category4 = 'EC3'
													+ increment_alphanumeric_str(vm.scancaf.category4
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
											$scope.lastcompletedbox = $cookieStore
											.get('completed')
										}
									}
									
									if (vm.scancaf.countCategoryRv === 800) {
										alert("Box Name is complete "+ vm.scancaf.categoryRv);
										$cookieStore.put('completed',
												vm.scancaf.categoryRv);
										vm.scancaf.countCategoryRv = 0;
										Scancaf
										.findCategoryRv(onSuccessCategoryRv);

								// noinspection JSAnnotator
								function onSuccessCategoryRv(result) {
									vm.scancaf.categoryRv = result.categoryRv;
										vm.scancaf.categoryRv = 'ERV'
												+ increment_alphanumeric_str(vm.scancaf.categoryRv
														.substring(3, 8));
										Scancaf.save(vm.scancaf, onSaveSuccessFinal);
										$scope.lastcompletedbox = $cookieStore
										.get('completed')
									  }
									}

									if (vm.scancaf.countCategoryNA === 800) {
										alert("Box Name is complete "+ vm.scancaf.categoryNA);
										$cookieStore.put('completed',
												vm.scancaf.categoryNA);
										vm.scancaf.countCategoryNA = 0;
										Scancaf
										.findCategoryNA(onSuccessCategoryNA);

								// noinspection JSAnnotator
								function onSuccessCategoryNA(result) {
									vm.scancaf.categoryNA = result.categoryNA;
										vm.scancaf.categoryNA = 'ENA'
												+ increment_alphanumeric_str(vm.scancaf.categoryNA
														.substring(3, 8));
										Scancaf.save(vm.scancaf, onSaveSuccessFinal);
										$scope.lastcompletedbox = $cookieStore
												.get('completed')
									  }
									} 
								}
								function onScancafError(result){
								vm.scancaf.cafbarcode='';
								if (vm.scancaf.countCategory5 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category5);
										$cookieStore.put('completed',
												vm.scancaf.category1);
										vm.scancaf.countCategory5 = 0;
										Scancaf.findCategory5(onSuccessCategory5);

										// noinspection JSAnnotator
										function onSuccessCategory5(result) {
											vm.scancaf.category5 = result.category5;
											vm.scancaf.category5 = 'EDA'
													+ increment_alphanumeric_str(vm.scancaf.category5
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
										$scope.lastcompletedbox = $cookieStore
												.get('completed')
										}
									}
									if (vm.scancaf.countCategory1 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category1);
										$cookieStore.put('completed',
												vm.scancaf.category1);
										vm.scancaf.countCategory1 = 0;
										Scancaf
												.findCategory1(onSuccessCategory1);

										// noinspection JSAnnotator
										function onSuccessCategory1(result) {
											vm.scancaf.category1 = result.category1;
											vm.scancaf.category1 = 'E1A'
													+ increment_alphanumeric_str(vm.scancaf.category1
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
											$scope.lastcompletedbox = $cookieStore
																				.get('completed');
										}
									}

									if (vm.scancaf.countCategory2 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category2);
										$cookieStore.put('completed',
												vm.scancaf.category2);
										vm.scancaf.countCategory2 = 0;
										Scancaf
												.findCategory2(onSuccessCategory2);

										// noinspection JSAnnotator
										function onSuccessCategory2(result) {
											vm.scancaf.category2 = result.category2;
											vm.scancaf.category2 = 'EC1'
													+ increment_alphanumeric_str(vm.scancaf.category2
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
											$scope.lastcompletedbox = $cookieStore
											.get('completed')
										}
									}
									if (vm.scancaf.countCategory3 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category3);
										$cookieStore.put('completed',
												vm.scancaf.category3);
										vm.scancaf.countCategory3 = 0;
										Scancaf
												.findCategory3(onSuccessCategory3);

										// noinspection JSAnnotator
										function onSuccessCategory3(result) {
											vm.scancaf.category3 = result.category3;
											vm.scancaf.category3 = 'EC2'
													+ increment_alphanumeric_str(vm.scancaf.category3
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
											$scope.lastcompletedbox = $cookieStore
											.get('completed')
										}
									}
									if (vm.scancaf.countCategory4 === 800) {
										alert("Box Name is complete "+ vm.scancaf.category4);
										$cookieStore.put('completed',
												vm.scancaf.category4);
										vm.scancaf.countCategory4 = 0;
										Scancaf
												.findCategory4(onSuccessCategory4);

										// noinspection JSAnnotator
										function onSuccessCategory4(result) {
											vm.scancaf.category4 = result.category4;
											vm.scancaf.category4 = 'EC3'
													+ increment_alphanumeric_str(vm.scancaf.category4
															.substring(3, 8));
											Scancaf.save(vm.scancaf, onSaveSuccessFinal);
											$scope.lastcompletedbox = $cookieStore
											.get('completed')
										}
									}
									
									if (vm.scancaf.countCategoryRv === 800) {
										alert("Box Name is complete "+ vm.scancaf.categoryRv);
										$cookieStore.put('completed',
												vm.scancaf.categoryRv);
										vm.scancaf.countCategoryRv = 0;
										Scancaf
										.findCategoryRv(onSuccessCategoryRv);

								// noinspection JSAnnotator
								function onSuccessCategoryRv(result) {
									vm.scancaf.categoryRv = result.categoryRv;
										vm.scancaf.categoryRv = 'ERV'
												+ increment_alphanumeric_str(vm.scancaf.categoryRv
														.substring(3, 8));
										Scancaf.save(vm.scancaf, onSaveSuccessFinal);
										$scope.lastcompletedbox = $cookieStore
										.get('completed')
									  }
									}

									if (vm.scancaf.countCategoryNA === 800) {
										alert("Box Name is complete "+ vm.scancaf.categoryNA);
										$cookieStore.put('completed',
												vm.scancaf.categoryNA);
										vm.scancaf.countCategoryNA = 0;
										Scancaf
										.findCategoryNA(onSuccessCategoryNA);

								// noinspection JSAnnotator
								function onSuccessCategoryNA(result) {
									vm.scancaf.categoryNA = result.categoryNA;
										vm.scancaf.categoryNA = 'ENA'
												+ increment_alphanumeric_str(vm.scancaf.categoryNA
														.substring(3, 8));
										Scancaf.save(vm.scancaf, onSaveSuccessFinal);
										$scope.lastcompletedbox = $cookieStore
												.get('completed')
									  }
									}
								}
							}
						});
		$scope.cat1 = function() {
			if (vm.scancaf.category1.length === 12) {
				Scancaf.getBoxCount(vm.scancaf, onSaveSuccess);
			}
			function onSaveSuccess(result) {
				vm.scancaf.countCategory1 = result.countCategory1;
			}
		};
		$scope.validate1 = function() {
			if (vm.scancaf.category1.length === 8) {
				if (vm.scancaf.category1.substring(0, 3) != 'EDA') {
					alert('Assigned Box in Category 1 is not correct !!! Please Check')
				}
			}
			if (vm.scancaf.category2.length === 8) {
				if (vm.scancaf.category2.substring(0, 3) != 'E1A') {
					alert('Assigned Box in Category 2 is not correct !!! Please Check')
				}
			}
			if (vm.scancaf.category3.length === 8) {
				if (vm.scancaf.category3.substring(0, 3) != 'EC1') {
					alert('Assigned Box in Category 3 is not correct !!! Please Check')
				}
			}
			if (vm.scancaf.category4.length === 8) {
				if (vm.scancaf.category4.substring(0, 3) != 'EC2') {
					alert('Assigned Box in Category 4 is not correct !!! Please Check')
				}
			}
			if (vm.scancaf.category5.length === 8) {
				if (vm.scancaf.category5.substring(0, 3) != 'EC3') {
					alert('Assigned Box in Category 5 is not correct !!! Please Check')
				}
			}
			if (vm.scancaf.categoryRv.length === 8) {
				if (vm.scancaf.categoryRv.substring(0, 3) != 'ERV') {
					alert('Assigned Box in Category RV is not correct !!! Please Check')
				}
			}
			if (vm.scancaf.categoryNA.length === 8) {
				if (vm.scancaf.categoryNA.substring(0, 3) != 'ENA') {
					alert('Assigned Box in Category NA is not correct !!! Please Check')
				}
			}
		}

		function onSaveSuccess(result) {
			vm.scancaf.countCategory1 = result.countCategory1;
		}
	

	$scope.custom = true;
	$scope.toggleCustom = function() {
		$scope.custom = $scope.custom === false ? true : false;
	};

	function increment_alphanumeric_str(str) {
		var numeric = str.match(/\d+$/)[0];
		var prefix = str.split(numeric)[0];

		function increment_string_num(str) {
			var inc = String(parseInt(str) + 1);
			return str.slice(0, str.length - inc.length) + inc;
		}

		return prefix + increment_string_num(numeric);
	}
	$scope.sourceBoxComplete = function() {
		vm.scancaf.sourcebox='';
		alert('Source box is completed :  '+ vm.scancaf.sourcebox)
		$cookieStore.put('sourcecompleted', vm.scancaf.sourcebox);
		$scope.lastsourcebox = $cookieStore.get('sourcecompleted')

	};

	$scope.$watch(
			'vm.scancaf.category', function() {
		if (vm.scancaf.category!= null && vm.scancaf.category.length === 12 && vm.scancaf.category!=='SECOND_LEVEL') {
			Scancaf.getBox(vm.scancaf, successCaf, errorcaf);
			// noinspection JSAnnotator
			function successCaf(result) {
				vm.scancaf = result;
				vm.scancaf.boxstatus = 'SECOND_LEVEL';
				Scancaf.update(vm.scancaf, updatesucess);
				function updatesucess(result) {
					vm.scancaf.boxstatus = '';
					vm.scancaf = result;
				}
			}
			// noinspection JSAnnotator
			function errorcaf(result) {
				vm.scancaf = result;
			}
		}
	});

	$scope.BoxComplete = function() {

		if (vm.scancaf.sourceboxstaus.indexOf('EDA') > -1) {
			vm.scancaf.caftype = 'category_1'
		}
		if (vm.scancaf.sourceboxstaus.indexOf('E1A') > -1) {
			vm.scancaf.caftype = 'category_2'
		}
		if (vm.scancaf.sourceboxstaus.indexOf('EC1') > -1) {
			vm.scancaf.caftype = 'category_3'
		}
		if (vm.scancaf.sourceboxstaus.indexOf('EC2') > -1) {
			vm.scancaf.caftype = 'category_4'
		}
		if (vm.scancaf.sourceboxstaus.indexOf('EC3') > -1) {
			vm.scancaf.caftype = 'category_5'
		}

		if (vm.scancaf.sourceboxstaus.indexOf('ERV') > -1) {
			vm.scancaf.caftype = 'category_rv'
		}

		if (vm.scancaf.sourceboxstaus.indexOf('ENA') > -1) {
			vm.scancaf.caftype = 'category_na'
		}

		Scancaf.getOutBox(vm.scancaf, getoutbox, geterrorbox);
		function getoutbox(result) {
			$scope.item = result;
			// alert(result.boxstatus);

			var blob = new Blob(
					[ result.boxstatus ],
					{
						type : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
					});

			saveAs(blob, "Report.xls");

		}
		function geterrorbox(result) {
			alert(result.boxstatus);
		}

	};
	
	};// end of scope
}

)();
