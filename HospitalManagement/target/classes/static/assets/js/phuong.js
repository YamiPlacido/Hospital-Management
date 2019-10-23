$(document).ready(function() {
	$("#load-more").click();
});

$("#load-more")
		.click(
				function() {
					$.ajax({
								type : "GET",
								url : '/admin/doctor',
								contentType : 'application/json',
								//        data: data,
								success : function(data, status) {
									var insert_content;
									jQuery
											.each(
													data,
													function(i, val) {
														insert_content = '<div class="col-md-4 col-sm-4 col-lg-3">';
														insert_content += '<div class="profile-widget">';
														insert_content += '<div class="doctor-img">';
														insert_content += '<a class="avatar" href="javascript:void(0);" onclick="showDetail('
																+ val.doctorId
																+ ');"><img alt="" src="/assets/img/'
																+ val.imagePath
																+ '"></a>';
														insert_content += '</div>';
														insert_content += '<div class="dropdown profile-action">';
														insert_content += '<a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>';
														insert_content += '<div class="dropdown-menu dropdown-menu-right">';
														insert_content += '<a class="dropdown-item" href="edit-doctor.html"><i class="fa fa-pencil m-r-5"></i> Edit</a>';
														insert_content += '<a class="dropdown-item" href="#" data-toggle="modal" data-target="#delete_doctor"><i class="fa fa-trash-o m-r-5"></i> Delete</a>';
														insert_content += '</div>';
														insert_content += '</div>';
														insert_content += '<h4 class="doctor-name text-ellipsis"><a href="profile.html">'
																+ val.name
																+ '</a></h4>';
														insert_content += '<div class="doc-prof">'
																+ val.speciality.name
																+ '</div>';
														insert_content += '<div class="user-country">';
														insert_content += '<i class=""></i> '
																+ val.code + '';
														insert_content += '</div>';
														insert_content += '</div>';
														insert_content += '</div>';

														$("#doctor-container")
																.append(
																		insert_content);
														insert_content = '';
													});

								}
							});
				});

function showDetail(id) {
	var insert_content='';
	var back_button = '<button type="button" class="btn btn-secondary" id="back-button">Load More</button>';
	$.ajax({
				type : "GET",
				url : '/admin/doctor/' + id,
				contentType : 'application/json',
				success : function(data, status) {
					insert_content += '<div class="card-box profile-header">';
					insert_content += '<div class="row">';
					insert_content += '<div class="col-md-12">';
					insert_content += '<div class="profile-view">';
					insert_content += '<div class="profile-img-wrap">';
					insert_content += '<div class="profile-img">';
					insert_content += '<a href="#"><img class="avatar" src="/assets/img/'+data.imagePath+'" alt=""></a>';
					insert_content += '</div>';
					insert_content += '</div>';
					insert_content += '<div class="profile-basic">';
					insert_content += ' <div class="row">';
					insert_content += '   <div class="col-md-5">';
					insert_content += '  <div class="profile-info-left">';
					insert_content += '   <h3 class="user-name m-t-0 mb-0">'+data.name+'</h3>';
					insert_content += '  <small class="text-muted">'+data.speciality.name+'</small>';
					insert_content += '  <div class="staff-id">Employee ID: '+data.code+'</div>';
					insert_content += ' <div class="staff-msg"><a href="chat.html" class="btn btn-primary">Send Message</a></div>';
					insert_content += '  </div>';
					insert_content += ' </div>';
					insert_content += ' <div class="col-md-7">';
					insert_content += '   <ul class="personal-info">';
					insert_content += '    <li>';
					insert_content += '     <span class="title">Phone:</span>';
					insert_content += '  <span class="text"><a href="#">'+data.phone+'</a></span>';
					insert_content += ' </li>';
					insert_content += '      <li>';
					insert_content += '    <span class="title">Email:</span>';
					insert_content += '          <span class="text"><a href="#">'+data.email+'</a></span>';
					insert_content += '       </li>';
					insert_content += '           <li>';
					insert_content += '        <span class="title">Birthday:</span>';
					insert_content += '       <span class="text">'+data.dob+'</span>';
					insert_content += '        </li>';
					insert_content += '   <li>';
					insert_content += '             <span class="title">Address:</span>';
					insert_content += '             <span class="text">'+data.address+'</span>';
					insert_content += '      </li>';
					insert_content += '                    <li>';
					insert_content += '   <span class="title">Gender:</span>';
					insert_content += '                   <span class="text">Female</span>';
					insert_content += '      </li>';
					insert_content += '    </ul>';
					insert_content += '          </div>';
					insert_content += '     </div>';
					insert_content += '        </div>';
					insert_content += '       </div>   ';
					insert_content += '     </div>';
					insert_content += '  </div>';
					insert_content += '</div>';
					$("#doctor-container").replaceWith(insert_content);
					$("#load-more").replaceWith(back_button);
				}
			});
}