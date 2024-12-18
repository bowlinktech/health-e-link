<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="date" class="java.util.Date" />

<div class="modal fade" id="settingsModal" role="dialog" tabindex="-1" aria-labeledby="Settings" aria-hidden="true" aria-describedby="Settings"></div>

<footer class="footer">
    <div class="container">
        <nav>
            <ul class="nav-inline">
                <li><a href="<c:url value='/'/>" title="Home">Home</a></li>
                <li><a href="<c:url value='/about'/>" title="About">About</a></li>
                <li><a href="<c:url value='/contact'/>" title="Contact">Contact Us</a></li>
                <li><a href="<c:url value='/privacy'/>" title="Contact">Privacy</a></li>
                <c:if test="${not empty pageContext.request.userPrincipal.name}"><li><a href="<c:url value='/profile'/>" title="My Account">My Account</a></li></c:if>
                <c:if test="${not empty pageContext.request.userPrincipal.name}"><li><a href="#settingsModal" id="settings" data-toggle="modal" title="Account Settings" class="settings">Account Settings</a></li></c:if>
            </ul>
        </nav>

            <p class="vcard">
                <span class="fn">BOWlink Technologies Inc.</span>
            </p>
        </p>
    </div>
</footer>

<script>

    require(['./main'], function() {
        require(['jquery'], function($) {

            $("input:text,form").attr("autocomplete", "off");

            //This function will open up the modal displaying the note for the
            //selected bp entry
            $(document).on('click', '#saveEmail', function() {

                $('.error').hide();
                $('.success').hide();

                var validEmail = validateEmail($('#emailAddress').val());
                var unsubscribe = false;
                
                if($('#unsubscribe').prop('checked') == true) {
                    unsubscribe = true;
                }
                
                if (validEmail == true) {
                    $.ajax({
                        url: 'emailSignUp.do',
                        data: {'emailAddress': $('#emailAddress').val(), 'unsubscribe': unsubscribe},
                        type: "POST",
                        success: function(data) {
                            if (data == 1) {
                                $('.success').show();
                                $('.error').hide();
                                $('#emailAddress').val('');
                                $('.alert').delay(2000).fadeOut(1000);
                            }
                            else if(data == 2) {
                                $('#errMsg').html("The email address is already registered.");
                                $('.error').show();
                                $('.alert').delay(2000).fadeOut(1000);
                            }
                            else {
                                $('#successMsg').html("The email address has been removed from our list.");
                                $('.success').show();
                                $('.alert').delay(2000).fadeOut(1000);
                            }
                        }
                    });
                }
                else {
                    $('.error').show();
                    $('.alert').delay(2000).fadeOut(1000);
                }
            });
        });
    });

    function validateEmail($email) {
        var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
        if ($email == '' || !emailReg.test($email)) {
            return false;
        }
        else {
            return true;
        }
    }
</script>