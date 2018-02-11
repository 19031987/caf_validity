(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('system', {
            parent: 'entity',
            url: '/system',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Systems'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/system/systems.html',
                    controller: 'SystemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('system-detail', {
            parent: 'system',
            url: '/system/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'System'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/system/system-detail.html',
                    controller: 'SystemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'System', function($stateParams, System) {
                    return System.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'system',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('system-detail.edit', {
            parent: 'system-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system/system-dialog.html',
                    controller: 'SystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['System', function(System) {
                            return System.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('system.new', {
            parent: 'system',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system/system-dialog.html',
                    controller: 'SystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                systemname: null,
                                user: null,
                                userdate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('system', null, { reload: 'system' });
                }, function() {
                    $state.go('system');
                });
            }]
        })
        .state('system.edit', {
            parent: 'system',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system/system-dialog.html',
                    controller: 'SystemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['System', function(System) {
                            return System.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('system', null, { reload: 'system' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('system.delete', {
            parent: 'system',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/system/system-delete-dialog.html',
                    controller: 'SystemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['System', function(System) {
                            return System.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('system', null, { reload: 'system' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
