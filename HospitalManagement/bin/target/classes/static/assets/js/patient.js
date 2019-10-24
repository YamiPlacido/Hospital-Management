//jQuery(function($) {
//
//	$('#btn-submit').click(function() {
//		console.log("aaa");
//		var url = getContextPath()+'/savePatient';
//		debugger;
//		var data = new FormData($('#patient-form')[0]);
//		$.ajax({
//			type : "POST",
//			url : url,
//			data : data,
//			processData : false,
//			contentType : false,
//			success : function(returnObject) {
//				alertMessage(returnObject.message);
//			},
//			complete : function() {
//				unblockbg();
//			}
//		});
//	});
//});
//
//function alertMessage(message) {
//	$('#modal-alert').modal('hide');
//	$('#modal-alert').modal('show');
//	$('#alert-message').text(message);
//}