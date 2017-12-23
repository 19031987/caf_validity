(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sourcebox', {
            parent: 'entity',
            url: '/sourcebox?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sourceboxes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sourcebox/sourceboxes.html',
                    controller: 'SourceboxController',
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
        .state('sourcebox-detail', {
            parent: 'sourcebox',
            url: '/sourcebox/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sourcebox'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sourcebox/sourcebox-detail.html',
                    controller: 'SourceboxDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Sourcebox', function($stateParams, Sourcebox) {
                    return Sourcebox.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sourcebox',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sourcebox-detail.edit', {
            parent: 'sourcebox-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sourcebox/sourcebox-dialog.html',
                    controller: 'SourceboxDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sourcebox', function(Sourcebox) {
                            return Sourcebox.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sourcebox.new', {
            parent: 'sourcebox',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sourcebox/sourcebox-dialog.html',
                    controller: 'SourceboxDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sourceboxname: null,
                                createduser: null,
                                createddate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('sourcebox', null, { reload: 'sourcebox' });
                }, function() {
                    $state.go('sourcebox');
                });
            }]
        })
        .state('sourcebox.edit', {
            parent: 'sourcebox',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sourcebox/sourcebox-dialog.html',
                    controller: 'SourceboxDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sourcebox', function(Sourcebox) {
                            return Sourcebox.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sourcebox', null, { reload: 'sourcebox' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sourcebox.delete', {
            parent: 'sourcebox',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sourcebox/sourcebox-delete-dialog.html',
                    controller: 'SourceboxDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sourcebox', function(Sourcebox) {
                            return Sourcebox.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sourcebox', null, { reload: 'sourcebox' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
