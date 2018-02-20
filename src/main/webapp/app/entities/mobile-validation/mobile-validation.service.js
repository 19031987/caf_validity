(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('MobileValidation', MobileValidation);

    MobileValidation.$inject = ['$resource', 'DateUtils'];

    function MobileValidation ($resource, DateUtils) {
        var resourceUrl =  'api/mobile-validations/:id';

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
            },
            'getByMobileNum': {
                method: 'POST',
                url:'/api/getmobilenumber',
                isArray:true,
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.userDate = DateUtils.convertLocalDateToServer(copy.userDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
