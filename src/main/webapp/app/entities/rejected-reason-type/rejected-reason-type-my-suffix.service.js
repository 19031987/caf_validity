(function() {
    'use strict';
    angular
        .module('termAuditAoApp')
        .factory('RejectedReasonType', RejectedReasonType);

    RejectedReasonType.$inject = ['$resource'];

    function RejectedReasonType ($resource) {
        var resourceUrl =  'api/rejected-reason-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
