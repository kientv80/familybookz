function signout() {
	$.ajax({
      url: '/acc',
      type: 'POST',
      dataType: 'json',
      data: {
        action: 'signout'
      },
      complete: function(xhr, textStatus) {
        //called when complete
      },
      success: function(data, textStatus, xhr) {
        //called when successful
        window.location = "/login";
      },
      error: function(xhr, textStatus, errorThrown) {
        //called when there is an error
        //alert("Có lỗi xảy ra! Vui lòng kiểm tra lại kết nối!");
      }
    });
}