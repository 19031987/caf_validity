(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('secondsegregation', {
            parent: 'entity',
            url: '/secondsegregation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Secondsegregations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/secondsegregation/secondsegregations.html',
                    controller: 'SecondsegregationController',
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
        .state('secondsegregation-detail', {
            parent: 'secondsegregation',
            url: '/secondsegregation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Secondsegregation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/secondsegregation/secondsegregation-detail.html',
                    controller: 'SecondsegregationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Secondsegregation', function($stateParams, Secondsegregation) {
                    return Secondsegregation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'secondsegregation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('secondsegregation-detail.edit', {
            parent: 'secondsegregation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secondsegregation/secondsegregation-dialog.html',
                    controller: 'SecondsegregationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Secondsegregation', function(Secondsegregation) {
                            return Secondsegregation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('secondsegregation.new', {
            parent: 'secondsegregation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secondsegregation/secondsegregation-dialog.html',
                    controller: 'SecondsegregationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                boxname: "",
                                cafcode: "",
                                firstleveluser: "",
                                segregatedcount: 0,
                                wrongsegregatedcount: 0,
                                notsegregatedcount: 0,
                                user: "",
                                userdate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('secondsegregation', null, { reload: 'secondsegregation' });
                }, function() {
                    $state.go('secondsegregation');
                });
            }]
        })
        .state('secondsegregation.edit', {
            parent: 'secondsegregation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secondsegregation/secondsegregation-dialog.html',
                    controller: 'SecondsegregationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Secondsegregation', function(Secondsegregation) {
                            return Secondsegregation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('secondsegregation', null, { reload: 'secondsegregation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('secondsegregation.delete', {
            parent: 'secondsegregation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/secondsegregation/secondsegregation-delete-dialog.html',
                    controller: 'SecondsegregationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Secondsegregation', function(Secondsegregation) {
                            return Secondsegregation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('secondsegregation', null, { reload: 'secondsegregation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
