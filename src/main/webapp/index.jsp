<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>2014 world cup</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="sticky-footer.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<!-- Begin page content -->
	<div class="container">
		<div class="page-header">
			<center><h1>Hello World Cup 2014</h1></center>
		</div>
		
		<div class="form-group">
		<div class="input-group input-group-lg">
		  <span class="input-group-btn">
	        <button class="btn btn-default" type="button" id="query">我的竞彩记录</button>
	      </span>
	      <input id="betname" name="name" type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
		</div>
	  	</div>
	  	
		<div id="list"></div>
		 
	</div>

	<div id="footer">
		<div class="container">
			<p class="text-muted">玉林组世界杯专属页面</p>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="wc.js"></script>
</body>
</html>