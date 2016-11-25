var redditLite = angular.module('redditLite', ['ngRoute']);

redditLite.config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home',
        controllerAs : 'controller'
    }).when('/page/:pagenum', {
        templateUrl : 'home.html',
        controller: 'page',
        controllerAs : 'controller'
    }).when("/post/:title-:id", {
        templateUrl : 'post.html',
        controller : 'post',
        controllerAs : 'controller'
    }).when("/userprofile-:username", {
        templateUrl : 'profile.html',
        controller : 'profile',
        controllerAs : 'controller'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'login',
        controllerAs : 'controller'
    }).when('/admin', {
        templateUrl : 'admin.html',
        controller : 'admin',
        controllerAs : 'controller'
    }).when('/register', {
        templateUrl : 'register.html',
        controller : 'register',
        controllerAs : 'controller'
    });
});

var config = {
    headers : {
        'Content-type': 'application/json',
        'Connection' : 'close'
    }
};

redditLite.controller('register', function($rootScope, $http, $location, $scope) {
    $scope.credentials = {};
    $scope.error = false;
    $scope.errorResponse = undefined;
    $scope.errorResponseUserName = undefined;
    var payloadCredentials = {};
    var validPassword = true;
    var validUsername = true;

    $scope.goToLogin = function() {
        $location.path("/login");
    };

    var clearPasswords = function() {
        $scope.credentials.password = undefined;
        $scope.credentials.confirmPassword = undefined;
    };

    var clearUsername = function() {
        $scope.credentials.username = undefined;
    };

    $scope.register = function(form) {

        //password validation
        if ($scope.credentials.password == undefined || $scope.credentials.confirmPassword == undefined) {
            $scope.errorResponse = "Password cannot be blank.";
            $scope.error = true;
            validPassword = false;
        } else if ($scope.credentials.password.length < 8) {
            $scope.errorResponse = "Password must be at least 8 characters.";
            clearPasswords();
            $scope.error = true;
            validPassword = false;
        } else if ($scope.credentials.password.length > 20) {
            $scope.errorResponse = "Password must be less than 20 characters.";
            clearPasswords();
            $scope.error = true;
            validPassword = false;
        } else if ($scope.credentials.password != $scope.credentials.confirmPassword) {
            $scope.errorResponse = "Passwords do not match.";
            clearPasswords();
            validPassword = false;
        }

        //username validation
        if ($scope.credentials.username == undefined) {
            $scope.errorResponseUserName = "Username cannot be blank.";
            $scope.error = true;
            validUsername = false;
        } else if ($scope.credentials.username.length > 25) {
            $scope.errorResponseUserName = "Username is too long. Must be 25 characters or less.";
            clearUsername();
            $scope.error = true;
            validUsername = false;
        }

        if (validPassword && validUsername) {

            payloadCredentials.username = $scope.credentials.username;
            payloadCredentials.password = $scope.credentials.password;

            $http.post('/register-user',payloadCredentials,config).then(function(response) {
                $rootScope.authenticated = true;
                $location.path("/");
            }, function(response) {
                $scope.errorResponseUserName = "Username already exists.";
                $scope.error = true;
                clearPasswords();
                clearUsername();
            });
        }
    };
});

