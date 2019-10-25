$(document).ready(function () {
    $("#load-more").click();
    currentDoctor = 3;
    currentExaminator = 9;
    loadAppointments(currentDoctor);
    loadExaminations(currentExaminator);
    callCKEditor();
});

var currentPage = 1;
//value on frontend
var currentDoctor;
var currentExaminator;
var currentExamination;
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

$("#examination-perform").click(
    function (event) {
        event.preventDefault();
        if (currentExamination==null){
            alert("Please select one exam to perform.");
            return false;
        } else {
            this.click();
            return true;
        }
    }
);
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

$("#load-more").click(function () {
    $.ajax({
        type: "GET",
        url: '/admin/api/doctor?current_page=' + currentPage,
        contentType: 'application/json',
        success: function (data, status) {
            currentPage += 1;
            var insert_content;
            jQuery.each(data, function (i, val) {
                insert_content = '<div class="col-md-4 col-sm-4 col-lg-3">';
                insert_content += '<div class="profile-widget">';
                insert_content += '<div class="doctor-img">';
                insert_content += '<a class="avatar" href="/admin/doctor/' + val.employeeId
                    + '"><img alt="" src="/upload/employee/'
                    + val.imagePath
                    + '"></a>';
                insert_content += '</div>';
                insert_content += '<div class="dropdown profile-action">';
                // insert_content += '<a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>';
                // insert_content += '<div class="dropdown-menu dropdown-menu-right">';
                // insert_content += '<a class="dropdown-item" href="/doctor/edit/' + val.employeeId + '"><i class="fa fa-pencil m-r-5"></i> Edit</a>';
                // insert_content += '<a class="dropdown-item" href="#" data-toggle="modal" data-target="#delete_doctor"><i class="fa fa-trash-o m-r-5"></i> Delete</a>';
                // insert_content += '</div>';
                insert_content += '</div>';
                insert_content += '<h4 class="doctor-name text-ellipsis"><a href="#">'
                    + val.firstName + ' ' + val.lastName
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

                $("#employee-container")
                    .append(insert_content);
                insert_content = '';
            });
        }
    });
});

$("#symptom-edit").click(function() {
    loadSymptomsToModal();
});

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
                insert_content += '<td><span class="custom-badge status-red">' + val.stage + '</span></td>';
                insert_content += '</tr>';

                $("#appointment-container")
                    .append(insert_content);
                insert_content = '';
            });
            $('#appointment-datatables').DataTable({
                destroy: true,
                // retrieve: true,
                "fnDrawCallback": function (oSettings) {
                    if ($('#appointment-container tr').length < 11) {
                        $('.dataTables_paginate').hide();
                    }
                }
            });
        }
    });
}

function loadExaminations(examinator_id) {
    $.ajax({
        type: "GET",
        url: '/admin/examinator/api/examinations/' + examinator_id,
        contentType: 'application/json',
        success: function (data, status) {
            $('#exams-container').empty();

            var insert_content;

            jQuery.each(data, function (i, val) {
                var age = calculateAge(val.patient.dob);
                var date = changeSpringDateToJSDate(val.date).toShortFormat();
                insert_content = '<tr>';
                insert_content += '<td>' + val.id + '</td>';
                insert_content += '<td><a href="#" onclick="performExamination('+ val.id +')">' + val.patient.name + '</a></td>';
                insert_content += '<td>' + age + '</td>';
                insert_content += '<td>'+val.examinationType.name+'</td>';
                insert_content += '<td>' + date + '</td>';
                insert_content += '<td>10:00am - 11:00am</td>';
                insert_content += '<td><span class="custom-badge status-red">' + val.stage + '</span></td>';
                insert_content += '</tr>';

                $("#exams-container")
                    .append(insert_content);
                insert_content = '';
            });
            examTable = $('#exam-datatables').DataTable({
                destroy: true,
                retrieve: true,
                "fnDrawCallback": function (oSettings) {
                    if ($('#exams-container tr').length < 11) {
                        $('.dataTables_paginate').hide();
                    }
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
            window.location.replace("http://localhost:8080/doctor");
        }
    });
}

$("#position").change(function () {
    var id = $("#position").val();
    $.ajax({
        type: "GET",
        url: '/system/speciality?position_id=' + id,
        success: function (data, status) {
            $("#speciality").empty();
            var insert_content;
            jQuery.each(data, function (i, val) {
                insert_content = '<option value="' + val.specialityId + '">' + val.name + '</option>';

                $("#speciality")
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
        url: '/system/symptoms',
        contentType: 'application/json',
        success: function (data, status) {
            jQuery.each(data, function (i, val) {
                $('#select-symptom').multiSelect('addOption', {value: val.symptomId, text: val.name, index: i});
            });
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
        url: '/admin/doctor/api/appointments/examination/full/' + currentAppointment,
        contentType: 'application/json',
        success: function (data, status) {
            jQuery.each(data, function (i, val) {
                if(val.stage  != null && val.stage  != 'FINISHED'){
                    $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name, index: i,selected:true});
                } else if(val.stage  != null && val.stage  == 'FINISHED'){
                    $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name, index: i,selected:true,disabled:true});
                } else if(val.stage  == null){
                    $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name, index: i});
                }
            });
            $('#select-examination').multiSelect('select', examinationValueSelected);

        }
    });
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
            $('#exampleModalExamination').modal('hide');
            //push
            sendBroadcast(currentDoctor,"LOADEXAMINATIONS",currentExaminator);
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

//lấy các giá trị đã được gán vào symptomValueSelected để gán vào multiselect
$("#symptom-edit").click(function(){
    console.log(symptomValueSelected);
    $('#select-symptom').multiSelect('select', symptomValueSelected);
});

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
                if (val.stage != "FINISHED"){
                    if (val.stage == "CREATED"){
                        $("#examinations")
                            .append('<li>'+ val.examinationType.name+'  <span class="custom-badge status-red">CREATED</span></li>');
                    }
                }
                else if (val.stage == "FINISHED"){
                    $("#examinations")
                        .append('<li>'+ val.examinationType.name+'  <a href="#" class="custom-badge status-green">FINISHED</a></li>');
                }
                //gán giá trị cho examinationValueSelected
                examinationValueSelected.push(val.examinationType.id.toString());
            });
        }
    });
}

