(function() {
    'use strict';

    angular
        .module('termAuditAoApp')
        .directive('nextfocus', nextfocus);

    function nextfocus () {
        var directive = {
        restrict: 'A',
        link: function(scope, elem, attrs) {
          elem.bind('keydown', function(e) {
            var partsId = attrs.id.match(/field(\d{1})/);
            var currentId = parseInt(partsId[1]);

            var code = e.keyCode || e.which;
            if (code === 13) {
              e.preventDefault();
              document.querySelector('#field' + (currentId + 1)).focus();
            }
          });
        }
      };
      return directive;
    }

    
})();
