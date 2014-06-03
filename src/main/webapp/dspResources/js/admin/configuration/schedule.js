

require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");

        showScheduleForm();
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }

        //function that will get the field mappings for the selected transport method
        $('.changeTransportMethod').click(function() {
            var selTransportMethod = $('#transportMethod').val();

            if (selTransportMethod === "") {
                $('#transportMethodDiv').addClass("has-error");
            }
            else {
                window.location.href = 'scheduling?i=' + selTransportMethod;
            }
        });

        //Toggle Scheduling Specs when process configuration is changed
        $('.processMethod').change(function() {
            showScheduleForm();
        });

        //Toggle check how often
        $('.processingType').change(function() {

            if ($(this).val() === "1") {
                $('#processingTimeDiv').show();
                $('#newfilecheckDiv').hide();
            }
            else {
                $('#newfilecheckDiv').show();
                $('#processingTimeDiv').hide();
            }

        });

        //This function will save the schedule mappings
        $('#saveDetails').click(function() {
            $('#schedulingSpecs').submit();
        });
        
        
        $('#next').click(function() {
            $('#action').val("next");
            $('#schedulingSpecs').submit();
        });
        
    });
})



function showScheduleForm() {
    var type = $('.processMethod:checked').val();
    $('#type').val(type);
    if (type !== "1" && type !== "5") {
        $('#specs').show();

        //Hide all fields
        $('.specFormFields').hide();

        //Daily
        if (type === "2") {
            $('#processingTypeDiv').show();

            if ($('.processingType:checked').val() === "1") {
                $('#processingTimeDiv').show();
            }
            else {
                $('#newfilecheckDiv').show();
            }
        }

        //Weekly
        if (type === "3") {
            $('#processingDayDiv').show();
            $('#processingTimeDiv').show();
        }

        //Monthly
        if (type === "4") {
            $('#processingTimeDiv').show();
        }
    }
    else {
        $('#specs').hide();
    }
}

