$(document).ready(function(){
	
	$.ajax({
		url : './cmd?m=list',
		async : false,
		dataType : 'json',
		type : 'GET',
		success : function(result) {
			var halfHour = 1 * 60 * 60 * 1000;
			var current = new Date();
			var curDate = '2014年0' + (current.getMonth()+1) + '月' + current.getDate() + '日';
			var curTime = current.getTime();
			for(var index = 0; index < result.length; index++) {
				var n = result[index];
				
				var date = n.startTime.split(' ')[0];
				var matchOff = (n.times - curTime) >= halfHour;
				var color = matchOff ? 'primary' : 'info';
				var idL = 'l_'+n.id;
				var idM = 'm_'+n.id;
				var idR = 'r_'+n.id;
				var panel  = '<div class="panel panel-'+color+'">';
				panel += '<div class="panel-heading">';
				panel += '<center><h3 class="panel-title">'+ (index+1) +'. '+n.startTime+' '+n.teamLeft+' VS '+n.teamRight+'</h3></center>';
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
				panel += '</center>';
				panel += '</div>';
				panel += '</div>';
				if(matchOff) {
					$('#list').append(panel);
				} else {
					$('#list2').append(panel);
				}
			}
		}
	});
	
	//query
	$('#query').click(function() {
		var name = $('#betname').val().trim();
		if(name == '') {
			alert('请填写一个有效的邮箱');
		} else {
			var data = {name : name};
			$.getJSON('./cmd?m=mylist', data, function(result) {
				if(result) {
					alert('load success, ' + result.length);
					for(var index = 0; index < result.length; index++) {
						var n = result[index];
						if(n.time) {
							$('#l_'+n.id).attr('value', n.l);
							$('#m_'+n.id).attr('value', n.m);
							$('#r_'+n.id).attr('value', n.r);
						}
					}
				} else {
					alert('load fail');
				}
			});
			
		}
	});
});

function bet(id) {
	var name = $('#betname').val().trim();
	var l = $('#l_'+id).val();
	var m = $('#m_'+id).val();
	var r = $('#r_'+id).val();
	if(name == '') {
		alert('请填写一个有效的邮箱');
	} else {
		
		var data = { id : id, L : l, M : m, R : r, name : name};
		$.post('./cmd?m=bet', data, function(result) {
			if(result.success) {
				alert('下注成功');
			} else {
				$('#l_'+id).val(0);
				$('#m_'+id).val(0);
				$('#r_'+id).val(0);
				alert('下注失败');
			}
		}, 'json');
	}
}