(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('boxassign', {
            parent: 'entity',
            url: '/boxassign?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Boxassigns'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/boxassign/boxassigns.html',
                    controller: 'BoxassignController',
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
        .state('boxassign-detail', {
            parent: 'boxassign',
            url: '/boxassign/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Boxassign'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/boxassign/boxassign-detail.html',
                    controller: 'BoxassignDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Boxassign', function($stateParams, Boxassign) {
                    return Boxassign.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'boxassign',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('boxassign-detail.edit', {
            parent: 'boxassign-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/boxassign/boxassign-dialog.html',
                    controller: 'BoxassignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Boxassign', function(Boxassign) {
                            return Boxassign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('boxassign.new', {
            parent: 'boxassign',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/boxassign/boxassign-dialog.html',
                    controller: 'BoxassignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                user: null,
                                boxassign: null,
                                churntype: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('boxassign', null, { reload: 'boxassign' });
                }, function() {
                    $state.go('boxassign');
                });
            }]
        })
        .state('boxassign.edit', {
            parent: 'boxassign',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/boxassign/boxassign-dialog.html',
                    controller: 'BoxassignDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Boxassign', function(Boxassign) {
                            return Boxassign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('boxassign', null, { reload: 'boxassign' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('boxassign.delete', {
            parent: 'boxassign',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/boxassign/boxassign-delete-dialog.html',
                    controller: 'BoxassignDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Boxassign', function(Boxassign) {
                            return Boxassign.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('boxassign', null, { reload: 'boxassign' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
