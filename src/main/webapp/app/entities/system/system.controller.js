(function() {
    'use strict';

    angular
        .module('cafvalidityV2App')
        .controller('SystemController', SystemController);

    SystemController.$inject = ['System', 'SystemSearch'];

    function SystemController(System, SystemSearch) {

        var vm = this;

        vm.systems = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            System.query(function(result) {
                vm.systems = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SystemSearch.query({query: vm.searchQuery}, function(result) {
                vm.systems = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
