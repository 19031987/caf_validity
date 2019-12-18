(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mobile-validation', {
            parent: 'entity',
            url: '/mobile-validation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MobileValidations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mobile-validation/mobile-validations.html',
                    controller: 'MobileValidationController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('mobile-validation-detail', {
            parent: 'mobile-validation',
            url: '/mobile-validation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MobileValidation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mobile-validation/mobile-validation-detail.html',
                    controller: 'MobileValidationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MobileValidation', function($stateParams, MobileValidation) {
                    return MobileValidation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mobile-validation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mobile-validation-detail.edit', {
            parent: 'mobile-validation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobile-validation/mobile-validation-dialog.html',
                    controller: 'MobileValidationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MobileValidation', function(MobileValidation) {
                            return MobileValidation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mobile-validation.new', {
            parent: 'mobile-validation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobile-validation/mobile-validation-dialog.html',
                    controller: 'MobileValidationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                mobilenumber: null,
                                activationDate: null,
                                customerName: null,
                                colorCode: null,
                                 countCategory1: 0,
                                 countCategory2: 0,
                                 countCategory3: 0,
                                 countCategory4: 0,
                                 countCategory5: 0,
                                 countCategoryRv: 0,
                                 countCategoryNA: 0,
                                 userCount:0,
                                user: null,
                                userDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mobile-validation', null, { reload: 'mobile-validation' });
                }, function() {
                    $state.go('mobile-validation');
                });
            }]
        })
        .state('mobile-validation.edit', {
            parent: 'mobile-validation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobile-validation/mobile-validation-dialog.html',
                    controller: 'MobileValidationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MobileValidation', function(MobileValidation) {
                            return MobileValidation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mobile-validation', null, { reload: 'mobile-validation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mobile-validation.delete', {
            parent: 'mobile-validation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobile-validation/mobile-validation-delete-dialog.html',
                    controller: 'MobileValidationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MobileValidation', function(MobileValidation) {
                            return MobileValidation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mobile-validation', null, { reload: 'mobile-validation' });
                }, function() {
                    $state.go('^');
                });
            }]
        }) .state('mobile-validation.second', {
            parent: 'mobile-validation',
            url: '/second',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobile-validation/mobile-validation-dialog_second.html',
                    controller: 'MobileValidationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sourcebox: null,
                                category1: null,
                                category2: null,
                                category3: null,
                                category4: null,
                                category5: null,
                                countCategory1: 0,
                                countCategory2: 0,
                                countCategory3: 0,
                                countCategory4: 0,
                                countCategory5: 0,
                                countCategoryRv: 0,
                                colorcode: null,
                                user: null,
                                userdate: null,
                                boxstatus: null,
                                mobilenumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mobile-validation', null, { reload: 'mobile-validation' });
                }, function() {
                    $state.go('mobile-validation');
                });
            }]
        });
    }

})();
