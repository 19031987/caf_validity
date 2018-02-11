(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('MobileValidationSearch', MobileValidationSearch);

    MobileValidationSearch.$inject = ['$resource'];

    function MobileValidationSearch($resource) {
        var resourceUrl =  'api/_search/mobile-validations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
