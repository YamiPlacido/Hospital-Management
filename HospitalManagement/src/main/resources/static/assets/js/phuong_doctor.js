$(document).ready(function () {
    if (window.currentDoctor == null){
        $.ajax({
            type: "GET",
            url: '/admin/api/get-current-id',
            contentType: 'application/json',
            success: function (data, status) {
                window.currentDoctor = parseInt(data);
                $("#doctor-person-info").attr("href", "/admin/doctor/personal-info/"+window.currentDoctor);
                loadAppointments(window.currentDoctor);
                loadAppointments(window.currentDoctor);
            }
        });
    }
    // $("#load-more").click();

    loadAppointments(window.currentDoctor);
    callCKEditor();
    var date = new Date();
    date.setFullYear( date.getFullYear() - 17 );
    var validateDate = formatDate(date);
    var objectRules = {
        rules: {
            firstName: {
                required: true,
                minlength: 2
            },
            lastName: {
                required: true,
                minlength: 2
            },
            email: {
                required: true,
                email: true
            },
            address: {
                required: true,
                minlength: 5
            },
            phone: {
                required: true,
                digits: true
            },
            file: {
                required: true
            },
            dob: {
                min: "2002-10-30"
                // min: validateDate
            }
        },
        messages: {
            firstName: {
                required: "First name is required",
                minlength: "Name need more than 2 characters"
            },
            lastName: {
                required: "Last name is required",
                minlength: "Name need more than 2 characters"
            },
            email: {
                required: "Email is required",
                email: "Please enter valid email"
            },
            address: {
                required: "Address is required",
                minlength: "Name need more than 5 characters"
            },
            phone: {
                required: "Phone is required",
                digits: "Phone must be a number"
            },
            file: {
                required: "Image is required"
            },
            dob: {
                min: "The minimum age require is 17"
            }
        }
    };
    $("#employee-form").validate(objectRules);
});

var currentPage = 1;
//value on frontend
// var currentDoctor;
var currentAppointment;
var symptomSelections;
var examinationSelections;
var examTable;
var appointmentTable;
//value on backend
var symptomValueSelected = [];
var examinationValueSelected = [];
var finishedExamList  = [];
var currentSender;
const CONTEXTPATH = "http://localhost:8080/";
const EXPORT_FOLDER = "upload/pdf/examination/"


$("#appointment-perform").click(
    function (event) {
        event.preventDefault();
        if (currentAppointment==null){
            alert("Please select an appointment to perform.");
            return false;
        } else {
            this.click();
            return true;
        }
    }
);

// $("#load-more").click(function () {
//     $.ajax({
//         type: "GET",
//         url: '/admin/api/doctor?current_page=' + currentPage,
//         contentType: 'application/json',
//         success: function (data, status) {
//             currentPage += 1;
//             var insert_content;
//             jQuery.each(data, function (i, val) {
//                 insert_content = '<div class="col-md-4 col-sm-4 col-lg-3">';
//                 insert_content += '<div class="profile-widget">';
//                 insert_content += '<div class="doctor-img">';
//                 insert_content += '<a class="avatar" href="/admin/doctor/' + val.employeeId
//                     + '"><img alt="" src="/upload/employee/'
//                     + val.imagePath
//                     + '"></a>';
//                 insert_content += '</div>';
//                 insert_content += '<div class="dropdown profile-action">';
//                 insert_content += '</div>';
//                 insert_content += '<h4 class="doctor-name text-ellipsis"><a href="#">'
//                     + val.firstName + ' ' + val.lastName
//                     + '</a></h4>';
//                 insert_content += '<div class="doc-prof">'
//                     + val.speciality.name
//                     + '</div>';
//                 insert_content += '<div class="user-country">';
//                 insert_content += '<i class=""></i> '
//                     + val.code + '';
//                 insert_content += '</div>';
//                 insert_content += '</div>';
//                 insert_content += '</div>';
//
//                 $("#employee-container")
//                     .append(insert_content);
//                 insert_content = '';
//             });
//         }
//     });
// });

