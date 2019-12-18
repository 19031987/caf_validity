(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('userchange', {
            parent: 'entity',
            url: '/userchange?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Userchanges'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/userchange/userchanges.html',
                    controller: 'UserchangeController',
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
        .state('userchange-detail', {
            parent: 'userchange',
            url: '/userchange/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Userchange'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/userchange/userchange-detail.html',
                    controller: 'UserchangeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Userchange', function($stateParams, Userchange) {
                    return Userchange.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'userchange',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('userchange-detail.edit', {
            parent: 'userchange-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/userchange/userchange-dialog.html',
                    controller: 'UserchangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Userchange', function(Userchange) {
                            return Userchange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('userchange.new', {
            parent: 'userchange',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/userchange/userchange-dialog.html',
                    controller: 'UserchangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                user: null,
                                changeuser: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('userchange', null, { reload: 'userchange' });
                }, function() {
                    $state.go('userchange');
                });
            }]
        })
        .state('userchange.edit', {
            parent: 'userchange',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/userchange/userchange-dialog.html',
                    controller: 'UserchangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Userchange', function(Userchange) {
                            return Userchange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('userchange', null, { reload: 'userchange' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('userchange.delete', {
            parent: 'userchange',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/userchange/userchange-delete-dialog.html',
                    controller: 'UserchangeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Userchange', function(Userchange) {
                            return Userchange.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('userchange', null, { reload: 'userchange' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
