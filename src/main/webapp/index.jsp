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
	<script type="text/javascript">
		$(document).ready(function(){
			$.getJSON('./cmd?m=list', function(result) {
				var halfHour = 30 * 60 * 1000;
				var current = new Date();
				var curDate = '2014年0' + (current.getMonth()+1) + '月' + current.getDate() + '日';
				var curTime = current.getTime();
				$.each(result, function(i, n) {
					var date = n.startTime.split(' ')[0];
					//var time = n.startTime.split(' ')[1];
					var matchOff = (n.times - curTime) > halfHour;
					console.log(curTime, n.times, matchOff);
					console.log(date, curDate, (date == curDate));
					var color = date == curDate ? 'primary' : 'info';
					var idL = 'l_'+n.id;
					var idM = 'm_'+n.id;
					var idR = 'r_'+n.id;
					var panel  = '<div class="panel panel-'+color+'">';
						panel += '<div class="panel-heading">';
						panel += '<center><h3 class="panel-title">'+ ++i +'. '+n.startTime+' '+n.teamLeft+' VS '+n.teamRight+'</h3></center>';
						panel += '</div>';
						panel += '<div class="panel-body">';
						panel += '<center>';
						
						panel += '<div class="row">';
						panel += '<div class="col-xs-3">';
						panel += n.teamLeft+'胜 '+n.resultLeft+' <input id="'+idL+'" name="'+idL+'" type="text" class="form-control" value=0>';
						panel += '</div>';
						panel += '<div class="col-xs-3">';
						panel += '平局 '+n.resultMiddle+' <input id="'+idM+'" name="'+idM+'" type="text" class="form-control" value=0>';
						panel += '</div>';
						panel += '<div class="col-xs-3">';
						panel += n.teamRight+'胜 '+n.resultRight+'<input id="'+idR+'" name="'+idR+'" type="text" class="form-control" value=0>';
						panel += '</div>';
						panel += '<div class="col-xs-2">';
						panel += '<button type="button" class="btn btn-primary" onclick="bet('+n.id+')">bet</button>';
						panel += '</div>';
						panel += '</div>';
						//panel += '<button type="button" class="btn btn-primary" id="'+idL+'" onclick="bet('+n.id+')">'+n.teamLeft+'胜 '+n.resultLeft+'</button>';
						//panel += '<button type="button" class="btn btn-default" id="'+idM+'" onclick="bet('+n.id+')">平局 '+n.resultMiddle+'</button>';
						//panel += '<button type="button" class="btn btn-danger"  id="'+idR+'" onclick="bet('+n.id+')">'+n.teamRight+'胜 '+n.resultRight+'</button>';
						panel += '</center>';
						panel += '</div>';
						panel += '</div>';
					$('#list').append(panel);
				});
			});
		});
		
		function bet(id) {
			//$('#myModal').modal('show');
			//$.post(url, [data], [callback], [type]);
			var name = $('#betname').val().trim();
			var l = $('#l_'+id).val();
			var m = $('#m_'+id).val();
			var r = $('#r_'+id).val();
			if(name == '') {
				alert('请填写一个有效的邮箱');
			} else if(!/^[0-9]+(.[0-9]{2})?$/.test(l) || !/^[0-9]+(.[0-9]{2})?$/.test(m) || !/^[0-9]+(.[0-9]{2})?$/.test(r)) {
				alert("请输入数字!");
			} else {
				
				var data = { id : id, L : l, M : m, R : r, name : name};
				console.log(data);
				$.post('./cmd?m=bet', data, function(result) {
					console.log('return ', result);
					if(result.success) {
						alert('下注成功');
					} else {
						alert('下注失败');
					}
				}, 'json');
			}
		}
		
	</script>
</body>
</html>