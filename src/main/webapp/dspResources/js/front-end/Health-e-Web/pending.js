/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    $("input:text,form").attr("autocomplete", "off");
    
    //Fade out the alert message after being displayed.
    if ($('.alert').length > 0) {
        $('.alert').delay(2000).fadeOut(1000);
    }
});

$(function() {
    
    $('.viewLink').click(function() {
       var configId = $(this).attr('rel2');
       var transactionId = $(this).attr('rel');
       
        $('#configId').val(configId);
        $('#transactionId').val(transactionId);
        $('#viewMessageForm').submit();
       
    });
    
});