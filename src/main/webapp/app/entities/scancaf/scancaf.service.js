(function() {
    'use strict';
    angular
        .module('cafvalidityV2App')
        .factory('Scancaf', Scancaf);

    Scancaf.$inject = ['$resource', 'DateUtils'];

    function Scancaf ($resource, DateUtils) {
        var resourceUrl =  'api/scancafs/:id';

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
            },'getSource': {
                method: 'POST',
                url: 'api/sourceboxes/getSource',
                transformResponse: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getCaf': {
                method: 'POST',
                url: 'api/scancafs/getCaf',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getBoxCount': {
                method: 'POST',
                url: 'api/scancafs/getBoxCount',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getBox': {
                method: 'POST',
                url: 'api/scancafs/getBox',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getOutBox': {
                method: 'POST',
                url: 'api/scancafs/getOutBox',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
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
            },'findCategory1': {
                method: 'POST',
                url: 'api/scancafs/category1',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory2': {
                method: 'POST',
                url: 'api/scancafs/category2',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory3': {
                method: 'POST',
                url: 'api/scancafs/category3',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory4': {
                method: 'POST',
                url: 'api/scancafs/category4',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategory5': {
                method: 'POST',
                url: 'api/scancafs/category5',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategoryRv': {
                method: 'POST',
                url: 'api/scancafs/categoryRv',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'findCategoryNA': {
                method: 'POST',
                url: 'api/scancafs/categoryNA',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            },'getDetailsByName': {
                method: 'POST',
                url: 'api/scancafs/getDetailsByName',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                   // copy.userdate = DateUtils.convertLocalDateToServer(copy.userdate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
