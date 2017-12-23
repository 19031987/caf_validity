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
                url: 'api/users',
                isArray: true,
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },'getCatLatest' :{
                method: 'POST',
                url: 'api/scancafs/getCatLatest',
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
