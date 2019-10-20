$(document).ready(function () {
    $("#load-more").click();
    currentDoctor = 1;
    currentExaminator = 9;
    loadAppointments(currentDoctor);
    loadExaminations(currentExaminator);
    callCKEditor();
    //ko chuyen tab neu chua co appointment
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
                insert_content += '<a class="avatar" href="doctor/' + val.employeeId
                    + '"><img alt="" src="/upload/employee/'
                    + val.imagePath
                    + '"></a>';
                insert_content += '</div>';
                insert_content += '<div class="dropdown profile-action">';
                insert_content += '<a href="#" class="action-icon dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-ellipsis-v"></i></a>';
                insert_content += '<div class="dropdown-menu dropdown-menu-right">';
                insert_content += '<a class="dropdown-item" href="/doctor/edit/' + val.employeeId + '"><i class="fa fa-pencil m-r-5"></i> Edit</a>';
                insert_content += '<a class="dropdown-item" href="#" data-toggle="modal" data-target="#delete_doctor"><i class="fa fa-trash-o m-r-5"></i> Delete</a>';
                insert_content += '</div>';
                insert_content += '</div>';
                insert_content += '<h4 class="doctor-name text-ellipsis"><a href="profile.html">'
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

$("#symptom-edit").click(
    loadSymptomsToModal()
);

$("#examination-edit").click(
    loadExaminationsToModal()
);

// $("#add-symptom").click(getAllSelectedItems());

function loadAppointments(doctor_id) {
    $.ajax({
        type: "GET",
        url: '/admin/doctor/api/appointments/' + doctor_id,
        contentType: 'application/json',
        success: function (data, status) {
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
                insert_content += '<td><span class="custom-badge status-red">' + val.status + '</span></td>';
                insert_content += '</tr>';

                $("#appointment-container")
                    .append(insert_content);
                insert_content = '';
            });
            // $('#appointment-container').empty();
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
                // destroy: true,
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

function loadExaminationsToModal() { //ok
    $.ajax({
        type: "GET",
        url: '/system/examinations',
        contentType: 'application/json',
        success: function (data, status) {
            jQuery.each(data, function (i, val) {
                $('#select-examination').multiSelect('addOption', {value: val.id, text: val.name, index: i});
            });
        }
    });
}



function saveSymptomToAppointment(appId) { //ok
    if (appId  ==0){
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

function saveExaminationToAppointment(appId) {
    if (appId  ==0){
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
        }
    });
}

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
                symptomValueSelected.push(val.symptomId);
            });
        }
    });
}
$("#symptom-edit").click(function(){
    console.log(symptomValueSelected);
    $('#select-symptom').multiSelect('select', symptomValueSelected);
});

//load examination list
function loadExaminationToAppointment(app_id) {
    $.ajax({
        type: "GET",
        url: '/admin/doctor/api/appointments/examination/' + app_id,
        contentType: 'application/json',
        success: function (data, status) {
            examinationValueSelected = [];
            $("#examinations").empty();
            jQuery.each(data, function (i, val) {
                $("#examinations")
                    .append('<li>'+ val.examinationType.name+'  <a href="#" class="badge badge-danger">CREATED</a></li>');
                examinationValueSelected.push(val.examinationType.id.toString());
            });
        }
    });
}

$("#examination-edit").click(function(){
    console.log(examinationValueSelected);
    $('#select-examination').multiSelect('select', examinationValueSelected);
    // $('#select-examination').multiSelect('select_all');
});
function performAppointment(id) {
    if (currentAppointment == null && id == null){
        alert("Please chose one appointment you want to perform.");
    } else if (id != null) {
        currentAppointment = id;
    }
    // console.log(currentExamination);
    loadSymptomToAppointment(currentAppointment);
    loadExaminationToAppointment(currentAppointment);
    getAppointmentById(currentAppointment);
}

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

function performExamination(id) {
    if (currentExamination == null && id == null){
        alert("Please chose one examination you want to perform.");
    } else if (id != null) {
        currentExamination = id;
    }
    // console.log(currentExamination);
    getExaminationById(currentExamination);
}

function getExaminationById(id) {
    $.ajax({
        type: "GET",
        url: '/admin/examinator/api/examination/' + id,
        contentType: 'application/json',
        success: function (data, status) {
            // alert(id);
            $("#patient-name").text(data.patient.name);
            $("#patient-age").text("Age: "+calculateAge(data.patient.dob));
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
            $("#examination-perform").click();
        }
    });
}

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

function ajaxSubmitForm() {
    //check empty
    // getAllFilename();

    if (!checkMultiUpload()){
        setResult(" You need at least one file to upload","text-danger");
    } else {
        if (!checkUniqueFilename(getAllFilename())){
            setResult(" You have duplicated files","text-danger");
            return false;
        } else {
            // Get form
            $("#ex_id").val(currentExamination);
            var form = $('#fileUploadForm')[0];

            var data = new FormData(form);

            $("#submitButton").prop("disabled", true);

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
                    $("#submitButton").prop("disabled", false);
                    // $('#fileUploadForm')[0].reset();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $("#result").html(jqXHR.responseText);
                    console.log("ERROR : ", jqXHR.responseText);
                    $("#submitButton").prop("disabled", false);

                }
            });
        }

    }
}

$("#finish-test").click(function(){
    if (currentExamination != null){
     loadExaminationToModal(currentExamination);
    }
});

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
        }
    });
}

function setResult(text, status) {
    $("#result").removeClass();
    $("#result").addClass(status);
    $("#result").hide();
    $("#result").text(text);
    $('#result').fadeIn(500);
    $('#result').delay(2000).fadeOut(400);
}

function checkUploadEmpty(name) {
    if ($(name).get(0).files.length === 0) {
        console.log("No files selected.");
        return true;
    } else {
        return false;
    }
}

function checkMultiUpload() {
    var files = (".files");
    var checked = false;
    $(files).each(function () {
        if (!checkUploadEmpty(this)){
            checked = true;
            return false;
        }
    });
    // console.log(checked);
    return checked;
}

function getAllFilename() {
    var items = [];
    var files = $(".files").get();
    for (var i =0;i<files.length;i++){
        if (files[i].files[0]!=null){
            items.push(files[i].files[0].name);
            // console.log(files[i].files[0].name);
        }
    }
    // console.log(files);
    // console.log(names);
    return items;
}

function checkUniqueFilename(items) {
    var uniqueItems = Array.from(new Set(items));
    if (items.length == uniqueItems.length){
        return true;
    } else {
        return false;
    }
}



//utility
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
    // console.log(dateStr);
    // console.log(date);
    // date.setDate(date.getDate() + 1);
    return date;
}

function calculateAge(springDateString) {
    return new Date().getFullYear() - changeSpringDateToJSDate(springDateString).getFullYear();
}



//library
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
