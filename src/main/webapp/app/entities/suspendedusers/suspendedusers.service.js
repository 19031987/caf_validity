(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('Suspendedusers', Suspendedusers);

    Suspendedusers.$inject = ['$resource', 'DateUtils'];

    function Suspendedusers ($resource, DateUtils) {
        var resourceUrl =  'api/suspendedusers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.userdate = DateUtils.convertDateTimeFromServer(data.userdate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
