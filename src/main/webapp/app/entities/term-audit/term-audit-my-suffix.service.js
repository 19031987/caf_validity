(function() {
    'use strict';
    angular
        .module('termAuditAoApp')
        .factory('TermAudit', TermAudit);

    TermAudit.$inject = ['$resource', 'DateUtils'];

    function TermAudit ($resource, DateUtils) {
        var resourceUrl =  'api/term-audits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.auditdate = DateUtils.convertLocalDateFromServer(data.auditdate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.auditdate = DateUtils.convertLocalDateToServer(copy.auditdate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.auditdate = DateUtils.convertLocalDateToServer(copy.auditdate);
                    return angular.toJson(copy);
                }
            }, 
            'getReasons': {
                method: 'POST',
                url:'api/rejected-reasons/getReasons',
                isArray:true,
                transformResponse: function (data) {
                	if (data) {
                		data = angular.fromJson(data);
                	}
                	   return data;
                }
            },'getData': {
                method: 'POST',
                url:'api/term-audits/getdata',
                transformResponse: function (data) {
                	if (data) {
                		data = angular.fromJson(data);
                	}
                	   return data;
                }
            },'findByTermNo': {
                method: 'POST',
                url:'api/term-audits/findByTermNo',
                transformResponse: function (data) {
                	if (data) {
                		data = angular.fromJson(data);
                	}
                	   return data;
                }
            }
        });
    }
})();
