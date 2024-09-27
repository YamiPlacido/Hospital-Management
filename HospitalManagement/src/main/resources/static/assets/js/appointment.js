$(document).ready(loaddata);
		function loaddata() {
			$('#SelectedDoctor').change(function(){
				if($('#selectdate').val()!= "" && $('#SelectedDoctor').val()!="0" && $('#SelectedPatient').val()!="0") {
					getShift();
				}
			});
			$('#selectdate').change(function(){
				if($('#selectdate').val()!= "" && $('#SelectedDoctor').val()!="0" && $('#SelectedPatient').val()!="0") {
					getShift();
				}
			});
			$('#SelectedPatient').change(function(){
				if($('#selectdate').val()!= "" && $('#SelectedDoctor').val()!="0" && $('#SelectedPatient').val()!="0") {
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
	        	"date": $("#selectdate").val(),
	        	"patientId": $('#SelectedPatient').val()},
	        contentType: "application/json",
	        dataType: "json",
	        type: "get",
	        beforeSend: function() {
	        	$('#selectshift').append('');
	        },
	        success: function(data){
	        	data.lstShift.forEach(function(e){
	           		$('#selectshift').append('<option value="'+e.shiftId+'">'+e.note+'</option>');
	        	});
	        }
	    });
		}
	