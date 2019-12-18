(function() {
    'use strict';
    angular
        .module('termAuditAoApp')
        .factory('RejectedReasons', RejectedReasons);

    RejectedReasons.$inject = ['$resource'];

    function RejectedReasons ($resource) {
        var resourceUrl =  'api/rejected-reasons/:id';

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
