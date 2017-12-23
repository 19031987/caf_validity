(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('BoxassignSearch', BoxassignSearch);

    BoxassignSearch.$inject = ['$resource'];

    function BoxassignSearch($resource) {
        var resourceUrl =  'api/_search/boxassigns/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
