$(document).ready(function() {
	$.ajax({
		url : './cmd?m=list',
		async : false,
		dataType : 'json',
		type : 'GET',
		success : function(result) {
			
			var halfHour = 30 * 60 * 1000;
			var current = new Date();
			var curDate = '2014年0' + (current.getMonth()+1) + '月' + current.getDate() + '日';
			var curTime = current.getTime();
			
			for(var i = 0; i < result.length; i++) {
				var n = result[i];
				
				var date = n.startTime.split(' ')[0];
				var matchOff = (n.times - curTime) > halfHour;
				var color = date == curDate ? 'primary' : 'info';
				var idL = 'l_'+n.id;
				var idM = 'm_'+n.id;
				var idR = 'r_'+n.id;
				var panel  = '<div class="panel panel-'+color+'">';
				panel += '<div class="panel-heading">';
				panel += '<center><h3 class="panel-title">'+ (i+1) +'. '+n.startTime+' '+n.teamLeft+' VS '+n.teamRight+'</h3></center>';
				panel += '</div>';
				panel += '<div class="panel-body">';
				panel += '<center>';
				
				panel += '<div class="row">';
				panel += '<div class="col-xs-3">';
				panel += n.teamLeft+'胜 <input id="'+idL+'" name="'+idL+'" type="text" class="form-control" value='+n.resultLeft+'>';
				panel += '</div>';
				panel += '<div class="col-xs-3">';
				panel += '平局 <input id="'+idM+'" name="'+idM+'" type="text" class="form-control" value='+n.resultMiddle+'>';
				panel += '</div>';
				panel += '<div class="col-xs-3">';
				panel += n.teamRight+'胜 <input id="'+idR+'" name="'+idR+'" type="text" class="form-control" value='+n.resultRight+'>';
				panel += '</div>';
				panel += '<div class="col-xs-2">';
				panel += '<button type="button" class="btn btn-primary" onclick="save('+n.id+')">save</button>';
				panel += '</div>';
				panel += '</div>';
				panel += '</center>';
				panel += '</div>';
				panel += '</div>';
				$('#list').append(panel);
			}
			
		}
	});
});

function save(id) {
	var l = $('#l_'+id).val();
	var m = $('#m_'+id).val();
	var r = $('#r_'+id).val();
	var data = { id : id, L : l, M : m, R : r};
	$.post('./cmd?m=save', data, function(result) {
		if(result.success) {
			alert('赔率保存成功');
		} else {
			$('#l_'+id).val(0);
			$('#m_'+id).val(0);
			$('#r_'+id).val(0);
			alert('赔率保存失败');
		}
	}, 'json');
}