$("#symptom-edit").click(function() {
    loadSymptomsToModal();
//lấy các giá trị đã được gán vào symptomValueSelected để gán vào multiselect
    loadSelectedSymptomToModal();
});
function loadSelectedSymptomToModal() {
    $('#select-symptom').multiSelect('select', symptomValueSelected);
}

$("#examination-edit").click(function() {
    console.log("Da vao day");
    loadExaminationsToModal();
});

// $("#add-symptom").click(getAllSelectedItems());

function loadAppointments(doctor_id) {
    $.ajax({
        type: "GET",
        url: '/admin/doctor/api/appointments/' + doctor_id,
        contentType: 'application/json',
        success: function (data, status) {
            $('#appointment-container').empty();
            var insert_content;
            jQuery.each(data, function (i, val) {
                // var appointmentDate = JSON.stringify(val.patient.dob).substr(1,16);
                var age = calculateAge(val.patient.dob);
                var date = changeSpringDateToJSDate(val.date).toShortFormat();
                console.log(age);
                insert_content = '<tr>';
                insert_content += '<td>' + val.appId + '</td>';
                insert_content += '<td><a href="#" onclick="performAppointment('+ val.appId +')">' + val.patient.name + '</a></td>';
                insert_content += '<td>' + age + '</td>';
                insert_content += '<td>Cardiology</td>';
                insert_content += '<td>' + date + '</td>';
                insert_content += '<td>10:00am - 11:00am</td>';
                if (val.stage == "CREATED"){
                    insert_content += '<td><span class="custom-badge status-red">' + val.stage + '</span></td>';
                } else if (val.stage == "DOING"){
                    insert_content += '<td><span class="custom-badge status-orange">' + val.stage + '</span></td>';
                } else if (val.stage == "FINISHED"){
                    insert_content += '<td><span class="custom-badge status-green">' + val.stage + '</span></td>';
                }
                insert_content += '</tr>';

                $("#appointment-container")
                    .append(insert_content);
                insert_content = '';
            });
            $('#appointment-datatables').DataTable({
                destroy: true,
                retrieve: true,
                "fnDrawCallback": function (oSettings) {
                    // if ($('#appointment-container tr').length < 11) {
                    //     $('.dataTables_paginate').hide();
                    // }
                }
            });
        }
    });
}

function deleteDoctor(id) {
    $.ajax({
        type: "DELETE",
        url: '/admin/api/doctor?id=' + id,
        //				contentType : 'application/json',
        success: function (data, status) {
            window.location.replace(CONTEXTPATH+"admin/doctor");
        }
    });
}

$("#positionId").change(function () {
    var id = $("#positionId").val();
    $.ajax({
        type: "GET",
        url: '/system/speciality?position_id=' + id,
        success: function (data, status) {
            $("#specialityId").empty();
            var insert_content;
            jQuery.each(data, function (i, val) {
                insert_content = '<option value="' + val.specialityId + '">' + val.name + '</option>';

                $("#specialityId")
                    .append(
                        insert_content);
                insert_content = '';
            });
        }
    });
});

$('#select-symptom').multiSelect({
    selectableHeader: "<input type='text' class='search-input form-control' autocomplete='off' placeholder='symptom' onfocus=\"this.placeholder = ''\"\n" +
        "onblur=\"this.placeholder = 'symptom'\">",
    afterInit: function (ms) {
        var that = this,
            $selectableSearch = that.$selectableUl.prev(),
            selectableSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selectable:not(.ms-selected)';

        that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
            .on('keydown', function (e) {
                if (e.which === 40) {
                    that.$selectableUl.focus();
                    return false;
                }
            });

    },
    afterSelect: function () {
        this.qs1.cache();
        renewSymptomSelection();
        // use array "selections" here..
    },
    afterDeselect: function () {
        this.qs1.cache();
        renewSymptomSelection();
    }
});

function renewSymptomSelection(){
    symptomSelections = [];
    $("#select-symptom option:selected").each(function () {
        var optionValue = $(this).val();
        var optionText = $(this).text();
        console.log("optionText", optionText);
        // collect all values
        symptomSelections.push(optionValue);
    });
    console.log(symptomSelections);
}

