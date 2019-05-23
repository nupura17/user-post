var appUser = angular.module('userPostApp', []);
appUser.controller("userPostCtrl", function($scope,$http){
    $scope.user;
    $scope.newcomment = {};
    $scope.newpost='';
    $scope.newuser='';
    $scope.newcity='';
    $scope.newlatitude='';
    $scope.newlongitude='';
    $scope.newtemperature='';

    $http.get("https://localhost:8080/userPost/api/getUserSubmission/Sunil")
        .then(function(response) {
            $scope.user = response.data;
        });

    $scope.newcomment = {};
    $scope.postComment = function(key, postId){
        alert('comment ' + $scope.newcomment[key].comment);
        alert('post id  ' + postId);
        if ($scope.newcomment[key].comment !== null && $scope.newcomment[key].comment !== undefined) {
            //alert('post id' + postId);
            //alert('comment ' + $scope.newcomment[key].comment);
            $scope.user.posts[key].comments.push($scope.newcomment[key]);
            var post = {
                "postId": postId,
                "comments": [{
                    "comment": $scope.newcomment[key].comment
                }]
            }
            var res = $http.post('https://localhost:8080/userPost/api/postComments', post);
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
         var res = $http.post('https://localhost:8080/userPost/api/postUserSubmission', $scope.user);
            res.success(function (data, status, headers, config) {
                $scope.user = data;
                alert(data.posts[0].postId);
                alert($scope.user.posts[0].postId);
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
    }
});