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

		<!-- 
		<div class="panel panel-info">
			<div class="panel-heading">
				<center><h3 class="panel-title">06月13日 星期五 04:00 巴西 VS 克罗地亚</h3></center>
			</div>
			<div class="panel-body">
			<center>
				<button type="button" class="btn btn-primary">稳胆 1.18</button>
				<button type="button" class="btn btn-default">中庸 5.1</button>
				<button type="button" class="btn btn-danger">拼了 12.5</button>
			</center>
			</div>
		</div>
		-->
		<div class="form-group">
	    	<input id="betname" name="name" type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
	  	</div>
		<div id="list"></div>
		 
	</div>

	<div id="footer">
		<div class="container">
			<p class="text-muted">Place sticky footer content here.</p>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
	<script src="wc.js"></script>
</body>
</html>