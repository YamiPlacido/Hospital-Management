$(document).ready(function () {
    // currentExaminator = $('#userSessionId').val();
    // currentExaminator = 9;
    loadExaminations(window.currentExaminator);
    callCKEditor();
});

// var currentExaminator;
var currentExamination;
var currentAppointment;
var examTable;
//value on backend
var finishedExamList = [];
var currentSender;
var currentExaminationStage;
const CONTEXTPATH = "http://localhost:8080/";

$("#examination-perform").click(
    function (event) {
        event.preventDefault();
        if (currentExamination == null) {
            alert("Please select one exam to perform.");
            return false;
        } else {
            this.click();
            return true;
        }
    }
);

function loadExaminations(examinator_id) {
    $.ajax({
        type: "GET",
        url: '/admin/examinator/api/examinations/unfinished/' + examinator_id,
        // url: '/admin/examinator/api/examinations/' + examinator_id,
        contentType: 'application/json',
        success: function (data, status) {
            $('#exams-container').empty();
            var insert_content;
            jQuery.each(data, function (i, val) {
                var age = calculateAge(val.patient.dob);
                var date = changeSpringDateToJSDate(val.date).toShortFormat();
                insert_content = '<tr>';
                insert_content += '<td>' + val.id + '</td>';
                insert_content += '<td><a href="#" onclick="performExamination(' + val.id + ',' + val.appointment.employee.employeeId + ',\'' + String(val.stage) +'\')">' + val.patient.name + '</a></td>';
                insert_content += '<td>' + age + '</td>';
                insert_content += '<td>' + val.examinationType.name + '</td>';
                insert_content += '<td>' + date + '</td>';
                insert_content += '<td>10:00am - 11:00am</td>';
                if (val.stage == "CREATED") {
                    insert_content += '<td><span class="custom-badge status-red">' + val.stage + '</span></td>';
                } else if (val.stage == "DOING") {
                    insert_content += '<td><span class="custom-badge status-orange">' + val.stage + '</span></td>';
                } else if (val.stage == "FINISHED") {
                    insert_content += '<td><span class="custom-badge status-green">' + val.stage + '</span></td>';
                }
                insert_content += '</tr>';

                $("#exams-container")
                    .append(insert_content);
                insert_content = '';
            });
            examTable = $('#exam-datatables').DataTable({
                destroy: true,
                retrieve: true,
                "fnDrawCallback": function (oSettings) {
                    // if ($('#exams-container tr').length < 11) {
                    //     $('.dataTables_paginate').hide();
                    // }
                }
            });
        }
    });
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
                    id: val.examinationType.id,
                    name: val.examinationType.name,
                    stage: val.stage
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

//examinator: chuyển sang tab perform
function performExamination(id, doctor_id,stage) {
    if (currentExamination == null && id == null) {
        alert("Please chose one examination you want to perform.");
    } else if (id != null) {
        currentExamination = id;
    }
    currentSender = doctor_id;
    currentExaminationStage = stage;
    // console.log(currentExamination);
    getExaminationById(currentExamination);
    loadSymptomTypeTwoToExam();
    if (currentExaminationStage != "DOING" && currentExaminationStage != "FINISHED"){
        updateExaminationStageToDoing();
    }
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
            $("#patient-age").text("Age: " + calculateAge(data.patient.dob));
            // console.log(data.patient.patientId);
            $("#patient-id").text("Patient Id: " + data.patient.patientId);
            $("#patient-phone").text(data.patient.phone);
            $("#patient-email").text(data.patient.email);
            $("#patient-address").text(data.patient.address);
            if (data.patient.gender == "f") {
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
            // currentExaminationStage = data.stage;
            //click vào tab
            $("#examination-perform").click();
        }
    });
}

function updateExaminationStageToDoing() {
    $.ajax({
        type: "POST",
        data: {
            ex_id: currentExamination,
            stage: "DOING"
        },
        url: '/admin/examinator/api/examination/stage',
        // contentType: 'application/json',
        success: function (data, status) {
            loadExaminations(window.currentExaminator);
            //send message để bên doctor update lại examination
            sendBroadcast("ONGOING", window.currentExaminator, "Your examination is on going", currentSender);
        }
    });
}

function loadSymptomTypeTwoToExam() {

    $.ajax({
        type: "GET",
        url: '/system/symptoms/type_two/',
        contentType: 'application/json',
        success: function (data, status) {
            $("#symptom-type-two").empty();
            $("#symptom-type-two")
                .append('<option value="0" selected>Null</option>');
            jQuery.each(data, function (i, val) {
                $("#symptom-type-two")
                    .append('<option value="' + val.symptomId + '">' + val.name + '</option>');
            });
        }
    });
}


