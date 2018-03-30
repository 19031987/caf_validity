(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scancaf', {
            parent: 'entity',
            url: '/scancaf?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Scancafs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scancaf/scancafs.html',
                    controller: 'ScancafController',
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
        .state('scancaf-detail', {
            parent: 'scancaf',
            url: '/scancaf/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Scancaf'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scancaf/scancaf-detail.html',
                    controller: 'ScancafDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Scancaf', function($stateParams, Scancaf) {
                    return Scancaf.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'scancaf',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('scancaf-detail.edit', {
            parent: 'scancaf-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scancaf/scancaf-dialog.html',
                    controller: 'ScancafDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scancaf', function(Scancaf) {
                            return Scancaf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scancaf.new', {
            parent: 'scancaf',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scancaf/scancaf-dialog.html',
                    controller: 'ScancafDialogController',
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
                                countCategoryNA: 0,
                                cafbarcode: null,
                                colorcode: null,
                                user: null,
                                userdate: null,
                                boxstatus: null,
                                sourceboxstaus: null,
                                mobilenumber: null,
                                centralbarcode: null,
                                caftype: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scancaf', null, { reload: 'scancaf' });
                }, function() {
                    $state.go('scancaf');
                });
            }]
        })
        .state('scancaf.edit', {
            parent: 'scancaf',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scancaf/scancaf-dialog.html',
                    controller: 'ScancafDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scancaf', function(Scancaf) {
                            return Scancaf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scancaf', null, { reload: 'scancaf' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scancaf.delete', {
            parent: 'scancaf',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scancaf/scancaf-delete-dialog.html',
                    controller: 'ScancafDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Scancaf', function(Scancaf) {
                            return Scancaf.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scancaf', null, { reload: 'scancaf' });
                }, function() {
                    $state.go('^');
                });
            }]
        }).state('scancaf.second', {
            parent: 'scancaf',
            url: '/second',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scancaf/scancaf-dialog_second.html',
                    controller: 'ScancafDialogController',
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
                                cafbarcode: null,
                                colorcode: null,
                                user: null,
                                userdate: null,
                                boxstatus: null,
                                sourceboxstaus: null,
                                mobilenumber: null,
                                centralbarcode: null,
                                caftype: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scancaf', null, { reload: 'scancaf' });
                }, function() {
                    $state.go('scancaf');
                });
            }]
        })
        ;
    }

})();
