$(document).ready(
		function() {
			$.ajax({
				url : "/admin/appointment/getDoctorScheduleDetail",
				data : {
					"employeeId" : $("#employeeId").val(),
					"dateFrom" : $("#dateFrom").val(),
					"dateTo" : $("#dateTo").val()
				},
				contentType : "application/json",
				dataType : "json",
				type : "get",
				success : function(data) {
					data.listShift1.forEach(function(e) {
							switch(e.dateOfWeek) {
								case 1:
									$("#shift1 td:nth-child(8)").html(''+e.name+''
											+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
											+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
									break;
								case 2:
									$("#shift1 td:nth-child(2)").html(''+e.name+''
											+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
											+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
									break;
								case 3:
									$("#shift1 td:nth-child(3)").html(''+e.name+''
											+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
											+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
									break;
								case 4:
									$("#shift1 td:nth-child(4)").html(''+e.name+''
											+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
											+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
									break;
								case 5:
									$("#shift1 td:nth-child(5)").html(''+e.name+''
											+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
											+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
									break;
								case 6:
									$("#shift1 td:nth-child(6)").html(''+e.name+''
											+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
											+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
									break;
								default:
									$("#shift1 td:nth-child(7)").html(''+e.name+''
											+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
											+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
									break;
							}
					});
					data.listShift2.forEach(function(e) {
						switch(e.dateOfWeek) {
							case 1:
								$("#shift2 td:nth-child(8)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 2:
								$("#shift2 td:nth-child(2)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 3:
								$("#shift2 td:nth-child(3)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 4:
								$("#shift2 td:nth-child(4)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 5:
								$("#shift2 td:nth-child(5)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 6:
								$("#shift2 td:nth-child(6)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							default:
								$("#shift2 td:nth-child(7)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
						}
				});
					data.listShift3.forEach(function(e) {
						switch(e.dateOfWeek) {
							case 1:
								$("#shift3 td:nth-child(8)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 2:
								$("#shift3 td:nth-child(2)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 3:
								$("#shift3 td:nth-child(3)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 4:
								$("#shift3 td:nth-child(4)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 5:
								$("#shift3 td:nth-child(5)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 6:
								$("#shift3 td:nth-child(6)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							default:
								$("#shift3 td:nth-child(7)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
						}
				});
					data.listShift4.forEach(function(e) {
						switch(e.dateOfWeek) {
							case 1:
								$("#shift4 td:nth-child(8)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 2:
								$("#shift4 td:nth-child(2)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 3:
								$("#shift4 td:nth-child(3)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 4:
								$("#shift4 td:nth-child(4)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 5:
								$("#shift4 td:nth-child(5)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 6:
								$("#shift4 td:nth-child(6)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							default:
								$("#shift4 td:nth-child(7)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
						}
				});
					data.listShift5.forEach(function(e) {
						switch(e.dateOfWeek) {
							case 1:
								$("#shift5 td:nth-child(8)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 2:
								$("#shift5 td:nth-child(2)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 3:
								$("#shift5 td:nth-child(3)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 4:
								$("#shift5 td:nth-child(4)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 5:
								$("#shift5 td:nth-child(5)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							case 6:
								$("#shift5 td:nth-child(6)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
							default:
								$("#shift5 td:nth-child(7)").html(''+e.name+''
										+'<br/><i class="fa fa-user"></i>'+e.identityCard+''
										+'<br/><i class="fa fa-phone"></i>'+e.phone+'');
								break;
						}
				});
				}
			});
			
			
//			$("#dateFrom").val($.datepicker.formatDate('dd M yy', $("#dateFrom").val()));
//			$("#dateTo").val($.datepicker.formatDate('dd M yy', $("#dateTo").val()));
			var from = $("#dateFrom").val().split("-")
			var mon = new Date(from[0], from[1] - 1, from[2])
			$("#tdDateFrom").text($("#dateFrom").val())
			$("#tdDateTo").text($("#dateTo").val())
			var tue = new Date();
			var wed = new Date();
			var thu = new Date();
			var fri = new Date();
			var sat = new Date();
			var sun = new Date();
			tue.setDate(mon.getDate() + 1);
			wed.setDate(mon.getDate() + 2);
			thu.setDate(mon.getDate() + 3);
			fri.setDate(mon.getDate() + 4);
			sat.setDate(mon.getDate() + 5);
			sun.setDate(mon.getDate() + 6);
			
			$("#sunday").text($.datepicker.formatDate('dd M yy', sun));
			$("#monday").text($.datepicker.formatDate('dd M yy', mon));
			$("#tuesday").text($.datepicker.formatDate('dd M yy', tue));
			$("#wednesday").text($.datepicker.formatDate('dd M yy', wed));
			$("#thursday").text($.datepicker.formatDate('dd M yy', thu));
			$("#friday").text($.datepicker.formatDate('dd M yy', fri));
			$("#saturday").text($.datepicker.formatDate('dd M yy', sat));
		});
