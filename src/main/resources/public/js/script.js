var appUser = angular.module('userPostApp', []);
appUser.controller("userPostCtrl", function($scope,$http,$window,$interval){
    $scope.user;
    $scope.newcomment = {};
    $scope.newpost='';
    $scope.newuser='';
    $scope.newcity='';
    $scope.newlatitude='';
    $scope.newlongitude='';
    $scope.newtemperature='';
    $scope.isPostAvailable=false;
    $scope.allPost={};
    $scope.url='https://localhost:8080/userPost/api/postComments';

    /*$window.onload = function() {
        $http.get("https://localhost:8080/userPost/api/getPostsOfAllUsers")
            .then(function(response) {
                $scope.allPost = response.data;
            });
    };

    $scope.callAtTimeout =function() {
        $http.get("https://localhost:8080/userPost/api/getPostsOfAllUsers")
            .then(function(response) {
                $scope.allPost = response.data;
            });
    };


    $interval(function(){ $scope.callAtTimeout(); }, 2000);*/

    $scope.newcomment = {};
    $scope.postComment = function(key, postId){
        $scope.isPostAvailable = true;
        if ($scope.newcomment[key].comment !== null && $scope.newcomment[key].comment !== undefined) {
            $scope.user.posts[key].comments.push($scope.newcomment[key]);
            var post = {
                "postId": postId,
                "comments": [{
                    "comment": $scope.newcomment[key].comment
                }]
            }
            var res = $http.post($scope.url, post);
            res.success(function (data, status, headers, config) {
                $scope.user.post = data;
                alert("Comments saved succesfully");
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });


        }
        else {
            var error= 'Cannot post blank comments';
            alert (error);
        }
        $scope.newcomment = {};

    };


    $scope.addPost=function(){
        if($scope.newuser !== '' && $scope.newpost != ''){
            $scope.user={
                "userName":$scope.newuser,
                "posts" :[{
                    "title": $scope.newpost,
                    "city": $scope.newcity,
                    "latitude": $scope.newlatitude,
                    "longitude": $scope.newlongitude,
                    "temperature": $scope.newtemperature
                }]
            }
         var res = $http.post($scope.url, $scope.user);
            res.success(function (data, status, headers, config) {
                $scope.user = data;
                alert("Post saved succesfully");
            });
            res.error(function (data, status, headers, config) {
                alert("Failed to save user's post");
                console.log(JSON.stringify({data: data}))
            });

             }
        else {
            var error ='';
            if ($scope.newuser === '')
            error += 'Username connot be blank';
            if ($scope.newpost === '')
                error += 'Post connot be blank';
            alert (error)
        }
        $scope.newpost ='';
        $scope.newuser='';
        $scope.newlatitude ='';
        $scope.newlongitude='';
        $scope.newpost ='';
        $scope.newtemperature='';
        $scope.newcity='';
        $scope.isPostAvailable = true;
    }
});