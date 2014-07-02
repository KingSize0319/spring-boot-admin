'use strict';

angular.module('springBootAdmin')
  	.controller('overviewCtrl', function ($scope, Applications, Application, ApplicationOverview, $location) {
  		$scope.applications = Applications.query({}, function(applications) {
  			for (var i = 0; i < applications.length; i++) {
  				var app = applications[i];
  				ApplicationOverview.getVersion(app);
  				ApplicationOverview.getHealth(app);
  			}	
  		});
  		// callback for ng-click 'showDetails':
  		$scope.showDetails = function(id) {
  			$location.path('/apps/details/' + id + '/infos');
  		};
  		// callback for ng-click 'refresh':
  		$scope.refresh = function(id) {
  			$scope.application = Application.query({id: id}, function(application) {
  				ApplicationOverview.refresh(application);
  	  		});
  		};
  		// url for ng-href 'logfile':
  		$scope.logfile = function(url) {
  			return url + '/logfile';
  		};
  	})
  	.controller('navCtrl', function ($scope, $location) {
  		$scope.navClass = function(page) {
  			var currentRoute = $location.path().substring(1) || 'main';
  			return page == currentRoute ? 'active' : '';
  		};
  	})
  	.controller('detailsCtrl', function ($scope, $stateParams, Application) {
  		$scope.application = Application.query({id: $stateParams.id});
  	})
  	.controller('infosCtrl', function ($scope, $stateParams, Application, ApplicationDetails) {
  		$scope.application = Application.query({id: $stateParams.id}, function(application) {
  			ApplicationDetails.getInfo(application);
  		});
  	})
  	.controller('metricsCtrl', function ($scope, $stateParams, Application, ApplicationDetails) {
  		$scope.application = Application.query({id: $stateParams.id}, function(application) {
  			ApplicationDetails.getMetrics(application);
  		});
  	})
  	.controller('envCtrl', function ($scope, $stateParams, Application, ApplicationDetails) {
  		$scope.application = Application.query({id: $stateParams.id}, function(application) {
  			ApplicationDetails.getEnv(application);
  		});
  	});
