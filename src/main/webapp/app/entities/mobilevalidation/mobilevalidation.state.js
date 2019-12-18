(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mobilevalidation', {
            parent: 'entity',
            url: '/mobilevalidation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mobilevalidations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mobilevalidation/mobilevalidations.html',
                    controller: 'MobilevalidationController',
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
        .state('mobilevalidation-detail', {
            parent: 'mobilevalidation',
            url: '/mobilevalidation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mobilevalidation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mobilevalidation/mobilevalidation-detail.html',
                    controller: 'MobilevalidationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Mobilevalidation', function($stateParams, Mobilevalidation) {
                    return Mobilevalidation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mobilevalidation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mobilevalidation-detail.edit', {
            parent: 'mobilevalidation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobilevalidation/mobilevalidation-dialog.html',
                    controller: 'MobilevalidationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mobilevalidation', function(Mobilevalidation) {
                            return Mobilevalidation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mobilevalidation.new', {
            parent: 'mobilevalidation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobilevalidation/mobilevalidation-dialog.html',
                    controller: 'MobilevalidationDialogController',
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
                                user: null,
                                userDate: null,
                                isselected: null,
                                category1: null,
                                category2: null,
                                category3: null,
                                catergory4: null,
                                catergory5: null,
                                countCategory1: null,
                                countCategory2: null,
                                countCategory3: null,
                                countCategory4: null,
                                countCategory5: null,
                                sourcebox: null,
                                categoryRv: null,
                                countCategoryRv: null,
                                categoryNA: null,
                                countCategoryNA: null,
                                barcode: null,
                                userCount: null,
                                barcodeName: null,
                                lot: null,
                                barcodeName1: null,
                                lot1: null,
                                fathername: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mobilevalidation', null, { reload: 'mobilevalidation' });
                }, function() {
                    $state.go('mobilevalidation');
                });
            }]
        })
        .state('mobilevalidation.edit', {
            parent: 'mobilevalidation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobilevalidation/mobilevalidation-dialog.html',
                    controller: 'MobilevalidationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mobilevalidation', function(Mobilevalidation) {
                            return Mobilevalidation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mobilevalidation', null, { reload: 'mobilevalidation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mobilevalidation.delete', {
            parent: 'mobilevalidation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mobilevalidation/mobilevalidation-delete-dialog.html',
                    controller: 'MobilevalidationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mobilevalidation', function(Mobilevalidation) {
                            return Mobilevalidation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mobilevalidation', null, { reload: 'mobilevalidation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
