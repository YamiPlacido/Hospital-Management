$(document).ready(loaddata);
		function loaddata() {
			console.log("aaa");
			$('#SelectedDoctor').change(function(){
				if($('#selectdate').val()!= "" && $('#SelectedDoctor').val()!="") {
					getShift();
				}
			});
			$('#selectdate').change(function(){
				if($('#selectdate').val()!= "" && $('#SelectedDoctor').val()!="") {
					getShift();
				}
			});
		}
		//Delete
		function getShift() {
			$('#selectshift')
		    .empty();
		$.ajax({
	        url: "/admin/appointment/getShift",
	        data: { "employeeId": $("#SelectedDoctor").val(),
	        	"date": $("#selectdate").val()},
	        contentType: "application/json",
	        dataType: "json",
	        type: "get",
	        beforeSend: function() {
	        	$('#selectshift').append('');
	        },
	        success: function(data){
	        	console.log(data);
	        	data.lstShift.forEach(function(e){
	           		$('#selectshift').append('<option value="'+e.shiftId+'">'+e.note+'</option>');
	        	});
	        }
	    });
		}
	