(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('Mobilevalidation', Mobilevalidation);

    Mobilevalidation.$inject = ['$resource', 'DateUtils'];

    function Mobilevalidation ($resource, DateUtils) {
        var resourceUrl =  'api/mobilevalidations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.userDate = DateUtils.convertLocalDateFromServer(data.userDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.userDate = DateUtils.convertLocalDateToServer(copy.userDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.userDate = DateUtils.convertLocalDateToServer(copy.userDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
