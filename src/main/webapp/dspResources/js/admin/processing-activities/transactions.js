/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



require(['./main'], function () {
    require(['jquery'], function($) {
        
        $("input:text,form").attr("autocomplete", "off");
        
        //This function will launch the status detail overlay with the selected
        //status
        $(document).on('click', '.viewStatus', function() {
            $.ajax({
                url: '../viewStatus' + $(this).attr('rel'),
                type: "GET",
                success: function(data) {
                    $("#statusModal").html(data);
                }
            });
        });
        
    });
});