$('#select-examination').multiSelect({
    selectableHeader: "<input type='text' class='search-input form-control' autocomplete='off' placeholder='examination' onfocus=\"this.placeholder = ''\"\n" +
        "onblur=\"this.placeholder = 'examination'\">",
    afterInit: function (ms) {
        var that = this,
            $selectableSearch = that.$selectableUl.prev(),
            selectableSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selectable:not(.ms-selected)';

        that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
            .on('keydown', function (e) {
                if (e.which === 40) {
                    that.$selectableUl.focus();
                    return false;
                }
            });

    },
    afterSelect: function () {
        this.qs1.cache();
        renewExaminationSelection();
    },
    afterDeselect: function () {
        this.qs1.cache();
        renewExaminationSelection();
    }
});

function renewExaminationSelection(){
    examinationSelections = [];
    $("#select-examination option:selected").each(function () {
        var optionValue = $(this).val();
        var optionText = $(this).text();
        console.log("optionText", optionText);
        // collect all values
        examinationSelections.push(optionValue);
    });
    console.log(examinationSelections);
}


//lấy symptom list (đầy đủ) từ server đổ vào trong multiselect
function loadSymptomsToModal() { //ok
    $.ajax({
        type: "GET",
        url: '/system/symptoms/type_one',
        contentType: 'application/json',
        success: function (data, status) {
            jQuery.each(data, function (i, val) {
                $('#select-symptom').multiSelect('addOption', {value: val.symptomId, text: val.name, index: i});
            });
            loadSelectedSymptomToModal();
        }
    });
}

//lấy examination list (đầy đủ) từ server đổ vào trong multiselect
function loadExaminationsToModal() { //chua ok
    console.log(currentAppointment);
    var fullList = [];
    var examList = getExamList(currentAppointment);
    console.log(examList.length);
    console.log(examList);

    $.ajax({
        type: "GET",
        // url: '/admin/doctor/api/appointments/examination/full/' + currentAppointment,
        url: '/system/examinations/suggest/' + currentAppointment,
        contentType: 'application/json',
        success: function (data, status) {
            jQuery.each(data, function (i, val) {
                if (val.suggested  == true){
                    $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name + " *", index: i});
                } else {
                    $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name, index: i});
                }
                // if(val.stage  != null && val.stage  != 'FINISHED'){
                //     $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name, index: i,selected:true});
                // } else if(val.stage  != null && val.stage  == 'FINISHED'){
                //     $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name, index: i,selected:true,disabled:true});
                // } else if(val.stage  == null){
                //     $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name, index: i});
                // }
            });
            $('#select-examination').multiSelect('select', examinationValueSelected);
        }
    });
}

function refreshExaminationInModal() {
    $('#select-examination').multiSelect('refresh');
}

function getFullExamList() {
    var fullExamList =[];
    $.ajax({
        type: "GET",
        url: '/system/examinations',
        contentType: 'application/json',
        success: function (data, status) {
            jQuery.each(data, function (i, val) {
                var object = {
                    id : val.id,
                    name : val.name,
                    stage : null
                };
                fullExamList.push(object);
                // console.log(object);
                // console.log(fullExamList);
            });
        }
    });
    // console.log(fullExamList);
    return fullExamList;
}
//lấy các exam đã finished
function getExamList(app_id) {
    var examList = [];
    $.ajax({
        type: "GET",
        url: '/admin/doctor/api/appointments/examination/' + app_id,
        contentType: 'application/json',
        success: function (data, status) {
            jQuery.each(data, function (i, val) {
                var object = {
                    id : val.examinationType.id,
                    name : val.examinationType.name,
                    stage : val.stage
                };
                examList.push(object);
                // console.log(object);
                // console.log(examList);
            });
        }
    });
    // console.log(examList);
    return examList;
}

