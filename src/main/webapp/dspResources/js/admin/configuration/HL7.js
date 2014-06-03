

require(['./main'], function() {
    require(['jquery'], function($) {

        $("input:text,form").attr("autocomplete", "off");

        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }


        $('#saveDetails').click(function(event) {
            var hasErrors = 0;
            hasErrors = checkform();
            if (hasErrors == 0) {
                $("#HL7Details").submit();
            }

        });

        $('#addNewSegment').click(function(event) {
            var hl7Id = $('#hl7Id').val();
            var lastSegPos = $('.segmentPos:last').val();
            var nextPos = 1;

            if (lastSegPos > 0) {
                nextPos = eval(eval(lastSegPos * 1) + 1);
            }

            $.ajax({
                url: 'newHL7Segment',
                type: "GET",
                data: {'hl7Id': hl7Id, 'nextPos': nextPos},
                success: function(data) {
                    $("#newSegmentModal").html(data);
                }
            });

        });

        //Function to submit the new HL7 Segment
        $(document).on('click', '#submitSegmentButton', function(event) {
            var errorFound = 0;

            //Remove any error message classes
            $('#segmentNameDiv').removeClass("has-error");
            $('#segmentNameMsg').removeClass("has-error");
            $('#segmentNameMsg').html('');

            //Make sure a name is added
            if ($('#newsegmentName').val() == '') {
                $('#segmentNameDiv').addClass("has-error");
                $('#segmentNameMsg').addClass("has-error");
                $('#segmentNameMsg').html('The segment name is a required field.');
                errorFound = 1;
            }


            if (errorFound == 1) {
                event.preventDefault();
                return false;
            }

            $('#hl7SegmentForm').attr('action', 'saveHL7Segment');
            $('#hl7SegmentForm').submit();

        });

        //Function to show the new segment element modal
        $('.addNewElement').click(function() {
            var hl7Id = $(this).attr("rel");
            var segmentId = $(this).attr("rel2");

            var lastDspPos = $('.displayPos_' + segmentId + ':last').val();
            var nextPos = 1;

            if (lastDspPos > 0) {
                nextPos = eval(eval(lastDspPos * 1) + 1);
            }

            $.ajax({
                url: 'newHL7Element',
                type: "GET",
                data: {'hl7Id': hl7Id, 'segmentId': segmentId, 'nextPos': nextPos},
                success: function(data) {
                    $("#newSegmentElement").html(data);
                }
            });

        });

        //Function to submit the new HL7 Segment Element
        $(document).on('click', '#submitElementButton', function(event) {
            var errorFound = 0;

            //Remove any error message classes
            $('#elementNameDiv').removeClass("has-error");
            $('#elementNameMsg').removeClass("has-error");
            $('#elementNameMsg').html('');

            //Make sure a name is added
            if ($('#newelementName').val() == '') {
                $('#elementNameDiv').addClass("has-error");
                $('#elementNameMsg').addClass("has-error");
                $('#elementNameMsg').html('The element name is a required field.');
                errorFound = 1;
            }


            if (errorFound == 1) {
                event.preventDefault();
                return false;
            }

            $('#hl7ElementForm').attr('action', 'saveHL7Element');
            $('#hl7ElementForm').submit();

        });


        //Function to show the new element component modal
        $('.addNewComponent').click(function() {
            var elementId = $(this).attr("rel");

            var lastDspPos = $('.displayPos_' + elementId + ':last').val();
            var nextPos = 1;

            if (lastDspPos > 0) {
                nextPos = eval(eval(lastDspPos * 1) + 1);
            }

            $.ajax({
                url: 'newHL7Component',
                type: "GET",
                data: {'elementId': elementId, 'nextPos': nextPos},
                success: function(data) {
                    $("#newSegmentElement").html(data);
                }
            });

        });

        //Function to submit the new HL7 Element Component
        $(document).on('click', '#submitComponentButton', function(event) {
            var errorFound = 0;

            //Remove any error message classes
            $('#fieldValueDiv').removeClass("has-error");
            $('#fieldValueMsg').removeClass("has-error");
            $('#fieldValueMsg').html('');

            //Make sure a name is added
            if ($('#newFieldValue').val() == '') {
                $('#fieldValueDiv').addClass("has-error");
                $('#fieldValueMsg').addClass("has-error");
                $('#fieldValueMsg').html('The field value is a required field.');
                errorFound = 1;
            }


            if (errorFound == 1) {
                event.preventDefault();
                return false;
            }

            $('#hl7ComponentForm').attr('action', 'saveHL7Component');
            $('#hl7ComponentForm').submit();

        });

        //Function to select an HL7 Spec
        $('#HL7Spec').change(function() {

            if ($('#HL7Spec option:selected').val() == "") {
                $('#HL7SpecDiv').addClass("has-error");
                $('#HL7SpecMsg').addClass("has-error");
                $('#HL7SpecMsg').html('An HL7 Spec is required.');
            }
            else {
                var confirmed = confirm("Are you sure you want to choose the HL7 Spec: " + $('#HL7Spec option:selected').text());

                if (confirmed) {
                    $('body').overlay({
                        glyphicon: 'floppy-disk',
                        message: 'Loading...'
                    });
                    
                    $.ajax({
                        url: 'loadHL7Spec',
                        type: "POST",
                        data: {'configId': $(this).attr('rel'), 'hl7SpecId': $('#HL7Spec option:selected').val()},
                        success: function(data) {
                            $('body').overlay('hide');
                            window.location.href = 'HL7';
                        }
                    });
                    
                }
            }
        });

    });

});

function checkform() {
    var errorFound = 0;

    //Check segment names
    $('.segmentName').each(function() {
        row = $(this).attr('rel');

        $('#segmentName_' + row).removeClass("has-error");
        $('#segmentNameMsg_' + row).html("");
        if ($(this).val() == '') {
            $('#segmentName_' + row).addClass("has-error");
            $('#segmentNameMsg_' + row).html("The segment name is required!");

            errorFound = 1;

        }
    });


    //Check element names
    $('.elementName').each(function() {
        row = $(this).attr('rel');
        $('#elementName_' + row).removeClass("has-error");
        $('#elementNameMsg_' + row).html("");
        if ($(this).val() == '') {
            $('#elementName_' + row).addClass("has-error");
            $('#elementNameMsg_' + row).html("The element name is required!");

            errorFound = 1;

        }
    });

    return errorFound;

}

