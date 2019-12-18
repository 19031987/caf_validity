(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('MobilevalidationSearch', MobilevalidationSearch);

    MobilevalidationSearch.$inject = ['$resource'];

    function MobilevalidationSearch($resource) {
        var resourceUrl =  'api/_search/mobilevalidations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