//lưu thông tin symptom multi select
function saveSymptomToAppointment(appId) { //ok
    if (appId  == 0){
        appId = currentAppointment;
    }
    console.log(symptomSelections);
    $.ajax({
        type: "POST",
        data : {
            appId : appId,
            selectedValue: symptomSelections
        },
        url: '/admin/doctor/api/symptom',
        // contentType: 'application/json',
        success: function (data, status) {
            loadSymptomToAppointment(appId);
            $('#exampleModalSymptom').modal('hide');
        }
    });
}
//lưu thông tin examination multi select
function saveExaminationToAppointment(appId) {
    if (appId  == 0){
        appId = currentAppointment;
    }
    console.log(examinationSelections);
    $.ajax({
        type: "POST",
        data : {
            appId : appId,
            selectedValue: examinationSelections
        },
        url: '/admin/doctor/api/examination',
        // contentType: 'application/json',
        success: function (data, status) {
            loadExaminationToAppointment(appId);
            createTopCenterNotify(data.message,data.status);
            refreshExaminationInModal();
            $('#exampleModalExamination').modal('hide');
            //push
            sendBroadcast("LOADEXAMINATIONS",window.currentDoctor,"You have received one examination.",10);
        }
    });
}
//lấy danh sách các symptom từ server
function loadSymptomToAppointment(app_id) {
    $.ajax({
        type: "GET",
        url: '/admin/doctor/api/appointments/symptom/' + app_id,
        contentType: 'application/json',
        success: function (data, status) {
            symptomValueSelected = [];
            $("#symptoms").empty();
            jQuery.each(data, function (i, val) {
                $("#symptoms")
                    .append('<li>'+ val.name+'</li>');
                symptomValueSelected.push(val.symptomId.toString());
            });
        }
    });
}

//load examination list của appointment từ server
function loadExaminationToAppointment(app_id) {
    $.ajax({
        type: "GET",
        url: '/admin/doctor/api/appointments/examination/' + app_id,
        contentType: 'application/json',
        success: function (data, status) {
            examinationValueSelected = [];
            $("#examinations").empty();
            jQuery.each(data, function (i, val) {
                // if (val.stage != "FINISHED"){
                //     if (val.stage == "CREATED"){
                //         $("#examinations")
                //             .append('<li>'+ val.examinationType.name+'  <span class="custom-badge status-red">CREATED</span></li>'
                //                 + '<button type="button" class="btn btn-outline-secondary btn-sm">PDF</button>');
                //     } else if (val.stage == "DOING"){
                //         $("#examinations")
                //             .append('<li>'+ val.examinationType.name+'  <span class="custom-badge status-orange">DOING</span></li>'
                //                 + '<button type="button" class="btn btn-outline-secondary btn-sm">PDF</button>');
                //     }
                // }
                // else if (val.stage == "FINISHED"){
                //     $("#examinations")
                //         .append('<li>'+ val.examinationType.name+'  <a href="#" class="custom-badge status-green" ' +
                //             'data-toggle="modal" data-target="#exampleModalExamFinish" ' +
                //             'id="finish-test" onclick="loadExaminationToModal('+val.id+')">FINISHED</a></li>'
                //             + '<button type="button" class="btn btn-outline-secondary btn-sm">PDF</button>');
                // }
                if (val.stage != "FINISHED"){
                    if (val.stage == "CREATED"){
                        $("#examinations")
                            .append('<tr><td>'+ (i+1) +'</td> '+
                                '<td>'+ val.examinationType.name+'</td> ' +
                                '<td> <span class="custom-badge status-red">CREATED</span></td>'
                                + '<td><button type="button" class="btn btn-outline-secondary btn-sm" onclick="exportPDF('+ val.examinationType.id+')">PDF</button><td> </tr>');
                    } else if (val.stage == "DOING"){
                        $("#examinations")
                            .append('<tr> <td>'+ (i+1) +'</td> '+
                                '<td>'+ val.examinationType.name+'</td> ' +
                                '<td> <span class="custom-badge status-orange">DOING</span></td>'
                                + '<td><button type="button" class="btn btn-outline-secondary btn-sm" onclick="exportPDF('+ val.examinationType.id+')">PDF</button><td> </tr>');
                    }
                }
                else if (val.stage == "FINISHED"){
                    $("#examinations")
                        .append('<tr> <td>'+ (i+1) +'</td> '+
                            '<td>'+ val.examinationType.name+'</td> ' +
                            '<td> <a href="#" class="custom-badge status-green" ' +
                            'data-toggle="modal" data-target="#exampleModalExamFinish" ' +
                            'id="finish-test" onclick="loadExaminationToModal('+val.id+')">FINISHED</a></td>'
                            + '<td><button type="button" class="btn btn-outline-secondary btn-sm" onclick="exportPDF('+ val.examinationType.id+')">PDF</button><td> </tr>');
                }
                //gán giá trị cho examinationValueSelected
                examinationValueSelected.push(val.examinationType.id.toString());
            });
        }
    });
}

