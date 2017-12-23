'use strict';

describe('Controller Tests', function() {

    describe('Scancaf Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockScancaf;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockScancaf = jasmine.createSpy('MockScancaf');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Scancaf': MockScancaf
            };
            createController = function() {
                $injector.get('$controller')("ScancafDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cafvalidityV2App:scancafUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
