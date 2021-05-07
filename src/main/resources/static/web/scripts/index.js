var mainVue = new Vue({
    el:"#app",
    data:{
        loginUser: '28313880',
        loginPass: null,
        teachers:[],
        subjects:[],
        user : null
    },

    methods: {
        login: function(){
            if((mainVue.loginUser && mainVue.loginPass) && (mainVue.loginUser.length !=0 || mainVue.loginPass.length !=0 )){
                $.post("/api/login", { username: mainVue.loginUser, password: mainVue.loginPass })
                .done(function() { location.reload(); })
                .fail(function() { alert("No user found") })
            } else
                alert("You must enter your DNI and File Number");
        },

        logout: function(){
            $.post("/api/logout").done(function() {location.reload()})
        },

        reload_url : function (url){
            location.href = url;
        },
        openTeacherControlModal : function (teacher){

            $('#txtTeacherDNI').val(teacher ? teacher.dni : '');
            $('#txtTeacherName').val(teacher ? teacher.name : '');
            $('#txtTeacherLastName').val(teacher ? teacher.last_name : '');
            $('#hidIdTeacher').val(teacher ? teacher.id : 0);
            $('#teacherControlModal').modal('show');

        },
        openModal : function (modalId){
            $("#" + modalId + " .mcList").show();
            $("#" + modalId + " .mcForm").hide();
            $("#" + modalId).modal('show');
        },
        showForm : function (modalId, entity){
            $('#' + modalId + ' .mcList').hide();
            $('#' + modalId + ' .mcForm .modal-title').text(entity ? "Edit" : "Create");
            if(modalId == "teacherModal")
            {
                $('#' + modalId + ' .mcForm .modal-title').append(" Teacher");
                $('#txtTeacherDNI').val(entity ? entity.dni : '');
                $('#txtTeacherName').val(entity ? entity.name : '');
                $('#txtTeacherLastName').val(entity ? entity.last_name : '');
                $('#hidIdTeacher').val(entity ? entity.id : 0);
                $('#teacherControlModal').modal('show');
            }
            else if(modalId == "subjectModal")
            {
                 $('#' + modalId + ' .modal-title').append(" Subject");
                 $('#txtSubjectName').val(entity ? entity.name : '');
                 $('#selIdTeacher').val(entity && entity.teacher ? entity.teacher.id : 0);
                 $('#txtSubjectTime').val(entity ? entity.time : '');
                 $('#txtSubjectAvailability').val(entity ? entity.availability : '');
                 $('#hidIdSubject').val(entity ? entity.id : 0);
            }
            $("#" + modalId + " .mcForm").show();
        },
        hideForm : function (modalId){

            if(modalId == "teacherModal")
            {
                $('#txtTeacherDNI').val('');
                $('#txtTeacherName').val('');
                $('#txtTeacherLastName').val('');
                $('#hidIdTeacher').val(0);
            }
            else if(modalId == "subjectModal")
            {
                 $('#txtSubjectName').val('');
                 $('#selIdTeacher').val(0);
                 $('#txtSubjectTime').val(0);
                 $('#txtSubjectAvailability').val(0);
                 $('#hidIdSubject').val();
            }
            $("#" + modalId + " .mcForm").hide();
            $("#" + modalId + " .mcList").show();
        },

        deleteTeacher : function (teacher){

            $.ajax({
                url: "/api/teachers/" + teacher.id,
                type: "DELETE"
            })
            .done(function (response) {
                alert("Deleted!");
                let index = mainVue.teachers.findIndex(x => x.id == response.id)
                if(index >= 0)
                    mainVue.teachers.splice(index, 1);
            })
            .fail(function (error) {
                alert("Error: " + JSON.parse(error.responseText).error);
            });
        },
        submitTeacher: function (){

            var oNewEntity = {
                dni: $('#txtTeacherDNI').val(),
                name: $('#txtTeacherName').val(),
                last_name: $('#txtTeacherLastName').val(),
                id: $('#hidIdTeacher').val(),
                active: true
            }

            //if(!oNewEntity.dni || ...) //TODO:validaciones

            $.post({
                url: "/api/teachers/" + oNewEntity.id,
                data: JSON.stringify(oNewEntity),
                dataType: "text",
                contentType: "application/json"
            })
            .done(function (response) {
                alert("Success");

                oNewEntity.id = JSON.parse(response).id;
                let index = mainVue.teachers.findIndex(x => x.id == oNewEntity.id)
                if(index >= 0)
                    mainVue.teachers.splice(index, 1);

                mainVue.teachers.push(oNewEntity);
                mainVue.hideForm('teacherModal');
            })
            .fail(function (error) {
                alert("Error: " + JSON.parse(error.responseText).error);
            });
        },

        deleteSubject : function (subject){

            $.ajax({
                url: "/api/subjects/" + subject.id,
                type: "DELETE"
            })
            .done(function (response) {
                alert("Deleted!");
                let index = mainVue.subjects.findIndex(x => x.id == response.id)
                if(index >= 0)
                    mainVue.subjects.splice(index, 1);
            })
            .fail(function (error) {
                alert("Error: " + JSON.parse(error.responseText).error);
            });
        },
        submitSubject: function (){

            var oNewEntity = {
                name: $('#txtSubjectName').val(),
                teacher: { id : $('#selIdTeacher').val() },
                time: $('#txtSubjectTime').val(),
                availability: $('#txtSubjectAvailability').val(),
                id: $('#hidIdSubject').val(),
                active: true
            }

            //if(!oNewEntity.dni || ...) //TODO:validaciones

            $.post({
                url: "/api/subjects/" + oNewEntity.id,
                data: JSON.stringify(oNewEntity),
                dataType: "text",
                contentType: "application/json"
            })
            .done(function (response) {
                alert("Success");

                oNewEntity = JSON.parse(response).entityDTO;
                let index = mainVue.subjects.findIndex(x => x.id == oNewEntity.id)
                if(index >= 0)
                    mainVue.subjects.splice(index, 1);

                mainVue.subjects.push(oNewEntity);
                mainVue.hideForm('subjectModal');
            })
            .fail(function (error) {
                alert("Error: " + JSON.parse(error.responseText).error);
            });
        },

        subscribe: function(idSubject, isSubscribing)
        {
            $.post({
                url: "/api/subjects/subscribe/" + (isSubscribing ? 1 : -1) * idSubject,
                dataType: "text",
                contentType: "application/json"
            })
            .done(function (response) {

                let oNewEntity = JSON.parse(response).entityDTO;
                if(isSubscribing)
                    mainVue.user.subscriptions.push(oNewEntity);
                else
                {
                    let index = mainVue.user.subscriptions.findIndex(x => x.subject.id == idSubject)
                    if(index >= 0)
                        mainVue.user.subscriptions.splice(index, 1);
                }
            })
            .fail(function (error) {
                alert("Error: " + JSON.parse(error.responseText).error);
            });
        }


    }
});


fetch('/api/getInfo')
.then(function(response){
    return response.json()
})
.then(function(json){
    mainVue.teachers = json.teachers;
    mainVue.subjects = json.subjects;
    mainVue.user = json.user;
    $("#loginUser").val("29591307");
});

