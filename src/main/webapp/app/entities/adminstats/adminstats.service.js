(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('Adminstats', Adminstats);

    Adminstats.$inject = ['$resource'];

    function Adminstats ($resource) {
        var resourceUrl =  'api/adminstats/:id';

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
