(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('adminstats', {
            parent: 'entity',
            url: '/adminstats?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Adminstats'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/adminstats/adminstats.html',
                    controller: 'AdminstatsController',
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
        .state('adminstats-detail', {
            parent: 'adminstats',
            url: '/adminstats/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Adminstats'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/adminstats/adminstats-detail.html',
                    controller: 'AdminstatsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Adminstats', function($stateParams, Adminstats) {
                    return Adminstats.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'adminstats',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('adminstats-detail.edit', {
            parent: 'adminstats-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adminstats/adminstats-dialog.html',
                    controller: 'AdminstatsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Adminstats', function(Adminstats) {
                            return Adminstats.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('adminstats.new', {
            parent: 'adminstats',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adminstats/adminstats-dialog.html',
                    controller: 'AdminstatsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                user: null,
                                usercount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('adminstats', null, { reload: 'adminstats' });
                }, function() {
                    $state.go('adminstats');
                });
            }]
        })
        .state('adminstats.edit', {
            parent: 'adminstats',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adminstats/adminstats-dialog.html',
                    controller: 'AdminstatsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Adminstats', function(Adminstats) {
                            return Adminstats.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('adminstats', null, { reload: 'adminstats' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('adminstats.delete', {
            parent: 'adminstats',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/adminstats/adminstats-delete-dialog.html',
                    controller: 'AdminstatsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Adminstats', function(Adminstats) {
                            return Adminstats.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('adminstats', null, { reload: 'adminstats' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
