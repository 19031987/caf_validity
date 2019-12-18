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
                isArray: true,
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.userDate = DateUtils.convertLocalDateToServer(copy.userDate);
                    return angular.toJson(copy);
                }
            },'getSystemByName': {
                method: 'GET',
                url: 'api/boxassigns/getSystemName',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getDetailsByName': {
                method: 'POST',
                url: 'api/mobile-validation/getDetailsByName',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory1': {
                method: 'POST',
                url: 'api/mobile-validation/category1',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory2': {
                method: 'POST',
                url: 'api/mobile-validation/category2',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory3': {
                method: 'POST',
                url: 'api/mobile-validation/category3',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory4': {
                method: 'POST',
                url: 'api/mobile-validation/category4',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory5': {
                method: 'POST',
                url: 'api/mobile-validation/category5',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getOutBox': {
                              method: 'POST',
                              url: 'api/mobile-validaiton/getOutBox',
                              transformRequest: function (data) {
                                  var copy = angular.copy(data);
                                 // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                                  return angular.toJson(copy);
                              }
            },'findCategoryRv': {
                              method: 'POST',
                              url: 'api/mobile-validation/categoryRv',
                              transformRequest: function (data) {
                                  var copy = angular.copy(data);
                                 // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                                  return angular.toJson(copy);
                              }
                          },
                          'findCategoryNa': {
                              method: 'POST',
                              url: 'api/mobile-validation/categoryNa',
                              transformRequest: function (data) {
                                  var copy = angular.copy(data);
                                 // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                                  return angular.toJson(copy);
                              }
                          },
        });
    }
})();
