(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('Boxassign', Boxassign);

    Boxassign.$inject = ['$resource'];

    function Boxassign ($resource) {
        var resourceUrl =  'api/boxassigns/:id';

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
            },'getCatLatest' :{
                method: 'POST',
                url: 'api/boxassigns/getCatLatest',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }

            },
            'system': {
                method: 'GET',
                url: 'api/systems',
                isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },'getCatLatestRes' :{
                method: 'POST',
                url: 'api/mobile-validation/getCatLatest',
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
