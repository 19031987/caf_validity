(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('term-audit-my-suffix', {
            parent: 'entity',
            url: '/term-audit-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TermAudits'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/term-audit/term-auditsmySuffix.html',
                    controller: 'TermAuditMySuffixController',
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
        .state('term-audit-my-suffix-detail', {
            parent: 'term-audit-my-suffix',
            url: '/term-audit-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TermAudit'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/term-audit/term-audit-my-suffix-detail.html',
                    controller: 'TermAuditMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TermAudit', function($stateParams, TermAudit) {
                    return TermAudit.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'term-audit-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('term-audit-my-suffix-detail.edit', {
            parent: 'term-audit-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/term-audit/term-audit-my-suffix-dialog.html',
                    controller: 'TermAuditMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TermAudit', function(TermAudit) {
                            return TermAudit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('term-audit-my-suffix.new', {
            parent: 'term-audit-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/term-audit/term-audit-my-suffix-dialog.html',
                    controller: 'TermAuditMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                mobilenumber: null,
                                termno: null,
                                state: null,
                                connection: null,
                                connectiontype: null,
                                barcode: null,
                                nameofcustomer: null,
                                fathername: null,
                                doa: null,
                                aoname: null,
                                checkavailabiltyofcaf: null,
                                photo: null,
                                identityproof: null,
                                addressproof: null,
                                address: null,
                                auditdate: null,
                                auditby: null,
                                caf: null,
                                activationdate: null,
                                activationsource: null,
                                vendor: null,
                                dob: null,
								checkcafstatus: true,
                                customernamestatus: true,
                                fathernamestatus: true,
                                doastatus: true,
                                aonamestatus: true,
                                identityproofstatus: true,
                                addressproofstatus: true,
                                pointsalenamestatus:true,
                                pointsalecode:true,
                                pointsaleaddressstatus:true,
								photo: true,	
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('term-audit-my-suffix.new', null, { reload: 'term-audit-my-suffix.new' });
                }, function() {
                    $state.go('term-audit-my-suffix');
                });
            }]
        })
        .state('term-audit-my-suffix.edit', {
            parent: 'term-audit-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/term-audit/term-audit-my-suffix-dialog.html',
                    controller: 'TermAuditMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TermAudit', function(TermAudit) {
                            return TermAudit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('term-audit-my-suffix', null, { reload: 'term-audit-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('term-audit-my-suffix.delete', {
            parent: 'term-audit-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/term-audit/term-audit-my-suffix-delete-dialog.html',
                    controller: 'TermAuditMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                     resolve: {
                        entity: ['TermAudit', function(TermAudit) {
                            return TermAudit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('term-audit-my-suffix', null, { reload: 'term-audit-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        }).state('term-audit-my-suffix.second', {
            parent: 'term-audit-my-suffix',
            url: '/second',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/term-audit/term-audit-my-suffix-second.html',
                    controller: 'TermAuditMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                mobilenumber: null,
                                termno: null,
                                state: null,
                                connection: null,
                                connectiontype: null,
                                barcode: null,
                                nameofcustomer: null,
                                fathername: null,
                                doa: null,
                                aoname: null,
                                checkavailabiltyofcaf: null,
                                photo: null,
                                identityproof: null,
                                addressproof: null,
                                address: null,
                                auditdate: null,
                                auditby: null,
                                caf: null,
                                activationdate: null,
                                activationsource: null,
                                vendor: null,
                                dob: null,
                                customernamestatus: true,
                                fathernamestatus: true,
                                doastatus: true,
                                aonamestatus: true,
                                identityproofstatus: true,
                                addressproofstatus: true,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('term-audit-my-suffix', null, { reload: 'term-audit-my-suffix' });
                }, function() {
                    $state.go('term-audit-my-suffix');
                });
            }]
        });
    }

})();
