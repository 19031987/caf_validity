(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('suspendedusers', {
            parent: 'entity',
            url: '/suspendedusers?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Suspendedusers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/suspendedusers/suspendedusers.html',
                    controller: 'SuspendedusersController',
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
        .state('suspendedusers-detail', {
            parent: 'suspendedusers',
            url: '/suspendedusers/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Suspendedusers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/suspendedusers/suspendedusers-detail.html',
                    controller: 'SuspendedusersDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Suspendedusers', function($stateParams, Suspendedusers) {
                    return Suspendedusers.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'suspendedusers',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('suspendedusers-detail.edit', {
            parent: 'suspendedusers-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suspendedusers/suspendedusers-dialog.html',
                    controller: 'SuspendedusersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Suspendedusers', function(Suspendedusers) {
                            return Suspendedusers.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('suspendedusers.new', {
            parent: 'suspendedusers',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suspendedusers/suspendedusers-dialog.html',
                    controller: 'SuspendedusersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                mobilenumber: null,
                                centralbarcode: null,
                                count: null,
                                user: null,
                                userdate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('suspendedusers', null, { reload: 'suspendedusers' });
                }, function() {
                    $state.go('suspendedusers');
                });
            }]
        })
        .state('suspendedusers.edit', {
            parent: 'suspendedusers',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suspendedusers/suspendedusers-dialog.html',
                    controller: 'SuspendedusersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Suspendedusers', function(Suspendedusers) {
                            return Suspendedusers.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('suspendedusers', null, { reload: 'suspendedusers' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('suspendedusers.delete', {
            parent: 'suspendedusers',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/suspendedusers/suspendedusers-delete-dialog.html',
                    controller: 'SuspendedusersDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Suspendedusers', function(Suspendedusers) {
                            return Suspendedusers.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('suspendedusers', null, { reload: 'suspendedusers' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
