<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>
<html>
  <head>
  <title>Progress Test</title>

  <script src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
  
  <script type="text/javascript">
  
	function getData(){
            var cookieValue = $.cookie("user_info");
            
            var auth = JSON.parse(cookieValue);
            if(typeof (auth)=='undefined'){
                alert("User expired");
                return;
            }
            $.ajax({
                type: 'GET',
                crossDomain: true,
                headers:{
                        "authorization": "Basic bXlSZXN0Q2xpZW50OlBAc3N3MHJk",
                        'Access-Control-Allow-Origin':'*'
                },
                url: 'http://localhost:8080/TestSpring/api/users?access_token='+auth.access_token,
                data: '',
                success: function (responseData, textStatus) {
                        //alert(responseData);
                        $('#result').html(JSON.stringify(responseData));
                },
                complete: function (textStatus) {

                },
                error: function (responseData)
                {
                }
		});
	}
	
    function _login(){
        $.ajax({
            type: 'POST',
            crossDomain: true,
            headers:{
                    "authorization": "Basic bXlSZXN0Q2xpZW50OlBAc3N3MHJk",
                    'Access-Control-Allow-Origin':'*'
            },
            url: 'http://localhost:8080/TestSpring/oauth/token?grant_type=password&username='+$('#user_id').val()+'&password='+$('#pass_id').val(),
            data: '',
            success: function (responseData, textStatus) {
                    if(responseData['access_token']!=''){
                        console.log(JSON.stringify(responseData));
                        var date = new Date();
                        date.setTime(date.getTime() + (60 * 2000));
                        $.removeCookie('user_info');
                        $.cookie('user_info',JSON.stringify(responseData),{expires:date});
                        
                    }
            },
            complete: function (textStatus) {

            },
            error: function (responseData)
            {
            }
            });
    }
  </script>
  
  <style>

  </style>

  </head>
  <body>
    
    
    <label>token </label>
    <input type="text" id="access_token_id" value="">
    <br>
    <label> User name</label>
    <input type="text" id="user_id" value="">
    <br>
    <label> Password</label>
    <input type="text" id="pass_id" value="">
    <br>
    <input type="button" value="Get Data" onclick="getData()">
    <input type="button" value="Login" onclick="_login()">
    <div id="result" class="waiting"></div>
  </body>
</html>
