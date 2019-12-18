(function () {
    'use strict';

    angular.module('cafvalidityV2App').controller(
        'MobileValidationDialogController',
        MobileValidationDialogController);

    MobileValidationDialogController.$inject = ['$timeout', '$scope',
        '$stateParams', '$uibModalInstance', 'entity', 'MobileValidation',
        '$cookies', '$cookieStore'];

    function MobileValidationDialogController($timeout, $scope, $stateParams,
                                              $uibModalInstance, entity, MobileValidation, $cookie, $cookieStore) {
        var vm = this;
        $scope.lastcompletedbox = $cookieStore.get('completed');
        $scope.lastsourcebox = $cookieStore.get('sourcecompleted');
        vm.mobileValidation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.search = search;
        vm.mobileValidations;
        vm.isRowSelected;
        vm.saveSelectedMobile = saveSelectedMobile;
        vm.getOutBoxButton = getOutBoxButton;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.mobileValidation.id !== null) {
                MobileValidation.update(vm.mobileValidation[i], onSaveSuccess,
                    onSaveError);
            } else {
                for (var i = 0; i < vm.mobileValidations.length; i++) {
                    if (vm.mobileValidations[i].isselected === true) {
                        if (vm.mobileValidation.countCategory5 >= 1600) {
                            alert('Category1 Box is completed please ask for another one');
                            vm.mobileValidation.boxstatus = 'catergory5-completed';
                            vm.mobileValidation.countCategory5 = 0;
                            vm.mobileValidation.catergory5 = MobileValidation.findCategory5(onSucess);

                            function onSuccess(result) {
                                vm.mobileValidation.catergory5 = result;
                            }

                            vm.mobileValidation.catergory5 = 'EDA'
                                + increment_alphanumeric_str(vm.mobileValidation.catergory5
                                    .substring(3, 8));
                        }

                        if (vm.mobileValidation.countCategory1 >= 800) {
                            alert('Category2 Box is completed please ask for another one');
                            vm.mobileValidation.countCatergory1 = 0;
                            vm.mobileValidation.category1 = mobileValidation
                                .findCategory1(onSucess);

                            function onSuccess(result) {
                                vm.mobileValidation.category1 = result;
                            }

                            vm.mobileValidation.category1 = 'EKA'
                                + increment_alphanumeric_str(vm.mobileValidation.category1
                                    .substring(3, 8));
                            if (vm.mobileValidation.boxstatus != null) {
                                vm.mobileValidation.boxstatus = ' ,category1-completed'
                            } else {
                                vm.mobileValidation.boxstatus = 'category1-completed'
                            }
                        }
                        if (vm.mobileValidation.countCategory2 >= 800) {
                            alert('Category2 Box is completed please ask for another one');
                            vm.mobileValidation.countCatergory2 = 0;
                            vm.mobileValidation.category2 = mobileValidation
                                .findCategory2(onSucess);

                            function onSuccess(result) {
                                vm.mobileValidation.category2 = result;
                            }

                            vm.mobileValidation.category2 = 'EC1'
                                + increment_alphanumeric_str(vm.mobileValidation.category1
                                    .substring(3, 8));
                            if (vm.mobileValidation.boxstatus != null) {
                                vm.mobileValidation.boxstatus = ' ,category2-completed'
                            } else {
                                vm.mobileValidation.boxstatus = 'category2-completed'
                            }
                        }

                        if (vm.mobileValidation.countCategory3 >= 800) {
                            alert('Category3 Box is completed please ask for another one');
                            vm.mobileValidation.countCatergory3 = 0;
                            vm.mobileValidation.category3 = mobileValidation
                                .findCategory3(onSucess);

                            function onSuccess(result) {
                                vm.mobileValidation.category3 = result;
                            }

                            vm.mobileValidation.category3 = 'EC2'
                                + increment_alphanumeric_str(vm.mobileValidation.category3
                                    .substring(3, 8));
                            if (vm.mobileValidation.boxstatus != null) {
                                vm.mobileValidation.boxstatus = ' ,category3-completed'
                            } else {
                                vm.mobileValidation.boxstatus = 'category3-completed'
                            }
                        }
                        if (vm.mobileValidation.countCategory4 >= 800) {
                            alert('Category4 Box is completed please ask for another one');
                            vm.mobileValidation.countCatergory4 = 0;
                            vm.mobileValidation.catergory4 = mobileValidation
                                .findCategory4(onSucess);

                            function onSuccess(result) {
                                vm.mobileValidation.catergory4 = result;
                            }

                            vm.mobileValidation.catergory4 = 'EC3'
                                + increment_alphanumeric_str(vm.mobileValidation.catergory4
                                    .substring(3, 8));
                            if (vm.mobileValidation.boxstatus != null) {
                                vm.mobileValidation.boxstatus = ' ,category4-completed'
                            } else {
                                vm.mobileValidation.boxstatus = 'category4-completed'
                            }
                        }
                        if (isLotComlpelted(vm.mobileValidation.countCategoryRv)) {
                            alert('For Category1 : Lot is completed');
                            vm.mobileValidation.categoryRv = mobileValidation
                                .findCategoryRv(onSucess);

                            function onSuccess(result) {
                                vm.mobileValidation.categoryRv = result;
                            }

                            vm.mobileValidation.categoryRv = 'ERV'
                                + increment_alphanumeric_str(vm.mobileValidation.categoryRv
                                    .substring(3, 8));
                        }

                        if (isLotComlpelted(vm.mobileValidation.countCategory5)
                            || vm.mobileValidation.countCategory5 === 800
                            || vm.mobileValidation.countCategory5 === 850
                            || vm.mobileValidation.countCategory5 === 900
                            || vm.mobileValidation.countCategory5 === 950
                            || vm.mobileValidation.countCategory5 === 1000
                            || vm.mobileValidation.countCategory5 === 1050
                            || vm.mobileValidation.countCategory5 === 1100
                            || vm.mobileValidation.countCategory5 === 1150
                            || vm.mobileValidation.countCategory5 === 1200
                            || vm.mobileValidation.countCategory5 === 1250
                            || vm.mobileValidation.countCategory5 === 1300
                            || vm.mobileValidation.countCategory5 === 1350
                            || vm.mobileValidation.countCategory5 === 1400
                            || vm.mobileValidation.countCategory5 === 1450
                            || vm.mobileValidation.countCategory5 === 1500
                            || vm.mobileValidation.countCategory5 === 1550) {
                            alert('For catergory5 : Lot is completed');
                            vm.mobileValidation.catergory5 = mobileValidation
                                .findcatergory5(onSucess);

                            function onSuccess(result) {
                                vm.mobileValidation.catergory5 = result;
                            }

                            vm.mobileValidation.catergory5 = 'EDA'
                                + increment_alphanumeric_str(vm.mobileValidation.catergory5
                                    .substring(3, 8));
                        }
                        if (vm.mobileValidation.countCategory3 === 50
                            || vm.mobileValidation.countCategory3 === 100
                            || vm.mobileValidation.countCategory3 === 150
                            || vm.mobileValidation.countCategory3 === 200
                            || vm.mobileValidation.countCategory3 === 250) {
                            alert('For Category3 : Lot is completed');
                        }
                        if (vm.mobileValidation.countCategory4 === 50
                            || vm.mobileValidation.countCategory4 === 100
                            || vm.mobileValidation.countCategory4 === 150
                            || vm.mobileValidation.countCategory4 === 200
                            || vm.mobileValidation.countCategory4 === 250) {
                            alert('For Category4 : Lot is completed');
                        }
                        if (vm.mobileValidation.countCategory5 === 50
                            || vm.mobileValidation.countCategory5 === 100
                            || vm.mobileValidation.countCategory5 === 150
                            || vm.mobileValidation.countCategory5 === 200
                            || vm.mobileValidation.countCategory5 === 250) {
                            alert('For catergory5 : Lot is completed');
                        }

                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccess, onSaveError);
                    }
                }

            }
        }

        init();

        function init() {
            MobileValidation.getSystemByName(vm.mobileValidation, onSuccess,
                onError);

            function onSuccess(data) {
                if (data.user != null) {
                    vm.mobileValidation.user = data.user;
                    var array = data.boxassign.split(",");

                    if (array[0].indexOf('EKA') > -1) {
                        vm.mobileValidation.category1 = array[0];
                    }
                    if (array[1].indexOf('EC1') > -1) {
                        vm.mobileValidation.category2 = array[1];
                    }
                    if (array[2].indexOf('EC2') > -1) {
                        vm.mobileValidation.category3 = array[2];
                    }
                    if (array[3].indexOf('EC3') > -1) {
                        vm.mobileValidation.catergory4 = array[3];
                    }
                    if (array[4].indexOf('EDA') > -1) {
                        vm.mobileValidation.catergory5 = array[4];
                    }
                    if (array[5].indexOf('ERV') > -1) {
                        vm.mobileValidation.categoryRv = array[5]
                    }
                    if (array[6].indexOf('ENA') > -1) {
                        vm.mobileValidation.categoryNA = array[6];
                    }
                }
                MobileValidation.getDetailsByName(vm.mobileValidation,
                    onSuccessName);

                function onSuccessName(result) {
                    if (result != null) {
                        vm.mobileValidation.sourcebox = result.sourcebox;
                        vm.mobileValidation.countCategory1 = result.countCategory1;
                        vm.mobileValidation.countCategory2 = result.countCategory2;
                        vm.mobileValidation.countCategory3 = result.countCategory3;
                        vm.mobileValidation.countCategory4 = result.countCategory4;
                        vm.mobileValidation.countCategory5 = result.countCategory5;
                        vm.mobileValidation.countCategoryRv = result.countCategoryRv;
                        vm.mobileValidation.countCategoryNA = result.countCategoryNA;
                        vm.mobileValidation.category1 = result.category1;
                        vm.mobileValidation.category2 = result.category2;
                        vm.mobileValidation.category3 = result.category3;
                        vm.mobileValidation.catergory4 = result.catergory4;
                        vm.mobileValidation.catergory5 = result.catergory5;
                        vm.mobileValidation.catergoryRv = result.catergoryRv;
                        vm.mobileValidation.catergoryNA = result.catergoryNA;
                        if (new Date().setHours(0, 0, 0, 0) > new Date(result.userDate).setHours(0, 0, 0, 0)) {
                            vm.mobileValidation.userCount = 0;
                        } else {
                            vm.mobileValidation.userCount = parseInt(result.userCount);
                        }
                    }
                }
            }

            function onError(result) {
                alert(result)
            }
        }

        function search() {
            if (vm.mobileValidation.mobilenumber.length === 10 && vm.mobileValidation.remobilenumber.length === 10) {
                MobileValidation.getByMobileNum(vm.mobileValidation, onSaveSuccessmob);

                function onSaveSuccessmob(result) {
                    if (result.length > 1) {
                        vm.mobileValidations = result;
                    } else {
                        result = result[0];
                        if (result === undefined) {
                            vm.mobileValidation.colorCode = 'Pink';
                            vm.mobileValidation.countCategoryNA = vm.mobileValidation.countCategoryNA + 1;
                            vm.mobileValidation.customerName = '';
                            vm.mobileValidation.activationDate = '';
                            vm.mobileValidation.fathername = '';
                            identifyColor(vm.mobileValidation);

                        } else {
                            if (result.mobilenumber !== null
                                && result.colorCode !== null) {
                                var cafType = result.colorCode;
                                vm.mobileValidation.customerName = result.customerName;
                                vm.mobileValidation.activationDate = result.activationDate;
                                vm.mobileValidation.fathername = result.fathername;
                                if (angular.equals(cafType.trim(), 'G3y')) {
                                    vm.mobileValidation.colorCode = 'Red';
                                }
                                if (angular.equals(cafType.trim(), 'Active')) {
                                    vm.mobileValidation.colorCode = 'Green';
                                }
                                if (angular.equals(cafType.trim(), 'L1')) {
                                    vm.mobileValidation.colorCode = 'Blue';
                                }
                                if (angular.equals(cafType.trim(), '1-2y')) {
                                    vm.mobileValidation.colorCode = 'Yellow';
                                }
                                if (angular.equals(cafType.trim(), '2-3y')) {
                                    vm.mobileValidation.colorCode = 'White';
                                }
                                if (angular.equals(cafType.trim(), 'RV')) {
                                    vm.mobileValidation.colorCode = 'Orange';
                                }
                                if (angular.equals(cafType.trim(), 'NA')
                                    || vm.mobileValidation.colorCode === "NA") {
                                    vm.mobileValidation.colorCode = 'Pink';
                                }

                            } else {
                                alert('You can not save with no mobile number');
                                vm.mobileValidation.mobilenumber = '';
                                vm.mobileValidation.remobilenumber = '';
                            }
                        }
                    }
                    $scope.updateSelection = function (position, entities) {
                        angular
                            .forEach(
                                entities,
                                function (subscription, index) {
                                    if (position === index) {
                                        subscription.isselected = true;
                                        vm.mobileValidation.customerName = entities[position].customerName;
                                        vm.mobileValidation.activationDate = entities[position].activationDate;
                                        vm.mobileValidation.fathername = entities[position].fathername;
                                        var cafType = entities[position].colorCode;
                                        if (angular.equals(cafType.trim(), 'G3y')) {
                                            vm.mobileValidation.colorCode = 'Red';
                                        }
                                        if (angular.equals(cafType.trim(), 'Active')) {
                                            vm.mobileValidation.colorCode = 'Green';
                                        }
                                        if (angular.equals(cafType.trim(), 'L1')) {
                                            vm.mobileValidation.colorCode = 'Blue';
                                        }
                                        if (angular.equals(cafType.trim(), '1-2y')) {
                                            vm.mobileValidation.colorCode = 'Yellow';
                                        }
                                        if (angular.equals(cafType.trim(), '2-3y')) {
                                            vm.mobileValidation.colorCode = 'White';
                                        }
                                        if (angular.equals(cafType.trim(), 'RV')) {
                                            vm.mobileValidation.colorCode = 'Orange';
                                        }
                                        if (angular.equals(cafType.trim(), 'NA')
                                            || vm.mobileValidation.colorCode === "NA") {
                                            vm.mobileValidation.colorCode = 'Pink';
                                        }
                                        // identifyColor(vm.mobileValidation);
                                    } else {
                                        subscription.isselected = false;
                                    }
                                });
                    }
                }
            } else {
                alert('please check mobile number');
            }
        }

        function saveSelectedMobile() {

            if (vm.mobileValidation.mobilenumber !== null && vm.mobileValidation.mobilenumber !== ''
                && vm.mobileValidation.remobilenumber !== null && vm.mobileValidation.remobilenumber !== ''
                && vm.mobileValidation.colorCode !== null) {
                if (vm.mobileValidation.colorCode === 'green') {
                    if (vm.mobileValidation.barcode !== null && vm.mobileValidation.barcode !== undefined) {
                        results(vm.mobileValidation);
                    } else {
                        alert('Bar code can not be null');
                    }
                } else {
                    results(vm.mobileValidation);

                }

            } else {
                alert('You can not save with no mobile number or color code is null');
            }
        }

        $scope.sourceBoxComplete = function () {

            alert('Source box is completed :  ' + vm.mobileValidation.sourcebox)
            $cookieStore.put('sourcecompleted', vm.mobileValidation.sourcebox);
            $scope.lastsourcebox = $cookieStore.get('sourcecompleted')
            vm.mobileValidation.sourcebox = '';
        };

        function results(result) {
            identifyColor(result);
            if (result === undefined) {
                result = vm.mobileValidation;
            }
            vm.mobileValidation.mobilenumber = result.mobilenumber;
            vm.mobileValidation.customerName = result.customerName;
            vm.mobileValidation.lastmobilenumber = result.mobilenumber;
            vm.mobileValidation.lastcolorCode = result.colorCode;
            vm.mobileValidation.activationDate = result.activationDate;
            vm.mobileValidation.fathername = result.fathername;


            // save functionality is being called
            MobileValidation.save(vm.mobileValidation, onSaveSuccessFinal,
                onMobileValidationError);

            function onSaveSuccessFinal(result) {
                vm.mobileValidation.id = null;
                vm.mobileValidation.remobilenumber = '';
                vm.mobileValidation.mobilenumber = '';
                vm.mobileValidation.colorCode = null;
                vm.mobileValidations = null;
                vm.mobileValidation.barcode = null;
                vm.mobileValidation.userCount = parseInt(result.userCount);

                if (vm.mobileValidation.countCategory5 >= 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.catergory5);
                    $cookieStore
                        .put('completed', vm.mobileValidation.catergory5);
                    vm.mobileValidation.countCategory5 = 0;
                    MobileValidation.findCategory5(onSuccesscatergory5);

                    // noinspection JSAnnotator
                    function onSuccesscatergory5(result) {
                        vm.mobileValidation.catergory5 = result.catergory5;
                        vm.mobileValidation.catergory5 = 'EDA'
                            + increment_alphanumeric_str(vm.mobileValidation.catergory5
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }
                if (vm.mobileValidation.countCategory1 >= 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.category1);
                    $cookieStore
                        .put('completed', vm.mobileValidation.category1);
                    vm.mobileValidation.countCategory1 = 0;
                    MobileValidation.findCategory1(onSuccessCategory1);

                    // noinspection JSAnnotator
                    function onSuccessCategory1(result) {
                        vm.mobileValidation.category1 = result.category1;
                        vm.mobileValidation.category1 = 'EKA'
                            + increment_alphanumeric_str(vm.mobileValidation.category1
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed');
                    }
                }

                if (vm.mobileValidation.countCategory2 >= 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.category2);
                    $cookieStore
                        .put('completed', vm.mobileValidation.category2);
                    vm.mobileValidation.countCategory2 = 0;
                    MobileValidation.findCategory2(onSuccessCategory2);

                    // noinspection JSAnnotator
                    function onSuccessCategory2(result) {
                        vm.mobileValidation.category2 = result.category2;
                        vm.mobileValidation.category2 = 'EC1'
                            + increment_alphanumeric_str(vm.mobileValidation.category2
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }
                if (vm.mobileValidation.countCategory3 >= 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.category3);
                    $cookieStore
                        .put('completed', vm.mobileValidation.category3);
                    vm.mobileValidation.countCategory3 = 0;
                    MobileValidation.findCategory3(onSuccessCategory3);

                    // noinspection JSAnnotator
                    function onSuccessCategory3(result) {
                        vm.mobileValidation.category3 = result.category3;
                        vm.mobileValidation.category3 = 'EC2'
                            + increment_alphanumeric_str(vm.mobileValidation.category3
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }
                if (vm.mobileValidation.countCategory4 >= 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.catergory4);
                    $cookieStore
                        .put('completed', vm.mobileValidation.catergory4);
                    vm.mobileValidation.countCategory4 = 0;
                    MobileValidation.findCategory4(onSuccessCategory4);

                    // noinspection JSAnnotator
                    function onSuccessCategory4(result) {
                        vm.mobileValidation.catergory4 = result.catergory4;
                        vm.mobileValidation.catergory4 = 'EC3'
                            + increment_alphanumeric_str(vm.mobileValidation.catergory4
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }

                if (vm.mobileValidation.countCategoryRv >= 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.categoryRv);
                    $cookieStore.put('completed',
                        vm.mobileValidation.categoryRv);
                    vm.mobileValidation.countCategoryRv = 0;
                    MobileValidation.findCategoryRv(onSuccessCategoryRv);

                    // noinspection JSAnnotator
                    function onSuccessCategoryRv(result) {
                        vm.mobileValidation.categoryRv = result.categoryRv;
                        vm.mobileValidation.categoryRv = 'ERV'
                            + increment_alphanumeric_str(vm.mobileValidation.categoryRv
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }

                if (vm.mobileValidation.countCategoryNA >= 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.categoryNA);
                    $cookieStore.put('completed',
                        vm.mobileValidation.categoryNA);
                    vm.mobileValidation.countCategoryNA = 0;
                    MobileValidation.findCategoryNa(onSuccessCategoryNA);

                    // noinspection JSAnnotator
                    function onSuccessCategoryNA(result) {
                        vm.mobileValidation.categoryNA = result.categoryNA;
                        vm.mobileValidation.categoryNA = 'ENA'
                            + increment_alphanumeric_str(vm.mobileValidation.categoryNA
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }
            }

            function onMobileValidationError() {

              //  vm.mobileValidation.userCount = parseInt(result.userCount) - 1;

                if (result.colorCode.trim() === 'G3y' || result.colorCode === 'red') {
                    vm.mobileValidation.colorCode = 'red';
                    vm.mobileValidation.countCategory5 = vm.mobileValidation.countCategory5 - 1;

                }
                if (angular.equals(result.colorCode.trim(), "Active")
                    || result.colorCode === "green") {
                    vm.mobileValidation.colorCode = 'green';
                    vm.mobileValidation.countCategory1 = vm.mobileValidation.countCategory1 - 1;

                }
                if (angular.equals(result.colorCode.trim(), "L1") || result.colorCode === "blue") {
                    vm.mobileValidation.colorCode = 'blue';
                    vm.mobileValidation.countCategory2 = vm.mobileValidation.countCategory2 - 1;

                }
                if (angular.equals(result.colorCode.trim(), "1-2y")
                    || result.colorCode === "yellow") {
                    vm.mobileValidation.colorCode = 'yellow';
                    vm.mobileValidation.countCategory3 = vm.mobileValidation.countCategory3 - 1;

                }
                if (angular.equals(result.colorCode.trim(), "2-3y") || result.colorCode === "white") {
                    vm.mobileValidation.colorCode = 'white';
                    vm.mobileValidation.countCategory4 = vm.mobileValidation.countCategory4 - 1;

                }
                if (angular.equals(result.colorCode.trim(), "RV") || result.colorCode === "orange") {
                    vm.mobileValidation.colorCode = 'orange';
                    vm.mobileValidation.countCategoryRv = vm.mobileValidation.countCategoryRv - 1;

                }


                if (vm.mobileValidation.countCategory5 === 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.catergory5);
                    $cookieStore
                        .put('completed', vm.mobileValidation.category1);
                    vm.mobileValidation.countCategory5 = 0;
                    MobileValidation.findcategory5(onSuccesscatergory5);

                    // noinspection JSAnnotator
                    function onSuccesscatergory5(result) {
                        vm.mobileValidation.catergory5 = result.catergory5;
                        vm.mobileValidation.catergory5 = 'EDA'
                            + increment_alphanumeric_str(vm.mobileValidation.catergory5
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }
                if (vm.mobileValidation.countCategory1 === 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.category1);
                    $cookieStore
                        .put('completed', vm.mobileValidation.category1);
                    vm.mobileValidation.countCategory1 = 0;
                    MobileValidation.findCategory1(onSuccessCategory1);

                    // noinspection JSAnnotator
                    function onSuccessCategory1(result) {
                        vm.mobileValidation.category1 = result.category1;
                        vm.mobileValidation.category1 = 'EKA'
                            + increment_alphanumeric_str(vm.mobileValidation.category1
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed');
                    }
                }

                if (vm.mobileValidation.countCategory2 === 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.category2);
                    $cookieStore
                        .put('completed', vm.mobileValidation.category2);
                    vm.mobileValidation.countCategory2 = 0;
                    MobileValidation.findCategory2(onSuccessCategory2);

                    // noinspection JSAnnotator
                    function onSuccessCategory2(result) {

                        vm.mobileValidation.category2 = result.category2;
                        vm.mobileValidation.category2 = 'EC1'
                            + increment_alphanumeric_str(vm.mobileValidation.category2
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }
                if (vm.mobileValidation.countCategory3 === 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.category3);
                    $cookieStore
                        .put('completed', vm.mobileValidation.category3);
                    vm.mobileValidation.countCategory3 = 0;
                    MobileValidation.findCategory3(onSuccessCategory3);

                    // noinspection JSAnnotator
                    function onSuccessCategory3(result) {
                        vm.mobileValidation.category3 = result.category3;
                        vm.mobileValidation.category3 = 'EC2'
                            + increment_alphanumeric_str(vm.mobileValidation.category3
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }
                if (vm.mobileValidation.countCategory4 === 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.category4);
                    $cookieStore
                        .put('completed', vm.mobileValidation.category4);
                    vm.mobileValidation.countCategory4 = 0;
                    MobileValidation.findCategory4(onSuccessCategory4);

                    // noinspection JSAnnotator
                    function onSuccessCategory4(result) {
                        vm.mobileValidation.category4 = result.category4;
                        vm.mobileValidation.category4 = 'EC3'
                            + increment_alphanumeric_str(vm.mobileValidation.category4
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }

                if (vm.mobileValidation.countCategoryRv === 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.categoryRv);
                    $cookieStore.put('completed',
                        vm.mobileValidation.categoryRv);
                    vm.mobileValidation.countCategoryRv = 0;
                    MobileValidation.findCategoryRv(onSuccessCategoryRv);

                    // noinspection JSAnnotator
                    function onSuccessCategoryRv(result) {
                        vm.mobileValidation.categoryRv = result.categoryRv;
                        vm.mobileValidation.categoryRv = 'ERV'
                            + increment_alphanumeric_str(vm.mobileValidation.categoryRv
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }

                if (vm.mobileValidation.countCategoryNA === 800) {
                    alert("Box Name is complete "
                        + vm.mobileValidation.categoryNA);
                    $cookieStore.put('completed',
                        vm.mobileValidation.categoryNA);
                    vm.mobileValidation.countCategoryNA = 0;
                    MobileValidation.findCategoryNa(onSuccessCategoryNA);

                    // noinspection JSAnnotator
                    function onSuccessCategoryNA(result) {
                        vm.mobileValidation.categoryNA = result.categoryNA;
                        vm.mobileValidation.categoryNA = 'ENA'
                            + increment_alphanumeric_str(vm.mobileValidation.categoryNA
                                .substring(3, 8));
                        MobileValidation.save(vm.mobileValidation,
                            onSaveSuccessFinal);
                        $scope.lastcompletedbox = $cookieStore.get('completed')
                    }
                }
            }
        }

        function onSaveSuccess() {

            if (vm.mobileValidation.countCategory5 === 1600) {

                $cookieStore.put('completed', vm.mobileValidation.category1);
                vm.mobileValidation.countCategory5 = 0;
                vm.mobileValidation.catergory5 = 'EDA'
                    + increment_alphanumeric_str(vm.mobileValidation.catergory5
                        .substring(3, 8));
                $scope.lastcompletedbox = $cookieStore.get('completed')
            }

            if (vm.mobileValidation.countCategory1 === 800) {
                $cookieStore.put('completed', vm.mobileValidation.category2);
                vm.mobileValidation.countCategory1 = 0;
                mobileValidation.findCategory1(onSuccessCategory1);

                function onSuccessCategory1(result) {
                    vm.mobileValidation.category1 = result.category1;
                    vm.mobileValidation.category1 = 'EKA'
                        + increment_alphanumeric_str(vm.mobileValidation.category1
                            .substring(3, 8));
                }

                $scope.lastcompletedbox = $cookieStore.get('completed')
            }
            if (vm.mobileValidation.countCategory2 === 300) {
                $cookieStore.put('completed', vm.mobileValidation.category2);
                vm.mobileValidation.countCategory2 = 0;
                mobileValidation.findCategory2(onSuccessCategory2);

                function onSuccessCategory2(result) {
                    vm.mobileValidation.category2 = result.category2;
                    vm.mobileValidation.category2 = 'EC2'
                        + increment_alphanumeric_str(vm.mobileValidation.category2
                            .substring(3, 8));
                }

                $scope.lastcompletedbox = $cookieStore.get('completed')
            }
            if (vm.mobileValidation.countCategory3 === 300) {
                $cookieStore.put('completed', vm.mobileValidation.category3);
                vm.mobileValidation.countCategory3 = 0;
                mobileValidation.findCategory4(onSuccessCategory3);

                function onSuccessCategory3(result) {
                    vm.mobileValidation.category3 = result.category3;
                    vm.mobileValidation.category3 = 'EC2'
                        + increment_alphanumeric_str(vm.mobileValidation.category3
                            .substring(3, 8));
                }
            }

            if (vm.mobileValidation.countCategory4 === 300) {
                $cookieStore.put('completed', vm.mobileValidation.catergory4);
                vm.mobileValidation.countCategory4 = 0;
                vm.mobileValidation.catergory4 = 'EC3'
                    + increment_alphanumeric_str(vm.mobileValidation.catergory4
                        .substring(4, 8));
                $scope.lastcompletedbox = $cookieStore.get('completed')
            }

            if (vm.mobileValidation.countCategory5 === 800) {
                $cookieStore.put('completed', vm.mobileValidation.catergory5);
                vm.mobileValidation.countCategory5 = 0;
                mobileValidation.findcatergory5(onSuccessCategory1);

                function onSuccesscatergory5(result) {
                    vm.mobileValidation.catergory5 = result.catergory5;
                    vm.mobileValidation.catergory5 = 'EKA'
                        + increment_alphanumeric_str(vm.mobileValidation.catergory5
                            .substring(3, 8));
                }

                $scope.lastcompletedbox = $cookieStore.get('completed')
            }
            if (vm.mobileValidation.countCategoryRv === 800) {
                alert("Box Name is complete " + vm.mobileValidation.categoryRv);
                $cookieStore.put('completed', vm.mobileValidation.categoryRv);
                vm.mobileValidation.countCategoryRv = 0;
                MobileValidation.findCategoryRv(onSuccessCategoryRv);

                // noinspection JSAnnotator
                function onSuccessCategoryRv(result) {
                    vm.mobileValidation.categoryRv = result.categoryRv;
                    vm.mobileValidation.categoryRv = 'ERV'
                        + increment_alphanumeric_str(vm.mobileValidation.categoryRv
                            .substring(3, 8));
                    MobileValidation.save(vm.mobileValidation,
                        onSaveSuccessFinal);
                    $scope.lastcompletedbox = $cookieStore.get('completed')
                }
            }

            if (vm.mobileValidation.countCategoryNA === 800) {
                alert("Box Name is complete " + vm.mobileValidation.categoryNA);
                $cookieStore.put('completed', vm.mobileValidation.categoryNA);
                vm.mobileValidation.countCategoryNA = 0;
                MobileValidation.findCategoryNa(onSuccessCategoryNA);

                // noinspection JSAnnotator
                function onSuccessCategoryNA(result) {
                    vm.mobileValidation.categoryNA = result.categoryNA;
                    vm.mobileValidation.categoryNA = 'ENA'
                        + increment_alphanumeric_str(vm.mobileValidation.categoryNA
                            .substring(3, 8));
                    MobileValidation.save(vm.mobileValidation,
                        onSaveSuccessFinal);
                    $scope.lastcompletedbox = $cookieStore.get('completed')
                }
            }
            vm.mobileValidation.mobilenumber = '';
            vm.mobileValidation.remobilenumber = '';
        }

        $scope.validate1 = function () {

            if (vm.mobileValidation.category1.length === 8) {
                if (vm.mobileValidation.category1.substring(0, 3) !== 'EDA') {
                    alert('Assigned Box in Category 1 is not correct !!! Please Check')
                }
            }
            if (vm.mobileValidation.category2.length === 8) {
                if (vm.mobileValidation.category2.substring(0, 3) !== 'EKA') {
                    alert('Assigned Box in Category 2 is not correct !!! Please Check')
                }
            }
            if (vm.mobileValidation.category3.length === 8) {
                if (vm.mobileValidation.category3.substring(0, 3) !== 'EC1') {
                    alert('Assigned Box in Category 3 is not correct !!! Please Check')
                }
            }
            if (vm.mobileValidation.catergory4.length === 8) {
                if (vm.mobileValidation.catergory4.substring(0, 3) !== 'EC2') {
                    alert('Assigned Box in Category 4 is not correct !!! Please Check')
                }
            }
            if (vm.mobileValidation.catergory5.length === 8) {
                if (vm.mobileValidation.catergory5.substring(0, 3) !== 'EC3') {
                    alert('Assigned Box in Category 5 is not correct !!! Please Check')
                }
            }
        };

        function onSaveError() {
            vm.isSaving = false;
        }

        $scope.isSelected = function (mobileValidation) {
            vm.mobileValidation.colorCode = mobileValidation.colorCode;
            if (mobileValidation.isselected) {
                if (vm.mobileValidation.colorCode.trim() === 'G3y') {
                    vm.mobileValidation.colorCode = 'red';
                    vm.mobileValidation.countCategory5 = vm.mobileValidation.countCategory5 + 1;
                }
                if (vm.mobileValidation.colorCode.trim() === 'Active') {
                    vm.mobileValidation.colorCode = 'green';
                    vm.mobileValidation.countCategory1 = vm.mobileValidation.countCategory1 + 1;
                }
                if (vm.mobileValidation.colorCode.trim() === 'L1') {
                    vm.mobileValidation.colorCode = 'blue';
                    vm.mobileValidation.countCategory2 = vm.mobileValidation.countCategory2 + 1;
                }
                if (vm.mobileValidation.colorCode.trim() === '0-1') {
                    vm.mobileValidation.colorCode = 'yellow';
                    vm.mobileValidation.countCategory3 = vm.mobileValidation.countCategory3 + 1;
                }
                if (vm.mobileValidation.colorCode.trim() === '1-2') {
                    vm.mobileValidation.colorode = 'white';
                    vm.mobileValidation.countCategory4 = vm.mobileValidation.countCategory4 + 1;
                }
                if (vm.mobileValidation.colorCode.trim() === 'RV') {
                    vm.mobileValidation.colorode = 'orange';
                    vm.mobileValidation.countCategoryRv = vm.mobileValidation.countCategoryRv + 1;
                }
            }
        };

        vm.datePickerOpenStatus.userDate = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        $scope.toggleCustom = function () {
            $scope.custom = $scope.custom === false;
        };

        function isLotComlpelted(category) {
            return (category === 50 || category === 100 || category === 150
                || category === 200 || category === 250 || category === 300
                || category === 350 || category === 400 || category === 450
                || category === 500 || category === 550 || category === 600
                || category === 650 || category === 700 || category === 750)
        }

        $scope.BoxComplete = function () {

            if (vm.mobileValidation.sourceboxstaus.indexOf('EDA') > -1) {
                vm.mobileValidation.caftype = 'category_1'
            }
            if (vm.mobileValidation.sourceboxstaus.indexOf('EKA') > -1) {
                vm.mobileValidation.caftype = 'category_2'
            }
            if (vm.mobileValidation.sourceboxstaus.indexOf('EC1') > -1) {
                vm.mobileValidation.caftype = 'category_3'
            }
            if (vm.mobileValidation.sourceboxstaus.indexOf('EC2') > -1) {
                vm.mobileValidation.caftype = 'category_4'
            }
            if (vm.mobileValidation.sourceboxstaus.indexOf('EC3') > -1) {
                vm.mobileValidation.caftype = 'category_5'
            }
        };

        function getOutBoxButton() {
            MobileValidation.getOutBox(vm.mobileValidation, getOutBox, geterrorbox);

            function getOutBox(result) {
                $scope.item = result;

                var blob = new Blob(
                    [result.boxstatus],
                    {
                        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
                    });

                saveAs(blob, "Report.xls");

            }

            function geterrorbox(result) {
                alert(result.boxstatus);
            }
        }

        function identifyColor(result) {

            if (result.colorCode.trim() === 'G3y' || result.colorCode === 'red') {
                vm.mobileValidation.colorCode = 'red';
                vm.mobileValidation.countCategory5 = vm.mobileValidation.countCategory5 + 1;
                if (isLotComlpelted(vm.mobileValidation.countCategory5)) {
                    alert('For >3 years : Lot is completed, Lot number is:'
                        + vm.mobileValidation.countCategory5 / 50
                        + " Box Name " + vm.mobileValidation.catergory5
                        + " Color " + vm.mobileValidation.colorCode);
                    alert('Please press ENTER');

                }
            }
            if (angular.equals(result.colorCode.trim(), "Active")
                || result.colorCode === "green") {
                vm.mobileValidation.colorCode = 'green';
                vm.mobileValidation.countCategory1 = vm.mobileValidation.countCategory1 + 1;
                if (isLotComlpelted(vm.mobileValidation.countCategory1)) {
                    alert('For Active : Lot is completed, Lot number is:'
                        + vm.mobileValidation.countCategory1 / 50
                        + " Box Name " + vm.mobileValidation.category1
                        + " Color " + vm.mobileValidation.colorCode);
                    alert('Please press ENTER');
                    // MobileValidation.findCategory1(onSuccessCategory);
                }
            }
            if (angular.equals(result.colorCode.trim(), "L1") || result.colorCode === "blue") {
                vm.mobileValidation.colorCode = 'blue';
                vm.mobileValidation.countCategory2 = vm.mobileValidation.countCategory2 + 1;
                if (isLotComlpelted(vm.mobileValidation.countCategory2)) {
                    alert('For < 1 year : Lot is completed, Lot number is:'
                        + vm.mobileValidation.countCategory2 / 50
                        + " Box Name " + vm.mobileValidation.category2
                        + " Color " + vm.mobileValidation.colorCode);
                    alert('Please press ENTER');
                }
            }
            if (angular.equals(result.colorCode.trim(), "1-2y")
                || result.colorCode === "yellow") {
                vm.mobileValidation.colorCode = 'yellow';
                vm.mobileValidation.countCategory3 = vm.mobileValidation.countCategory3 + 1;
                if (isLotComlpelted(vm.mobileValidation.countCategory3)) {
                    alert('For 1-2 years : Lot is completed, Lot number is:'
                        + vm.mobileValidation.countCategory3
                        / 50
                        + " Box Name "
                        + vm.mobileValidation.category3
                        + " Color " + vm.mobileValidation.colorCode);
                    alert('Please press ENTER');
                }
            }
            if (angular.equals(result.colorCode.trim(), "2-3y") || result.colorCode === "white") {
                vm.mobileValidation.colorCode = 'white';
                vm.mobileValidation.countCategory4 = vm.mobileValidation.countCategory4 + 1;
                if (isLotComlpelted(vm.mobileValidation.countCategory4)) {
                    alert('For 2-3 years : Lot is completed, Lot number is:'
                        + vm.mobileValidation.countCategory4
                        / 50
                        + " Box Name "
                        + vm.mobileValidation.category4
                        + " Color " + vm.mobileValidation.colorCode);
                    alert('Please press ENTER');
                }
            }
            if (angular.equals(result.colorCode.trim(), "RV") || result.colorCode === "orange") {
                vm.mobileValidation.colorCode = 'orange';
                vm.mobileValidation.countCategoryRv = vm.mobileValidation.countCategoryRv + 1;
                if (isLotComlpelted(vm.mobileValidation.countCategoryRv)) {
                    alert('RV : Lot is completed, Lot number is:'
                        + vm.mobileValidation.countCategoryRv / 50
                        + " Box Name " + vm.mobileValidation.categoryRv
                        + " Color " + vm.mobileValidation.colorCode);
                    alert('Please press ENTER');
                }
            }
            if (angular.equals(result.colorCode.trim(), "NA") || result.colorCode === "pink") {
                vm.mobileValidation.colorCode = 'pink';
                vm.mobileValidation.countCategoryNA = vm.mobileValidation.countCategoryNA + 1;
                if (isLotComlpelted(vm.mobileValidation.countCategoryNA)) {
                    alert('NA : Lot is completed, Lot number is:'
                        + vm.mobileValidation.countCategoryNA / 50
                        + " Box Name " + vm.mobileValidation.categoryNA
                        + " Color " + vm.mobileValidation.colorCode);
                    alert('Please press ENTER');
                }
            }
        }

        function increment_alphanumeric_str(str) {
            var numeric = str.match(/\d+$/)[0];
            var prefix = str.split(numeric)[0];

            function increment_string_num(str) {
                var inc = String(parseInt(str) + 1);
                return str.slice(0, str.length - inc.length) + inc;
            }

            return prefix + increment_string_num(numeric);
        }
    }

})();
