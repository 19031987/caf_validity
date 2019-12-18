(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rejected-reasons-my-suffix', {
            parent: 'entity',
            url: '/rejected-reasons-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RejectedReasons'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rejected-reasons/rejected-reasonsmySuffix.html',
                    controller: 'RejectedReasonsMySuffixController',
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
        .state('rejected-reasons-my-suffix-detail', {
            parent: 'rejected-reasons-my-suffix',
            url: '/rejected-reasons-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RejectedReasons'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rejected-reasons/rejected-reasons-my-suffix-detail.html',
                    controller: 'RejectedReasonsMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RejectedReasons', function($stateParams, RejectedReasons) {
                    return RejectedReasons.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rejected-reasons-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rejected-reasons-my-suffix-detail.edit', {
            parent: 'rejected-reasons-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rejected-reasons/rejected-reasons-my-suffix-dialog.html',
                    controller: 'RejectedReasonsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RejectedReasons', function(RejectedReasons) {
                            return RejectedReasons.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rejected-reasons-my-suffix.new', {
            parent: 'rejected-reasons-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rejected-reasons/rejected-reasons-my-suffix-dialog.html',
                    controller: 'RejectedReasonsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fkreasonType: null,
                                rejectReasons: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rejected-reasons-my-suffix', null, { reload: 'rejected-reasons-my-suffix' });
                }, function() {
                    $state.go('rejected-reasons-my-suffix.new');
                });
            }]
        })
        .state('rejected-reasons-my-suffix.edit', {
            parent: 'rejected-reasons-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rejected-reasons/rejected-reasons-my-suffix-dialog.html',
                    controller: 'RejectedReasonsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RejectedReasons', function(RejectedReasons) {
                            return RejectedReasons.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rejected-reasons-my-suffix', null, { reload: 'rejected-reasons-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rejected-reasons-my-suffix.delete', {
            parent: 'rejected-reasons-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rejected-reasons/rejected-reasons-my-suffix-delete-dialog.html',
                    controller: 'RejectedReasonsMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RejectedReasons', function(RejectedReasons) {
                            return RejectedReasons.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rejected-reasons-my-suffix', null, { reload: 'rejected-reasons-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
