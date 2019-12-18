(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('Userchange', Userchange);

    Userchange.$inject = ['$resource'];

    function Userchange ($resource) {
        var resourceUrl =  'api/userchanges/:id';

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
            , 'users': {
                method: 'GET',
                url: 'api/usersList',
                isArray: true,
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
