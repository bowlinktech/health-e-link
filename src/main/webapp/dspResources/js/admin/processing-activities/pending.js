/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");
        
        //Fade out the updated/created message after being displayed.
        if ($('.alert').length > 0) {
            $('.alert').delay(2000).fadeOut(1000);
        }
        
        //This function will process the selected transaction
        $(document).on('click', '.processAll', function() {
            
            var confirmed = confirm("Are you sure you want to process these transactions?");

             if (confirmed) {
                 
                $('body').overlay({
                    glyphicon : 'floppy-disk',
                    message : 'Processing...'
                }); 
                 
                $.ajax({
                  url: 'processAllTransactions',
                  data:{'orgId': $(this).attr('rel'), 'messageTypeId': 0},
                  type: "POST",
                  success: function(data) {
                     window.location.href= '/administrator/processing-activity/pending?msg=processed';
                  }
                });
             }
           
        });
        
        //This function will not process the selected transaction
        $(document).on('click', '.donotprocess', function() {
             
             var confirmed = confirm("Are you sure you do not want to process these transactions?");

             if (confirmed) {
                 
                $('body').overlay({
                    glyphicon : 'floppy-disk',
                    message : 'Canceling...'
                }); 
                 
                $.ajax({
                  url: 'donotprocessAllTransactions',
                  data:{'orgId': $(this).attr('rel'), 'messageTypeId': 0},
                  type: "POST",
                  success: function(data) {
                     window.location.href= '/administrator/processing-activity/pending?msg=notprocessed';
                  }
              });
            
             }
           
        });
        
        //This function will handle clicking the "View" link for each target org in the list
        $(document).on('click', '.viewLink', function() {
           var orgId = $(this).attr('rel');
           
           $('#orgId').val(orgId);
           $('#showMessageTypes').submit();
        });
        
   });
});
