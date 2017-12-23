(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('SuspendedusersSearch', SuspendedusersSearch);

    SuspendedusersSearch.$inject = ['$resource'];

    function SuspendedusersSearch($resource) {
        var resourceUrl =  'api/_search/suspendedusers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
