(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('Secondsegregation', Secondsegregation);

    Secondsegregation.$inject = ['$resource', 'DateUtils'];

    function Secondsegregation ($resource, DateUtils) {
        var resourceUrl =  'api/secondsegregations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                     //   data.userdate = DateUtils.convertLocalDateFromServer(data.userdate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                  //  copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                 //   copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getBox': {
                method: 'POST',
                url: 'api/secondsegregations/getBox',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getOutbox': {
                method: 'POST',
                
                url: 'api/secondsegregations/getOutbox',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            }
            ,'getLatestBox': {
                method: 'POST',
                
                url: 'api/secondsegregations/getLatestBox',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
