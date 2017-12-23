(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('Sourcebox', Sourcebox);

    Sourcebox.$inject = ['$resource', 'DateUtils'];

    function Sourcebox ($resource, DateUtils) {
        var resourceUrl =  'api/sourceboxes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createddate = DateUtils.convertLocalDateFromServer(data.createddate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createddate = DateUtils.convertLocalDateToServer(copy.createddate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createddate = DateUtils.convertLocalDateToServer(copy.createddate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