//doctor: chuyển sang tab appointment perform
function performAppointment(id) {
    if (currentAppointment == null && id == null){
        alert("Please chose one appointment you want to perform.");
    } else if (id != null) {
        currentAppointment = id;
    }
    // console.log(currentExamination);

    loadSymptomToAppointment(currentAppointment);
    // loadSelectedSymptomToModal();
    // loadExaminationsToModal();
    loadExaminationToAppointment(currentAppointment);
    getAppointmentById(currentAppointment);
    updateAppointmentStageToDoing();
}
//chuyển sang trạng thái doing
function updateAppointmentStageToDoing() {
    $.ajax({
        type: "POST",
        data : {
            app_id : currentAppointment,
            stage: "DOING"
        },
        url: '/admin/doctor/api/appointment/stage',
        // contentType: 'application/json',
        success: function (data, status) {
            loadAppointments(window.currentDoctor);
        }
    });
}
//doctor: lấy thông tin trên server đổ vào tab apppointment perform
function getAppointmentById(id) {
    $.ajax({
        type: "GET",
        url: '/admin/doctor/api/appointment/' + id,
        contentType: 'application/json',
        success: function (data, status) {
            // alert(id);
            $("#patient-name").text(data.patient.name);
            $("#patient-age").text("Age: "+calculateAge(data.patient.dob));
            $("#patient-id").text("Patient Id: "+data.patient.patientId);
            // console.log(data.patientId);
            $("#patient-phone").text(data.patient.phone);
            $("#patient-email").text(data.patient.email);
            $("#patient-address").text(data.patient.address);
            if (data.patient.gender == "f"){
                $("#patient-gender").text("Female");
            } else {
                $("#patient-gender").text("Male");
            }
            $("#appointment-id").text(data.appId);
            $('#patient-history').attr('onClick','getPatientHistory('+data.patient.patientId+')');
            $("#appointment-perform").click();
        }
    });
}


//hiển thị toàn bộ exam dạng modal trước khi lưu
function loadExaminationToModal(id) {
    $.ajax({
        type: "GET",
        url: '/admin/examinator/api/examination/' + id,
        contentType: 'application/json',
        success: function (data, status) {
            // alert(id);
            $("#examination-detail-patient-name").text(data.patient.name);
            $("#examination-detail-patient-age").text("Age: "+calculateAge(data.patient.dob));
            $("#examination-detail-patient-id").text("Patient Id: "+data.patient.patientId);
            if (data.patient.gender == "f"){
                $("#examination-detail-patient-gender").text("Female");
            } else {
                $("#examination-detail-patient-gender").text("Male");
            }
            $("#examination-detail-exam-name").text(data.examinationType.name);
            $("#examination-detail-examinator").text("Implement by: "+data.examinator.firstName +" "+data.examinator.lastName);
            $("#examination-detail-exam-id").text(data.id);
            $("#examination-detail-exam-time").text(new Date(data.date).toUTCString());
            $("#examination-detail-exam-content").html(data.content);
            $("#examination-detail-exam-result").html(data.result);
            $("#examination-view-pdf-result").attr("href", CONTEXTPATH + EXPORT_FOLDER+ data.pdfResultPath);

            $("#carousel-container").empty();
            $("#carousel-indicators").empty();
            if (data.image_path_1 !=null && data.image_path_1 !=""){
                $("#carousel-indicators").append(
                    '<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>'
                )
                $("#carousel-container").append(
                    '<div class="carousel-item active">\n' +
                    '<img class="d-block w-100" src="/upload/exam/'+data.image_path_1+'" alt="First slide">\n' +
                    '</div>'
                )
            }
            if (data.image_path_2 !=null && data.image_path_2 !=""){
                $("#carousel-indicators").append(
                    '<li data-target="#carouselExampleIndicators" data-slide-to="0"></li>'
                )
                $("#carousel-container").append(
                    '<div class="carousel-item">\n' +
                    '<img class="d-block w-100" src="/upload/exam/'+data.image_path_2+'" alt="First slide">\n' +
                    '</div>'
                )
            }
            if (data.image_path_3 !=null && data.image_path_3 !=""){
                $("#carousel-indicators").append(
                    '<li data-target="#carouselExampleIndicators" data-slide-to="0"></li>'
                )
                $("#carousel-container").append(
                    '<div class="carousel-item">\n' +
                    '<img class="d-block w-100" src="/upload/exam/'+data.image_path_3+'" alt="First slide">\n' +
                    '</div>'
                )
            }
            // $("#examination-detail-examination-perform").click();
        }
    });
}

