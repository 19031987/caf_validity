(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('System', System);

    System.$inject = ['$resource', 'DateUtils'];

    function System ($resource, DateUtils) {
        var resourceUrl =  'api/systems/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.userdate = DateUtils.convertLocalDateFromServer(data.userdate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