//examinator: lưu lại content
function saveContentOfExam() {
    var content = CKEDITOR.instances['editor1'].getData();
    if (content.length < 5) {
        createTopCenterWarrning("You need to enter at least 5 character.", "danger")
    } else if (content.length > 255) {
        createTopCenterWarrning("Your text is longer than 255 character.", "danger")
    } else {
        $.ajax({
            type: "POST",
            data: {
                ex_id: currentExamination,
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
}

//lưu lại result
function saveResultOfExam() {
    var content = CKEDITOR.instances['editor2'].getData();
    if (content.length < 5) {
        createTopCenterWarrning("You need to enter at least 5 character.", "danger")
    } else if (content.length > 255) {
        createTopCenterWarrning("Your text is longer than 255 character.", "danger")
    } else {
        $.ajax({
            type: "POST",
            data: {
                ex_id: currentExamination,
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

$("#finish-test").click(function () {
    if (currentExamination != null) {
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
            $("#examination-detail-patient-age").text("Age: " + calculateAge(data.patient.dob));
            $("#examination-detail-patient-id").text("Patient Id: " + data.patient.patientId);
            if (data.patient.gender == "f") {
                $("#examination-detail-patient-gender").text("Female");
            } else {
                $("#examination-detail-patient-gender").text("Male");
            }
            $("#examination-detail-exam-name").text(data.examinationType.name);
            $("#examination-detail-examinator").text("Implement by: " + data.examinator.firstName + " " + data.examinator.lastName);
            $("#examination-detail-exam-id").text(data.id);
            $("#examination-detail-exam-time").text(new Date(data.date).toUTCString());
            $("#examination-detail-exam-content").html(data.content);
            $("#examination-detail-exam-result").html(data.result);

            $("#carousel-container").empty();
            $("#carousel-indicators").empty();
            if (data.image_path_1 != null && data.image_path_1 != "") {
                $("#carousel-indicators").append(
                    '<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>'
                )
                $("#carousel-container").append(
                    '<div class="carousel-item active">\n' +
                    '<img class="d-block w-100" src="/upload/exam/' + data.image_path_1 + '" alt="First slide">\n' +
                    '</div>'
                )
            }
            if (data.image_path_2 != null && data.image_path_2 != "") {
                $("#carousel-indicators").append(
                    '<li data-target="#carouselExampleIndicators" data-slide-to="0"></li>'
                )
                $("#carousel-container").append(
                    '<div class="carousel-item">\n' +
                    '<img class="d-block w-100" src="/upload/exam/' + data.image_path_2 + '" alt="First slide">\n' +
                    '</div>'
                )
            }
            if (data.image_path_3 != null && data.image_path_3 != "") {
                $("#carousel-indicators").append(
                    '<li data-target="#carouselExampleIndicators" data-slide-to="0"></li>'
                )
                $("#carousel-container").append(
                    '<div class="carousel-item">\n' +
                    '<img class="d-block w-100" src="/upload/exam/' + data.image_path_3 + '" alt="First slide">\n' +
                    '</div>'
                )
            }
            // $("#examination-detail-examination-perform").click();
        }
    });
}


function createPdfResult() {
    var content = getPlainTextFromContent();
    var result = getPlainTextFromResult();
    $.ajax({
        type: "POST",
        data: {
            ex_id: currentExamination,
            content: content,
            result: result
        },
        url: '/admin/examinator/api/examination/create_pdf',
        success: function (data, status) {
            openInNewTab(CONTEXTPATH + data);
        }
    });
}

//kết thúc examination
function finishExamination() {
    $.ajax({
        type: "POST",
        data: {
            ex_id: currentExamination,
            content: "OK"
        },
        url: '/admin/examinator/api/examination',
        success: function (data, status) {
            examTable.destroy();
            $('#exams-container').empty();
            loadExaminations(window.currentExaminator);
            $('#exampleModalExamFinish').modal('hide');
            $("#examination-today").click();
            //send message để bên doctor update lại examination
            sendBroadcast("FINISHED", window.currentExaminator, "Your examination has finish", currentSender);
        }
    });
}

//upload nhiều ảnh cùng lúc
function ajaxSubmitForm() {
    if (allFilesIsEmpty()) {
        setResult("result", " You need at least one file to upload", "text-danger");
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
            success: function (data, textStatus, jqXHR) {
                setResult("result", " Upload success", "text-success");

                console.log("SUCCESS : ", data);
                // $("#submitButton").prop("disabled", false);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $("#result").html(jqXHR.responseText);
                console.log("ERROR : ", jqXHR.responseText);
            }
        });
    }
}

function setResult(id, text, status) {
    var des = $("#" + id);
    console.log(des);
    des.removeClass();
    des.addClass(status);
    des.hide();
    des.text(text);
    des.fadeIn(500);
    des.delay(2000).fadeOut(400);
}

$('.files').on('change', function () {
    if (!fileSizeOK(this)) {
        setResult("result", " File must be between less than 4 MB to upload", "text-danger");
        this.value = '';
    }
    if (!filesNameAreUnique(getAllFilename())) {
        setResult("result", " You have duplicated files", "text-danger");
        this.value = '';
    }
});

function allFilesIsEmpty() {
    var files = (".files");
    var checked = true;
    $(files).each(function () {
        if (!fileIsEmpty(this)) {
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
    for (var i = 0; i < files.length; i++) {
        if (files[i].files[0] != null) {
            items.push(files[i].files[0].name);
        }
    }
    return items;
}

function filesNameAreUnique(items) {
    var uniqueItems = Array.from(new Set(items));
    if (items.length == uniqueItems.length) {
        return true;
    } else {
        return false;
    }
}

function getPlainTextFromContent() {
    var plain_text = $("#exam-content").text();
    return plain_text;
}

function getPlainTextFromResult() {
    var plain_text = $("#exam-result").text();
    return plain_text;
}