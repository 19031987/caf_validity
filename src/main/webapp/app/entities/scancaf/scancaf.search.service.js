(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('ScancafSearch', ScancafSearch);

    ScancafSearch.$inject = ['$resource'];

    function ScancafSearch($resource) {
        var resourceUrl =  'api/_search/scancafs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