//doctor: lấy các giá trị đã được gán vào examinationValueSelected để gán vào multiselect
// $("#examination-edit").click(function(){
//     console.log(examinationValueSelected);
//     $('#select-examination').multiSelect('select', examinationValueSelected);
// });

//chuyển sang tab appointment perform
function performAppointment(id) {
    if (currentAppointment == null && id == null){
        alert("Please chose one appointment you want to perform.");
    } else if (id != null) {
        currentAppointment = id;
    }
    // console.log(currentExamination);

    loadSymptomToAppointment(currentAppointment);
    // loadExaminationsToModal();
    loadExaminationToAppointment(currentAppointment);
    getAppointmentById(currentAppointment);
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
            $("#appointment-perform").click();
        }
    });
}
//examinator: chuyển sang tab perform
function performExamination(id) {
    if (currentExamination == null && id == null){
        alert("Please chose one examination you want to perform.");
    } else if (id != null) {
        currentExamination = id;
    }
    // console.log(currentExamination);
    getExaminationById(currentExamination);
}

//examinator: lấy thông tin đổ vào examination perform
function getExaminationById(id) {
    $.ajax({
        type: "GET",
        url: '/admin/examinator/api/examination/' + id,
        contentType: 'application/json',
        success: function (data, status) {
            currentAppointment = data.appointment.appId;
            $("#patient-name").text(data.patient.name);
            $("#patient-age").text("Age: "+calculateAge(data.patient.dob));
            // console.log(data.patient.patientId);
            $("#patient-id").text("Patient Id: "+data.patient.patientId);
            $("#patient-phone").text(data.patient.phone);
            $("#patient-email").text(data.patient.email);
            $("#patient-address").text(data.patient.address);
            if (data.patient.gender == "f"){
                $("#patient-gender").text("Female");
            } else {
                $("#patient-gender").text("Male");
            }
            $("#exam-name").text(data.examinationType.name);
            // $("#exam-sender").text(data.examinationType.name);
            $("#exam-id").text(data.id);
            $("#exam-time").text(new Date(data.date).toUTCString());
            $("#exam-content").html(data.content);
            $("#exam-result").html(data.result);
            //click vào tab
            $("#examination-perform").click();
        }
    });
}
//examinator: lưu lại content
function saveContentOfExam() {
    var content = CKEDITOR.instances['editor1'].getData();
    // var content = $("#editor1").val();
    $.ajax({
        type: "POST",
        data : {
            ex_id : currentExamination,
            content: content
        },
        url: '/admin/examinator/api/examination/content',
        // contentType: 'application/json',
        success: function (data, status) {
            getExaminationById(currentExamination);
            $('#exampleModalContent').modal('hide');
        }
    });
}

