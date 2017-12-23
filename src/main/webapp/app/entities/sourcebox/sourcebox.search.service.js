(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('SourceboxSearch', SourceboxSearch);

    SourceboxSearch.$inject = ['$resource'];

    function SourceboxSearch($resource) {
        var resourceUrl =  'api/_search/sourceboxes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