redditLite.controller('login', function($rootScope, $http, $location, $scope) {
    var authorities = [];
    var i;
    var authenticate = function(credentials, callback) {

        var headers = credentials ? {authorization : "Basic "
        + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('user', {headers : headers}).then(function(response) {

            console.log(headers.toString());

            if (response.data.name) {
                $rootScope.authenticated = true;
                authorities = response.data.authorities;

                for (i = 0; i < authorities.length; ++i) {
                    var role = {};
                    role = authorities[i];
                    if (role.authority == "ROLE_ADMIN") {
                        console.log("admin account");
                        $rootScope.admin = true;
                    }
                }

            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }, function() {
            $rootScope.authenticated = false;
            callback && callback();
        });

    }

    authenticate();
    $scope.error = false;
    $scope.credentials = {};
    $scope.login = function(loginForm) {
        authenticate($scope.credentials, function() {
            if ($rootScope.authenticated) {
                $location.path("/");
            } else {
                $scope.credentials = {};
                loginForm.$setPristine();
                $scope.error = true;
            }
        });
    };

});

redditLite.controller('home', function($http, $scope, $location) {
    var pageResponse = {};
    var controller = this;
    $scope.show = false;
    $scope.addComment = {};
    $scope.pageCount = [];
    $scope.postList = [];

    $scope.addPost = function() {
        var postObject = {};
        postObject.title = $scope.addComment.title;
        postObject.body = $scope.addComment.body;

        $http.post('add-new-post',postObject, config).then(function(response) {
            $scope.postList.push(response.data);
        });

    };

    $scope.loginPage = function() {
        $location.path("/login");
    };

    $scope.adminPage = function() {
        $location.path("/admin");
    };

    $scope.registerPage = function() {
        $location.path("/register");
    };

    $http.get('homelist', config).then(function(response) {
        pageResponse = response.data;
        $scope.postList = pageResponse.content;
    });

    $http.get('page-count', config).then(function(response) {
        $scope.pageCount = response.data;
    });

    controller.changePage = function(pageNum) {
        $location.path("/page/" + pageNum);
    };

    controller.goToPost = function(postId, postTitle) {
        console.log("function fired");
        $location.path("/post/" + postTitle + "-" + postId)
    };

    //get principal and redirect to user profile page
    $scope.profilePage = function() {
        $http.get('user', config).then(function(response) {
            var username = response.data.principal.username;
            $location.path("/userprofile-" + username);
        });
    }
});

redditLite.controller('page', function($http, $scope, $routeParams, $location) {
    $scope.postList = [];
    $scope.pageCount = [];
    var controller = this;
    var pageNum = ($routeParams.pagenum) - 1;

    $http.post('pageList',pageNum, config).then(function(response) {
        $scope.postList = response.data.content;
    });

    $http.get('page-count', config).then(function(response) {
        $scope.pageCount = response.data;
    });

    controller.changePage = function(pageNum) {
        $location.path("/page/" + pageNum);
    };

    controller.goToPost = function(postId, postTitle) {
        console.log("function fired");
        $location.path("/post/" + postTitle + "-" + postId)
    }

});

redditLite.controller('admin', function($http, $scope, $routeParams, $location, $rootScope) {
    $scope.commentInfo = {};
    $scope.node = {};
    $scope.post = {};
    $scope.response = undefined;
    $scope.showConfirm = false;
    var postId = undefined;
    var nodeDelete = {};

    if (!$rootScope.admin) {
        $location.path("/")
    }

    $scope.queryComment = function() {
        var payload = {};

        payload.body = $scope.commentInfo.body;
        payload.username = $scope.commentInfo.username;

        $http.post('admin/query-node',payload, config).then(function (response) {
            $scope.node = response.data;
            postId = response.data.postId;

            $http.get('admin/get/' + postId, config).then(function(response) {
                $scope.post = response.data;

                if (response.data.status == 400) {
                    $scope.response = "Comment does not exist. Please try again.";
                    $scope.commentInfo = {};
                } else {
                    $scope.showConfirm = true;
                }

            });
        });
    };

    $scope.deleteComment = function() {
        nodeDelete = $scope.node;

        $http.post('admin/delete-comment',nodeDelete, config).then(function (response) {
            $scope.response = "Comment successfully deleted.";
            $scope.showConfirm = false;
            $scope.commentInfo = {};
        });
    };

});

redditLite.controller('profile', function($routeParams, $scope, $http, $location, $rootScope) {
    $scope.principal = $routeParams.username;
    $scope.topPosts = [];
    $scope.topComment = {};
    $scope.originalPost = undefined;
    var postId = undefined;

    if (!$rootScope.authenticated) {
        $location.path("/login")
    }

    //get top posts
    $http.get('top-posts', config).then(function(response) {
        $scope.topPosts = response.data;
    });

    $http.get('top-comment', config).then(function(response) {
        $scope.topComment = response.data;
        postId = response.data.postId;

        $http.get('find-post/' + postId, config).then(function(response) {
            $scope.originalPost = response.data.title;
        });
        
    });

    $scope.goToPost = function(postId, postTitle) {
        $location.path("/post/" + postTitle + "-" + postId)
    }
    
});

redditLite.controller('post', function($routeParams, $scope, $http, $rootScope, $location) {
    var postId = $routeParams.id;
    var controller = this;
    var postComment = {};
    var node = {};
    $scope.addComment = {};
    $scope.post = {};
    $scope.x = {};
    $scope.x.show = true;
    $scope.x.likes = undefined;
    $scope.x.errorResponse = undefined;
    $scope.x.nodeList = [];

    //used to handle errors from duplicate likes
    $scope.errorResponse = undefined;
    
    $http.get('post/' + postId, config).then(function(response) {
        $scope.post = response.data;
        $scope.post.show = true;
    });

    $scope.toggleShow = function() {
        if ($rootScope.authenticated) {
            $scope.post.show = !($scope.post.show);
        } else {
            $location.path("/register")
        }
    };

    $scope.toggleNodeShow = function(x) {
        if ($rootScope.authenticated) {
            x.show = !(x.show);
        } else {
            $location.path("/register")
        }
    };

    controller.like = function(id) {

        if ($rootScope.authenticated) {

            console.log("This is the id: " + id);
            $http.get('like/' + id, config).then(function (response) {

                if (response.data == 200) {
                    $scope.post.likes++;
                } else {
                    $scope.errorResponse = "You have already liked this."
                }
            });

        } else {
            $location.path("/register");
        }
    };

    controller.likeNode = function(id, currentScope) {
        if ($rootScope.authenticated) {
            $http.get('like/node/' + id, config).then(function (response) {
                if (response.data == 200) {
                    currentScope.likes++;
                } else {
                    currentScope.errorResponse = "You have already liked this."
                }
            });
        } else {
            $location.path("/login");
        }
    };

    $scope.comment = function(parentObject,parentId,type) {
        postComment.comment = parentObject.addComment;
        postComment.parentId = parentId;
        postComment.rootPostId = $scope.post.id;
        console.log("Type is:" + type);

        if (postComment.comment == undefined) {

            if (type == 1) {
                $scope.errorResponse = "Comment cannot be blank." ;
            } else {
                parentObject.errorResponse = "Comment cannot be blank.";
            }

        } else if (postComment.comment.length > 255) {

            if (type == 1) {
                $scope.errorResponse = "Comment cannot be more than 255 characters.";
            } else {
                parentObject.errorResponse = "Comment cannot be more than 255 characters.";
            }

        } else {

            if ($rootScope.authenticated) {
                if (type == 1) {

                    $http.post('add-comment/1', postComment, config).then(function (response) {
                        node = response.data;
                        node.show = true;
                        $scope.post.nodeList.push(node);
                        $scope.post.cmmnts = $scope.post.cmmnts++;
                    });

                } else {

                    $http.post('add-comment/2', postComment, config).then(function (response) {
                        node = response.data;
                        node.show = true;
                        parentObject.nodeList.push(node);

                        parentObject.cmmnts = parentObject.cmmnts++;
                    });

                }
            } else {
                $location.path("/login");
            }
        }
    }

});






