//lưu lại result
function saveResultOfExam() {
    var content = CKEDITOR.instances['editor2'].getData();
    $.ajax({
        type: "POST",
        data : {
            ex_id : currentExamination,
            content: content
        },
        url: '/admin/examinator/api/examination/result',
        // contentType: 'application/json',
        success: function (data, status) {
            getExaminationById(currentExamination);
            $('#exampleModalResult').modal('hide');
        }
    });
}
function loadContentOfExamToCKEditor() {
    $.ajax({
        type: "GET",
        url: '/admin/examinator/api/examination/' + currentExamination,
        contentType: 'application/json',
        success: function (data, status) {
            CKEDITOR.instances['editor1'].setData(data.content);
        }
    });
}

function loadResultOfExamToCKEditor() {
    $.ajax({
        type: "GET",
        url: '/admin/examinator/api/examination/' + currentExamination,
        contentType: 'application/json',
        success: function (data, status) {
            CKEDITOR.instances['editor2'].setData(data.result);
        }
    });
}

$("#finish-test").click(function(){
    if (currentExamination != null){
     loadExaminationToModal(currentExamination);
    }
});

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

//kết thúc examination
function saveExamination() {
    $.ajax({
        type: "POST",
        data : {
            ex_id: currentExamination,
            content: "OK"
        },
        url: '/admin/examinator/api/examination',
        success: function (data, status) {
            examTable.destroy();
            $('#exams-container').empty();
            loadExaminations(currentExaminator);
            $('#exampleModalExamFinish').modal('hide');
            $("#examination-today").click();
            //send message để bên doctor update lại examination
            sendBroadcast(currentExaminator,"LOADEXAMINATIONTOAPPOINTMENT",currentDoctor);
        }
    });
}

//upload nhiều ảnh cùng lúc
function ajaxSubmitForm() {
    if (allFilesIsEmpty()){
        setResult(" You need at least one file to upload","text-danger");
        return false;
    } else {
                // Get form
                $("#ex_id").val(currentExamination);
                var form = $('#fileUploadForm')[0];

                var data = new FormData(form);

                // $("#submitButton").prop("disabled", true);

                $.ajax({
                    type: "POST",
                    enctype: 'multipart/form-data',
                    url: "/admin/api/uploadMultiFiles/examination",
                    data: data,

                    // prevent jQuery from automatically transforming the data into a query string
                    processData: false,
                    contentType: false,
                    cache: false,
                    timeout: 1000000,
                    success: function(data, textStatus, jqXHR) {
                        setResult(" Upload success","text-success");

                        console.log("SUCCESS : ", data);
                        // $("#submitButton").prop("disabled", false);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        $("#result").html(jqXHR.responseText);
                        console.log("ERROR : ", jqXHR.responseText);
                    }
                });
            }
}

function setResult(text, status) {
    $("#result").removeClass();
    $("#result").addClass(status);
    $("#result").hide();
    $("#result").text(text);
    $('#result').fadeIn(500);
    $('#result').delay(2000).fadeOut(400);
}

$('.files').on('change', function() {
    if (!fileSizeOK(this)){
        setResult(" File must be between less than 4 MB to upload","text-danger");
        this.value = '';
    }
    if (!filesNameAreUnique(getAllFilename())) {
        setResult(" You have duplicated files","text-danger");
        this.value = '';
    }
});
// function allFilesSizeIsOK() {
//     var files = (".files");
//     var checked = true;
//     $(files).each(function () {
//         if (!fileSizeOK(this)){
//             checked = false;
//             return false;
//         }
//     });
//     // console.log(checked);
//     return checked;
// }

function allFilesIsEmpty() {
    var files = (".files");
    var checked = true;
    $(files).each(function () {
        if (!fileIsEmpty(this)){
            checked = false;
            return false;
        }
    });
    // console.log(checked);
    return checked;
}
function fileSizeOK(input_file) {
    const size =
        (input_file.files[0].size / 1024 / 1024).toFixed(2);
    if (size > 4) {
        return false;
    } else {
        return true;
    }
}
function fileIsEmpty(name) {
    if ($(name).get(0).files.length === 0) {
        console.log("No files selected.");
        return true;
    } else {
        return false;
    }
}

function getAllFilename() {
    var items = [];
    var files = $(".files").get();
    for (var i =0;i<files.length;i++){
        if (files[i].files[0]!=null){
            items.push(files[i].files[0].name);
        }
    }
    return items;
}

