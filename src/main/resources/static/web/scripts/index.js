var mainVue = new Vue({
    el:"#app",
    data:{
        loginUser: null,
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
        deleteTeacher : function (teacher){

                alert('Confirma borrar teacher' + teacher.dni + '?');
              //teacherControlModal
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

