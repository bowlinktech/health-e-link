

require(['./main'], function () {
    require(['jquery'], function($) {
        $("input:text,form").attr("autocomplete", "off");
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert-success').length > 0) {
            $('.alert-success').delay(2000).fadeOut(1000);
        }

        //If any field changes need to show the message in red that nothign
        //will be saved unless teh "Saved" button is pressed
        $(document).on('change', '.formField', function() {
            $('#saveMsgDiv').show();
        });

        //function that will get the field mappings for the selected transport method
        $('.changeTransportMethod').click(function() {
            var selTransportMethod = $('#transportMethod').val();

            if (selTransportMethod == "") {
                $('#transportMethodDiv').addClass("has-error");
            }
            else {
                window.location.href = 'mappings?i=' + selTransportMethod;
            }
        });


        //This function will save the messgae type field mappings
        $('#saveDetails').click(function(event) {
            $('#action').val('save');

            //Need to make sure all required fields are marked if empty.
            var hasErrors = 0;
            $('.alert-danger').hide();

            if (hasErrors == 0) {
                
                var formData = $("#formFields").serialize();
                
                $.ajax({
                    url: 'saveFields',
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function(data) {
                        $('.fieldsUpdated').show();
                        $('.alert').delay(2000).fadeOut(1000);
                    }
                });
                event.preventDefault();
                return false;
                
            }
        });

        $('#next').click(function(event) {
            $('#action').val('next');
            $('.alert-danger').hide();

            var hasErrors = 0;

            if (hasErrors == 0) {
                var formData = $("#formFields").serialize();
                
                $.ajax({
                    url: 'saveFields',
                    data: formData,
                    type: "POST",
                    async: false,
                    success: function(data) {
                       if(data == 1) {
                           window.location.href='translations';
                       }
                       else {
                           window.location.href='ERGCustomize';
                       }
                       
                    }
                });
                event.preventDefault();
                return false;
            }
        });


        //Clicking the "Meets Standard" button will preselect the matching
        //field select box to the appropiate template field
        $(document).on('click', '#meetsStandard', function() {
            var fieldNo = null;
            var optionText = null;
            $('#saveMsgDiv').show();

            $('.uFieldRow').each(function() {
                fieldNo = $(this).attr('rel');
                $('#matchingField_' + fieldNo + ' > option').each(function() {
                    if ($(this).text().indexOf(fieldNo+" - ") != -1) {
                        $('#matchingField_' + fieldNo).val($(this).val());
                        return false;
                    }
                });
            });
            //<span class='glyphicon glyphicon-ok'></span> 
            $(this).html("Clear Matching Fields");
            $(this).attr("data-original-title","Click here to clear the matching fields.");
            $(this).attr('id', 'clearFields');
        });

        //Clicking the "Clear Fields" button will unselect the matching
        //field select box.
        $(document).on('click', '#clearFields', function() {
            $('#saveMsgDiv').show();

            $('.uFieldRow').each(function() {
                fieldNo = $(this).attr('rel');
                $('#matchingField_' + fieldNo).val("0");
            });

            $(this).html("Meets Standard");
            $(this).attr('id', 'meetsStandard');
            $(this).attr("data-original-title","Click here to match to the starndard fields.");
        });
        
        
    });
});