function filesNameAreUnique(items) {
    var uniqueItems = Array.from(new Set(items));
    if (items.length == uniqueItems.length){
        return true;
    } else {
        return false;
    }
}




//UTILITY
function getElementByXpath(path) {
    return document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
Date.prototype.toShortFormat = function () {

    var month_names = ["Jan", "Feb", "Mar",
        "Apr", "May", "Jun",
        "Jul", "Aug", "Sep",
        "Oct", "Nov", "Dec"];

    var day = this.getDate();
    var month_index = this.getMonth();
    var year = this.getFullYear();

    return "" + day + " " + month_names[month_index] + " " + year;
}

Date.prototype.addHours = function(h) {
    this.setTime(this.getTime() + (h*60*60*1000));
    return this;
}

function changeSpringDateToJSDate(springDateString) {
    var dateStr = JSON.stringify(springDateString).substr(1, 10);
    var date = new Date(dateStr.replace(/-/g, '\/'));
    date.addHours(8);
    return date;
}

function calculateAge(springDateString) {
    return new Date().getFullYear() - changeSpringDateToJSDate(springDateString).getFullYear();
}



//LIBRARY
function callCKEditor(){
    if($("#" + "editor1").length != 0  &&
        $("#" + "editor2").length != 0 ) {
        CKEDITOR.replace('editor1', {
            height: 260,
            width: 700,
        });
        CKEDITOR.replace('editor2', {
            height: 260,
            width: 700,
        });
    }
}

$('#carouselExampleIndicators').carousel({
    interval: false
});






// PUSH NOTIFICATION
var header = $('#header');
var content = $('#content');
var input = $('#input');
var status = $('#status');
var myName = false;
var author = null;
var logged = false;
var socket = atmosphere;
var subSocket;
var transport = 'websocket';

// We are now ready to cut the request
var request = {
    url : '/chat',
    contentType : "application/json",
    logLevel : 'debug',
    transport : transport,
    trackMessageLength : true,
    reconnectInterval : 5000
};

request.onOpen = function(response) {
    console.log('Atmosphere connected using ' + response.transport);
    transport = response.transport;

    // Carry the UUID. This is required if you want to call
    // subscribe(request) again.
    request.uuid = response.request.uuid;
};

request.onClientTimeout = function(r) {
    console.log('Client closed the connection after a timeout. Reconnecting in '
        + request.reconnectInterval);
    subSocket
        .push(atmosphere.util
            .stringifyJSON({
                author : author,
                message : 'is inactive and closed the connection. Will reconnect in '
                    + request.reconnectInterval
            }));
    input.attr('disabled', 'disabled');
    setTimeout(function() {
        subSocket = socket.subscribe(request);
    }, request.reconnectInterval);
};

request.onReopen = function(response) {
    console.log('Atmosphere re-connected using ' + response.transport);
};

// For demonstration of how you can customize the fallbackTransport using
// the onTransportFailure function
request.onTransportFailure = function(errorMsg, request) {
    atmosphere.util.info(errorMsg);
    console.log('Atmosphere Chat. Default transport is WebSocket, fallback is '
        + request.fallbackTransport);

    request.fallbackTransport = "long-polling";
};

request.onClose = function(response) {
    console.log('Server closed the connection after a timeout');
    if (subSocket) {
        subSocket.push(JSON.stringify({
            author : author,
            message : 'disconnecting'
        }));
    }
    input.attr('disabled', 'disabled');
};

request.onError = function(response) {
    console.log('Sorry, but there\'s some problem with your '
        + 'socket or the server is down');
    logged = false;
};

request.onReconnect = function(request, response) {
    console.log('Connection lost, trying to reconnect. Trying to reconnect '
        + request.reconnectInterval);
};

//react dựa trên nội dung message
request.onMessage = function(response) {

    var message = response.responseBody;
    try {
        var json = JSON.parse(message);
    } catch (e) {
        console.log('This doesn\'t look like a valid JSON: ', message);
        return;
    }

    if (json.receiver == currentExaminator && json.message == "LOADEXAMINATIONS"){
            loadExaminations(currentExaminator);
    } else if (json.receiver == currentDoctor  && json.message == "LOADEXAMINATIONTOAPPOINTMENT") {
            loadExaminationToAppointment(currentAppointment);
            loadExaminationsToModal();
    }
};

subSocket = socket.subscribe(request);

//gửi message đến tất cả các client
function sendBroadcast(sender, message, receiver){
    subSocket.push(JSON.stringify({
        sender : sender,
        receiver: receiver,
        message : message
     }));
}