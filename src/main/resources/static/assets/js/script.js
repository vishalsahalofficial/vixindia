$(document).ready(function() {
  var token; // declare token at the top level

  $("#verify-button").click(function(event) {
    event.preventDefault(); // prevent form from submitting normally

    // get mobile number value
    var mobileNumber = $("#mobile_number").val();

    // call API endpoint to get OTP
    $.ajax({
      type: "POST",
      url: "http://localhost:8080/login/generate/otp",
      data: { mobile_number: mobileNumber },
      success: function(response) {
        alert("sucess");
        // handle success response from API
        console.log(response);
        $('#mobile_number').prop('disabled',true).css('opacity',0.5);
        $('#verify-button').prop('disabled',true).css('opacity',0.5);
        $("#otp-field").show();

        // assign token value to the top-level variable
        token = response.result.token;
      },
      error: function(error) {
        // handle error response from API
      }
    });
  });

  $("#login-button").click(function(event) {
    event.preventDefault(); // prevent form from submitting normally

    // get mobile and otp number value
    var mobileNumber = $("#mobile_number").val();
    var otp = $("#otp_input").val();
    alert(token);
    $.ajax({
      type: "POST",
      url: "http://localhost:8080/vixindia/verify/otp",
      headers: {
        "token": token
      },
      data: { mobile_number: mobileNumber,
              otp: otp},
      success: function(response) {
        // handle success response from API
        console.log(response);
        alert("Login Sucess");
        window.location.href = "dashboard.html";
      },
      error: function(error) {
        // handle error response from API
      }
    });
    
  });

});
