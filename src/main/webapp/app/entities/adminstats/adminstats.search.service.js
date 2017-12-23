(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('AdminstatsSearch', AdminstatsSearch);

    AdminstatsSearch.$inject = ['$resource'];

    function AdminstatsSearch($resource) {
        var resourceUrl =  'api/_search/adminstats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
