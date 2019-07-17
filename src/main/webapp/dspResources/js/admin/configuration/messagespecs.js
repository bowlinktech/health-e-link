
require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");
	
	$(document).on('click', '.createDataTranslationDownload', function() {
           $.ajax({
                url: '/administrator/configurations/createDataTranslationDownload',
                data: {
		   'configId':$(this).attr('rel')
		},
                type: "GET",
                success: function(data) {
                    $("#dtDownloadModal").html(data);
                }
            });
        });
	
	$(document).on('click', '#generateDTButton', function() {
	    
	    var errorFound = 0;
	    
	   //Makes sure name is entered and entity is selected
	   if($('#fileName').val() === "") {
	       $('#dtnameDiv').addClass("has-error");
	       errorFound = 1;
	   }
	   
	   if(errorFound == 0) {
	       $.ajax({
		    url: '/administrator/configurations/dataTranslationsDownload',
		    data: {
			'configId':$(this).attr('rel'),
			'fileName': $('#fileName').val()
		    },
		    type: "GET",
		    dataType : 'text',
		    contentType : 'application/json;charset=UTF-8',
		    success: function(data) {
			if(data !== '') {
			    window.location.href = '/administrator/configurations/downloadDTCWFile/'+ data;
			    $('#successMsg').show();
			    //$('#dtDownloadModal').modal('toggle');
			}
			else {
			    $('#errorMsg').show();
			}
		    }
		});
	   }
           
        });
        
        //This function will save the messgae type field mappings
        $('#saveDetails').click(function() {
            $('#action').val('save');

            //Need to make sure all required fields are marked if empty.
            var hasErrors = 0;
            hasErrors = checkFormFields();

            if (hasErrors == 0) { 
                $('#messageSpecs').submit();
            }
        });

        $('#next').click(function(event) {
            $('#action').val('next');

            var hasErrors = 0;
            hasErrors = checkFormFields();

            if (hasErrors == 0) {
                $('#messageSpecs').submit();
            }
        });
    });
});



function checkFormFields() {
    var hasErrors = 0;
    
    //Remove all has-error class
    $('div.form-group').removeClass("has-error");
    $('span.control-label').removeClass("has-error");
    $('span.control-label').html("");
    
    //Make sure at least one reportable field is selected
    var rptField1 = $('#rptField1').val();
    var rptField2 = $('#rptField2').val();
    var rptField3 = $('#rptField3').val();
    var rptField4 = $('#rptField4').val();
    
    if(rptField1 == 0 && rptField2 == 0 && rptField3 == 0 && rptField4 == 0) {
        $('.rtpField').addClass("has-error");
        $('#rptFieldMsg').addClass("has-error");
        $('#rptFieldMsg').html('At least one reportable field must be selected.<br />');
        hasErrors = 1;
    }
    
    /* Check to make sure there are different selected fields */
    if(hasErrors == 0 && rptField1 > 0 && (rptField1 == rptField2 || rptField1 == rptField3 || rptField1 == rptField4)) {
        $('.rtpField').addClass("has-error");
        $('#rptFieldMsg').addClass("has-error");
        $('#rptFieldMsg').html('All reportable fields must be different.<br />');
        hasErrors = 1;
    }
    if(hasErrors == 0 && rptField2 > 0 && (rptField2 == rptField3 || rptField2 == rptField4)) {
        $('.rtpField').addClass("has-error");
        $('#rptFieldMsg').addClass("has-error");
        $('#rptFieldMsg').html('All reportable fields must be different.<br />');
        hasErrors = 1;
    }
    if(hasErrors == 0 && rptField3 > 0 && (rptField3 == rptField4)) {
        $('.rtpField').addClass("has-error");
        $('#rptFieldMsg').addClass("has-error");
        $('#rptFieldMsg').html('All reportable fields must be different.<br />');
        hasErrors = 1;
    }
    
    return hasErrors;
}