//xuất PDF
function exportPDF(examinationTypeId) {
    $.ajax({
        type: "POST",
        data : {
            appId : currentAppointment,
            examinationTypeId: examinationTypeId
        },
        url: '/admin/doctor/api/examination/create_pdf',
        // contentType: 'application/json',
        success: function (data, status) {
            openInNewTab(CONTEXTPATH+data);
        }
    });
}

function getPatientHistory(patient_id) {
    $.ajax({
        type: "GET",
        url: '/admin/doctor/api/record-detail/'+patient_id,
        contentType: 'application/json',
        success: function (data, status) {
            $("#record-patient-name").text(data.patient.name);
            $("#record-patient-age").text("Age: "+calculateAge(data.patient.dob));
            $("#record-patient-id").text("Patient Id: "+data.patient.patientId);
            // console.log(data.patientId);
            $("#record-patient-phone").text(data.patient.phone);
            $("#record-patient-email").text(data.patient.email);
            $("#record-patient-address").text(data.patient.address);
            if (data.patient.gender == "f"){
                $("#record-patient-gender").text("Female");
            } else {
                $("#record-patient-gender").text("Male");
            }
            jQuery.each(data.records, function (i, val) {
                var date = changeSpringDateToJSDate(val.appointment.date).toShortFormat();
                var insert_content = "";
                insert_content += '<li>' +
                    '<div class="experience-user">' +
                    '\t<div class="before-circle"></div>' +
                    '</div>' +
                    '<div class="experience-content">' +
                    '<div class="timeline-content">' +
                    '<a href="#/" class="name"> '
                    + val.appointment.employee.firstName+' '
                    + val.appointment.employee.lastName +
                    '</a>' +
                    '<div>';
                for (i = 0; i < val.symptoms.length; i++) {
                    insert_content += val.symptoms[i].name + ", ";
                }
                insert_content +=   '</div>';
                insert_content +=   '<div>';
                    for (i = 0; i < val.examinations.length; i++) {
                        insert_content += val.examinations[i].examinationType.name + ", ";
                    }
                insert_content +=   '</div>';
                insert_content +=   '<span class="time">';
                insert_content +=   date;
                insert_content +=  '</span>' +
                    '</div>' +
                    '</div>' +
                    '</li>';
                $("#record-patient-appointments")
                    .append(insert_content);
            });
        }
    });

}
//
// $("#btn-create-employee").click(function () {
//     preventSubmit();
// });
//
// $("#btn-edit-employee").click(function () {
//     preventSubmit();
// });

// function preventSubmit() {
//     var checked = validateDate();
//     if (!checked){
//         $('form').bind('submit',function(e){
//             validateDate();
//             e.preventDefault();
//         });
//     } else {
//         $('form').unbind('submit');
//     }
// }
//
// function validateDate() {
//     var currentDate = new Date();
//     var selectedDate = new Date($("#dob").val());
//     var age = Math.floor((currentDate-selectedDate) / (365.25 * 24 * 60 * 60 * 1000));
//     if(age<17){
//         $('#dob').next().remove();
//         $("#dob").after('<label class="error" for="date">The minimum age require is 17</label>');
//         return false;
//     } else {
//         $('#dob + label').hide();
//         return true;
//     }
// }