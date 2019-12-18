(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .factory('UserchangeSearch', UserchangeSearch);

    UserchangeSearch.$inject = ['$resource'];

    function UserchangeSearch($resource) {
        var resourceUrl =  'api/_search/userchanges/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
