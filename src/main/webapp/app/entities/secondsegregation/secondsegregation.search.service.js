(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('SecondsegregationSearch', SecondsegregationSearch);

    SecondsegregationSearch.$inject = ['$resource'];

    function SecondsegregationSearch($resource) {
        var resourceUrl =  'api/_search/secondsegregations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
