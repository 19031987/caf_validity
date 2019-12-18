'use strict';

describe('Controller Tests', function() {

    describe('RejectedReasons Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRejectedReasons, MockRejectedReasonType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRejectedReasons = jasmine.createSpy('MockRejectedReasons');
            MockRejectedReasonType = jasmine.createSpy('MockRejectedReasonType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RejectedReasons': MockRejectedReasons,
                'RejectedReasonType': MockRejectedReasonType
            };
            createController = function() {
                $injector.get('$controller')("RejectedReasonsMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'termAuditAoApp:rejectedReasonsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
