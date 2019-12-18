(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rejected-reason-type-my-suffix', {
            parent: 'entity',
            url: '/rejected-reason-type-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RejectedReasonTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rejected-reason-type/rejected-reason-typesmySuffix.html',
                    controller: 'RejectedReasonTypeMySuffixController',
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
        .state('rejected-reason-type-my-suffix-detail', {
            parent: 'rejected-reason-type-my-suffix',
            url: '/rejected-reason-type-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RejectedReasonType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rejected-reason-type/rejected-reason-type-my-suffix-detail.html',
                    controller: 'RejectedReasonTypeMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RejectedReasonType', function($stateParams, RejectedReasonType) {
                    return RejectedReasonType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rejected-reason-type-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rejected-reason-type-my-suffix-detail.edit', {
            parent: 'rejected-reason-type-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rejected-reason-type/rejected-reason-type-my-suffix-dialog.html',
                    controller: 'RejectedReasonTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RejectedReasonType', function(RejectedReasonType) {
                            return RejectedReasonType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rejected-reason-type-my-suffix.new', {
            parent: 'rejected-reason-type-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rejected-reason-type/rejected-reason-type-my-suffix-dialog.html',
                    controller: 'RejectedReasonTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                reasonType: null,
                                rejectReasonDesc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rejected-reason-type-my-suffix', null, { reload: 'rejected-reason-type-my-suffix' });
                }, function() {
                    $state.go('rejected-reason-type-my-suffix');
                });
            }]
        })
        .state('rejected-reason-type-my-suffix.edit', {
            parent: 'rejected-reason-type-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rejected-reason-type/rejected-reason-type-my-suffix-dialog.html',
                    controller: 'RejectedReasonTypeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RejectedReasonType', function(RejectedReasonType) {
                            return RejectedReasonType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rejected-reason-type-my-suffix', null, { reload: 'rejected-reason-type-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rejected-reason-type-my-suffix.delete', {
            parent: 'rejected-reason-type-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rejected-reason-type/rejected-reason-type-my-suffix-delete-dialog.html',
                    controller: 'RejectedReasonTypeMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RejectedReasonType', function(RejectedReasonType) {
                            return RejectedReasonType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rejected-reason-type-my-suffix', null, { reload: 'rejected-reason-type-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
