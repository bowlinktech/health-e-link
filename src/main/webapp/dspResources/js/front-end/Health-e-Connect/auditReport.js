/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");
        
      //This function will handle sending rejected transactions
        $(document).on('click','.rejectMessages', function() {

           $('.alert-danger').hide();

           var idList = "";

           //Loop through all batch ids
           $('input[type=checkbox]').each(function() {
               if(this.checked) {
            	   idList += (idList ==="" ? this.value : "," + this.value);
                }
           });

           if(idList === "") {
               $('.alert-danger').html("At least one transaction must be selected to reject!");
               $('.alert-danger').show();
           }
           else {
               $('#idList').val(idList);
               $('input[name="batchOption"]').val("rejectMessages"); 
               $('#batchOptions').submit();
           }
        });
        
        //click this will release batch
        $('.releaseBatch').click(function() {
        	$('input[name="batchOption"]').val("releaseBatch");       	
        	$('#batchOptions').submit();
        });
        
        //click this will cancel batch
        $('.cancelBatch').click(function() {
        	$('input[name="batchOption"]').val("cancelBatch");
        	$('#batchOptions').submit();
        });    
        
        //click this will reset batch
        $('.resetBatch').click(function() {
        	$('input[name="batchOption"]').val("resetBatch");
        	$('#batchOptions').submit();
        });  
        
        
        //this function will submit the transactionInId to the ERG form for edit
        $(document).on('click', '.viewLink', function() {
        	$('input[name="transactionInId"]').val($(this).attr('rel'));
        	$('#transAction').submit();
        });

        
        //This function will launch the new file upload overlay with a blank screen
        $(document).on('click', '.uploadFile', function() {
            $.ajax({
                url: 'fileUploadForm',
                type: "GET",
                success: function(data) {
                    $("#uploadFile").html(data);
                }
            });
        });

        //Function to submit file upload from the modal window.
        $(document).on('click', '#submitButton', function(event) {
            var errorFound = 0;

            //Remove any error message classes
            $('#configIdsDiv').removeClass("has-error");
            $('#configIdsMsg').removeClass("has-error");
            $('#configIdsMsg').html('');
            $('#uploadedFileDiv').removeClass("has-error");
            $('#uploadedFileMsg').removeClass("has-error");
            $('#uploadedFileMsg').html('');

            //Make sure at least one message type is selected
            if ($('#configIds').val() == '' || $('#configIds').val() == null) {
                $('#configIdsDiv').addClass("has-error");
                $('#configIdsMsg').addClass("has-error");
                $('#configIdsMsg').html('A message type must be selected.');
                errorFound = 1;
            }

            //Make sure a file is uploaded
            if ($('#uploadedFile').val() == '') {
                $('#uploadedFileDiv').addClass("has-error");
                $('#uploadedFileMsg').addClass("has-error");
                $('#uploadedFileMsg').html('The file is a required field.');
                errorFound = 1;
            }
            //Make sure the file is not an invalid format
            //(.jpg, .gif, .jpeg)
            if ($('#uploadedFile').val() != '' && ($('#uploadedFile').val().indexOf('.jpg') != -1 &&
                    $('#uploadedFile').val().indexOf('.jpeg') != -1 &&
                    $('#uploadedFile').val().indexOf('.gif') != -1 &&
                    $('#uploadedFile').val().indexOf('.pdf') != -1)) {

                $('#uploadedFileDiv').addClass("has-error");
                $('#uploadedFileMsg').addClass("has-error");
                $('#uploadedFileMsg').html('The uploaded file cannot be an image.');
                errorFound = 1;

            }

            if (errorFound == 1) {
                event.preventDefault();
                return false;
            }

            $('#fileUploadForm').submit();

        });

        //This function will launch the status detail overlay with the selected
        //status
        $(document).on('click', '.viewStatus', function() {
            $.ajax({
                url: '/Health-e-Web/viewStatus' + $(this).attr('rel'),
                type: "GET",
                success: function(data) {
                    $("#statusModal").html(data);
                }
            });
        });
    });
});








