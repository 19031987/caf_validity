(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('SystemSearch', SystemSearch);

    SystemSearch.$inject = ['$resource'];

    function SystemSearch($resource) {
        var resourceUrl =  'api/_search/systems/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
